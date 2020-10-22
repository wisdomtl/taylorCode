import android.graphics.*
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.graphics.applyCanvas
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.doOnPreDraw
import test.taylor.com.taylorcode.kotlin.dp

val SHADOW_LEFT: Int
    get() = 1.shl(1)
val SHADOW_RIGHT: Int
    get() = 1.shl(2)
val SHADOW_BOTTOM: Int
    get() = 1.shl(3)
val SHADOW_TOP: Int
    get() = 1.shl(4)

/**
 * add shadow under this [View]
 */
fun View.shadow(
    direction: Int = SHADOW_LEFT.or(SHADOW_BOTTOM).or(SHADOW_RIGHT),
    radius: Int = 5.dp,
    color: Int = Color.parseColor("#3c000000")
) {
    post {
        // keep the origin background here
        val backgroundBitmap = background.let { it.toBitmap(width,height) }
        var extraPaddingRight = 0
        var extraPaddingLeft = 0
        var extraPaddingBottom = 0
        var extraPaddingTop = 0

        // calculate extra space for shadow
        var extraWidth: Int = 0
        extraWidth += if (direction.and(SHADOW_LEFT) == SHADOW_LEFT) {
            extraPaddingLeft = radius
            radius
        } else 0
        extraWidth += if (direction.and(SHADOW_RIGHT) == SHADOW_RIGHT) {
            extraPaddingRight = radius
            radius
        } else 0
        var extraHeight: Int = 0
        extraHeight += if (direction.and(SHADOW_BOTTOM) == SHADOW_BOTTOM) {
            extraPaddingBottom = radius
            radius
        } else 0
        extraHeight += if (direction.and(SHADOW_TOP) == SHADOW_TOP) {
            extraPaddingTop = radius
            radius
        } else 0

        //shift view by set padding according to the shadow radius
        val paddingLeft = paddingLeft
        val paddingRight = paddingRight
        val paddingBottom = paddingBottom
        val paddingTop = paddingTop
        setPadding(
            paddingLeft + extraPaddingLeft,
            paddingTop + extraPaddingTop,
            paddingRight + extraPaddingRight,
            paddingBottom + extraPaddingBottom
        )
        val shadowWidth = width + extraWidth
        val shadowHeight = height + extraHeight

        /**
         * create a bitmap which is bigger than the origin background of this view, the extra space is used to draw shadow
         * 1. draw shadow firstly
         * 2. draw the origin background upon shadow
         */
        Bitmap.createBitmap(shadowWidth, shadowHeight, Bitmap.Config.ARGB_8888).applyCanvas {
            val shadowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                maskFilter = BlurMaskFilter(radius.toFloat(), BlurMaskFilter.Blur.NORMAL)
                this.color = color
            }
            drawRect(Rect(radius, radius, width - radius, height - radius), shadowPaint)
            drawBitmap(
                backgroundBitmap,
                null,
                Rect(extraPaddingLeft, extraPaddingTop, width - extraPaddingRight, height - extraPaddingBottom),
                Paint()
            )
        }.let {
            // renew view's background
            background = it.toDrawable(resources)
        }
    }
}

