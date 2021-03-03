package test.taylor.com.taylorcode.ui.performance.widget

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import test.taylor.com.taylorcode.kotlin.PercentLayout
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.kotlin.layout_width
import test.taylor.com.taylorcode.kotlin.match_parent

class PercentActivity:AppCompatActivity() {

    private val contentView by lazy {
        PercentLayout {
            layout_width = match_parent
            layout_height = match_parent
            background_color = "#80c9c9c9"

            TextView {
                layout_id = "tvChange"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 50f
                textColor = "#ffffff"
                text = "save"
                gravity = gravity_center
                top_percent = 0.5f
                left_percent = 0.5f
                shape = shape {
                    corner_radius = 10
                    solid_color = "#ff00ff"
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)
    }
}