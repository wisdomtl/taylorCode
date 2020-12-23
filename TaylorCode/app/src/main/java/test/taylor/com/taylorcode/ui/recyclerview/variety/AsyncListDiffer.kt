package test.taylor.com.taylorcode.ui.recyclerview.variety

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import kotlinx.coroutines.*

/**
 * Helper for computing difference between two list via [DiffUtil] in a background thread
 */
class AsyncListDiffer(
    /**
     * [ListUpdateCallback] which diff result dispatched to
     */
    var listUpdateCallback: ListUpdateCallback,
    /**
     * a [CoroutineDispatcher] defined by yourself
     * common usage is to turn existing [Executor] into [CoroutineDispatcher] by [asCoroutineDispatcher]
     */
    dispatcher: CoroutineDispatcher
) : CoroutineScope by CoroutineScope(SupervisorJob() + dispatcher) {
    /**
     * the result list after last diffing
     */
    var oldList = listOf<Any>()

    /**
     * an Int to auto-increase by the times of [submitList] invocation
     */
    private var maxSubmitGeneration: Int = 0

    /**
     * submit a new list and begin diffing with the old list in a background thread,
     * when the diff is completed, the result will be dispatched to [ListUpdateCallback]
     */
    fun submitList(newList: List<Any>) {
        val submitGeneration = ++maxSubmitGeneration

        // fast return: old list is empty, just add all new list
        if (this.oldList.isEmpty()) {
            oldList = newList.toList()
            listUpdateCallback.onInserted(0, newList.size)
            return
        }

        // begin diffing in a new coroutine
        launch {
            val diffResult = DiffUtil.calculateDiff(DiffCallback(oldList, newList))
            // dispatch the diff result to main thread
            withContext(Dispatchers.Main) {
                // just apply the last diffResult, discard the others
                if (submitGeneration == maxSubmitGeneration) {
                    oldList = newList.toList()
                    diffResult.dispatchUpdatesTo(listUpdateCallback)
                }
            }
        }
    }

    inner class DiffCallback(var oldList: List<Any>, var newList: List<Any>) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            // using existing hashCode() to make sure whether new and old items are one object
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            // using existing hashCode() to make sure whether new and old items have the same content
            return oldItem == newItem
        }

        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
            val oldItem = oldList[oldItemPosition] as? Diff
            val newItem = newList[newItemPosition] as? Diff
            if (oldItem == null || newItem == null) return null
            // if new and old items are the same object but have different content, call diff() to find the precise difference
            return oldItem diff newItem
        }
    }
}

/**
 * an interface should be implemented by object wanna be differentiated by [AsyncListDiffer]
 */
interface Diff {
    /**
     * diff one object to [other] object
     * @return the detail of difference defined by yourself
     */
    infix fun diff(other: Any?): Any?
}