package test.taylor.com.taylorcode.ui.one

import android.content.Context
import android.graphics.*
import android.os.Build
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import test.taylor.com.taylorcode.kotlin.dp
import test.taylor.com.taylorcode.kotlin.sp
import test.taylor.com.taylorcode.kotlin.toLayoutId
import kotlin.math.min

@RequiresApi(Build.VERSION_CODES.M)
class OneViewGroup @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private val drawableMap = HashMap<Int, Drawable>()
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
        val parentId = test.taylor.com.taylorcode.kotlin.parent_id.toLayoutId()
        return when {
            drawable.topPercent != -1f -> (parentHeight * drawable.topPercent).toInt()
            drawable.centerVerticalOf != -1 -> {
                if (drawable.centerVerticalOf == parentId) {
                    (parentHeight - drawable.height) / 2
                } else {
                    (drawableMap[drawable.centerVerticalOf]?.let { it.top + (it.bottom - it.top) / 2 } ?: 0) - drawable.measuredHeight / 2
                }
            }
            drawable.topToBottomOf != -1 -> {
                val b = if (drawable.topToBottomOf == parentId) bottom else drawableMap[drawable.topToBottomOf]?.bottom ?: 0
                (b + drawable.topMargin)
            }
            drawable.topToTopOf != -1 -> {
                val t = if (drawable.topToTopOf == parentId) top else drawableMap[drawable.topToTopOf]?.top ?: 0
                (t + drawable.topMargin)
            }
            drawable.bottomToTopOf != -1 -> {
                val t = if (drawable.bottomToTopOf == parentId) top else drawableMap[drawable.bottomToTopOf]?.top ?: 0
                (t - drawable.bottomMargin) - drawable.measuredHeight
            }
            drawable.bottomToBottomOf != -1 -> {
                val b = if (drawable.bottomToBottomOf == parentId) bottom else drawableMap[drawable.bottomToBottomOf]?.bottom ?: 0
                (b - drawable.bottomMargin) - drawable.measuredHeight
            }
            else -> 0
        }
    }

    private fun getChildLeft(drawable: Drawable, parentWidth: Int): Int {
        val parentId = test.taylor.com.taylorcode.kotlin.parent_id.toLayoutId()
        return when {
            drawable.leftPercent != -1f -> (parentWidth * drawable.leftPercent).toInt()
            drawable.centerHorizontalOf != -1 -> {
                if (drawable.centerHorizontalOf == parentId) {
                    (parentWidth - drawable.width) / 2
                } else {
                    (drawableMap[drawable.centerHorizontalOf]?.let { it.left + (it.right - it.left) / 2 } ?: 0) - drawable.measuredWidth / 2
                }
            }
            drawable.startToEndOf != -1 -> {
                val r = if (drawable.startToEndOf == parentId) right else drawableMap[drawable.startToEndOf]?.right ?: 0
                (r + drawable.leftMargin)
            }
            drawable.startToStartOf != -1 -> {
                val l = if (drawable.startToStartOf == parentId) left else drawableMap[drawable.startToStartOf]?.left ?: 0
                (l + drawable.leftMargin)
            }
            drawable.endToStartOf != -1 -> {
                val l = if (drawable.endToStartOf == parentId) left else drawableMap[drawable.endToStartOf]?.left ?: 0
                (l - drawable.rightMargin) - drawable.measuredWidth
            }
            drawable.endToEndOf != -1 -> {
                val r = if (drawable.endToEndOf == parentId) right else drawableMap[drawable.endToEndOf]?.right ?: 0
                (r - drawable.rightMargin) - drawable.measuredWidth
            }
            else -> 0
        }
    }

    abstract class Drawable {
        /**
         * the measured dimension of [Drawable]
         */
        internal var measuredWidth = 0
        internal var measuredHeight = 0

        /**
         * the frame rect of [Drawable]
         */
        internal var left = 0
        internal var right = 0
        internal var top = 0
        internal var bottom = 0

        /**
         * the unique id of this [Drawable]
         */
        var id: Int = -1

        /**
         * the relative position of this [Drawable] to another
         */
        var leftPercent: Float = -1f
        var topPercent: Float = -1f
        var startToStartOf: Int = -1
        var startToEndOf: Int = -1
        var endToEndOf: Int = -1
        var endToStartOf: Int = -1
        var topToTopOf: Int = -1
        var topToBottomOf: Int = -1
        var bottomToTopOf: Int = -1
        var bottomToBottomOf: Int = -1
        var centerHorizontalOf: Int = -1
        var centerVerticalOf: Int = -1

        /**
         * dimension of [Drawable]
         */
        var width = 0
        var height = 0


        /**
         * inner padding of [Drawable]
         */
        var paddingStart = 0
        var paddingEnd = 0
        var paddingTop = 0
        var paddingBottom = 0

        /**
         * out margin of [Drawable]
         */
        var topMargin = 0
        var bottomMargin = 0
        var leftMargin = 0
        var rightMargin = 0

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

        abstract fun measure(widthMeasureSpec: Int, heightMeasureSpec: Int)
        abstract fun draw(canvas: Canvas?)
    }
}


