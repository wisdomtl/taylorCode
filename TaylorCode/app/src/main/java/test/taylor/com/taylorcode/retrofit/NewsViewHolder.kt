package test.taylor.com.taylorcode.retrofit

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import test.taylor.com.taylorcode.kotlin.find

class NewsViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
    fun bind(news:News){
        itemView.apply {
            find<TextView>("tvNewsTitle")?.text = news.title
            Glide.with(context).load(news.image).into(find("ivNewsImg"))
        }
    }
}