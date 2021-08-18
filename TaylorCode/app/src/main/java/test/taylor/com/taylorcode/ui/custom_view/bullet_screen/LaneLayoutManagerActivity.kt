package test.taylor.com.taylorcode.ui.custom_view.bullet_screen.sample

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.launchIn
import test.taylor.com.taylorcode.kotlin.ConstraintLayout
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.kotlin.coroutine.countdown2
import test.taylor.com.taylorcode.ui.custom_view.bullet_screen.LaneLayoutManager
import test.taylor.com.taylorcode.ui.recyclerview.variety.VarietyAdapter2

class LaneLayoutManagerActivity : AppCompatActivity() {

    private lateinit var rv: RecyclerView

    private val laneAdapter by lazy {
        VarietyAdapter2().apply {
            addProxy(LaneProxy())
        }
    }

    private val contentView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent

            rv = RecyclerView {
                layout_width = 50
                layout_height = 200
                layoutManager = LaneLayoutManager()
                adapter = laneAdapter
                background_color = "#eeeeee"
                makeUnTouchable()
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
            LaneBean("ggg"),
            LaneBean("hhh"),
            LaneBean("iii"),
            LaneBean("jjj"),
            LaneBean("kkk"),
            LaneBean("lll"),
            LaneBean("mmm"),
            LaneBean("nnn"),
            LaneBean("ooo"),
            LaneBean("ppp"),
            LaneBean("qqq"),
            LaneBean("uuu"),
        )

        countdown2(100000, 50) {
            rv.smoothScrollBy(10, 0)
        }.launchIn(MainScope())
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
        Log.v("ttaylor", "onBindViewHolder() data=${data.text}")
    }

}

data class LaneBean(var text: String)

class LaneViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvText = itemView.find<TextView>("tvText")
}

fun RecyclerView.makeUnTouchable() {
    setOnTouchListener { v, event -> true }
}