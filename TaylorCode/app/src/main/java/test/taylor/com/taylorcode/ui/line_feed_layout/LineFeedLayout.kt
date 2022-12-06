package test.taylor.com.taylorcode.ui.line_feed_layout

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

/**
 * a special [ViewGroup] acts like [LinearLayout] in horizontal orientation,
 * it spreads the children from left to right until there is not enough horizontal space for them,
 * then the next child will be placed at a new line
 */
class LineFeedLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    var horizontalGap: Int = 0
    var verticalGap: Int = 0
    var onNewLine: ((Int) -> Unit)? = null
    private var lines = 0

    suspend fun getLines() = suspendCancellableCoroutine<Int> { continuation ->
        post { continuation.resume(lines) }
    }

    /**
     * the height of [LineFeedLayout] depends on how much lines it has
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        var height = 0

        var remainWidth = width
        lines = if (childCount > 0) 1 else 0
        onNewLine?.invoke(lines)
        (0 until childCount).map { getChildAt(it) }.forEach { child ->
            val lp = child.layoutParams as? MarginLayoutParams
            val appendWidth = child.measuredWidth + lp?.marginStart.orZero + lp?.marginEnd.orZero
            if (isNewLine(appendWidth, remainWidth)) {
                remainWidth = width - child.measuredWidth
                height += (lp?.topMargin.orZero + lp?.bottomMargin.orZero + child.measuredHeight + verticalGap)
                ++lines
                onNewLine?.invoke(lines)
            } else {
                remainWidth -= child.measuredWidth
                if (height == 0) height =
                    (lp?.topMargin.orZero + lp?.bottomMargin.orZero + child.measuredHeight + verticalGap)
            }
            remainWidth -= (lp?.leftMargin.orZero + lp?.rightMargin.orZero + horizontalGap)
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = MeasureSpec.getSize(heightMeasureSpec)
        }

        setMeasuredDimension(width, height)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var left = 0
        var top = 0
        var lastBottom = 0
        (0 until childCount).map { getChildAt(it) }.forEach { child ->
            val lp = child.layoutParams as? MarginLayoutParams
            val appendWidth = child.measuredWidth + lp?.marginStart.orZero + lp?.marginEnd.orZero
            if (isNewLine(appendWidth, r - l - left)) {
                left = -lp?.leftMargin.orZero
                top = lastBottom
                lastBottom = 0
            }
            val childLeft = left + lp?.leftMargin.orZero
            val childTop = top + lp?.topMargin.orZero
            child.layout(
                childLeft,
                childTop,
                childLeft + child.measuredWidth,
                childTop + child.measuredHeight
            )
            if (lastBottom == 0) lastBottom = child.bottom + lp?.bottomMargin.orZero + verticalGap
            left += child.measuredWidth + lp?.leftMargin.orZero + lp?.rightMargin.orZero + horizontalGap
        }
    }

    /**
     * place the [child] in a new line or not
     */
    private fun isNewLine(usedWidth: Int, remainWidth: Int): Boolean = usedWidth > remainWidth
}


val Int?.orZero: Int
    get() = this ?: 0
