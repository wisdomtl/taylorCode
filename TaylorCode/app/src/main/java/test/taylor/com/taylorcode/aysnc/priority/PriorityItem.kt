package test.taylor.com.taylorcode.aysnc.priority

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class PriorityItem {

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

    internal var next: PriorityItem? = null
    internal var pre: PriorityItem? = null

    internal fun addNext(item: PriorityItem) {
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

//    internal fun addPre(item: PriorityItem) {
//        pre?.let {
//            it.next = item
//            item.pre = it
//            item.next = this
//            this.pre = item
//        } ?: also {
//            item.next = this
//            item.pre = null
//            this.pre = item
//        }
//    }
}

fun emptyPriorityItem(): PriorityItem = PriorityItem().apply {
    priority = -1
}
