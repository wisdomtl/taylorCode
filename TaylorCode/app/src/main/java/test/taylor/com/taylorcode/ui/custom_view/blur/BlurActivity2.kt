package test.taylor.com.taylorcode.ui.custom_view.blur

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import test.taylor.com.taylorcode.R
import test.taylor.com.taylorcode.kotlin.*

class BlurActivity2:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(
            ConstraintLayout {
                layout_width = match_parent
                layout_height = match_parent

                ImageView {
                    layout_width = 100
                    layout_height = 100
                    scaleType = scale_fit_xy
                    center_horizontal = true
                    center_vertical = true
                    src = R.drawable.old_man
                }

                TextView {
                    layout_width  = 100
                    layout_height = 50
                    text = "show blur mask"
                    textSize = 40f
                    bottom_toBottomOf = parent_id
                    center_horizontal = true
                    onClick = {
                        BlurDialogFragment.show(supportFragmentManager)
                    }
                }
            }
        )
    }
}