package test.taylor.com.taylorcode.ui.custom_view.bullet_screen

import androidx.recyclerview.widget.RecyclerView

class LaneLayoutManager : RecyclerView.LayoutManager() {
    private var laneCount = 0 //todo when to assign
    private var currentIndex = 0 // todo when to update
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

    private fun fill(recycler: RecyclerView.Recycler?) {
        var remainLane = laneCount
        while (remainLane > 0) {
            layoutView(recycler)
            remainLane--
        }
    }

    private fun layoutView(recycler: RecyclerView.Recycler?) {
       val view = recycler?.getViewForPosition(currentIndex)
    }
}