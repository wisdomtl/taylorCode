package test.taylor.com.taylorcode.ui.performance.better_performance

import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.ui.performance.Rank
import test.taylor.com.taylorcode.ui.recyclerview.variety.VarietyAdapter2
import kotlin.math.min

class BetterRankProxy : VarietyAdapter2.Proxy<BetterRank, BetterRankViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = parent.context.run {
            LinearLayout {
                layout_width = match_parent
                layout_height = wrap_content
                orientation = vertical
                margin_start = 20
                margin_end = 20
                padding_top = 16
                padding_bottom = 16
                padding_start = 20
                padding_end = 20
                shape = shape {
                    corner_radius = 20
                    solid_color = "#ffffff"
                }

                TextView {
                    layout_id = "tvFansRankTitle"
                    layout_width = wrap_content
                    layout_height = wrap_content
                    textSize = 16f
                    textColor = "#3F4658"
                    textStyle = bold
                    margin_bottom = 3
                }

                ConstraintLayout {
                    layout_width = match_parent
                    layout_height = wrap_content
                    margin_top = 16

                    TextView {
                        layout_id = "tvRankColumn"
                        layout_width = wrap_content
                        layout_height = wrap_content
                        textSize = 11f
                        textColor = "#9DA4AD"
                        start_toStartOf = parent_id
                        center_vertical = true
                    }

                    TextView {
                        layout_id = "tvAnchormanColumn"
                        layout_width = wrap_content
                        layout_height = wrap_content
                        textSize = 11f
                        textColor = "#9DA4AD"
                        align_vertical_to = "tvRankColumn"
                        start_toEndOf = "tvRankColumn"
                        margin_start = 19
                    }

                    TextView {
                        layout_id = "tvSumColumn"
                        layout_width = wrap_content
                        layout_height = wrap_content
                        textSize = 11f
                        textColor = "#9DA4AD"
                        align_vertical_to = "tvRankColumn"
                        end_toEndOf = parent_id
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
            data.ranks.forEachIndexed { index, member ->
                ConstraintLayout {
                    layout_width = match_parent
                    layout_height = 30

                    TextView {
                        layout_id = "vAnchormanRank"
                        layout_width = 18
                        layout_height = wrap_content
                        textSize = 14f
                        textColor = "#9DA4AD"
                        gravity = gravity_center
                        center_vertical = true
                        start_toStartOf = parent_id
                        margin_start = 2
                        text = member.rank.toString()
                    }

                    ImageView {
                        layout_id = "ivAnchormanAvatar"
                        layout_width = 20
                        layout_height = 20
                        scaleType = scale_fit_xy
                        center_vertical = true
                        start_toEndOf = "vAnchormanRank"
                        margin_start = 12
                        Glide.with(this.context).load(member.avatarUrl).into(this)
                    }

                    TextView {
                        layout_id = "tvAnchormanName"
                        layout_width = wrap_content
                        layout_height = wrap_content
                        textSize = 12f
                        textColor = "#3F4658"
                        center_vertical = true
                        start_toEndOf = "ivAnchormanAvatar"
                        margin_start = 10
                        maxLines = 1
                        ellipsize = TextUtils.TruncateAt.END
                        text = member.name
                    }

                    ImageView {
                        layout_id = "ivLevel"
                        layout_width = 15
                        layout_height = 15
                        scaleType = scale_fit_xy
                        center_vertical = true
                        start_toEndOf = "tvAnchormanName"
                        margin_start = 5
                    }

                    TextView {
                        layout_id = "tvFansCount"
                        layout_width = wrap_content
                        layout_height = wrap_content
                        textSize = 12f
                        textColor = "#747E8B"
                        center_vertical = true
                        end_toEndOf = parent_id
                        text = member.count.toString()
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
    val tvTitle = itemView.find<TextView>("tvFansRankTitle")
    val tvRankColumn = itemView.find<TextView>("tvRankColumn")
    val tvAnchormanColumn = itemView.find<TextView>("tvAnchormanColumn")
    val tvSumColumn = itemView.find<TextView>("tvSumColumn")
}