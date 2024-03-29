package test.taylor.com.taylorcode.ui.performance.better_performance1

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.ContextThemeWrapper
import androidx.recyclerview.widget.RecyclerView
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.ui.performance.widget.PercentLayout
import test.taylor.com.taylorcode.ui.recyclerview.variety.VarietyAdapter2

class HeaderProxy : VarietyAdapter2.ItemBuilder<Header, HeaderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val start = System.currentTimeMillis()
        lateinit var tvTitle: TextView
        lateinit var tvRank: TextView
        lateinit var tvName: TextView
        lateinit var tvCount: TextView
        val itemView = parent.context.run {
//            LinearLayout {
//                layout_width = match_parent
//                layout_height = wrap_content
//                orientation = vertical
//                padding_top = 10
//                padding_horizontal = 10
//                shape = shape {
//                    corner_radii = intArrayOf(20, 20, 20, 20, 0, 0, 0, 0)
//                    solid_color = "#ffffff"
//                }
//
//                tvTitle = TextView {
//                    layout_id = "tvTitle"
//                    layout_width = wrap_content
//                    layout_height = wrap_content
//                    textSize = 16f
//                    textColor = "#3F4658"
//                    textStyle = bold
//                    margin_bottom = 3
//                }
//
//                ConstraintLayout {
//                    layout_width = match_parent
//                    layout_height = wrap_content
//                    margin_top = 16
//
//                    tvRank = TextView {
//                        layout_id = "tvRank"
//                        layout_width = wrap_content
//                        layout_height = wrap_content
//                        textSize = 11f
//                        textColor = "#9DA4AD"
//                        start_toStartOf = parent_id
//                        center_vertical = true
//                    }
//
//                    tvName = TextView {
//                        layout_id = "tvName"
//                        layout_width = wrap_content
//                        layout_height = wrap_content
//                        textSize = 11f
//                        textColor = "#9DA4AD"
//                        align_vertical_to = "tvRank"
//                        start_toEndOf = "tvRank"
//                        margin_start = 19
//                    }
//
//                    tvCount = TextView {
//                        layout_id = "tvCount"
//                        layout_width = wrap_content
//                        layout_height = wrap_content
//                        textSize = 11f
//                        textColor = "#9DA4AD"
//                        align_vertical_to = "tvRank"
//                        end_toEndOf = parent_id
//                    }
//                }
//            }


            PercentLayout {
                layout_width = match_parent
                layout_height = 60
                shape = shape {
                    corner_radii = intArrayOf(20, 20, 20, 20, 0, 0, 0, 0)
                    solid_color = "#ffffff"
                }

                tvTitle = TextView {
                    layout_id = "tvTitle"
                    layout_width = wrap_content
                    layout_height = wrap_content
                    textSize = 16f
                    textColor = "#3F4658"
                    textStyle = bold
                    start_to_start_of_percent = parent_id
                    margin_start = 20
                    top_percent = 0.23f
                }

                tvRank = TextView {
                    layout_id = "tvRank"
                    layout_width = wrap_content
                    layout_height = wrap_content
                    textSize = 11f
                    textColor = "#9DA4AD"
                    left_percent = 0.06f
                    top_percent = 0.78f
                }

                tvName = TextView {
                    layout_id = "tvName"
                    layout_width = wrap_content
                    layout_height = wrap_content
                    textSize = 11f
                    textColor = "#9DA4AD"
                    left_percent = 0.18f
                    top_percent = 0.78f
                }

                tvCount = TextView {
                    layout_id = "tvCount"
                    layout_width = wrap_content
                    layout_height = wrap_content
                    textSize = 11f
                    textColor = "#9DA4AD"
                    end_to_end_of_percent = parent_id
                    margin_end = 20
                    top_percent = 0.78f
                }
            }
        }
        val viewHolder = HeaderViewHolder(itemView)
        Log.v("ttaylor", " header create view holder duration = ${System.currentTimeMillis() - start}")
        return viewHolder
    }

    override fun onBindViewHolder(holder: HeaderViewHolder, data: Header, index: Int, action: ((Any?) -> Unit)?) {
        val start = System.currentTimeMillis()
        holder.tvCount?.text = data.count
        holder.tvName?.text = data.name
        holder.tvRank?.text = data.rank
        holder.tvTitle?.text = data.title
        Log.v("ttaylor", " header bind view duration = ${System.currentTimeMillis() - start}")
    }
}

data class Header(
    val rank: String,
    val name: String,
    val count: String,
    val title: String
)

class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvRank = itemView.find<TextView>("tvRank")
    val tvName = itemView.find<TextView>("tvName")
    val tvCount = itemView.find<TextView>("tvCount")
    val tvTitle = itemView.find<TextView>("tvTitle")
}


class HeaderViewHolder2(
    itemView: View,
    var tvRank: TextView,
    var tvName: TextView,
    var tvCount: TextView,
    var tvTitle: TextView
) : RecyclerView.ViewHolder(itemView)


/**
 * create [PercentLayout] instance within a [ViewGroup]
 * @param style an style int value defined in xml
 * @param autoAdd whether add [PercentLayout] into [ViewGroup] automatically
 * @param init set attributes for this view in this lambda
 */
inline fun ViewGroup.PercentLayout(
    style: Int? = null,
    autoAdd: Boolean = true,
    init: PercentLayout.() -> Unit
): PercentLayout {
    val percentLayout =
        if (style != null) PercentLayout(
            ContextThemeWrapper(context, style)
        ) else PercentLayout(context)
    return percentLayout.apply(init).also { if (autoAdd) addView(it) }
}

/**
 * create [PercentLayout] instance
 * @param style an style int value defined in xml
 * @param init set attributes for this view in this lambda
 */
inline fun Context.PercentLayout(style: Int? = null, init: PercentLayout.() -> Unit): PercentLayout {
    val percentLayout =
        if (style != null) PercentLayout(
            ContextThemeWrapper(this, style)
        ) else PercentLayout(this)
    return percentLayout.apply(init)
}
