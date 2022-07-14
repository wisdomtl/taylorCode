package test.taylor.com.taylorcode.ui.custom_view

import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.applyCanvas
import kotlinx.android.synthetic.main.publish_login_pop_up.*
import test.taylor.com.taylorcode.R
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.ui.custom_view.clip_path.crop

class DrawTriangleActivity : AppCompatActivity() {

    private lateinit var tv: TextView

    private val contentView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent

            tv = TextView {
                layout_id = "tvChange"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 12f
                textColor = "#D2D3DC"
                text = "前100名可获得现金奖励"
                gravity = gravity_center
                padding_top = 15
                padding_bottom = 9
                padding_horizontal = 11
                onClick = {
                    val window = PopupWindow()
                    val view = LayoutInflater.from(this@DrawTriangleActivity).inflate(R.layout.publish_login_pop_up,null)
                    view.setRoundTriangleBackground(20f.dp, 8f.dp, 6f.dp, 120f.dp,TriangleGravity.TOP,Color.parseColor("#000000"))
                    window.contentView = view
                    window.width = ViewGroup.LayoutParams.WRAP_CONTENT
                    window.height = ViewGroup.LayoutParams.WRAP_CONTENT
                    window.showAsDropDown(tv,0,0)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)
//        tvkkk.setRoundTriangleBackground(20f.dp, 8f.dp, 6f.dp, 120f.dp,Color.parseColor("#000000"))
    }
}
/**
 * set a special background for View, the background is a round rect with a little triangle beneath
 * @param radius the radius of round rect
 * @param triangleWidth the width of triangle
 * @param triangleHeight the height of triangle
 * @param offsetX the horizontal offset of triangle according to View
 * @param color the color of background
 */
fun View.setRoundTriangleBackground(radius: Float, triangleWidth: Float, triangleHeight: Float, offsetX: Float, gravity: TriangleGravity, color: Int) {
    this.post {
        val triangleY = if (gravity == TriangleGravity.BOTTOM) this.height - triangleHeight else triangleHeight
        val bitmap = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888).applyCanvas {
            val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                this.color = color
                style = Paint.Style.FILL
            }
            val top = if(gravity == TriangleGravity.BOTTOM) 0f else triangleHeight
            drawRoundRect(
                RectF(0f, top, this.width.toFloat(), this.height.toFloat() - triangleHeight),
                radius,
                radius,
                paint
            )
            drawPath(Path().apply {
                val peak = if (gravity == TriangleGravity.BOTTOM) triangleY + triangleHeight else triangleY - triangleHeight
                moveTo(offsetX, triangleY)
                lineTo(offsetX + triangleWidth / 2, peak)
                lineTo(offsetX + triangleWidth, triangleY)
                close()
            }, paint)
        }
        this.background = BitmapDrawable(resources, bitmap)
    }
}

enum class TriangleGravity {
    TOP,
    BOTTOM
}