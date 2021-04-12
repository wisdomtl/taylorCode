package test.taylor.com.taylorcode.ui.one

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import test.taylor.com.taylorcode.kotlin.dp
import kotlin.math.min

@RequiresApi(Build.VERSION_CODES.M)
class OneViewGroup @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private val drawableMap = HashMap<String, Drawable>()
    private val drawables = mutableListOf<Drawable>()

    fun addDrawable(drawable: Drawable) {
        drawables.add(drawable)
        drawableMap[drawable.id] = drawable
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        drawables.forEach { it.measure(widthMeasureSpec, heightMeasureSpec) }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val parentWidth = right - left
        val parentHeight = bottom - top
        drawables.forEach {
            val left = getChildLeft(it, parentWidth)
            val top = getChildTop(it, parentHeight)
            it.setRect(left, top, left + it.measuredWidth, top + it.measuredHeight)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawables.forEach { it.draw(canvas) }
    }

    private fun getChildTop(drawable: Drawable, parentHeight: Int): Int {
        return when {
            drawable.topPercent != -1f -> (parentHeight * drawable.topPercent).toInt()
            drawable.centerVerticalOf.isNotEmpty() -> {
                if (drawable.centerVerticalOf == parent_id) {
                    (parentHeight - drawable._height) / 2
                } else {
                    (drawableMap[drawable.centerVerticalOf]?.let { it.top + (it.bottom - it.top) / 2 } ?: 0) - drawable.measuredHeight / 2
                }
            }
            drawable.topToBottomOf.isNotEmpty() -> {
                val b = if (drawable.topToBottomOf == parent_id) bottom else drawableMap[drawable.topToBottomOf]?.bottom ?: 0
                (b + drawable._topMargin)
            }
            drawable.topToTopOf.isNotEmpty() -> {
                val t = if (drawable.topToTopOf == parent_id) top else drawableMap[drawable.topToTopOf]?.top ?: 0
                (t + drawable._topMargin)
            }
            drawable.bottomToTopOf.isNotEmpty() -> {
                val t = if (drawable.bottomToTopOf == parent_id) top else drawableMap[drawable.bottomToTopOf]?.top ?: 0
                (t - drawable._bottomMargin) - drawable.measuredHeight
            }
            drawable.bottomToBottomOf.isNotEmpty() -> {
                val b = if (drawable.bottomToBottomOf == parent_id) bottom else drawableMap[drawable.bottomToBottomOf]?.bottom ?: 0
                (b - drawable._bottomMargin) - drawable.measuredHeight
            }
            else -> 0
        }
    }

    private fun getChildLeft(drawable: Drawable, parentWidth: Int): Int {
        return when {
            drawable.leftPercent != -1f -> (parentWidth * drawable.leftPercent).toInt()
            drawable.centerHorizontalOf.isNotEmpty() -> {
                if (drawable.centerHorizontalOf == parent_id) {
                    (parentWidth - drawable._width) / 2
                } else {
                    (drawableMap[drawable.centerHorizontalOf]?.let { it.left + (it.right - it.left) / 2 } ?: 0) - drawable.measuredWidth / 2
                }
            }
            drawable.startToEndOf.isNotEmpty() -> {
                val r = if (drawable.startToEndOf == parent_id) right else drawableMap[drawable.startToEndOf]?.right ?: 0
                (r + drawable._leftMargin)
            }
            drawable.startToStartOf.isNotEmpty() -> {
                val l = if (drawable.startToStartOf == parent_id) left else drawableMap[drawable.startToStartOf]?.left ?: 0
                (l + drawable._leftMargin)
            }
            drawable.endToStartOf.isNotEmpty() -> {
                val l = if (drawable.endToStartOf == parent_id) left else drawableMap[drawable.endToStartOf]?.left ?: 0
                (l - drawable._rightMargin) - drawable.measuredWidth
            }
            drawable.endToEndOf.isNotEmpty() -> {
                val r = if (drawable.endToEndOf == parent_id) right else drawableMap[drawable.endToEndOf]?.right ?: 0
                (r - drawable._rightMargin) - drawable.measuredWidth
            }
            else -> 0
        }
    }

    abstract class Drawable {
        /**
         * the unique id of this [Drawable]
         */
        var id: String = ""

        /**
         * the relative position of this [Drawable] to another
         */
        var leftPercent: Float = -1f
        var topPercent: Float = -1f
        var startToStartOf: String = ""
        var startToEndOf: String = ""
        var endToEndOf: String = ""
        var endToStartOf: String = ""
        var topToTopOf: String = ""
        var topToBottomOf: String = ""
        var bottomToTopOf: String = ""
        var bottomToBottomOf: String = ""
        var centerHorizontalOf: String = ""
        var centerVerticalOf: String = ""
        var topMargin = 0
        internal val _topMargin: Int
            get() = topMargin.dp
        var bottomMargin = 0
        internal val _bottomMargin: Int
            get() = bottomMargin.dp
        var leftMargin = 0
        internal val _leftMargin: Int
            get() = leftMargin.dp
        var rightMargin = 0
        internal val _rightMargin: Int
            get() = rightMargin.dp

        /**
         * dimension of [Drawable]
         */
        var width = 0
        internal val _width: Int
            get() = width.dp
        var height = 0
        internal val _height: Int
            get() = height.dp

        /**
         * the measured dimension of [Drawable]
         */
        internal var measuredWidth = 0
        internal var measuredHeight = 0

        /**
         * the frame rect of [Drawable]
         */
        var left = 0
        var right = 0
        var top = 0
        var bottom = 0
        var paddingStart = 0
        var paddingEnd = 0
        var paddingTop = 0
        var paddingBottom = 0


        abstract fun measure(widthMeasureSpec: Int, heightMeasureSpec: Int)
        abstract fun draw(canvas: Canvas?)

        /**
         * the the frame rect of [Drawable] is set after this function
         */
        fun setRect(left: Int, top: Int, right: Int, bottom: Int) {
            this.left = left
            this.right = right
            this.top = top
            this.bottom = bottom
        }

        /**
         * the measured width and height of [Drawable] is set after this function
         */
        fun setDimension(width: Int, height: Int) {
            this.measuredWidth = width
            this.measuredHeight = height
        }
    }

    class Text : Drawable() {
        private var textPaint: TextPaint? = null
        private var staticLayout: StaticLayout? = null

        var text: CharSequence = ""
        var textSize: Float = 0f
        private val _textSize: Float
            get() = textSize.dp
        var textColor: String = "#ffffff"
        var spaceAdd: Float = 0f
        var spaceMulti: Float = 1.0f
        var maxLines = 1
        var maxWidth = 0

        var gravity: Layout.Alignment = Layout.Alignment.ALIGN_NORMAL
        private val _maxWidth: Int
            get() = maxWidth.dp
        var shapePaint: Paint? = null
        var shape: Shape? = null
            set(value) {
                field = value
                shapePaint = Paint().apply {
                    isAntiAlias = true
                    style = Paint.Style.FILL
                    color = Color.parseColor(value?.color)
                }
            }

        override fun measure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
            if (textPaint == null) {
                textPaint = TextPaint().apply {
                    isAntiAlias = true
                    textSize = this@Text._textSize
                    color = Color.parseColor(textColor)
                }
            }

            val measureWidth = if (_width != 0) _width else min(textPaint!!.measureText(text.toString()).toInt(), _maxWidth)
            if (staticLayout == null) {
                staticLayout = StaticLayout.Builder.obtain(text, 0, text.length, textPaint!!, measureWidth)
                    .setAlignment(gravity)
                    .setLineSpacing(spaceAdd, spaceMulti)
                    .setIncludePad(false)
                    .setMaxLines(maxLines)
                    .build()
            }

            val measureHeight = staticLayout!!.height
            setDimension(measureWidth + paddingEnd + paddingStart, measureHeight + paddingTop + paddingBottom)
        }

        override fun draw(canvas: Canvas?) {
            canvas?.save()
            canvas?.translate(left.toFloat(), top.toFloat())
            drawBackground(canvas)
            canvas?.translate(paddingStart.toFloat(), paddingTop.toFloat())
            staticLayout?.draw(canvas)
            canvas?.restore()
        }

        private fun drawBackground(canvas: Canvas?) {
            shape?.let { shape ->
                canvas?.drawRoundRect(0f, 0f, measuredWidth.toFloat(), measuredHeight.toFloat(), shape._radius, shape._radius, shapePaint!!)
            }
        }
    }

    class Image : Drawable() {
        override fun measure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        }

        override fun draw(canvas: Canvas?) {
        }

    }

    class Shape {
        var color: String? = null
        var colors: List<String>? = null
        var radius: Float = 0f
        internal val _radius: Float
            get() = radius.dp
        var radii: IntArray? = null
    }
}

@RequiresApi(Build.VERSION_CODES.M)
inline fun OneViewGroup.text(init: OneViewGroup.Text.() -> Unit) = OneViewGroup.Text().apply(init).also { addDrawable(it) }

@RequiresApi(Build.VERSION_CODES.M)
inline fun OneViewGroup.image(init: OneViewGroup.Image.() -> Unit) = OneViewGroup.Image().apply(init).also { addDrawable(it) }

val OneViewGroup.parent_id: String
    get() = "-1"

fun OneViewGroup.shape(init: OneViewGroup.Shape.() -> Unit): OneViewGroup.Shape = OneViewGroup.Shape().apply(init)

val gravity_center = Layout.Alignment.ALIGN_CENTER
val gravity_left = Layout.Alignment.ALIGN_NORMAL
