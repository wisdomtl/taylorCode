package test.taylor.com.taylorcode.ui.anim

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.ui.custom_view.bullet_screen.LaneView

class AddViewActivity : AppCompatActivity() {

    private lateinit var viewGroup: ViewGroup

    private val contentView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent

            viewGroup = LaneView(context).apply {
                layout_width = 300
                layout_height = 200
                center_horizontal = true
                background_color = "#00ff00"
                center_vertical = true
                verticalGap = 0
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
                    val tv = TextView(autoAdd = false) {
                        layout_width = wrap_content
                        layout_height = 100
                        gravity = gravity_center
                        background_color ="#0000ff"
                        textSize = 20f
                        text = "asdf"
                    }
                    viewGroup.addView(tv)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)
    }
}