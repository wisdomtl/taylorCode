package test.taylor.com.taylorcode.ui.pagers.paging

import android.annotation.SuppressLint
import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import test.taylor.com.taylorcode.ui.recyclerview.variety.Diff

class ItemCallback2<T>: DiffUtil.ItemCallback<T>(){
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        val oldDiff = oldItem as? Diff
        val newDiff = newItem as? Diff
        Log.d("ttaylor", "ItemCallback2.areItemsTheSame(): ")
        return if (oldDiff == null || newDiff == null) oldItem.hashCode() == newItem.hashCode()
        else oldDiff sameAs newItem
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        val oldDiff = oldItem as? Diff
        val newDiff = newItem as? Diff
        Log.d("ttaylor", "ItemCallback2.areContentsTheSame(): ")
        return if (oldDiff == null || newDiff == null) oldItem == newItem
        else oldDiff contentSameAs newDiff
    }

    override fun getChangePayload(oldItem:  T, newItem:  T): Any? {
        val oldItem = oldItem as? Diff
        val newItem = newItem as? Diff
        Log.d("ttaylor", "ItemCallback2.getChangePayload(): ")
        if (oldItem == null || newItem == null) return null
        // if new and old items are the same object but have different content, call diff() to find the precise difference
        return oldItem diff newItem
    }

}