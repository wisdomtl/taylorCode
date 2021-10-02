package test.taylor.com.taylorcode.ui.recyclerview.anim

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

class OverlapLayoutManager(var overlap: Float = 0f) : RecyclerView.LayoutManager() {
    private var adapterIndex = 0
    private val LAYOUT_FINISH = -1
    private val LAYOUT_CONTINUE = 0
    private var lastEnd = 0
    private var lastWidth = 0

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT)
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        fill(recycler)
    }

    override fun canScrollHorizontally(): Boolean {
        return true
    }

    override fun scrollHorizontallyBy(dx: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        return scrollBy(dx, recycler)
    }

    private fun scrollBy(dx: Int, recycler: RecyclerView.Recycler?): Int {
        if (childCount == 0 || dx == 0) return 0
        lastEnd = getChildAt(childCount - 1)?.getEnd() ?: 0
        val absDx = abs(dx)
        if (lastEnd - absDx < width) fill(recycler)
        offsetChildrenHorizontal(-dx)
        return dx
    }

    private fun fill(recycler: RecyclerView.Recycler?) {
        while (hasSpace()) {
            val consume = layoutView(recycler)
            if (consume == LAYOUT_FINISH) break
        }
    }

    private fun layoutView(recycler: RecyclerView.Recycler?): Int {
        val view = recycler?.getViewForPosition(adapterIndex)
        view ?: return LAYOUT_FINISH
        measureChildWithMargins(view, 0, 0)

        val overlapWidth = (lastWidth * overlap).toInt()
        val left = lastEnd - overlapWidth
        val top = 0
        val right = left + getDecoratedMeasuredWidth(view)
        val bottom = top + getDecoratedMeasuredHeight(view)

        if (right > width) return LAYOUT_FINISH

        addView(view)
        layoutDecoratedWithMargins(view, left, top, right, bottom)

        adapterIndex++
        lastEnd = right
        lastWidth = getDecoratedMeasuredWidth(view)
        return LAYOUT_CONTINUE
    }

    private fun View.getEnd() = getDecoratedRight(this) + (layoutParams as RecyclerView.LayoutParams).rightMargin

    /**
     * whether there is more space to layout child
     */
    private fun hasSpace() = lastEnd < width && adapterIndex in 0 until itemCount
}