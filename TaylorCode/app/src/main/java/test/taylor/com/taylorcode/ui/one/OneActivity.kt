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
                padding = 10

                text {
                    text = "dskfsldkfkldsflksdjlk;f;jdlskfj;;lksdjflksdj;lfsad;jklfkldjfslkjflskdflkasdjlkfsadl"
                    textSize = 30.dp.toFloat()
                    textColor = Color.parseColor("#ffffff")
                    textWidth = 200.dp
                }
            }.also { addView(it) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)
    }
}