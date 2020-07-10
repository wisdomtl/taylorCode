package test.taylor.com.taylorcode.kotlin.extension

import android.animation.ValueAnimator
import android.app.Activity
import android.graphics.Rect
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView

fun View.extraAnimClickListener(animator: ValueAnimator, action: (View) -> Unit) {
    setOnTouchListener { v, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> animator.start()
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> animator.reverse()
        }
        false
    }

    setOnClickListener { action(this) }
}


val View.inScreen: Boolean
    get() {
        val screenWidth = context?.resources?.displayMetrics?.widthPixels ?: 0
        val screenHeight = context?.resources?.displayMetrics?.heightPixels ?: 0
        val screenRect = Rect(0, 0, screenWidth, screenHeight)
        val array = IntArray(2)
        getLocationOnScreen(array)
        val viewRect = Rect(array[0], array[1], array[0] + width, array[1] + height)
        return screenRect.intersect(viewRect)
    }
/**
 * add listener to RecyclerView which listens it's items in and out event
 * @param inOrOut 1 means item enter RecyclerView, 0 means item leave RecyclerView, 2 means item is fully visible in RecyclerView
 */
fun RecyclerView.addItemInOutListener(listener: ((childView: View?, adapterIndex: Int, inOrOut: Int) -> Unit)?) {
    data class Item(var index: Int, var view: View?) {
        infix fun after(item: Item) = this.index > item.index
        infix fun before(item: Item) = this.index < item.index

        val isInvalid = index < 0
    }
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        private var preTopItem = Item(-1, null)
        private var preBottomItem = Item(-1, null)
        private var curTopItem = Item(0, null)
        private var curBottomItem = Item(Int.MAX_VALUE, null)

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            recyclerView.findChildViewUnder(0f, 0f)?.let { topChildView ->
                val index = recyclerView.getChildAdapterPosition(topChildView)
                curTopItem = curTopItem.copy(index, topChildView)
                if (preTopItem.isInvalid) preTopItem = curTopItem
                if (preTopItem before curTopItem) listener?.invoke(preTopItem.view, preTopItem.index, 0)
                else if (preTopItem after curTopItem) {
                    listener?.invoke(curTopItem.view, curTopItem.index, 1)
                    listener?.invoke(preTopItem.view, preTopItem.index, 2)
                }
                preTopItem = preTopItem.copy(curTopItem.index, curTopItem.view)
            }

            recyclerView.findChildViewUnder(recyclerView.width.toFloat(), recyclerView.height.toFloat())?.let { bottomChildView ->
                val index = recyclerView.getChildAdapterPosition(bottomChildView)
                curBottomItem = curBottomItem.copy(index, bottomChildView)
                if (preBottomItem.isInvalid) preBottomItem = curBottomItem
                if (preBottomItem before curBottomItem) {
                    listener?.invoke(curBottomItem.view, curBottomItem.index, 1)
                    listener?.invoke(preBottomItem.view, preBottomItem.index, 2)
                } else if (preBottomItem after curBottomItem) listener?.invoke(preBottomItem.view, preBottomItem.index, 0)
                preBottomItem = preBottomItem.copy(curBottomItem.index, curBottomItem.view)
            }
        }
    })
}
