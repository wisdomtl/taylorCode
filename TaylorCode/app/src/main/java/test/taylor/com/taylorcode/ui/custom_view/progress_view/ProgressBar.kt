package test.taylor.com.taylorcode.ui.custom_view.progress_view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import kotlin.properties.Delegates

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
    var backgroundColor: String = "#00ff00"
        set(value) {
            field = value
            barPaint.color = Color.parseColor(value)
        }

    var progress: Progress? = null

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

    /**
     * the horizontal radius of background round corner
     */
    var backgroundRx: Float = 0f
        set(value) {
            field = value.dp
        }

    /**
     * the vertical radius of background round corner
     */
    var backgroundRy: Float = 0f
        set(value) {
            field = value.dp
        }

    /**
     * the horizontal radius of progress round corner
     */
    var progressRx: Float = 0f
        set(value) {
            field = value.dp
        }

    /**
     * the vertical radius of progress round corner
     */
    var progressRy: Float = 0f
        set(value) {
            field = value.dp
        }

    /**
     * the percentage of bar, range from 0% to 100%
     */
    var percentage: Int by Delegates.observable(0) { _, oldValue, newValue ->
        progress?.onPercentageChange(oldValue, newValue)
        postInvalidate()
    }

    private var barPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor(backgroundColor)
        style = Paint.Style.FILL
    }


    var progressRectF = RectF()
    private var backgroundRectF = RectF()

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        backgroundRectF.set(0f, 0f, width.toFloat(), height.toFloat())
        canvas?.drawRoundRect(backgroundRectF, backgroundRx, backgroundRy, barPaint)

        val progressMaxWidth = width - paddingStart - paddingEnd
        val progressMaxHeight = height - paddingTop - paddingBottom
        val progressWidth = if (orientation == HORIZONTAL) progressMaxWidth * percentage / 100F else progressMaxWidth.toFloat()
        val progressTop = if (orientation == HORIZONTAL) paddingTop else progressMaxHeight * (1 - percentage / 100F) + paddingTop
        val progressBottom = height - paddingBottom
        val progressLeft = paddingStart
        progressRectF.set(progressLeft, progressTop, progressLeft + progressWidth, progressBottom)
        progress?.draw(canvas, this)
    }
}

interface Progress {
    fun draw(canvas: Canvas?, progressBar: ProgressBar)

    fun onPercentageChange(old: Int, new: Int) {}
}

val Float.dp: Float
    get() {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            Resources.getSystem().displayMetrics
        )
    }