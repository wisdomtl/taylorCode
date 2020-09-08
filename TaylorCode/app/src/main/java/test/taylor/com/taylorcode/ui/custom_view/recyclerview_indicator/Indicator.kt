package test.taylor.com.taylorcode.ui.custom_view.recyclerview_indicator

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import test.taylor.com.taylorcode.util.dp

class Indicator @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {

    /**
     * the background color of the whole bar
     */
    var barColor: String = "#00ff00"
        set(value) {
            field = value
            barPaint.color = Color.parseColor(value)
        }

    /**
     * the color of progress part
     */
    var progressColor: String = "#ff00ff"
        set(value) {
            field = value
            progressPaint.color = Color.parseColor(value)
        }

    /**
     * the horizontal radius of round corner
     */
    var rx: Float = 0f
        set(value) {
            field = value.dp().toFloat()
        }

    /**
     * the vertical radius of round corner
     */
    var ry: Float = 0f
        set(value) {
            field = value.dp().toFloat()
        }

    var indicatorWidth: Float = 0f
        set(value) {
            field = value.dp().toFloat()
        }

    /**
     * the progress of bar, range from 0 to 1
     */
    var progress: Float = 0f
        set(value) {
            field = value
            postInvalidate()
        }

    private var barPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor(barColor)
        style = Paint.Style.FILL
    }

    private var progressPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor(progressColor)
        style = Paint.Style.FILL
    }

    private var foregroundRectF = RectF()
    private var backgroundRectF = RectF()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        backgroundRectF.set(0f, 0f, width.toFloat(), height.toFloat())
        canvas?.drawRoundRect(backgroundRectF, rx, ry, barPaint)

        val indicatorLeft = (width - indicatorWidth) * progress
        val indicatorRight = indicatorLeft + indicatorWidth
        val indicatorTop = 0f
        val indicatorBottom = height.toFloat()
        foregroundRectF.set(indicatorLeft, indicatorTop, indicatorRight, indicatorBottom)
        canvas?.drawRoundRect(foregroundRectF, rx, ry, progressPaint)
    }
}

