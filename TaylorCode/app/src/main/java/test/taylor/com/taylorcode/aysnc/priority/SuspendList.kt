package test.taylor.com.taylorcode.aysnc.priority

import android.util.Log
import androidx.collection.ArrayMap
import kotlinx.coroutines.*

class SuspendList private constructor() {
    private val TAG = "SuspendList"

    companion object {
        var map = ArrayMap<String, SuspendList>()
        fun of(key: String, init: SuspendList.() -> Unit): SuspendList = (map[key] ?: let {
            val p = SuspendList()
            map[key] = p
            p
        }).apply(init)
    }

    private var head: Item = emptyItem()

    fun add(item: Item) {
        head.findItem(item.priority).addNext(item)
    }

    fun observe() = GlobalScope.launch(Dispatchers.Main) {
        Log.d(TAG, "observe: ")
        var p: Item? = head.next
        while (p != null) {
            p.resumeAction?.invoke(p.deferred?.await())
            p.job?.cancel()
            p = p.next
        }
    }

    private fun Item.findItem(priority: Int): Item {
        var p: Item? = this
        var next: Item? = p?.next
        while (next != null) {
            if (priority in p!!.priority until next.priority) {
                break
            }
            p = p.next
            next = p?.next
        }
        return p!!
    }

    class Item {

        companion object {
            const val PRIORITY_DEFAULT = 0
        }

        var suspendAction: (suspend () -> Any?)? = null
        var resumeAction: ((Any?) -> Unit)? = null
        var deferred: Deferred<*>? = null
        var timeout: Long = -1
        var priority: Int = PRIORITY_DEFAULT

        internal var job: Job? = null
        internal var next: Item? = null
        internal var pre: Item? = null

        internal fun addNext(item: Item) {
            next?.let {
                it.pre = item
                item.next = it
                item.pre = this
                this.next = item
            } ?: also {
                this.next = item
                item.pre = this
                item.next = null
            }
        }

        internal fun addPre(item: Item) {
            pre?.let {
                it.next = item
                item.pre = it
                item.next = this
                this.pre = item
            } ?: also {
                item.next = this
                item.pre = null
                this.pre = item
            }
        }
    }
}

fun SuspendList.Item(init: SuspendList.Item.() -> Unit): SuspendList.Item =
        SuspendList.Item().apply {
            init()
            job = GlobalScope.launch {
                deferred = async {
                    if (timeout > 0) {
                        withTimeoutOrNull(timeout) { suspendAction?.invoke() }
                    } else {
                        suspendAction?.invoke()
                    }
                }
            }
        }.also { add(it) }

fun emptyItem(): SuspendList.Item = SuspendList.Item().apply { priority = -1 }