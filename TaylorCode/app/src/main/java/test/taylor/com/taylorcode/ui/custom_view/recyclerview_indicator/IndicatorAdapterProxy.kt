package test.taylor.com.taylorcode.ui.custom_view.recyclerview_indicator

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.ui.recyclerview.variety.VarietyAdapter

class IndicatorAdapterProxy : VarietyAdapter.Proxy<IndicatorBean, IndicatorViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = parent.context.run {
            TextView {
                layout_id = "tvFansDefalutKonledge"
                layout_width = 100
                layout_height = 50
                textSize = 16f
                gravity = gravity_center
                background_color = "#ff00ff"
                textColor = "#3F4658"
            }
        }
        return IndicatorViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: IndicatorViewHolder, data: IndicatorBean, index: Int, action: ((Any?) -> Unit)?) {
        holder.tv?.text = data.str.toString()
    }
}

data class IndicatorBean(var str: Int)

class IndicatorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tv = itemView.find<TextView>("tvFansDefalutKonledge")
}