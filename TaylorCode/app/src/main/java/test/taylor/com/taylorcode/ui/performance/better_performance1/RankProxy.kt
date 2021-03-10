package test.taylor.com.taylorcode.ui.performance.better_performance1

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import test.taylor.com.taylorcode.kotlin.ConstraintLayout
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.ui.recyclerview.variety.VarietyAdapter2

class RankProxy : VarietyAdapter2.Proxy<Rank, RankViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val start = System.currentTimeMillis()
        lateinit var tvRank: TextView
        lateinit var tvName: TextView
        lateinit var ivAvatar: ImageView
        lateinit var ivLevel: ImageView
        lateinit var tvCount: TextView
        lateinit var tvTag: TextView
        lateinit var tvLevel: TextView
        val itemView = parent.context.run {
//            ConstraintLayout {
//                layout_width = match_parent
//                layout_height = 35
//                padding_horizontal = 10
//                background_color = "#ffffff"
//
//                tvRank = TextView {
//                    layout_id = "tvRank"
//                    layout_width = 18
//                    layout_height = wrap_content
//                    textSize = 14f
//                    textColor = "#9DA4AD"
//                    gravity = gravity_center
//                    center_vertical = true
//                }
//
//                ivAvatar = ImageView {
//                    layout_id = "ivAvatar"
//                    layout_width = 20
//                    layout_height = 20
//                    scaleType = scale_center_crop
//                    center_vertical = true
//                    start_toEndOf = "tvRank"
//                    margin_start = 12
//                }
//
//                tvName = TextView {
//                    layout_id = "tvName"
//                    layout_width = wrap_content
//                    layout_height = wrap_content
//                    textSize = 11f
//                    textColor = "#3F4658"
//                    gravity = gravity_center
//                    maxLines = 1
//                    start_toEndOf = "ivAvatar"
//                    margin_start = 10
//                    top_toTopOf = parent_id
//                    bottom_toTopOf = "tvTag"
//                    includeFontPadding = false
//                    vertical_chain_style = packed
//                }
//
//                tvTag = TextView {
//                    layout_id = "tvTag"
//                    layout_width = wrap_content
//                    layout_height = wrap_content
//                    textSize = 8f
//                    textColor = "#ffffff"
//                    text = "save"
//                    gravity = gravity_center
//                    start_toStartOf = "tvName"
//                    top_toBottomOf = "tvName"
//                    bottom_toBottomOf = parent_id
//                    vertical_chain_style = packed
//                    padding_vertical = 1
//                    includeFontPadding = false
//                    padding_horizontal = 2
//                    shape = shape {
//                        corner_radius = 4
//                        solid_color = "#8cc8c8c8"
//                    }
//                }
//
//                ivLevel = ImageView {
//                    layout_id = "ivLevel"
//                    layout_width = 10
//                    layout_height = 10
//                    scaleType = scale_fit_xy
//                    align_vertical_to = "tvName"
//                    start_toEndOf = "tvName"
//                    margin_start = 5
//                }
//
//                tvLevel = TextView {
//                    layout_id = "tvLevel"
//                    layout_width = wrap_content
//                    layout_height = wrap_content
//                    textSize = 7f
//                    textColor = "#ffffff"
//                    gravity = gravity_center
//                    align_vertical_to = "ivLevel"
//                    start_toEndOf = "ivLevel"
//                    margin_start = 5
//                    padding_horizontal = 2
//                    shape = shape {
//                        gradient_colors = listOf("#FFC39E", "#FFC39E")
//                        orientation = gradient_left_right
//                        corner_radius = 20
//                    }
//                }
//
//                tvCount = TextView {
//                    layout_id = "tvCount"
//                    layout_width = wrap_content
//                    layout_height = wrap_content
//                    textSize = 14f
//                    textColor = "#3F4658"
//                    gravity = gravity_center
//                    end_toEndOf = parent_id
//                    center_vertical = true
//                }
//            }

            PercentLayout {
                layout_width = match_parent
                layout_height = 35
                background_color = "#ffffff"

                tvRank = TextView {
                    layout_id = "tvRank"
                    layout_width = 18
                    layout_height = wrap_content
                    textSize = 14f
                    textColor = "#9DA4AD"
                    left_percent = 0.08f
                    center_vertical_of_percent = parent_id
                }

                ivAvatar = ImageView {
                    layout_id = "ivAvatar"
                    layout_width = 20
                    layout_height = 20
                    scaleType = scale_center_crop
                    center_vertical_of_percent = parent_id
                    left_percent = 0.15f
                }

                tvName = TextView {
                    layout_id = "tvName"
                    layout_width = wrap_content
                    layout_height = wrap_content
                    textSize = 11f
                    textColor = "#3F4658"
                    gravity = gravity_center
                    maxLines = 1
                    includeFontPadding = false
                    start_to_end_of_percent = "ivAvatar"
                    top_to_top_of_percent = "ivAvatar"
                    margin_start = 5
                }

                tvTag = TextView {
                    layout_id = "tvTag"
                    layout_width = wrap_content
                    layout_height = wrap_content
                    textSize = 8f
                    textColor = "#ffffff"
                    text = "save"
                    gravity = gravity_center
                    padding_vertical = 1
                    includeFontPadding = false
                    padding_horizontal = 2
                    shape = shape {
                        corner_radius = 4
                        solid_color = "#8cc8c8c8"
                    }
                    start_to_start_of_percent = "tvName"
                    top_to_bottom_of_percent = "tvName"
                }

                ivLevel = ImageView {
                    layout_id = "ivLevel"
                    layout_width = 10
                    layout_height = 10
                    scaleType = scale_fit_xy
                    center_vertical_of_percent = "tvName"
                    start_to_end_of_percent = "tvName"
                    margin_start = 5
                }

                tvLevel = TextView {
                    layout_id = "tvLevel"
                    layout_width = wrap_content
                    layout_height = wrap_content
                    textSize = 7f
                    textColor = "#ffffff"
                    gravity = gravity_center
                    padding_horizontal = 2
                    shape = shape {
                        gradient_colors = listOf("#FFC39E", "#FFC39E")
                        orientation = gradient_left_right
                        corner_radius = 20
                    }
                    center_vertical_of_percent = "tvName"
                    start_to_end_of_percent = "ivLevel"
                    margin_start = 5
                }

                tvCount = TextView {
                    layout_id = "tvCount"
                    layout_width = wrap_content
                    layout_height = wrap_content
                    textSize = 14f
                    textColor = "#3F4658"
                    gravity = gravity_center
                    center_vertical_of_percent = parent_id
                    end_to_end_of_percent = parent_id
                    margin_end = 20
                }
            }
        }
        val viewHolder = RankViewHolder(
            itemView
//            ,
//            tvRank,
//            tvName,
//            ivAvatar,
//            ivLevel, tvCount, tvTag, tvLevel
        )
        Log.w("ttaylor", "rank create view duration = ${System.currentTimeMillis() - start}")
        return viewHolder
    }

    override fun onBindViewHolder(holder: RankViewHolder, data: Rank, index: Int, action: ((Any?) -> Unit)?) {
        val start = System.currentTimeMillis()
        holder.tvCount?.text = data.count.formatNums()
        holder.ivAvatar?.let {
            Glide.with(holder.ivAvatar.context).load(data.avatarUrl).into(it)
        }
        holder.ivLevel?.let {
            Glide.with(holder.ivLevel.context).load(data.levelUrl).into(it)
        }
        holder.tvRank?.text = data.rank.toString()
        holder.tvName?.text = data.name
        holder.tvLevel?.text = data.level.toString()
        holder.tvTag?.text = data.tag
        Log.w("ttaylor", "rank bind view duration = ${System.currentTimeMillis() - start}")
    }
}

data class Rank(
    val rank: Int,
    val name: String,
    val count: Int,
    val avatarUrl: String,
    val levelUrl: String,
    val level: Int = 200,
    val tag: String = "达人"
)

class RankViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvRank = itemView.find<TextView>("tvRank")
    val tvName = itemView.find<TextView>("tvName")
    val ivAvatar = itemView.find<ImageView>("ivAvatar")
    val ivLevel = itemView.find<ImageView>("ivLevel")
    val tvCount = itemView.find<TextView>("tvCount")
    val tvTag = itemView.find<TextView>("tvTag")
    val tvLevel = itemView.find<TextView>("tvLevel")
}

class RankViewHolder2(
    itemView: View,
    var tvRank: TextView,
   var tvName: TextView,
   var ivAvatar: ImageView,
   var ivLevel: ImageView,
   var tvCount: TextView,
   var tvTag: TextView,
   var tvLevel: TextView
) : RecyclerView.ViewHolder(itemView)