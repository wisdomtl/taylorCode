package test.taylor.com.taylorcode.ui.anim

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.ui.custom_view.bullet_screen.LaneView

class AddViewActivity : AppCompatActivity() {

    private lateinit var laneView: LaneView

    private val laneBeans = listOf(
        LaneBean("哈哈哈哈"),
        LaneBean("fffffff"),
        LaneBean("ddddddd"),
        LaneBean("dkfjdslkfjsdkf"),
        LaneBean("哈heieieie "),
        LaneBean("nihaoadiaoj"),
        LaneBean("55555555555"),
        LaneBean("2333333333"),
        LaneBean("暗恋"),
        LaneBean("三联"),
        LaneBean("skdjkfdjksj"),
        LaneBean("skdj哈哈哈fdjksj"),
        LaneBean("skdj哈哈哈fdjksj"),
        LaneBean("skdj哈哈哈fdjksj"),
        LaneBean("skdj哈哈哈fdjksj"),
        LaneBean("你不知道skdjfdjksj"),
        LaneBean("你不知道skdjfdjksj"),
        LaneBean("你不知道skdjfdjksj"),
        LaneBean("你不知道skdjfdjksj"),
        LaneBean("skdjfdtiantian天天jksj"),
        LaneBean("skdjfdtiantian天天jksj"),
        LaneBean("skdjfdtiantian天天jksj"),
        LaneBean("skdjfdjksj"),
        LaneBean("skdjfdjksj"),
        LaneBean("skdjfdjksj")
    )

    private val contentView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent

            laneView = LaneView(context).apply {
                layout_width = 300
                layout_height = 200
                center_horizontal = true
                background_color = "#00ff00"
                center_vertical = true
                verticalGap = 5
                horizontalGap = 10
                speedMode = LaneView.Speed.Sync
                loopMode = LaneView.Loop.Forever
                duration = 4000L
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
                    (data as? LaneBean)?.let {
                        view.find<TextView>("tv")?.text = it.text
                    }
                }

                onEmpty = {
                    Log.v("ttaylor","tag=, AddViewActivity.()  lane is empty")
                }
            }.also {
                addView(it)
            }

            TextView {
                layout_id = "tv1"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 20f
                text = "add view"
                textColor = "#3F4658"
                gravity = gravity_center
                padding = 10
                bottom_toBottomOf = parent_id
                center_horizontal = true
                background_color = "#ff00ff"
                onClick = {
                    laneView.show(laneBeans)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)
    }
}

data class LaneBean(
    var text: String
)