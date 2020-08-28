package test.taylor.com.taylorcode.ui.recyclerview.variety

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.ui.recyclerview.variety.VarietyAdapter.AdapterProxy

class TextAdapterProxy : AdapterProxy<Text, TextViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = parent.context.run {
            TextView {
                layout_id = "tvName"
                layout_width = match_parent
                layout_height = wrap_content
                textSize = 40f
                gravity = gravity_center
                textColor = "#ff00ff"
            }
        }
        return TextViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TextViewHolder, data: Text, index: Int, action: ((Any?) -> Unit)?) {
        holder.tvName?.apply {
            text = data.text
            onClick = {
                action?.invoke(index)
            }
        }
    }
}

data class Text(
    var text: String
)

class TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvName = itemView.find<TextView>("tvName")
}