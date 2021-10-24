package test.taylor.com.taylorcode.ui.zoom

import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.scaleMatrix
import test.taylor.com.taylorcode.R
import test.taylor.com.taylorcode.kotlin.ConstraintLayout
import test.taylor.com.taylorcode.kotlin.*

class ZoomActivity : AppCompatActivity() {

    private val HEIGHT = 200
    private val WIDTH = 300

    private lateinit var iv: ImageView
    private lateinit var iv2: ImageView

    private val imgRes = R.drawable.portrait

    private val contentView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent

            iv = TouchImageView(context).apply {
                layout_width = match_parent
                layout_height = match_parent
                center_horizontal = true
                center_vertical = true
//                src = R.drawable.landscape
                src = imgRes
                background_color = "#ff00ff"
                initWidth = WIDTH.dp
                initHeight = HEIGHT.dp
            }.also { addView(it) }

//            iv2 = ImageView {
//                layout_width = WIDTH
//                layout_height = HEIGHT
//                center_horizontal = true
//                center_vertical = true
//                src = R.drawable.portrait
//                scaleType = scale_matrix
//                background_color = "#ff0000"
//            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)

        setMatrix()
    }

    private fun setMatrix() {
        /**
         * case: scale ImageView with matrix
         */
//        iv.post {
//            iv.imageMatrix = Matrix().apply {
//                val (width, height) = BitmapFactory.Options().let { option ->
//                    option.inJustDecodeBounds = true
//                    BitmapFactory.decodeResource(resources, imgRes, option)
//                    option.outWidth to option.outHeight
//                }
//                val widthScale = WIDTH.dp / width
//                val heightScale = HEIGHT.dp / height
//                Log.v("ttaylor", "setMatrix() width=$width,height=$height,wScale=$widthScale,hScale=$heightScale, height.dp=${HEIGHT.dp}")
//                postScale(heightScale, heightScale)
//            }
//        }

//        iv.post {
//            iv.imageMatrix = Matrix().apply {
//                val sx = 0.5f
//                val sy = 0.5f
//                postScale(sx,sy)
////                val tx = iv.measuredWidth/2f - (iv.measuredWidth/2f * sx)
////                val ty = iv.measuredHeight/2f -(iv.measuredHeight/2f *sy)
////                postTranslate(tx,ty)
//            }
//        }
    }
}