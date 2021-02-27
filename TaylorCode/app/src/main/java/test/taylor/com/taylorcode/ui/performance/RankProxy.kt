package test.taylor.com.taylorcode.ui.performance

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
        val itemView = parent.context.run {
            ConstraintLayout {
                layout_width = match_parent
                layout_height = 30
                padding_horizontal = 10
                background_color = "#ffffff"

                TextView {
                    layout_id = "tvRank"
                    layout_width = 18
                    layout_height = wrap_content
                    textSize = 14f
                    textColor = "#9DA4AD"
                    gravity = gravity_center
                    center_vertical = true
                }

                ImageView {
                    layout_id = "ivAvatar"
                    layout_width = 20
                    layout_height = 20
                    scaleType = scale_center_crop
                    center_vertical = true
                    start_toEndOf = "tvRank"
                    margin_start = 12
                }

                TextView {
                    layout_id = "tvName"
                    layout_width = wrap_content
                    layout_height = wrap_content
                    textSize = 14f
                    textColor = "#3F4658"
                    gravity = gravity_center
                    maxLines = 1
                    center_vertical = true
                    start_toEndOf = "ivAvatar"
                    margin_start = 10
                }

                ImageView {
                    layout_id = "ivLevel"
                    layout_width = 15
                    layout_height = 15
                    scaleType = scale_fit_xy
                    center_vertical = true
                    start_toEndOf = "tvName"
                    margin_start = 5
                }

                TextView {
                    layout_id = "tvCount"
                    layout_width = wrap_content
                    layout_height = wrap_content
                    textSize = 14f
                    textColor = "#3F4658"
                    gravity = gravity_center
                    end_toEndOf = parent_id
                    center_vertical = true
                }
            }
        }
        return RankViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RankViewHolder, data: Rank, index: Int, action: ((Any?) -> Unit)?) {
        holder.tvCount?.text = data.count.formatNums()
        holder.ivAvatar?.let {
            Glide.with(holder.ivAvatar.context).load(data.avatarUrl).into(it)
        }
        holder.ivLevel?.let {
            Glide.with(holder.ivLevel.context).load(data.levelUrl).into(it)
        }
        holder.tvRank?.text = data.rank.toString()
        holder.tvName?.text = data.name
    }
}

data class Rank(
    val rank: Int,
    val name: String,
    val count: Int,
    val avatarUrl: String,
    val levelUrl: String
)

class RankViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvRank = itemView.find<TextView>("tvRank")
    val tvName = itemView.find<TextView>("tvName")
    val ivAvatar = itemView.find<ImageView>("ivAvatar")
    val ivLevel = itemView.find<ImageView>("ivLevel")
    val tvCount = itemView.find<TextView>("tvCount")
}