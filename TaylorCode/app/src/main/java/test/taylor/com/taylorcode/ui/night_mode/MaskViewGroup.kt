package test.taylor.com.taylorcode.ui.night_mode

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.FrameLayout

class MaskViewGroup @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {

    private lateinit var paint: Paint

    init {
        //this is must, or no drawing will show in ViewGroup
        setWillNotDraw(false)
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.parseColor("#c8000000")
    }

    override fun onDrawForeground(canvas: Canvas?) {
        super.onDrawForeground(canvas)
        canvas?.drawRect(0f, 0f, right.toFloat(), bottom.toFloat(), paint)
    }
}