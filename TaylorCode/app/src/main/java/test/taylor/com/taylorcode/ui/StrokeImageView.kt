package test.taylor.com.taylorcode.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.min

/**
 * Custom [ImageView] that stroke supported
 *
 * > Created by alvince on 2019/3/14
 *
 * ## Custom attrs
 *
 * ### `img_cornerRadius`
 * > set image corner radius
 *
 * ### `img_strokeColor`
 * > set image stroke color
 *
 * ### `img_strokeWidth`
 * > set image stroke width dimension
 *
 * ### `img_strokeCorner`(删除 现在图片默认被stroke裁剪 by: xwh)
 * > set image stroke round radius
 *
 * ### `img_maskColor`
 * > set image mask color
 *
 * ### `img_maskTopLevel`(删除 没太清楚这搞什么的 by: xwh)
 * > indicate that whether rendering mask color on top
 *
 * ### `img_roundedAsCircle`
 * > indicate that whether display rounded as circle
 * > If this attribute set, view's size measured with the same width & height
 *
 * @author y.zhang@neoclub.cn
 * @since 4.4.5
 * @version 5.5.0, 2020/2/25
 *
 *
 * 2019/11/18 xwh: 使用该view时 不再需要自己手动裁剪图片  图片将以stroke为边界进行裁剪
 * 如果需要圆形图片 只需要设置img_roundedAsCircle即可 此view默认不再拥有stroke width
 * 且此view在设置img_roundedAsCircle后也不再强制 为正方形 而是以短边为直径 view的中心点为圆心进行渲染
 *
 * 2020/2/25 fix: draw color mask before restore canvas
 *
 * 2020/3/27 feat: add each corner radius
 *
 * 2020/4/23  feat: stroke 支持padding
 */
open class StrokeImageView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    AppCompatImageView(context, attrs, defStyleAttr) {

    @Dimension
    var cornerRadius: Float = 0F
        set(value) {
            if (field != value) {
                field = value
                postInvalidate()
            }
        }

    @ColorInt
    var strokeColor: Int = Color.WHITE
        set(value) {
            if (field != value) {
                field = value
                postInvalidate()
            }
        }

    @Dimension
    var strokeWidth: Float = 0F
        set(value) {
            if (field != value) {
                field = value
                postInvalidate()
            }
        }

    @ColorInt
    var maskColor: Int = 0
        set(value) {
            if (field != value) {
                field = value
                postInvalidate()
            }
        }

    @Dimension
    var topLeftRadius: Float = 0F
        set(value) {
            if (field != value) {
                field = value
                postInvalidate()
            }
        }

    @Dimension
    var topRightRadius: Float = 0F
        set(value) {
            if (field != value) {
                field = value
                postInvalidate()
            }
        }

    @Dimension
    var bottomLeftRadius: Float = 0F
        set(value) {
            if (field != value) {
                field = value
                postInvalidate()
            }
        }

    @Dimension
    var bottomRightRadius: Float = 0F
        set(value) {
            if (field != value) {
                field = value
                postInvalidate()
            }
        }

    /**
     * Indicate that whether is rendering rounded as circle
     *
     * @since 5.1.9
     */
    var roundedAsCircle: Boolean = false
        set(value) {
            field = value
            postInvalidate()
        }

    private val paint = Paint()

    private val targetRectF = RectF()

    private val canvasPath by lazy { Path() }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    init {
//        context.obtainStyledAttributes(attrs, R.styleable.StrokeImageView, defStyleAttr, 0).apply {
//            cornerRadius =
//                getDimensionPixelSize(R.styleable.StrokeImageView_img_cornerRadius, 0).toFloat()
//            strokeColor = getColor(R.styleable.StrokeImageView_img_strokeColor, Color.TRANSPARENT)
//            strokeWidth = getDimension(R.styleable.StrokeImageView_img_strokeWidth, 2F)
//            maskColor = getColor(R.styleable.StrokeImageView_img_maskColor, Color.TRANSPARENT)
//            roundedAsCircle = getBoolean(R.styleable.StrokeImageView_img_roundedAsCircle, false)
//            topLeftRadius =
//                getDimensionPixelSize(R.styleable.StrokeImageView_img_top_left_radius, 0).toFloat()
//            topRightRadius =
//                getDimensionPixelSize(R.styleable.StrokeImageView_img_top_right_radius, 0).toFloat()
//            bottomLeftRadius = getDimensionPixelSize(
//                R.styleable.StrokeImageView_img_bottom_left_radius,
//                0
//            ).toFloat()
//            bottomRightRadius = getDimensionPixelSize(
//                R.styleable.StrokeImageView_img_bottom_right_radius,
//                0
//            ).toFloat()
//            recycle()
//        }

        paint.apply {
            isAntiAlias = true
        }
    }

