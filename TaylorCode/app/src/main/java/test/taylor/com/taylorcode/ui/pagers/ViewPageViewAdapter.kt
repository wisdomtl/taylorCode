package test.taylor.com.taylorcode.ui.pagers

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import test.taylor.com.taylorcode.kotlin.*

class ViewPageViewAdapter : RecyclerView.Adapter<ViewPagerViewHolder>() {

    var data: List<String> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val itemView = parent.context.run {
            ImageView {
                layout_id = "iv"
                layout_width = match_parent
                layout_height = match_parent
                scaleType = scale_fix_xy
            }
        }
        return ViewPagerViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return when (data.size) {
            0 -> 0
            1 -> 1
            else -> Int.MAX_VALUE
        }

    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        data.getOrNull(position % data.size)?.let { holder.bind(it) }
    }
}