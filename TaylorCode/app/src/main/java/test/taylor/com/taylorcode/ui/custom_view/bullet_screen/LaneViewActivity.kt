package test.taylor.com.taylorcode.ui.custom_view.bullet_screen

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.ui.anim.LaneBean
import test.taylor.com.taylorcode.ui.recyclerview.variety.VarietyAdapter

class LaneViewActivity : AppCompatActivity() {

    private val liveCommentAdapter = VarietyAdapter().apply {
        addProxy(LiveCommentProxy())
        addProxy(TextProxy())

        onViewRecycled = {
            Log.v("ttaylor","tag=, LaneViewActivity.() onViewRecycled  ${it.itemView.tag}")
        }
    }

    private val contentView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent

            RecyclerView {
                layout_width = match_parent
                layout_height = match_parent
                layoutManager = LinearLayoutManager(this@LaneViewActivity)
                adapter = liveCommentAdapter
            }
        }
    }

    private val laneBeans = listOf(
        "1",
        "2",
        "3",
        "4",
        "5 ",
        "6",
        "7",
        "8",
        "9",
        "11",
        "22",
        "33",
        "44",
        "55",
        "66",
        "77",
        "88",
        "99",
        "111",
        "222",
        "333",
        "444",
        "555",
        "666",
        "777"
    )

    private val LiveCom = LiveCom(laneBeans)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)

        liveCommentAdapter.dataList = listOf(
            LiveCom,
            "ddddd",
            "ddddd",
            "ddddd",
            "ddddd",
            "ddddd",
            "ddddd",
            "ddddd",
            "ddddd",
            "ddddd",
            "ddddd",
            "ddddd",
            "ddddd",
            "ddddd",
            "ddddd",
            "ddddd",
            "ddddd",
            "ddddd",
            "ddddd",
            "ddddd",
            "ddddd",
            "ddddd",
            "ddddd",
            "ddddd",
            "ddddd",
            "ddddd",
            "ddddd",
            "ddddd",
            "ddddd",
            "ddddd",
            "ddddd",
            "ddddd",
            "ddddd",
            "ddddd",
            "ddddd",
            "ddddd",
            "ddddd",
            "ddddd",
            "ddddd",
            "ddddd",
            "ddddd",
            "ddddd",
            "ddddd",
            "ddddd",
            "ddddd",
            "ddddd",
            "ddddd",
            "ddddd",
            "ddddd",
            "ddddd",
            "ddddd",
            "ddddd",
            "ddddd"
        )
    }
}

class TextProxy : VarietyAdapter.Proxy<String, TextViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = parent.context.run {
            TextView {
                tag = "tttvv"
                layout_id = "tvFansDefalutKonledge"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 26f
                textColor = "#3F4658"
                gravity = gravity_center
                text = "100000000000"
            }
        }
        return TextViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TextViewHolder, data: String, index: Int, action: ((Any?) -> Unit)?) {

    }

}

class TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

}

class LiveCommentProxy : VarietyAdapter.Proxy<LiveCom, LiveCommentViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.v("ttaylor","tag=, LiveCommentProxy.onCreateViewHolder()  ")
        val itemView = parent.context.run {
            ConstraintLayout {
                tag = "lanview"
                layout_width = match_parent
                layout_height = 50

                TextView {
                    layout_id = "tvFansDefalutKonledge"
                    layout_width = wrap_content
                    layout_height = wrap_content
                    textSize = 16f
                    textColor = "#3F4658"
                    gravity = gravity_center
                    text = "1234567891234567"
                    maxEms = 3
                    isSingleLine = true
                    ellipsize = ellipsize_end
                    top_toTopOf = parent_id
                    center_horizontal = true
                }

                LaneView(context).apply {
                    layout_id = "lanview"
                    layout_width = match_parent
                    layout_height = 50
                    top_toBottomOf = "tvFansDefalutKonledge"
                    bottom_toBottomOf = parent_id
                    background_color = "#00ff00"
                    verticalGap = 5
                    horizontalGap = 10
                    speedMode = LaneView.Speed.Sync
                    loopMode = LaneView.Loop.Forever
                    duration = 3000L
                    createView = {
                        TextView(autoAdd = false) {
                            layout_id = "tv"
                            layout_width = wrap_content
                            layout_height = wrap_content
                            gravity = gravity_center
                            textSize = 20f
                            text = "asdf"
                            padding_start = 12
                            padding_bottom = 5
                            padding_end = 12
                            padding_top = 5
                            shape = shape {
                                corner_radius = 25
                                solid_color = "#80c0c0c0"
                            }
                        }
                    }
                    bindView = { data, view ->
                        (data as? String)?.let {
                            view.find<TextView>("tv")?.text = it
                        }
                    }
                }.also {
                    addView(it)
                }
            }
        }

        return LiveCommentViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LiveCommentViewHolder, data: LiveCom, index: Int, action: ((Any?) -> Unit)?) {
        Log.v("ttaylor","tag=, LiveCommentProxy.onBindViewHolder()  ")
        holder.laneView?.apply{
                show(data.strings)
        }
    }

}

data class LiveCom(var strings: List<String>)

class LiveCommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val laneView = itemView.find<LaneView>("lanview")

}