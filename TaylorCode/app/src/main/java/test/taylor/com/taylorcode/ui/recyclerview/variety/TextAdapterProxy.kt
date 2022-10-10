package test.taylor.com.taylorcode.ui.recyclerview.variety

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import test.taylor.com.taylorcode.kotlin.*

class TextAdapterProxy : VarietyAdapter.Proxy<Text, TextViewHolder>() {
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

data class Text( var text: String ) : Diff {
    override fun diff(other: Any?): Any? {
        return null
    }

    override fun sameAs(other: Any?): Boolean {
        return if (other !is Text) false
        else this.text == other.text
    }

    override fun contentSameAs(other: Any?): Boolean {
        return if (other !is Text) false
        else this.text == other.text
    }
}

data class Text2(val id:Int, val text: String ) : Diff {
    override fun diff(other: Any?): Any? {
        return null
    }

    override fun sameAs(other: Any?): Boolean {
        return if (other !is Text2) false
        else this.id == other.id
    }

    override fun contentSameAs(other: Any?): Boolean {
        return if (other !is Text2) false
        else this.text == other.text
    }
}

class TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvName = itemView.find<TextView>("tvName")
}