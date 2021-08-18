package test.taylor.com.taylorcode.ui.custom_view.bullet_screen

import androidx.recyclerview.widget.RecyclerView
import test.taylor.com.taylorcode.kotlin.dp
import test.taylor.com.taylorcode.kotlin.horizontal

class LaneLayoutManager : RecyclerView.LayoutManager() {
    private val LAYOUT_FINISH = -1

    private val PRELOAD_COLUMN_COUNT = 2 // preload 2 columns in advance
    private var currentIndex = 0 // todo when to update
    private var lastColumnOffsets = mutableListOf<ColumnOffset>()

    /**
     * the vertical gap of comment view
     */
    var verticalGap = 5
        get() = field.dp

    /**
     * the horizontal gap of comment view
     */
    var horizontalGap = 3
        get() = field.dp

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT)
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        repeat(PRELOAD_COLUMN_COUNT) { fillLanes(recycler) }
        lastColumnOffsets.forEach { it.reset() }
    }

    override fun scrollHorizontallyBy(dx: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        return super.scrollHorizontallyBy(dx, recycler, state)
    }

    override fun canScrollHorizontally(): Boolean {
        return true
    }

    /**
     * fill children into [RecyclerView]
     */
    private fun fillLanes(recycler: RecyclerView.Recycler?) {
        val totalSpace = height - paddingTop - paddingBottom
        var remainSpace = totalSpace
        while (hasMoreLane(remainSpace)) {
            val consumeSpace = layoutView(recycler, totalSpace)
            if (consumeSpace == LAYOUT_FINISH) break
            remainSpace -= consumeSpace
        }
    }

    /**
     * whether continue to layout child
     */
    private fun hasMoreLane(remainSpace: Int) = remainSpace > 0 && currentIndex in 0 until itemCount

    /**
     * layout a single view
     */
    private fun layoutView(recycler: RecyclerView.Recycler?, totalSpace: Int): Int {
        val view = recycler?.getViewForPosition(currentIndex)
        view ?: return LAYOUT_FINISH
        addView(view)
        measureChildWithMargins(view, 0, 0)

        // layout even
        val laneCount = (totalSpace + verticalGap) / (view.measuredHeight + verticalGap)
        val index = currentIndex % laneCount
        val lastOffset = lastColumnOffsets.getOrElse(index) { emptyLayoutOffset() }
        val left = lastOffset.right + horizontalGap
        val top = index * (view.measuredHeight + verticalGap)
        val right = left + view.measuredWidth
        val bottom = top + view.measuredHeight
        layoutDecorated(view, left, top, right, bottom)
        updateOffsets(index, lastOffset, right)

        val verticalMargin = (view.layoutParams as? RecyclerView.LayoutParams)?.let { it.topMargin + it.bottomMargin } ?: 0
        currentIndex++
        return getDecoratedMeasuredHeight(view) + verticalMargin
    }

    /**
     * keep the last view's right in list prepared for layout follow-up views
     */
    private fun updateOffsets(index: Int, lastOffset: ColumnOffset, newRight: Int) {
        lastOffset.right = newRight
        if (!lastColumnOffsets.contains(lastOffset)) {
            lastColumnOffsets.add(index, lastOffset)
        }
    }

    /**
     * column is the vertical space across lanes, the end of column is the left of follow-up views in the lane
     */
    data class ColumnOffset(var right: Int) {
        fun reset() {
            right = 0
        }
    }

    private fun emptyLayoutOffset() = ColumnOffset(-horizontalGap+width)
}