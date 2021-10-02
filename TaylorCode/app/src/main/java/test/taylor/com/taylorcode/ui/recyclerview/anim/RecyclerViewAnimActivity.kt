package test.taylor.com.taylorcode.ui.recyclerview.anim

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import test.taylor.com.taylorcode.kotlin.ConstraintLayout
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.ui.recyclerview.variety.VarietyAdapter2

class RecyclerViewAnimActivity : AppCompatActivity() {
    private val overlapAdapter by lazy {
        VarietyAdapter2().apply {
            addProxy(ImageProxy())
        }
    }
    private val contentView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent

            RecyclerView {
                layout_width = 200
                layout_height = 80
                center_horizontal = true
                center_vertical = true
                adapter = overlapAdapter
                layoutManager = OverlapLayoutManager(0.1f)
                background_color = "#00ff00"
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)

        overlapAdapter.dataList = listOf(
            Image("djfkdsf//"),
            Image("djfkdsf//"),
            Image("djfkdsf//"),
            Image("djfkdsf//"),
            Image("djfkdsf//"),
            Image("djfkdsf//"),
            Image("djfkdsf//"),
            Image("djfkdsf//"),
            Image("djfkdsf//"),
            Image("djfkdsf//"),
            Image("djfkdsf//"),
        )
    }


}

class ImageProxy : VarietyAdapter2.Proxy<Image, ImageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = parent.context.run {
            TextView {
                layout_id = "tvChange"
                layout_width = wrap_content
                layout_height = match_parent
                textSize = 20f
                textColor = "#888888"
                gravity = gravity_center
            }
        }
        return ImageViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, data: Image, index: Int, action: ((Any?) -> Unit)?) {
        holder.tv?.text = data.url
    }

}

data class Image(var url: String)

class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tv = itemView.find<TextView>("tvChange")
}