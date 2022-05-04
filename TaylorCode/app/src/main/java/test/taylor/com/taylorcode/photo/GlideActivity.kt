package test.taylor.com.taylorcode.photo

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.startActivity

class GlideActivity : AppCompatActivity() {

    private lateinit var iv: ImageView
    private val contentView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent

            iv = ImageView {
                layout_id = "tvChange"
                layout_width = 100
                layout_height = 100
                scaleType = scale_fit_xy
                center_vertical = true
                center_horizontal = true
            }

            TextView {
                layout_id = "tvChange"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 12f
                textColor = "#ffffff"
                text = "go to another activity"
                gravity = gravity_center
                shape = shape {
                    corner_radius = 50
                    solid_color = "#ff00ff"
                }
                onClick = {
                    startActivity<GlideActivity2>()
                }
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)
        Glide.with(applicationContext)
            .load("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2F68c7e4ec9a8b3804d387012c8f2c2c69f9db95a42b695-Rxfr9z_fw658&refer=http%3A%2F%2Fhbimg.b0.upaiyun.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1653817842&t=9fa35989a42b23f39f692e03aebe4672")
            .into(iv)
    }
}