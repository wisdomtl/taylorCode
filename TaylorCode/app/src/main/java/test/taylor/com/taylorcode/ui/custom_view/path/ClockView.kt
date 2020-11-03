package test.taylor.com.taylorcode.ui.custom_view.path

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import test.taylor.com.taylorcode.ui.custom_view.progress_view.dp

/**
 * a linear progress bar
 */
class ClockView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {

    var radius = 50f.dp
    var degree = 0f
    private var pointerPath = Path().apply {
        fillType = Path.FillType.WINDING
        reset()
        lineTo(0f, radius)
    }
    private var paint = Paint(ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#ff00ff")
        style = Paint.Style.STROKE
        strokeWidth = 10f
    }

    fun start() {
        post(object : Runnable {
            override fun run() {
                degree += 1
                invalidate()
                postDelayed(this, 1000)
            }
        })
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        canvas?.translate((width / 2).toFloat(), (height / 2).toFloat())
        pointerPath.reset()
        pointerPath.apply {
            lineTo(0f, radius)
        }
        /**
         * case: rotating canvas for drawing clock pointer
         */
        canvas?.rotate(degree)
        /**
         * case: draw path
         */
        canvas?.drawPath(pointerPath, paint)
    }
}
