package test.taylor.com.taylorcode.ui

import android.os.Bundle
import android.widget.ViewFlipper
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import test.taylor.com.taylorcode.kotlin.*

class ViewFlipperActivity : AppCompatActivity() {

    private lateinit var vp: ViewFlipper

    private val imgUrls = mutableListOf(
        "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3030050658,3694586235&fm=26&gp=0.jpg"
        , "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2571315283,182922750&fm=26&gp=0.jpg",
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1591790054139&di=627d2e1d16d93f1f2fdcac074a623d39&imgtype=0&src=http%3A%2F%2Fpngimg.com%2Fuploads%2Fdonald_trump%2Fdonald_trump_PNG56.png"
    )

    private val rootView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent

            vp = ViewFlipper {
                layout_width = match_parent
                layout_height = 200
                background_color = "#ff00ff"
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(rootView)

        addViewToFlipper()
    }

    private fun addViewToFlipper() {
        imgUrls.forEach {url->
            val iv = ImageView {
                layout_width = match_parent
                layout_height = 200
                scaleType = scale_fix_xy
            }

            vp.addView(iv)
            Glide.with(this).load(url).into(iv)
        }
        vp.startFlipping()
    }
}