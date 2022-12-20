package test.taylor.com.taylorcode.ui.zoom

import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.Rect
import android.graphics.RectF
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import test.taylor.com.taylorcode.R
import test.taylor.com.taylorcode.kotlin.ConstraintLayout
import test.taylor.com.taylorcode.kotlin.*
import kotlin.math.min

class ZoomActivity : AppCompatActivity() {

    private val HEIGHT = 200f
    private val WIDTH = 300f

    private lateinit var iv: ImageView
    private lateinit var iv2: ImageView

//    private val imgRes = R.drawable.portrait

    private val contentView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent

//            iv = TouchImageView(context).apply {
//                layout_width = match_parent
//                layout_height = match_parent
//                center_horizontal = true
//                center_vertical = true
//                src = imgRes
//                background_color = "#ff00ff"
//                initWidth = WIDTH.dp.toInt()
//                initHeight = HEIGHT.dp.toInt()
//
//            }.also { addView(it) }

            iv2 = ImageView {
                layout_width = 300
                layout_height = 500
                center_horizontal = true
                center_vertical = true
//                src = R.drawable.dkfdjsdk
                background_color = "#ff0000"
//                fitIntoRect(RectF(0.17f,0.38f,0.83f,0.62f))

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)

//        setMatrix()
    }

    private fun setMatrix() {
        /**
         * case: scale ImageView with matrix
         */
//        fitIntoRect()
    }

//    private fun fitIntoRect() {
//        iv2.post {
//            iv2.imageMatrix = Matrix().apply {
//                val (width, height) = iv2.drawable?.let { it.intrinsicWidth to it.intrinsicHeight } ?: return@post
//                val widthScale = iv2.measuredWidth / width.toFloat()
//                val heightScale = iv2.measuredHeight / height.toFloat()
//                val scale = min(widthScale, heightScale)
//                postScale(scale, scale)
//                val translateX = (iv2.measuredWidth - scale * width) / 2
//                val translateY = (iv2.measuredHeight - scale * height) / 2
//                postTranslate(translateX, translateY)
//            }
//        }
//    }
}

fun ImageView.fitIntoRect(rect: RectF? = null) {
    post {
        val r = rect ?: RectF(0f, 0f, 1f, 1f)
        scaleType = ImageView.ScaleType.MATRIX
        imageMatrix = imageMatrix.apply {
            val (imgWidth, imgHeight) = drawable?.let { it.intrinsicWidth to it.intrinsicHeight } ?: return@post
            val scaleX = r.width() * measuredWidth / imgWidth.toFloat()
            val scaleY = r.height() * measuredHeight / imgHeight.toFloat()
            val scale = min(scaleX, scaleY)
            postScale(scale, scale)
            val dx = r.centerX() * measuredWidth - scale * imgWidth / 2
            val dy = r.centerY() * measuredHeight - scale * imgHeight / 2
            postTranslate(dx, dy)
        }
    }
}

fun ImageView.fitTop() {
    post {
        scaleType = ImageView.ScaleType.MATRIX
        imageMatrix = imageMatrix.apply {
            val (imgWidth, imgHeight) = drawable?.let { it.intrinsicWidth to it.intrinsicHeight } ?: return@post
            val widgetHeight = layoutParams.height
            if (imgHeight < widgetHeight) {
                val dy = (widgetHeight - imgHeight) / 2
                setTranslate(0f,dy.toFloat())
            }
        }
    }
}