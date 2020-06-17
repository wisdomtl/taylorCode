package test.taylor.com.taylorcode.aysnc.priority

import androidx.collection.ArrayMap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SuspendList {

    companion object {
        var map = ArrayMap<String, SuspendList>()
        fun of(key: String): SuspendList = map[key] ?: let {
            val p = SuspendList()
            map[key] = p
            p
        }
    }
    private constructor()

    private var head: SuspendItem = emptySuspendItem()

    fun add(item: SuspendItem) {
        head.findItem(item.priority).addNext(item)
    }

    fun observe() = GlobalScope.launch(Dispatchers.Main) {
        var p: SuspendItem? = head.next
        while (p != null) {
            p.deferred?.await()
            p.resumeAction?.invoke()
            p = p.next
        }
    }

    private fun SuspendItem.findItem(priority: Int): SuspendItem {
        var p: SuspendItem? = this
        var next: SuspendItem? = p?.next
        while (next != null) {
            if (priority in p!!.priority until next.priority) {
                break
            }
            p = p.next
            next = p?.next
        }
        return p!!
    }
}

fun suspendItem(init: SuspendItem.() -> Unit): SuspendItem = SuspendItem().apply(init)