    override fun onDraw(canvas: Canvas) {
        if (roundedAsCircle) {
            onDrawAsCircle(canvas)
        } else {
            onDrawAsRect(canvas)
        }
    }

    @SuppressLint("WrongCall")
    private fun onDrawAsCircle(canvas: Canvas) {
        val bounder = min(width, height)

        val l = (width - bounder) / 2f
        val t = (height - bounder) / 2f
        val r = l + bounder
        val b = t + bounder
        targetRectF.set(l, t, r, b)

        val centerX = (r - l) / 2
        val centerY = (b - t) / 2

        val circlePath = canvasPath.apply { reset() }
        val cr = (bounder - strokeWidth) / 2
        circlePath.addCircle(centerX, centerY, cr, Path.Direction.CW)

        canvas.save()

        canvas.clipPath(circlePath)
        super.onDraw(canvas)
        drawColorFilter(canvas)
        canvas.restore()

        if (strokeWidth > 0) {
            paint.color = strokeColor
            paint.strokeWidth = strokeWidth
            paint.style = Paint.Style.STROKE
            val cx = centerX.plus(paddingStart.minus(paddingEnd).div(2))
            val cy = centerY.plus(paddingTop.minus(paddingBottom).div(2))
            val radius =
                cr.minus(
                    paddingStart.plus(paddingEnd).coerceAtLeast(paddingTop.plus(paddingBottom))
                        .div(2)
                )
            canvas.drawCircle(cx, cy, radius, paint)
        }
    }

    @SuppressLint("WrongCall")
    private fun onDrawAsRect(canvas: Canvas) {
        val l = strokeWidth / 2f
        val t = strokeWidth / 2f
        val r = l + width - strokeWidth
        val b = t + height - strokeWidth
        targetRectF.set(l, t, r, b)

        if (cornerRadius != 0f) {
            val targetPath = canvasPath.apply { reset() }
            targetPath.addRoundRect(targetRectF, cornerRadius, cornerRadius, Path.Direction.CW)

            canvas.save()
            canvas.clipPath(targetPath)
            super.onDraw(canvas)
            drawColorFilter(canvas)
            canvas.restore()
        } else if (hasCornersRadius()) {
            val targetPath = canvasPath.apply { reset() }
            targetPath.addRoundRect(
                targetRectF,
                floatArrayOf(
                    topLeftRadius,
                    topLeftRadius,
                    topRightRadius,
                    topRightRadius,
                    bottomLeftRadius,
                    bottomLeftRadius,
                    bottomRightRadius,
                    bottomRightRadius
                ),
                Path.Direction.CW
            )

            canvas.save()
            canvas.clipPath(targetPath)
            super.onDraw(canvas)
            drawColorFilter(canvas)
            canvas.restore()
        } else {
            super.onDraw(canvas)
        }

        if (strokeWidth > 0) {
            paint.color = strokeColor
            paint.strokeWidth = strokeWidth
            paint.style = Paint.Style.STROKE
            targetRectF.left = targetRectF.left.minus(paddingStart)
            targetRectF.right = targetRectF.right.minus(paddingEnd)
            targetRectF.top = targetRectF.top.minus(paddingTop)
            targetRectF.bottom = targetRectF.bottom.minus(paddingBottom)
            canvas.drawRoundRect(targetRectF, cornerRadius, cornerRadius, paint)
        }
    }

    private fun hasCornersRadius() =
        topLeftRadius != 0f || topRightRadius != 0f || bottomLeftRadius != 0f || bottomRightRadius != 0f

    private fun drawColorFilter(canvas: Canvas) {
        canvas.drawColor(maskColor)
    }

}
