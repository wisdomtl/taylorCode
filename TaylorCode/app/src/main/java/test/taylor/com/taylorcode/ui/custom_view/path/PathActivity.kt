package test.taylor.com.taylorcode.ui.custom_view.path

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import test.taylor.com.taylorcode.kotlin.*

class PathActivity : AppCompatActivity() {

    private lateinit var clockView: ClockView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            ConstraintLayout {
                layout_width = match_parent
                layout_height = match_parent

                clockView = ClockView(context).apply {
                    layout_id = "cv"
                    layout_width = 300
                    layout_height = 300
                    center_horizontal = true
                    center_vertical = true
                    addView(this)
                    background_color = "#f9f9f9"
                }

                TextView {
                    layout_width = wrap_content
                    layout_height = wrap_content
                    textSize = 16f
                    textColor ="#3F4658"
                    gravity = gravity_center
                    top_toBottomOf = "cv"
                    text = "start"
                    center_horizontal = true
                    onClick = {
                        clockView.start()
                    }
                }
            }
        )
    }
}