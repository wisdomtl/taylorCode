package test.taylor.com.taylorcode.aysnc.priority

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Priority {

    private var head: PriorityItem = emptyPriorityItem()

    fun add(item: PriorityItem) {
//        if (head == null) {
//            head = item
//            item.next = null
//        } else {
        head.findPos(item.priority).addNext(item)
//        }
    }

    fun observe() = GlobalScope.launch(Dispatchers.Main) {
        var p: PriorityItem? = head.next
        while (p != null) {
            p.deferred?.await()
            p.resumeAction?.invoke()
            p = p.next
        }
    }

    private fun PriorityItem.findPos(priority: Int): PriorityItem {
        var p: PriorityItem? = this
        var next: PriorityItem? = p?.next
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