/**
 * one kind of drawable shows image
 */
class Image : OneViewGroup.Drawable() {
    override fun measure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    }

    override fun draw(canvas: Canvas?) {
    }
}

/**
 *  one kind of drawable shows text
 */
class Text : OneViewGroup.Drawable() {
    private var textPaint: TextPaint? = null
    private var staticLayout: StaticLayout? = null

    var text: CharSequence = ""
    var textSize: Float = 0f
    var textColor: Int = Color.parseColor("#ffffff")
    var spaceAdd: Float = 0f
    var spaceMulti: Float = 1.0f
    var maxLines = 1
    var maxWidth = 0
    var gravity: Layout.Alignment = Layout.Alignment.ALIGN_NORMAL
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

    @RequiresApi(Build.VERSION_CODES.M)
    override fun measure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (textPaint == null) {
            textPaint = TextPaint().apply {
                isAntiAlias = true
                textSize = this@Text.textSize
                color = textColor
            }
        }

        val measureWidth = if (width != 0) width else min(textPaint!!.measureText(text.toString()).toInt(), maxWidth)
        if (staticLayout == null) {
            staticLayout = StaticLayout.Builder.obtain(text, 0, text.length, textPaint!!, measureWidth)
                .setAlignment(gravity)
                .setLineSpacing(spaceAdd, spaceMulti)
                .setIncludePad(false)

                .setMaxLines(maxLines).build()
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
        if (shape == null) return
        val _shape = shape!!
        if (_shape.radius != 0f) {
            canvas?.drawRoundRect(0f, 0f, measuredWidth.toFloat(), measuredHeight.toFloat(), _shape.radius, _shape.radius, shapePaint!!)
        } else if (_shape.corners != null) {
            _shape.path!!.apply {
                addRoundRect(
                    RectF(0f, 0f, measuredWidth.toFloat(), measuredHeight.toFloat()),
                    _shape.corners!!.radii,
                    Path.Direction.CCW
                )
            }
            canvas?.drawPath(_shape.path!!, shapePaint!!)
        }
    }
}

/**
 * a round rect shows in background
 */
class Shape {
    var color: String? = null
    var colors: List<String>? = null
    var radius: Float = 0f
    internal var path: Path? = null
    var corners: Corners? = null
        set(value) {
            field = value
            path = Path()
        }

    class Corners(
        var leftTopRx: Float = 0f,
        var leftTopRy: Float = 0f,
        var leftBottomRx: Float = 0f,
        var LeftBottomRy: Float = 0f,
        var rightTopRx: Float = 0f,
        var rightTopRy: Float = 0f,
        var rightBottomRx: Float = 0f,
        var rightBottomRy: Float = 0f
    ) {
        val radii: FloatArray
            get() = floatArrayOf(leftTopRx, leftTopRy, rightTopRx, rightTopRy, rightBottomRx, rightBottomRy, leftBottomRx, LeftBottomRy)
    }
}

@RequiresApi(Build.VERSION_CODES.M)
inline fun OneViewGroup.text(init: Text.() -> Unit) = Text().apply(init).also { addDrawable(it) }

@RequiresApi(Build.VERSION_CODES.M)
inline fun OneViewGroup.image(init: Image.() -> Unit) = Image().apply(init).also { addDrawable(it) }

