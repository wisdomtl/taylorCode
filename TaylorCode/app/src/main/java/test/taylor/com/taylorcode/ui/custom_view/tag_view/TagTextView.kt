package test.taylor.com.taylorcode.ui.custom_view.tag_view

import android.content.Context
import android.graphics.*
import androidx.appcompat.widget.AppCompatTextView
import android.util.AttributeSet

open class TagTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : AppCompatTextView(context, attrs, defStyleAttr) {

    var tagBgColor: Int = Color.parseColor("#FFFF5183")
        set(value) {
            field = value
            bgPaint.color = value
        }
    var tagTextSize: Float = 0F
        set(value) {
            field = value
            textPaint.textSize = value
        }
    var tagTextColor: Int = Color.parseColor("#FFFFFF")
        set(value) {
            field = value
            textPaint.color = value
        }
    var tagTextTypeFace: Typeface? = null

    var tagText: String? = null
    var tagTextPaddingTop: Float = 5f
    var tagTextPaddingBottom: Float = 5f
    var tagTextPaddingStart: Float = 5f
    var tagTextPaddingEnd: Float = 5f

    private var textRect: Rect = Rect()
    private var bgPaint: Paint = Paint()
    private var textPaint: Paint = Paint()

    init {
        bgPaint.apply {
            isAntiAlias = true
            style = Paint.Style.FILL
            color = tagBgColor
        }
        textPaint.apply {
            color = tagTextColor
            textSize = tagTextSize
            isAntiAlias = true
            textAlign = Paint.Align.CENTER
            style = Paint.Style.FILL
            tagTextTypeFace?.let { typeface = tagTextTypeFace }
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (oldw != 0 && oldh != 0) {
            val widthRatio = w.toFloat() / oldw
            widthRatio.takeIf { it > 0 }?.let { tagTextSize *= widthRatio }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        tagText?.takeIf { it.isNotEmpty() }?.let { text ->
            textPaint.apply {
                getTextBounds(text, 0, text.length, textRect)
                fontMetricsInt.let {
                    val bgWidth = (textRect.right - textRect.left) + tagTextPaddingStart + tagTextPaddingEnd
                    val bgHeight = tagTextPaddingBottom + tagTextPaddingTop + it.bottom - it.top

                    val radius = if (bgWidth > bgHeight) bgWidth / 2 else bgHeight / 2
                    val centerX = width - radius
                    val centerY = radius
                    canvas?.drawCircle(centerX, centerY, radius, bgPaint)

                    val textHeight = it.bottom - it.top
                    val baseline = radius + (textHeight / 2 - it.bottom)
                    canvas?.drawText(text, width - radius, baseline, textPaint)
                }
            }
        }
    }
}


