package test.taylor.com.taylorcode.ui.custom_view.bullet_screen

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import test.taylor.com.taylorcode.kotlin.dp
import kotlin.math.abs

class LaneLayoutManager : RecyclerView.LayoutManager() {
    /**
     * the return value of [layoutView], it means filling view into lane should be over
     */
    private val LAYOUT_FINISH = -1

    /**
     * the index related to data in adapter
     */
    private var adapterIndex = 0

    /**
     * the min right most pixel according to RecyclerView of lane view,
     * it means that lane will drain out first when scrolling
     */
    private var minEnd = Int.MAX_VALUE

    /**
     * the lane index which will drain out first
     */
    private var firstDrainLaneIndex = 0

    /**
     * all the [Lane] in the RecyclerView
     */
    private var lanes = mutableListOf<Lane>()

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

    /**
     * define the layout params for child view in RecyclerView
     * override this is a must for customized [RecyclerView.LayoutManager]
     */
    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT)
    }

    /**
     * define how to layout child view in RecyclerView
     * override this is a must for customized [RecyclerView.LayoutManager]
     */
    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        fillLanes(recycler, lanes)
    }

    /**
     * define how to scroll views in RecyclerView
     * override this is a must for customized [RecyclerView.LayoutManager]
     */
    override fun scrollHorizontallyBy(dx: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        return scrollBy(dx, recycler)
    }

    /**
     * define whether scrolling horizontally is allowed
     * override this is a must for customized [RecyclerView.LayoutManager]
     */
    override fun canScrollHorizontally(): Boolean {
        return true
    }

    /**
     * scroll the all the [Lane]s in RecyclerView by [dx]
     */
    private fun scrollBy(dx: Int, recycler: RecyclerView.Recycler?): Int {
        if (childCount == 0 || dx == 0) return 0

        updateLanesEnd(lanes)
        val absDx = abs(dx)
        // fill new views into lanes after scrolled
        val endView = lanes.getOrNull(firstDrainLaneIndex)?.getEndView()
        if (endView != null && isVisibleByScroll(endView, absDx)) {
            fillLanes(recycler, lanes) //todo refactor: just preload the drain out lane
        }
        // recycle views in lanes after scrolled
        recycleGoneView(lanes, absDx, recycler)
        // make lane move left
        offsetChildrenHorizontal(-absDx)
        return dx
    }

    /**
     * fill children into [Lane]
     */
    private fun fillLanes(recycler: RecyclerView.Recycler?, lanes: MutableList<Lane>) {
        val totalSpace = height - paddingTop - paddingBottom
        var remainSpace = totalSpace
        while (hasMoreLane(remainSpace)) {
            val consumeSpace = layoutView(recycler, totalSpace, remainSpace, lanes)
            if (consumeSpace == LAYOUT_FINISH) break
            remainSpace -= consumeSpace
        }
        minEnd = Int.MAX_VALUE
    }

    private fun recycleGoneView(lanes: List<Lane>, dx: Int, recycler: RecyclerView.Recycler?) {
        recycler ?: return
        lanes.forEach { lane ->
            getChildAt(lane.startLayoutIndex)?.let { startView ->
                if (isGoneByScroll(startView, dx)) {
                    removeAndRecycleView(startView, recycler)
                    updateLaneIndexAfterRecycle(lanes, lane.startLayoutIndex)
                    lane.startLayoutIndex += lanes.size - 1
                }
            }
        }
    }

    /**
     * after view is recycled and remove from RecyclerView, the layout index in lane should be minus 1
     */
    private fun updateLaneIndexAfterRecycle(lanes: List<Lane>, recycleIndex: Int) {
        lanes.forEachIndexed {index, lane ->
            if (lane.startLayoutIndex > recycleIndex) {
                lane.startLayoutIndex--
            }
            if (lane.endLayoutIndex > recycleIndex){
                lane.endLayoutIndex--}
        }
    }

    /**
     * layout a single view in the one [Lane]
     */
    private fun layoutView(recycler: RecyclerView.Recycler?, totalSpace: Int, remainSpace: Int, lanes: MutableList<Lane>): Int {
        val view = recycler?.getViewForPosition(adapterIndex)
        view ?: return LAYOUT_FINISH
        measureChildWithMargins(view, 0, 0)
        val verticalMargin = (view.layoutParams as? RecyclerView.LayoutParams)?.let { it.topMargin + it.bottomMargin } ?: 0
        val consumed = getDecoratedMeasuredHeight(view) + verticalMargin + verticalGap
        if (remainSpace - consumed < 0) return LAYOUT_FINISH

        addView(view)
        // layout even
        val laneCount = (totalSpace + verticalGap) / (view.measuredHeight + verticalGap)
        val laneIndex = adapterIndex % laneCount
        val lane = lanes.getOrElse(laneIndex) { emptyLane(adapterIndex) }.apply { endLayoutIndex = childCount - 1 }
        val left = lane.end + horizontalGap
        val top = paddingTop + laneIndex * (view.measuredHeight + verticalGap)
        val right = left + view.measuredWidth
        val bottom = top + view.measuredHeight
        layoutDecorated(view, left, top, right, bottom)

        updateLane(laneIndex, right, lane, lanes)
        findFirstDrainLane(right, laneIndex)

        adapterIndex++
        return consumed
    }

    /**
     * whether [view] will be exposed in RecyclerView if scrolled [dx] to the left
     */
    private fun isVisibleByScroll(view: View, dx: Int): Boolean = getEnd(view) - dx < width

    /**
     * whether [view] will be invisible in RecyclerView if scrolled [dx] to the left
     */
    private fun isGoneByScroll(view: View, dx: Int): Boolean = getEnd(view) - dx < 0

    /**
     * update every [Lane]'s end to the newest position by next scroll starts,
     * which is the most right pixel of last view in Lane according to the RecyclerView
     */
    private fun updateLanesEnd(lanes: MutableList<Lane>) {
        lanes.forEach { lane ->
            lane.getEndView()?.let { lane.end = getEnd(it) }
        }
    }

    /**
     * get the right most pixel according to RecyclerView of [view], take decoration and margin into consideration
     */
    private fun getEnd(view: View) = getDecoratedRight(view) + (view.layoutParams as RecyclerView.LayoutParams).rightMargin

    /**
     * whether continue to layout child
     */
    private fun hasMoreLane(remainSpace: Int) = remainSpace > 0 && adapterIndex in 0 until itemCount

    /**
     * find the lane which will drain out first
     */
    private fun findFirstDrainLane(right: Int, laneIndex: Int) {
        if (right < minEnd) {
            minEnd = right
            firstDrainLaneIndex = laneIndex
        }
    }

    /**
     * keep the last view's right in list prepared for layout follow-up views
     */
    private fun updateLane(laneIndex: Int, right: Int, lane: Lane, lanes: MutableList<Lane>) {
        lane.end = right
        if (!lanes.contains(lane)) {
            lanes.add(laneIndex, lane)
        }
    }

    /**
     * return the layout index according to the adapter index
     */
    private fun getLayoutIndex(adapterIndex: Int): Int {
        val firstChildIndex = getChildAt(0)?.let { (it.layoutParams as RecyclerView.LayoutParams).viewAdapterPosition } ?: 0
        return adapterIndex - firstChildIndex
    }

    /**
     * live comment is composed of several [Lane]
     * @param end pixel offset according to the left of RecyclerView, where the layout of follow-up view in the lane should start
     * @param endLayoutIndex the  layout index of the last view in lane
     */
    data class Lane(var end: Int, var endLayoutIndex: Int, var startLayoutIndex: Int)

    /**
     * get the right most view in the lane
     */
    private fun Lane.getEndView(): View? = getChildAt(endLayoutIndex)

    /**
     * create an empty [Lane] object
     */
    private fun emptyLane(adapterIndex: Int) = Lane(-horizontalGap + width, 0, getLayoutIndex(adapterIndex))
}