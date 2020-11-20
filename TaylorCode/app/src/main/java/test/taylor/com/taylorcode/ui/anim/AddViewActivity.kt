package test.taylor.com.taylorcode.ui.anim

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.ui.custom_view.bullet_screen.LiveCommentView2

class AddViewActivity : AppCompatActivity() {

    private lateinit var viewGroup: ViewGroup

    private val contentView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent

            viewGroup = LiveCommentView2(context).apply {
                layout_width = 300
                layout_height = 100
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
//            TextView {
//                layout_id = "tv2"
//                layout_width = wrap_content
//                layout_height = wrap_content
//                textSize = 20f
//                text = "add view2"
//                textColor = "#3F4658"
//                padding = 10
//                gravity = gravity_center
//                bottom_toTopOf = "tv1"
//                center_horizontal = true
//                background_color = "#ff00ff"
//                onClick = {
//                    viewGroup.addView(
//                        TextView(autoAdd = false) {
//                            layout_width = wrap_content
//                            layout_height = wrap_content
//                            textSize = 20f
//                            text = "dddddddddd"
//                        })
//                }
//            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)
    }
}