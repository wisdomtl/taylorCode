package test.taylor.com.taylorcode.ui.pagers.paging

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.ui.recyclerview.variety.Text2

class PagingAdapter:PagingDataAdapter<Text2, TextViewHolder2>(ItemCallback2()) {
    override fun onBindViewHolder(holder: TextViewHolder2, position: Int) {
        val data = getItem(position)
        holder.tvName?.text = data?.text
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder2 {
        val itemView = parent.context.run {
            TextView {
                layout_id = "tvName"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 40f
                textColor = "#00ff00"
            }
        }
        return TextViewHolder2(itemView)
    }
}

class TextViewHolder2(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvName = itemView.find<TextView>("tvName")
}