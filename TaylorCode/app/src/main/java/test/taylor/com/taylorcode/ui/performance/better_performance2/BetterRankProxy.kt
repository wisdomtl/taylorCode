package test.taylor.com.taylorcode.ui.performance.better_performance2

import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.ui.performance.better_performance1.Rank
import test.taylor.com.taylorcode.ui.performance.viewScope
import test.taylor.com.taylorcode.ui.recyclerview.variety.VarietyAdapter2

class BetterRankProxy : VarietyAdapter2.Proxy<BetterRank, BetterRankViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = parent.context.run {
            LinearLayout {
                layout_width = match_parent
                layout_height = wrap_content
                orientation = vertical
                margin_start = 20
                margin_end = 20
                padding_bottom = 16
                shape = shape {
                    corner_radius = 20
                    solid_color = "#ffffff"
                }

                // optimize: use PercentLayout is faster
                PercentLayout {
                    layout_width = match_parent
                    layout_height = 60
                    shape = shape {
                        corner_radii = intArrayOf(20, 20, 20, 20, 0, 0, 0, 0)
                        solid_color = "#ffffff"
                    }

                    TextView {
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

                    TextView {
                        layout_id = "tvRank"
                        layout_width = wrap_content
                        layout_height = wrap_content
                        textSize = 11f
                        textColor = "#9DA4AD"
                        left_percent = 0.06f
                        top_percent = 0.78f
                    }

                    TextView {
                        layout_id = "tvName"
                        layout_width = wrap_content
                        layout_height = wrap_content
                        textSize = 11f
                        textColor = "#9DA4AD"
                        left_percent = 0.18f
                        top_percent = 0.78f
                    }

                    TextView {
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

        }
        return BetterRankViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BetterRankViewHolder, data: BetterRank, index: Int, action: ((Any?) -> Unit)?) {
        holder.tvAnchormanColumn?.text = data.nameColumn
        holder.tvRankColumn?.text = data.rankColumn
        holder.tvSumColumn?.text = data.countColumn
        holder.tvTitle?.text = data.title

        (holder.itemView as? LinearLayout)?.apply {
            data.ranks.forEachIndexed { index, rank ->
                // optimize: use PercentLayout is faster
                PercentLayout {
                    layout_width = match_parent
                    layout_height = 35
                    background_color = "#ffffff"

                    TextView {
                        layout_id = "tvRank"
                        layout_width = 18
                        layout_height = wrap_content
                        textSize = 14f
                        textColor = "#9DA4AD"
                        left_percent = 0.08f
                        center_vertical_of_percent = parent_id
                        text = rank.rank.toString()
                    }

                    ImageView {
                        layout_id = "ivAvatar"
                        layout_width = 20
                        layout_height = 20
                        scaleType = scale_center_crop
                        center_vertical_of_percent = parent_id
                        left_percent = 0.15f
                        Glide.with(this.context).load(rank.avatarUrl).into(this)
                        // optimize: use coroutine is faster
//                        viewScope.launch {
//                            val futureTask = Glide.with(this@ImageView.context).asBitmap().load(rank.avatarUrl).submit()
//                            val bitmap = futureTask.get()
//                            withContext(Dispatchers.Main) {
//                                this@ImageView.setImageBitmap(bitmap)
//                            }
//                        }
//                        this.load(rank.avatarUrl)
                    }

                    TextView {
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
                        ellipsize = TextUtils.TruncateAt.END
                        text = rank.name
                    }

                    TextView {
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

                    ImageView {
                        layout_id = "ivLevel"
                        layout_width = 10
                        layout_height = 10
                        scaleType = scale_fit_xy
                        center_vertical_of_percent = "tvName"
                        start_to_end_of_percent = "tvName"
                        margin_start = 5
                        Glide.with(this.context).load(rank.levelUrl).submit()
                        // optimize: use coroutine is faster
//                        viewScope.launch {
//                            val futureTask = Glide.with(this@ImageView.context).asBitmap().load(rank.levelUrl).submit()
//                            val bitmap = futureTask.get()
//                            withContext(Dispatchers.Main) {
//                                this@ImageView.setImageBitmap(bitmap)
//                            }
//                        }
//                        this.load(rank.levelUrl)
                    }

                    TextView {
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
                        text = rank.level.toString()
                    }

                    TextView {
                        layout_id = "tvCount"
                        layout_width = wrap_content
                        layout_height = wrap_content
                        textSize = 14f
                        textColor = "#3F4658"
                        gravity = gravity_center
                        center_vertical_of_percent = parent_id
                        end_to_end_of_percent = parent_id
                        margin_end = 20
                        text = rank.count.formatNums()
                    }
                }
            }
        }
    }
}

data class BetterRank(
    val title: String,
    val rankColumn: String,
    val nameColumn: String,
    val countColumn: String,
    val ranks: List<Rank>
)

class BetterRankViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvTitle = itemView.find<TextView>("tvTitle")
    val tvRankColumn = itemView.find<TextView>("tvRank")
    val tvAnchormanColumn = itemView.find<TextView>("tvName")
    val tvSumColumn = itemView.find<TextView>("tvCount")

//    val tvTitle = itemView.find<TextView>("tvFansRankTitle")
//    val tvRankColumn = itemView.find<TextView>("tvRankColumn")
//    val tvAnchormanColumn = itemView.find<TextView>("tvAnchormanColumn")
//    val tvSumColumn = itemView.find<TextView>("tvSumColumn")
}