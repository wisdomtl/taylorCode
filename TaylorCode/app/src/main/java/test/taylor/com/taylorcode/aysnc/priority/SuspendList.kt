package test.taylor.com.taylorcode.aysnc.priority

import androidx.collection.ArrayMap
import kotlinx.coroutines.*

class SuspendList private constructor() {

    companion object {
        var map = ArrayMap<String, SuspendList>()
        fun of(key: String): SuspendList = map[key] ?: let {
            val p = SuspendList()
            map[key] = p
            p
        }
    }

    private var head: Item = emptySuspendItem()

    fun add(item: Item) {
        head.findItem(item.priority).addNext(item)
    }

    fun observe() = GlobalScope.launch(Dispatchers.Main) {
        var p: Item? = head.next
        while (p != null) {
            p.deferred?.await()
            p.resumeAction?.invoke()
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
            set(value) {
                field = value
                value?.let {
                    GlobalScope.launch { deferred = async { it.invoke() } }
                }
            }
        var resumeAction: (() -> Unit)? = null
        var deferred: Deferred<*>? = null
        var priority: Int = PRIORITY_DEFAULT

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

fun suspendItem(init: SuspendList.Item.() -> Unit): SuspendList.Item = SuspendList.Item().apply(init)

fun emptySuspendItem(): SuspendList.Item = SuspendList.Item().apply { priority = -1 }