fun OneViewGroup.shape(init: Shape.() -> Unit): Shape = Shape().apply(init)

fun Shape.corners(init: Shape.Corners.() -> Unit): Shape.Corners = Shape.Corners().apply(init)

val gravity_center = Layout.Alignment.ALIGN_CENTER
val gravity_left = Layout.Alignment.ALIGN_NORMAL

inline var OneViewGroup.Drawable.top_margin: Int
    get() {
        return 0
    }
    set(value) {
        topMargin = value.dp
    }

inline var OneViewGroup.Drawable.bottom_margin: Int
    get() {
        return 0
    }
    set(value) {
        bottomMargin = value.dp
    }

inline var OneViewGroup.Drawable.left_margin: Int
    get() {
        return 0
    }
    set(value) {
        leftMargin = value.dp
    }

inline var OneViewGroup.Drawable.right_margin: Int
    get() {
        return 0
    }
    set(value) {
        rightMargin = value.dp
    }

inline var OneViewGroup.Drawable.padding_start: Int
    get() {
        return 0
    }
    set(value) {
        paddingStart = value.dp
    }

inline var OneViewGroup.Drawable.padding_end: Int
    get() {
        return 0
    }
    set(value) {
        paddingEnd = value.dp
    }

inline var OneViewGroup.Drawable.padding_top: Int
    get() {
        return 0
    }
    set(value) {
        paddingTop = value.dp
    }

inline var OneViewGroup.Drawable.padding_bottom: Int
    get() {
        return 0
    }
    set(value) {
        paddingBottom = value.dp
    }

inline var OneViewGroup.Drawable.layout_id: String
    get() {
        return ""
    }
    set(value) {
        id = value.toLayoutId()
    }

inline var OneViewGroup.Drawable.start_to_start_of: String
    get() {
        return ""
    }
    set(value) {
        startToStartOf = value.toLayoutId()
    }

inline var OneViewGroup.Drawable.start_to_end_of: String
    get() {
        return ""
    }
    set(value) {
        startToEndOf = value.toLayoutId()
    }

inline var OneViewGroup.Drawable.end_to_start_of: String
    get() {
        return ""
    }
    set(value) {
        endToStartOf = value.toLayoutId()
    }

inline var OneViewGroup.Drawable.end_to_end_of: String
    get() {
        return ""
    }
    set(value) {
        endToEndOf = value.toLayoutId()
    }

inline var OneViewGroup.Drawable.top_to_top_of: String
    get() {
        return ""
    }
    set(value) {
        topToTopOf = value.toLayoutId()
    }

inline var OneViewGroup.Drawable.top_to_bottom_of: String
    get() {
        return ""
    }
    set(value) {
        topToBottomOf = value.toLayoutId()
    }

inline var OneViewGroup.Drawable.bottom_to_top_of: String
    get() {
        return ""
    }
    set(value) {
        bottomToTopOf = value.toLayoutId()
    }

inline var OneViewGroup.Drawable.bottom_to_bottom_of: String
    get() {
        return ""
    }
    set(value) {
        bottomToBottomOf = value.toLayoutId()
    }

inline var OneViewGroup.Drawable.center_horizontal_of: String
    get() {
        return ""
    }
    set(value) {
        centerHorizontalOf = value.toLayoutId()
    }

inline var OneViewGroup.Drawable.center_vertical_of: String
    get() {
        return ""
    }
    set(value) {
        centerVerticalOf = value.toLayoutId()
    }

inline var Text.text_size: Float
    get() {
        return 0f
    }
    set(value) {
        textSize = value.sp
    }

inline var Text.max_width: Int
    get() {
        return 0
    }
    set(value) {
        maxWidth = value.dp
    }

inline var Text.layout_width: Int
    get() {
        return 0
    }
    set(value) {
        width = value.dp
    }

inline var Text.layout_height: Int
    get() {
        return 0
    }
    set(value) {
        height = value.dp
    }

inline var Text.text_color: String
    get() {
        return ""
    }
    set(value) {
        textColor = Color.parseColor(value)
    }

inline var Shape.radius: Float
    get() {
        return 0f
    }
    set(value) {
        radius = value.dp
    }