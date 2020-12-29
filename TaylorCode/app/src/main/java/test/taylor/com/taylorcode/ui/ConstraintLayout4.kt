package test.taylor.com.taylorcode.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Guideline
import test.taylor.com.taylorcode.R
import test.taylor.com.taylorcode.kotlin.*

class ConstraintLayout4 : AppCompatActivity() {
    private lateinit var  tv1:TextView
    private lateinit var  tv2:TextView
    private lateinit var  tv3:TextView

    private val contentView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent

           Guideline {
               layout_id = "glTop"
               layout_width = wrap_content
               layout_height = wrap_content
               guide_percentage = 0.5f
               guide_orientation = vertical
           }

            tv1 = TextView {
                layout_id = "tv1"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 16f
                textColor = "#3F4658"
                gravity = gravity_center
                fontFamily = R.font.pingfang
                text = "ttt1"
                end_toEndOf = parent_id
                center_vertical = true
            }

            tv2 = TextView {
                layout_id = "tv2"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 16f
                textColor = "#3F4658"
                gravity = gravity_center
                fontFamily = R.font.pingfang
                text = "ttt2"
                end_toStartOf = "tv1"
                margin_end = 10
                center_vertical = true
            }

            tv3 = TextView {
                layout_id = "tv3"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 16f
                textColor = "#3F4658"
                gravity = gravity_center
                fontFamily = R.font.pingfang
                text = "ttt3"
                end_toStartOf = "tv2"
                margin_end = 10
                center_vertical = true
            }

            TextView {
                layout_id = "tvDelete"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 16f
                textColor = "#3F4658"
                gravity = gravity_center
                fontFamily = R.font.pingfang
                bottom_toBottomOf = parent_id
                text = "delete"
                onClick = {
                 tv2.visibility = gone
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)
    }
}