package test.taylor.com.taylorcode.ui

import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import test.taylor.com.taylorcode.R
import test.taylor.com.taylorcode.kotlin.*

class ImageViewScaleTypeActivity : AppCompatActivity() {

    private val contentView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent

            /**
             * fit_center is the same as center_inside when view is smaller than picture
             * if view is bigger, picture will be stretch to fill all the view in fit_center mode,while center_inside wont
             */
            ImageView {
                layout_id = "tvChange"
                layout_width = 50
                layout_height = 50
                scaleType = scale_center_inside
                top_toTopOf = parent_id
                center_horizontal = true
                background_color = "#00ff00"
                Glide.with(this).load("https://images0.cnblogs.com/blog2015/448960/201507/012312257345289.jpg").into(this)
                onClick = {
                    val toast = Toast(applicationContext)
                    val view = layoutInflater.inflate(R.layout.toast_view,null)
                    toast.view = view
                    toast.duration = Toast.LENGTH_LONG
                    toast.setGravity(Gravity.CENTER,0,0)
                    toast.show()
                }
            }

            ImageView {
                layout_id = "tvChange2"
                layout_width = 50
                layout_height = 50
                scaleType = scale_fit_center
                top_toBottomOf = "tvChange"
                center_horizontal = true
                margin_top =  20
                background_color = "#00ff00"
                Glide.with(this).load("https://images0.cnblogs.com/blog2015/448960/201507/012312257345289.jpg").into(this)
            }

            ImageView {
                layout_id = "tvChange3"
                layout_width = 50
                layout_height = 50
                scaleType = scale_center_inside
                top_toBottomOf = "tvChange2"
                center_horizontal = true
                margin_top =  20
                background_color = "#00ff00"
                Glide.with(this).load("https://upload-images.jianshu.io/upload_images/677256-1fb6afa3593d1b2c.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/1200/format/webp").into(this)
            }

            ImageView {
                layout_id = "tvChange4"
                layout_width = 50
                layout_height = 50
                scaleType = scale_fit_center
                top_toBottomOf = "tvChange3"
                center_horizontal = true
                margin_top =  20
                background_color = "#00ff00"
                Glide.with(this).load("https://upload-images.jianshu.io/upload_images/677256-1fb6afa3593d1b2c.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/1200/format/webp").into(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)
    }
}