package test.taylor.com.taylorcode.ui.recyclerview.anim

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

class ItemTransformerAdapter(var recyclerView: RecyclerView) : RecyclerView.OnScrollListener() {

    var itemTransformer: ItemTransformer? = null
    var overlap = 0
    private var layoutManager = recyclerView.layoutManager as LinearLayoutManager
    private var scrollState: ScrollState = ScrollState()

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (dx == 0 && dy == 0) return
        scrollState.index = layoutManager.findFirstVisibleItemPosition()
        if (scrollState.index == RecyclerView.NO_POSITION) {
            scrollState.reset()
            return
        }
        var firstItem = layoutManager.findViewByPosition(scrollState.index)
        if (firstItem == null) {
            scrollState.reset()
            return
        }

        val lastItemIndex = layoutManager.findLastVisibleItemPosition()
        val lastItem = layoutManager.findViewByPosition(lastItemIndex) ?: return

        val firstWidth = layoutManager.getDecoratedMeasuredWidth(firstItem) - overlap

        val position = if (recyclerView.layoutDirection == View.LAYOUT_DIRECTION_LTR) {
            firstItem.left - layoutManager.getLeftDecorationWidth(firstItem) - recyclerView.paddingLeft
        } else {
            firstItem.right + layoutManager.getRightDecorationWidth(firstItem) + recyclerView.paddingRight - recyclerView.width
        }

        scrollState.offsetPx = abs(position)
        scrollState.offset = when {
            firstWidth == 0 -> 0f
            position == 0 -> 1f
            else -> scrollState.offsetPx.toFloat() / firstWidth
        }

        firstItem = if (position == 0) null else firstItem

        itemTransformer?.onItemTransform(firstItem, lastItem, scrollState.offset)
    }

    class ScrollState(
        var index: Int = RecyclerView.NO_POSITION,
        var offset: Float = 0f,
        var offsetPx: Int = 0
    ) {
        fun reset() {
            index = RecyclerView.NO_POSITION
            offset = 0f
            offsetPx = 0
        }
    }
}

interface ItemTransformer {
    fun onItemTransform(firstItem: View?, lastItem: View, offset: Float)
}