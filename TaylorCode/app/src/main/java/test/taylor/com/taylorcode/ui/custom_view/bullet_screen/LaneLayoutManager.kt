package test.taylor.com.taylorcode.ui.custom_view.bullet_screen

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.channels.consumesAll
import test.taylor.com.taylorcode.kotlin.dp

class LaneLayoutManager : RecyclerView.LayoutManager() {
    private val LAYOUT_FINISH = -1
    private var laneCount = 0 //todo when to assign
    private var currentIndex = 0 // todo when to update

    /**
     * the vertical gap of comment view
     */
    var gap = 5
        get() = field.dp

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT)
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        fill(recycler)
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
    private fun fill(recycler: RecyclerView.Recycler?) {
        var totalSpace = height - paddingTop - paddingBottom
        var remainSpace = totalSpace
        while (goOnLayout(remainSpace)) {
            val consumeSpace = layoutView(recycler)
            if (consumeSpace == LAYOUT_FINISH) break
            remainSpace -= consumeSpace
        }
    }

    /**
     * whether continue to layout child
     */
    private fun goOnLayout(remainSpace: Int) = remainSpace > 0 && currentIndex in 0 until itemCount

    /**
     * layout a single view
     */
    private fun layoutView(recycler: RecyclerView.Recycler?): Int {
        val view = recycler?.getViewForPosition(currentIndex)
        view ?: return LAYOUT_FINISH

        addView(view)
        measureChildWithMargins(view, 0, 0)
        var totalSpace = height - paddingTop - paddingBottom
        val laneCount = (totalSpace + gap) / (view.measuredHeight + gap)
        val index = currentIndex % laneCount
        val left = 0
        val top = index * (view.measuredHeight + gap)
        val right = left + view.measuredWidth
        val bottom = top + view.measuredHeight
        layoutDecorated(view, left, top, right, bottom)
        val verticalMargin = (view.layoutParams as? RecyclerView.LayoutParams)?.let { it.topMargin + it.bottomMargin } ?: 0
        currentIndex++
        return getDecoratedMeasuredHeight(view) + verticalMargin
    }
}