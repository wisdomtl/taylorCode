package test.taylor.com.taylorcode.ui.one

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import test.taylor.com.taylorcode.kotlin.ConstraintLayout
import test.taylor.com.taylorcode.kotlin.*

@RequiresApi(Build.VERSION_CODES.M)
class OneActivity : AppCompatActivity() {

    private val contentView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent

            OneViewGroup(this@OneActivity).apply {
                layout_width = 200
                layout_height = 300
                background_color = "#ff00ff"
                center_horizontal = true
                center_vertical = true

                text {
                    id = "title"
                    maxWidth = 100
                    text = "title"
                    textSize = 40f
                    textColor = "#ffffff"
                    leftPercent = 0.2f
                    topPercent = 0.2f
                }

                text {
                    maxWidth = 60
                    text = "content"
                    textSize = 15f
                    textColor ="#88ffffff"
                    topToBottomOf = "title"
                    startToStartOf = "title"
                }

                text {
                    maxWidth = 70
                    text = "subTitle"
                    textSize = 18f
                    textColor = "#ffffff"
                    centerVerticalOf = "title"
                    startToEndOf = "title"
                }

                text {
                    maxWidth = 70
                    text = "left"
                    textSize = 15f
                    textColor = "#ffffff"
                    endToStartOf = "title"
                    centerVerticalOf = "title"
                    rightMargin = 10
                }

                text {
                    maxWidth = 70
                    text = "top"
                    textSize = 15f
                    textColor = "#ffffff"
                    bottomToTopOf = "title"
                    centerHorizontalOf = "title"
                    bottomMargin = 20
                }

            }.also { addView(it) }

//            TextView {
//                layout_id = "title"
//                layout_width = wrap_content
//                layout_height = wrap_content
//                textSize = 20f
//                textColor = "#ffffff"
//                text = "title"
//                gravity = gravity_center
//                start_toStartOf = parent_id
//                top_toTopOf = parent_id
//                background_color = "#00ff00"
//            }
//
//            TextView {
//                layout_id = "content"
//                layout_width = wrap_content
//                layout_height = wrap_content
//                textSize = 12f
//                textColor = "#ffffff"
//                text = "content"
//                gravity = gravity_center
//                top_toBottomOf = "title"
//                start_toStartOf = "title"
//                background_color = "#ffff00"
//            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)
    }
}