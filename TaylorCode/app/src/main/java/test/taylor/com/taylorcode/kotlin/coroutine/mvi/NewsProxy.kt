package test.taylor.com.taylorcode.kotlin.coroutine.mvi

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.retrofit.News
import test.taylor.com.taylorcode.ui.recyclerview.variety.VarietyAdapter2

class NewsProxy: VarietyAdapter2.Proxy<News, NewsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = parent.context.run {
            ConstraintLayout {
                layout_width = match_parent
                layout_height = 80
                padding_start = 20
                padding_end = 20
                padding_top = 10
                padding_bottom = 10

                ImageView {
                    layout_id = "ivNewsImg"
                    layout_width = 50
                    layout_height = 50
                    scaleType = scale_fit_xy
                    center_vertical = true
                    start_toStartOf = parent_id
                }

                TextView {
                    layout_id = "tvNewsTitle"
                    layout_width = 0
                    layout_height = wrap_content
                    maxLines = 2
                    textSize = 15f
                    margin_start = 10
                    end_toEndOf = parent_id
                    horizontal_bias = 0f
                    start_toEndOf = "ivNewsImg"
                    top_toTopOf = "ivNewsImg"
                }

                View {
                    layout_width = match_parent
                    layout_height = 1
                    background_color = "#eeeeee"
                    bottom_toBottomOf = parent_id
                }
            }
        }
        return NewsViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: NewsViewHolder,
        data: News,
        index: Int,
        action: ((Any?) -> Unit)?
    ) {
       holder.tv?.text = data.title
        holder.iv?.let {
            Glide.with(it).load(data.image).into(it)
        }
    }
}

class NewsViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
    val tv = itemView.find<TextView>("tvNewsTitle")
    val iv = itemView.find<ImageView>("ivNewsImg")
}

