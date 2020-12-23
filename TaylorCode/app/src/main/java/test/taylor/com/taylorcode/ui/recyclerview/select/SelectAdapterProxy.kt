package test.taylor.com.taylorcode.ui.recyclerview.select

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import test.taylor.com.taylorcode.R
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.ui.custom_view.selector.kt.Selector
import test.taylor.com.taylorcode.ui.custom_view.selector.kt.SelectorGroup
import test.taylor.com.taylorcode.ui.recyclerview.variety.VarietyAdapter
import test.taylor.com.taylorcode.util.value

class SelectAdapterProxy : VarietyAdapter.Proxy<SelectionBeanWrapper, SelectionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = parent.context.run {
            ConstraintLayout {
                layout_width = match_parent
                layout_height = 40

                TextView {
                    layout_id = "name"
                    layout_width = wrap_content
                    layout_height = wrap_content
                    textSize = 20f
                    center_vertical = true
                    maxLines = 1
                    maxWidth = 100
                    start_toStartOf = parent_id
                }

                TextView {
                    layout_id = "tvNumber"
                    layout_width = wrap_content
                    layout_height = wrap_content
                    text = "100"
                    textSize = 15f
                    end_toStartOf = "s"
                    margin_end = 10
                }

                Selector {
                    layout_id = "s"
                    layout_width = 40
                    layout_height = 40
                    end_toEndOf = parent_id
                    center_vertical = true
                    contentView = ImageView(autoAdd = false) {
                        layout_id = "ivsel"
                        layout_width = 40
                        layout_height = 40
                        src = R.drawable.unselect
                        scaleType = scale_fit_xy
                    }

                    onSelectChange = { selector, select ->
                        selector.find<ImageView>("ivsel")?.src = if (select) R.drawable.selected else R.drawable.unselect
                    }
                }
            }
        }
        return SelectionViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SelectionViewHolder, data: SelectionBeanWrapper, index: Int, action: ((Any?) -> Unit)?) {
        holder.selector?.apply {
            visibility = if (data.showSelector) visible else gone
            group = data.selectorGroup
            tag = data.selectionBean?.name.value
        }
        holder.tvName?.text = data.selectionBean?.name
    }
}

class SelectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val selector = itemView.find<Selector>("s")
    val tvName = itemView.find<TextView>("name")
}

data class SelectionBean(
    var name: String
)

data class SelectionBeanWrapper(
    var selectionBean: SelectionBean? = null,
    var selectorGroup: SelectorGroup? = null,
    var showSelector: Boolean = true
)