package test.taylor.com.taylorcode.ui.custom_view.bullet_screen.sample

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import test.taylor.com.taylorcode.kotlin.ConstraintLayout
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.ui.custom_view.bullet_screen.LaneLayoutManager
import test.taylor.com.taylorcode.ui.recyclerview.variety.VarietyAdapter2

class LaneLayoutManagerActivity : AppCompatActivity() {

    private val laneAdapter by lazy {
        VarietyAdapter2().apply {
            addProxy(LaneProxy())
        }
    }

    private val contentView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent

            RecyclerView {
                layout_width = 300
                layout_height = 200
                layoutManager = LaneLayoutManager()
                adapter = laneAdapter
                background_color = "#eeeeee"
                center_horizontal = true
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)
        laneAdapter.dataList = listOf(
            LaneBean("aaa"),
            LaneBean("bbb"),
            LaneBean("ccc"),
            LaneBean("ddd"),
            LaneBean("eee"),
            LaneBean("fff"),
        )
    }
}

class LaneProxy : VarietyAdapter2.Proxy<LaneBean, LaneViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = parent.context.run {
            ConstraintLayout {
                layout_width = wrap_content
                layout_height = 50
                shape = shape {
                    solid_color = "#8e8e8e"
                    corner_radius = 20
                }

                TextView {
                    layout_id = "tvText"
                    layout_width = wrap_content
                    layout_height = wrap_content
                    textSize = 17f
                    textColor = "#0000ff"
                    text = "save"
                    gravity = gravity_center
                    padding_horizontal = 5
                    center_horizontal = true
                    center_vertical = true
                }
            }
        }
        return LaneViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LaneViewHolder, data: LaneBean, index: Int, action: ((Any?) -> Unit)?) {
        holder.tvText?.text = data.text
        Log.v("ttaylor","onBindViewHolder() data=${data.text}")
    }

}

data class LaneBean(var text: String)

class LaneViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvText = itemView.find<TextView>("tvText")
}