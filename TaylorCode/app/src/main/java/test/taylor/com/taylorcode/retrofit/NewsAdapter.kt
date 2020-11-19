package test.taylor.com.taylorcode.retrofit

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import test.taylor.com.taylorcode.kotlin.*

class NewsAdapter : RecyclerView.Adapter<NewsViewHolder>() {

    var news: List<News>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
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

    override fun getItemCount(): Int {
        return news?.size ?: 0
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        news?.getOrNull(position)?.let { holder.bind(it) }
    }
}