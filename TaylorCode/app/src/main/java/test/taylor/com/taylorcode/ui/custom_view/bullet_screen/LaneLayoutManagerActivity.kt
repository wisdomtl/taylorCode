package test.taylor.com.taylorcode.ui.custom_view.bullet_screen

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.launchIn
import test.taylor.com.taylorcode.kotlin.ConstraintLayout
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.kotlin.coroutine.countdown2
import test.taylor.com.taylorcode.ui.recyclerview.variety.VarietyAdapter2

class LaneLayoutManagerActivity : AppCompatActivity() {

    private lateinit var rv: RecyclerView


    private val contentViewAdapter by lazy {
        VarietyAdapter2().apply {
            addItemBuilder(LanesProxy())
            addItemBuilder(StringProxy())
        }
    }

    private val contentView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent

            RecyclerView {
                layout_width = match_parent
                layout_height = match_parent
                adapter = contentViewAdapter
                layoutManager = LinearLayoutManager(this@LaneLayoutManagerActivity)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)

        contentViewAdapter.dataList = listOf(
            LaneBeans(

                listOf(
                    LaneBean("aaa0"),
                    LaneBean("bbb1"),
                    LaneBean("ccc2"),
                    LaneBean("ddd3"),
                    LaneBean("111111111cccdkfjsdlfjdslkfjlsdkfjlkdsfjksdl"),
                    LaneBean("dddidddddddddddddddddddd"),
                    LaneBean("eee4"),
                    LaneBean("fff5"),
                    LaneBean("ggg6"),
                    LaneBean("hhh7"),
                    LaneBean("iii8"),
                    LaneBean("22222222222iiiklsdjflksdjflkjsdlkfjlskdfjlksdjflksdfs"),
                    LaneBean("jjj9"),
                    LaneBean("kkk10"),
                    LaneBean("lll11"),
                    LaneBean("mmm12"),
                    LaneBean("333333333mmmdkslfslkdfjlksdjlksdjfsldkjfkdfsdfsdfsdf"),
                    LaneBean("nnn13"),
                    LaneBean("ooo14"),
                    LaneBean("ppp15"),
                    LaneBean("qqqq16"),
                    LaneBean("rrr17"),
                    LaneBean("ssss18"),
                    LaneBean("ttt19"),
                    LaneBean("44444444444qqsdjklfjlsdkfjlsdkfjlsdkfjlsdkfjlsdkfjsldkfjsldkfq"),
                    LaneBean("uuu20"),
                    LaneBean("666666666666iiiklsdjflksdjflkjsdlkfjlskdfjlksdjflksdfs"),
                    LaneBean("7777777777mmmdkslfslkdfjlksdjlksdjfsldkjfkdfsdfsdfsdf"),
                    LaneBean("88888888888qqsdjklfjlsdkfjlsdkfjlsdkfjlsdkfjlsdkfjsldkfjsldkfq"),
                )
            ),
            Text("dfadff"),
            Text("dfadff"),
            Text("dfadff"),
            Text("dfadff"),
            Text("dfadff"),
            Text("dfadff"),
            Text("dfadff"),
            Text("dfadff"),
            Text("dfadff"),
            Text("dfadff"),
            Text("dfadff"),
            Text("dfadff"),
            Text("dfadff"),
            Text("dfadff"),
            Text("dfadff"),
            Text("dfadff"),
            Text("dfadff"),
            Text("dfadff"),
            Text("dfadff"),
            Text("dfadff"),
            Text("dfadff"),
            Text("dfadff"),
            Text("dfadff"),
            Text("dfadff"),
            Text("dfadff"),
            Text("dfadff"),
            Text("dfadff"),
            Text("dfadff"),
            Text("dfadff"),
            Text("dfadff"),
            Text("dfadff"),
            Text("dfadff"),
            Text("dfadff"),
            Text("dfadff"),
            Text("dfadff"),
            Text("dfadff"),
            Text("dfadff"),
            Text("dfadff"),
            )

    }
}

class LaneAdapter : VarietyAdapter2() {

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    override fun getIndex(position: Int): Int = position % dataList.size
}

class LaneProxy : VarietyAdapter2.ItemBuilder<LaneBean, LaneViewHolder>() {
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

data class LaneBeans(
    var laneList: List<LaneBean>,
)

class LanesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val rv = itemView.find<RecyclerView>("rv")
}

class LanesProxy : VarietyAdapter2.ItemBuilder<LaneBeans, LanesViewHolder>() {
    private val laneAdapter by lazy {
        LaneAdapter().apply {
            addItemBuilder(LaneProxy())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = parent.context.run {
            ConstraintLayout {
                layout_width = match_parent
                layout_height = 200

                RecyclerView {
                    layout_id = "rv"
                    layout_width = 400
                    layout_height = 200
                    padding_top =30
                    padding_bottom = 30
                    layoutManager = LaneLayoutManager().apply { horizontalGap = 10
                    verticalGap = 10}
                    adapter = laneAdapter
                    background_color = "#eeeeee"
                    makeUnTouchable()
                    center_horizontal = true
                }
            }
        }
        return LanesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LanesViewHolder, data: LaneBeans, index: Int, action: ((Any?) -> Unit)?) {
        laneAdapter.dataList = data.laneList

        holder.rv?.let { rv ->
            countdown2(Long.MAX_VALUE, 50) {
                rv.smoothScrollBy(10, 0)
            }.launchIn(MainScope())
        }
    }
}

data class Text(val string: String)

class StringViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tv = itemView.find<TextView>("tvChange")
}

class StringProxy : VarietyAdapter2.ItemBuilder<Text, StringViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = parent.context.run {
            ConstraintLayout {
                layout_width = match_parent
                layout_height = 50

                TextView {
                    layout_id = "tvChange"
                    layout_width = wrap_content
                    layout_height = wrap_content
                    textSize = 12f
                    textColor = "#000000"
                    text = "save"
                    gravity = gravity_center
                }
            }
        }
        return StringViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StringViewHolder, data: Text, index: Int, action: ((Any?) -> Unit)?) {
        holder.tv?.text = data.string
    }

}