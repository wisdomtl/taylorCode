package test.taylor.com.taylorcode.ui.pagers

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import test.taylor.com.taylorcode.kotlin.find

class ViewPagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(url: String) {
        itemView.apply {
            Glide.with(context).load(url).into(find("iv"))
        }
    }
}