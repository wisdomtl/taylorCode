package test.taylor.com.taylorcode.ui.one

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.os.Build
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.M)
class OneViewGroup @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private var textPaint: TextPaint? = null
    private var staticLayout: StaticLayout? = null
    var text: CharSequence = ""
    var textSize: Float = 0f
    var textColor: Int = Color.parseColor("#ff00ff")
    var textWidth: Int = 0
    var spaceAdd: Float = 0f
    var spcaeMult: Float = 1.0f



    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        if (textPaint == null) {
            textPaint = TextPaint().apply {
                isAntiAlias = true
                textSize = this@OneViewGroup.textSize
                color = textColor
            }
        }

        if (staticLayout == null) {
            staticLayout = StaticLayout.Builder.obtain(text, 0, text.length, textPaint!!, textWidth)
                .setAlignment(Layout.Alignment.ALIGN_NORMAL)
                .setLineSpacing(spaceAdd, spcaeMult)
                .setIncludePad(false)
                .build()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.save()
        canvas?.translate(paddingLeft.toFloat(), paddingTop.toFloat())
        staticLayout?.draw(canvas)
        canvas?.restore()
    }

    interface Drawable {
        fun measure(widthMeasureSpec: Int, heightMeasureSpec: Int)
        fun draw(canvas: Canvas?)
    }

    class Text : Drawable {
        var textPaint: TextPaint? = null
        var staticLayout: StaticLayout? = null
        var text: CharSequence = ""
        var textSize: Float = 0f
        var textColor: Int = Color.parseColor("#ff00ff")
        var textWidth: Int = 0
        var spaceAdd: Float = 0f
        var spcaeMult: Float = 1.0f
        override fun measure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        }

        override fun draw(canvas: Canvas?) {
        }
    }

    class Image : Drawable {
        override fun measure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        }

        override fun draw(canvas: Canvas?) {
        }

    }
}