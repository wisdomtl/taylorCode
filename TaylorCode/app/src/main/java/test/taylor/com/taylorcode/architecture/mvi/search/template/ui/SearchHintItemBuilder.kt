package com.bilibili.studio.search.template.ui

import android.graphics.Color
import android.text.SpannableStringBuilder
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.recyclerview.widget.RecyclerView
import com.bilibili.studio.search.template.data.SearchHint
import test.taylor.com.taylorcode.R
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.ui.recyclerview.variety.VarietyAdapter2

class SearchHintItemBuilder : VarietyAdapter2.ItemBuilder<SearchHint, SearchHintViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = parent.context.run {
            ConstraintLayout {
                layout_width = match_parent
                layout_height = 44

                ImageView {
                    layout_id = "ivSearch"
                    layout_width = 18
                    layout_height = 18
                    scaleType = scale_fit_xy
                    center_vertical = true
                }

                TextView {
                    layout_id = "tvHint"
                    layout_width = wrap_content
                    layout_height = wrap_content
                    textSize = 16f
                    gravity = gravity_center
                    center_vertical = true
                    start_toEndOf = "ivSearch"
                    margin_start = 8
                    maxLines = 1
                }
            }
        }
        return SearchHintViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SearchHintViewHolder, data: SearchHint, index: Int, action: ((Any?) -> Unit)?) {
        holder.tvHint?.apply {
            text = buildSpannedString {
                color(Color.parseColor("#F05079")) { append(data.keyword) }
                color(Color.parseColor("#F2F4FF")) { append(data.hint.removePrefix(data.keyword)) }
            }
        }
    }
}

class SearchHintViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvHint = itemView.find<TextView>("tvHint")
}