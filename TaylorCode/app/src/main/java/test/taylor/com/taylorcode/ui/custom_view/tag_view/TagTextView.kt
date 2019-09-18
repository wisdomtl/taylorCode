package test.taylor.com.taylorcode.ui.custom_view.tag_view

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import android.widget.TextView

class TagTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : TextView(context, attrs, defStyleAttr) {

    var tagBgColor: Int = Color.parseColor("#ff0000")
        set(value) {
            field = value
            bgPaint.color = value
        }
    var tagTextSize: Float = 0F
        set(value) {
            field = value
            textPaint.textSize = value
        }
    var tagTextColor: Int = Color.parseColor("#ffffff")
        set(value) {
            field = value
            textPaint.color = value
        }

    var tagText: String? = null
    var tagTextPaddingTop: Float = 0f
    var tagTextPaddingBottom: Float = 0f
    var tagTextPaddingStart: Float = 0f
    var tagTextPaddingEnd: Float = 0f

    private var textRect: Rect = Rect()
    private var bgPaint: Paint = Paint()
    private var textPaint: Paint = Paint()

    init {
        bgPaint.isAntiAlias = true
        bgPaint.style = Paint.Style.FILL
        bgPaint.color = tagBgColor
        textPaint.color = tagTextColor
        textPaint.textSize = tagTextSize
        textPaint.isAntiAlias = true
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        tagText?.takeIf { it.isNotEmpty() }?.let { text ->
            textPaint.getTextBounds(text, 0, text.length, textRect)
            textPaint.fontMetricsInt.let {
                val bgWidth = (textRect.right - textRect.left) + tagTextPaddingStart + tagTextPaddingEnd
                val bgHeight = tagTextPaddingBottom + tagTextPaddingTop + it.bottom - it.top

                val radius = if (bgWidth > bgHeight) bgWidth / 2 else bgHeight / 2
                val centerX = width - radius
                val centerY = radius
                canvas?.drawCircle(centerX, centerY, radius, bgPaint)

                val textHeight = it.bottom - it.top
                val baseline = radius  + (textHeight / 2 - it.bottom)
                canvas?.drawText(text, width - radius, baseline, textPaint)
            }
        }
    }
}


/**
 * Get [DisplayMetrics] from [Resources]
 */
val Context.displayMetrics: DisplayMetrics get() = resources.displayMetrics

/**
 * Calculate dimensions in pixel of dip
 */
fun Context.dip(value: Float): Float =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, displayMetrics)