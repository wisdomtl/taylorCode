package test.taylor.com.taylorcode.ui.custom_view.progress_view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View

/**
 * a linear progress bar
 */
class ProgressBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {

    companion object {
        const val HORIZONTAL = 1
        const val VERTICAL = 2
    }

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

    var orientation: Int = HORIZONTAL
    var paddingStart: Float = 0f
        set(value) {
            field = value.dp
        }
    var paddingEnd: Float = 0f
        set(value) {
            field = value.dp
        }
    var paddingTop: Float = 0f
        set(value) {
            field = value.dp
        }
    var paddingBottom: Float = 0f
        set(value) {
            field = value.dp
        }

    var padding: Float = 0f
        set(value) {
            field = value.dp
            paddingStart = value
            paddingEnd = value
            paddingTop = value
            paddingBottom = value
        }

    private var _colors = intArrayOf()

    /**
     * the horizontal radius of round corner
     */
    var rx: Float = 0f
        set(value) {
            field = value.dp
        }

    /**
     * the vertical radius of round corner
     */
    var ry: Float = 0f
        set(value) {
            field = value.dp
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
        backgroundRectF.set(0f, 0f, width.toFloat(), height.toFloat())
        canvas?.drawRoundRect(backgroundRectF, rx, ry, barPaint)

        val foregroundWidth = if (orientation == HORIZONTAL) width * progress else width.toFloat()
        val foregroundTop = if (orientation == HORIZONTAL) 0f else height * (1 - progress) + paddingTop
        val foregroundRight = foregroundWidth - paddingEnd
        val foregroundBottom = height.toFloat() - paddingBottom
        val foregroundLeft = paddingStart
        progressPaint.shader =
            if (colors.isEmpty()) null else LinearGradient(
                foregroundLeft,
                foregroundTop,
                foregroundRight,
                foregroundBottom,
                _colors,
                null,
                Shader.TileMode.CLAMP
            )
        foregroundRectF.set(foregroundLeft, foregroundTop, foregroundRight, foregroundBottom)
        canvas?.drawRoundRect(foregroundRectF, rx, ry, progressPaint)
    }
}

val Float.dp: Float
    get() {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            Resources.getSystem().displayMetrics
        )
    }