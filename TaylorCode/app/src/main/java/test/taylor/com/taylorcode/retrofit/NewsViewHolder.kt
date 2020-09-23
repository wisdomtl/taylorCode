package test.taylor.com.taylorcode.retrofit

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.applyCanvas
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import test.taylor.com.taylorcode.kotlin.find

class NewsViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
    fun bind(news:News){
        itemView.apply {
            find<TextView>("tvNewsTitle")?.text = news.title
            // load bitmap from glide
            Glide.with(context).asBitmap().circleCrop().load(news.image).into(object :CustomTarget<Bitmap>(){
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    resource.applyCanvas {
                        val stroke_Width = 2f
                        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                            color = Color.parseColor("#ff00ff")
                            style = Paint.Style.STROKE
                            strokeWidth = stroke_Width
                        }
                        drawCircle(width/2.toFloat(),height/2.toFloat(),(height*0.488*stroke_Width/2).toFloat(),paint)
                    }
                    find<ImageView>("ivNewsImg")?.setImageBitmap(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
        }
    }
}