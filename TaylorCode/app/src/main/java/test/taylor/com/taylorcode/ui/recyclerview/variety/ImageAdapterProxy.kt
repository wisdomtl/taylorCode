package test.taylor.com.taylorcode.ui.recyclerview.variety

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.ui.recyclerview.variety.VarietyAdapter.AdapterProxy

class ImageAdapterProxy: AdapterProxy<Image, ImageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = parent.context.run {
            ImageView {
                layout_id = "ivAvatar"
                layout_width = match_parent
                layout_height = 50
                scaleType = scale_fix_xy
            }
        }

        return ImageViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, data: Image) {
        Glide.with(holder.ivAvatar?.context)
            .load(data.url)
            .into(holder.ivAvatar)
    }
}


data class Image(
    var url:String
)

class ImageViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
    val ivAvatar = itemView.find<ImageView>("ivAvatar")
}