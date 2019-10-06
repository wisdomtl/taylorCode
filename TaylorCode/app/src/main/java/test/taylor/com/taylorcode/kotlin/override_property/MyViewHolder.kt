package test.taylor.com.taylorcode.kotlin.override_property

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.my_viewholder.view.*

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(myBean: MyBean?, colorBean: ColorBean?) {
        itemView.apply {
            colorBean?.run { myBackground.setBackgroundColor(Color.parseColor(color)) }
            tvMyViewHolder.text = myBean?.name ?: "no name"
        }
    }
}