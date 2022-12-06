package test.taylor.com.taylorcode.ui.custom_view.overlap_anim

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import test.taylor.com.taylorcode.kotlin.ConstraintLayout
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.ui.StrokeImageView
import test.taylor.com.taylorcode.ui.recyclerview.variety.VarietyAdapter2


class CarouselProxy : VarietyAdapter2.ItemBuilder<CarouselData, CarouselViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = parent.context.run {
            ConstraintLayout {
                layout_id = "container"
                layout_width = 70
                layout_height = match_parent
            }
        }

        return CarouselViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, data: CarouselData, index: Int, action: ((Any?) -> Unit)?) {
        data.urls.takeIf { it.isNullOrEmpty() }?.let { urls ->
            holder.container?.let {
                OverlapCarouselAnim(it).apply {
                    overlapDp = 4
                    dimensionDp = 20
                    interval = 1500
                    duration = 300
                    onBindItemView = { context, index, url ->
                        StrokeImageView(context).apply {
                            scaleType = scale_center_crop
                            roundedAsCircle = true
                            strokeColor = Color.parseColor("#ffffff")
                            strokeWidth = 0.5f
                            Glide.with(this).load(url).into(this)
                        }
                    }
                }.start(if (urls.size > 5) urls.take(6) else urls, urls.size > 5)
            }
        }
    }
}

data class CarouselData(val urls: List<String>)

class CarouselViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val container = itemView.find<ConstraintLayout>("container")
}