package test.taylor.com.taylorcode.ui.custom_view.time_picker

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import test.taylor.com.taylorcode.kotlin.*

class TimePickerActivity:AppCompatActivity() {

    private val contentView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent
            background_color = "#ffffff"


            TextView {
                layout_id = "tvChang2"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 30f
                textColor = "#ffff77"
                text = "sssssj"
                gravity = gravity_center
                top_toTopOf = parent_id
                center_horizontal = true
                onClick = {
                    Log.v("ttaylor","onclick")
                }
            }

            TextView {
                layout_id = "tvChange"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 20f
                textColor = "#000000"
                text = "show "
                gravity = gravity_center
                center_horizontal = true
                center_vertical = true
                background_color = "#ffff00"
                padding = 10
                onClick = {
                    val fragment  = SelectTimeDialogFragment()
                    fragment.show(supportFragmentManager,"time")
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)
    }
}