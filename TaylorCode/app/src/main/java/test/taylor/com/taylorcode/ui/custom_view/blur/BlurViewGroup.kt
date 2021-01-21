package test.taylor.com.taylorcode.ui.custom_view.blur

import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.FrameLayout

class BlurViewGroup @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    FrameLayout(context, attrs, defStyleAttr) {

    var radius: Float = 10f

    var blurColor:Int = Color.parseColor("#A30C0B1C")

    private val blurPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        maskFilter = BlurMaskFilter(radius, BlurMaskFilter.Blur.NORMAL)
        this.color = blurColor
    }

    init {
        setWillNotDraw(false)
    }

    override fun draw(canvas: Canvas?) {
        val left = 0F
        val right = measuredWidth.toFloat()
        val top = 0F
        val bottom = measuredHeight.toFloat()
        canvas?.drawRect(left, top, right, bottom, blurPaint)
        super.draw(canvas)
    }
}