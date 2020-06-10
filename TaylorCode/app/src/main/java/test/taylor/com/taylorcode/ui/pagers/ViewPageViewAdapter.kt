package test.taylor.com.taylorcode.ui.pagers

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import test.taylor.com.taylorcode.kotlin.*

class ViewPageViewAdapter: RecyclerView.Adapter<ViewPagerViewHolder>() {

    var data:List<String>? = null

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
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        data?.getOrNull(position)?.let { holder.bind(it) }
    }
}