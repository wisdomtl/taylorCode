package test.taylor.com.taylorcode.ui.touch_event.touch_delegate

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import test.taylor.com.taylorcode.kotlin.ConstraintLayout
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.kotlin.extension.expand

class TouchDelegateActivity : AppCompatActivity() {

    private lateinit var container: ViewGroup

    private lateinit var child1: TextView
    private lateinit var child2: TextView

    private val contentView by lazy {
        container = ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent

            child1 = TextView {
                layout_id = "tvChange"
                layout_width = 200
                layout_height = 100
                textSize = 12f
                textColor = "#ffffff"
                text = "save"
                gravity = gravity_center
                center_horizontal = true
                center_vertical = true
                background_color = "#ffff00"
                onClick = {
                    Log.v("ttaylor", "delegate view is click()")
                }
            }

            child2 = TextView {
                layout_id = "tvChange"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 12f
                textColor = "#ffffff"
                text = "save"
                gravity = gravity_center
                bottom_toBottomOf = parent_id
                center_horizontal = true
                background_color = "#00ff00"
                onClick =  {
                    Log.v("ttaylor","delegate view 2 is clicked()")
                }
            }
        }

        container
    }

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)

        child1.expand(100,100)
        child2.expand(0,300)
    }
}