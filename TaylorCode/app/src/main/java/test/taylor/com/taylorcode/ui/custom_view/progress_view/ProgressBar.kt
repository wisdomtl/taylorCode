package test.taylor.com.taylorcode.ui.custom_view.progress_view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import test.taylor.com.taylorcode.util.dp

/**
 * a linear progress bar
 */
class ProgressBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
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
     * the gradient colors of progress part
     */
    var colors = emptyArray<String>()
        set(value) {
            field = value
            _colors = value.map { Color.parseColor(it) }.toIntArray()
        }

    private var _colors = intArrayOf()

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

    /**
     * the progress of bar, range from 0 to 1
     */
    var progress: Float = 0f

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

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        progressPaint.shader =
            if (colors.isEmpty()) null else LinearGradient(0f, 0f, width * progress, height.toFloat(), _colors, null, Shader.TileMode.CLAMP)
        backgroundRectF.set(0f, 0f, width.toFloat(), height.toFloat())
        canvas?.drawRoundRect(backgroundRectF, rx, ry, barPaint)
        foregroundRectF.set(0f, 0f, width * progress, height.toFloat())
        canvas?.drawRoundRect(foregroundRectF, rx, ry, progressPaint)
    }
}