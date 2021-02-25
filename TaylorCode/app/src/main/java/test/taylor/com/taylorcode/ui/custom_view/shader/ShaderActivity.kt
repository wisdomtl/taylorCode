package test.taylor.com.taylorcode.ui.custom_view.shader

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import test.taylor.com.taylorcode.R
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.ui.custom_view.clip_path.crop

class ShaderActivity : AppCompatActivity() {

    private val contentView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent

            ImageView {
                layout_id = "tvChange"
                layout_width = 100
                layout_height = 100
                scaleType = scale_fit_xy
                center_horizontal = true
                center_vertical = true
                bitmap = getMutableBitmap(R.drawable.p1).crop(300f)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)
    }
}

fun Context.getMutableBitmap(res: Int): Bitmap = BitmapFactory.decodeResource(resources, res).copy(Bitmap.Config.ARGB_8888, true)