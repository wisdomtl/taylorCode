package test.taylor.com.taylorcode.ui.performance.better_performance3

import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.kotlin.shape
import test.taylor.com.taylorcode.ui.one.*
import test.taylor.com.taylorcode.ui.performance.better_performance2.BetterRank
import test.taylor.com.taylorcode.ui.performance.load
import test.taylor.com.taylorcode.ui.recyclerview.variety.VarietyAdapter2

class OneRankProxy : VarietyAdapter2.Proxy<BetterRank, OneRankViewHolder>() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = parent.context.run {
            LinearLayout {
                layout_id = "container"
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
                OneViewGroup {
                    layout_id = "one"
                    layout_width = match_parent
                    layout_height = 60
                    shape = shape {
                        corner_radii = intArrayOf(20, 20, 20, 20, 0, 0, 0, 0)
                        solid_color = "#ffffff"
                    }

                    text {
                        drawable_layout_id = "tvTitle"
                        drawable_max_width = 60
                        drawable_text_size = 16f
                        drawable_text_color = "#3F4658"
                        drawable_start_to_start_of = parent_id
                        drawable_left_margin = 20
                        topPercent = 0.23f
                    }

                    text {
                        drawable_layout_id = "tvRank"
                        drawable_max_width = 60
                        drawable_text_size = 11f
                        drawable_text_color = "#9DA4AD"
                        leftPercent = 0.06f
                        topPercent = 0.78f
                    }

                    text {
                        drawable_layout_id = "tvName"
                        drawable_max_width = 60
                        drawable_text_size = 11f
                        drawable_text_color = "#9DA4AD"
                        leftPercent = 0.18f
                        topPercent = 0.78f
                    }

                    text {
                        drawable_layout_id = "tvCount"
                        drawable_max_width = 100
                        drawable_text_size = 11f
                        drawable_text_color = "#9DA4AD"
                        drawable_end_to_end_of = parent_id
                        drawable_right_margin = 20
                        topPercent = 0.78f
                    }

                }
            }

        }
        return OneRankViewHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: OneRankViewHolder, data: BetterRank, index: Int, action: ((Any?) -> Unit)?) {
        holder.tvTitle?.text = data.title
        holder.tvCount?.text = data.countColumn
        holder.tvRank?.text = data.rankColumn
        holder.tvName?.text = data.nameColumn

        holder.container?.apply {
            data.ranks.forEachIndexed { index, rank ->
                // optimize: use PercentLayout is faster
                OneViewGroup {
                    layout_width = match_parent
                    layout_height = 35
                    background_color = "#ffffff"

                    text {
                        drawable_layout_id = "tvRank"
                        drawable_layout_width = 18
                        drawable_text_size = 14f
                        drawable_text_color = "#9DA4AD"
                        leftPercent = 0.08f
                        drawable_center_vertical_of = parent_id
                        text = rank.rank.toString()
                    }

                    image {
                        layout_id = "ivAvatar"
                        layout_width = 20
                        layout_height = 20
                        scaleType = scale_center_crop
                        drawable_center_vertical_of = parent_id
                        leftPercent = 0.15f
                        load(rank.avatarUrl)
                    }

                    text {
                        drawable_layout_id = "tvName"
                        drawable_max_width = 200
                        drawable_text_size = 11f
                        drawable_text_color = "#3F4658"
                        drawable_gravity = test.taylor.com.taylorcode.ui.one.gravity_center
                        drawable_max_lines = 1
                        drawable_start_to_end_of = "ivAvatar"
                        drawable_top_to_top_of = "ivAvatar"
                        drawable_left_margin = 5
                        drawable_text = rank.name
                    }

                    text {
                        drawable_layout_id = "tvTag"
                        drawable_max_width = 100
                        drawable_text_size = 8f
                        drawable_text_color = "#ffffff"
                        drawable_gravity = test.taylor.com.taylorcode.ui.one.gravity_center
                        drawable_padding_top = 1
                        drawable_padding_bottom = 1
                        drawable_padding_start = 2
                        drawable_padding_end = 2
                        drawable_text = "save"
                        drawable_shape = drawableShape {
                            radius = 4f
                            color = "#8cc8c8c8"
                        }
                        drawable_start_to_start_of = "tvName"
                        drawable_top_to_bottom_of = "tvName"
                    }

                    image {
                        layout_id = "ivLevel"
                        layout_width = 10
                        layout_height = 10
                        scaleType = scale_fit_xy
                        drawable_center_vertical_of = "tvName"
                        drawable_start_to_end_of = "tvName"
                        drawable_left_margin = 5
                        load(rank.levelUrl)
                    }

                    text {
                        drawable_layout_id = "tvLevel"
                        drawable_max_width = 200
                        drawable_text_size = 7f
                        drawable_text_color = "#ffffff"
                        drawable_gravity = test.taylor.com.taylorcode.ui.one.gravity_center
                        drawable_padding_start = 2
                        drawable_padding_end = 2
                        drawable_shape = drawableShape {
                            color = "#FFC39E"
                            radius = 20f
                        }
                        drawable_center_vertical_of = "tvName"
                        drawable_start_to_end_of = "ivLevel"
                        drawable_left_margin = 5
                        drawable_text = rank.level.toString()
                    }

                    text {
                        drawable_layout_id = "tvCount"
                        drawable_max_width = 200
                        drawable_text_size = 14f
                        drawable_text_color ="#3F4658"
                        drawable_gravity = test.taylor.com.taylorcode.ui.one.gravity_center
                        drawable_center_vertical_of = parent_id
                        drawable_end_to_end_of = parent_id
                        drawable_right_margin = 20
                        drawable_text = rank.count.formatNums()
                    }
                }
            }
        }
    }
}

class OneRankViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val oneViewGroup = itemView.find<OneViewGroup>("one")
    val container = itemView.find<LinearLayout>("container")

    @RequiresApi(Build.VERSION_CODES.M)
    val tvTitle = oneViewGroup?.findDrawable<Text>("tvTitle")

    @RequiresApi(Build.VERSION_CODES.M)
    val tvRank = oneViewGroup?.findDrawable<Text>("tvRank")

    @RequiresApi(Build.VERSION_CODES.M)
    val tvName = oneViewGroup?.findDrawable<Text>("tvName")

    @RequiresApi(Build.VERSION_CODES.M)
    val tvCount = oneViewGroup?.findDrawable<Text>("tvCount")
}