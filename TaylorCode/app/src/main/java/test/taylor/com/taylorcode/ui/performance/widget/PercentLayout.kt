package test.taylor.com.taylorcode.ui.performance.widget

import android.content.Context
import android.util.ArrayMap
import android.util.AttributeSet
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup

/**
 * the child in [PercentLayout] use [LayoutParam.leftPercent] and [LayoutParam.topPercent] to locate itself, which is a percentage of the width and height of [PercentLayout].
 *
 * [PercentLayout] must have a specific width or height, or the children view of it will have no idea where to locate.
 */
class PercentLayout
@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) : ViewGroup(context, attrs, defStyleAttr, defStyleRes) {

    private val childViewMap = SparseArray<View>()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val parentWidth = right - left
        val parentHeight = bottom - top
        (0 until childCount).map { getChildAt(it) }.forEach { child ->
            val lp = child.layoutParams as LayoutParam
            val childLeft = if (lp.centerHorizontal) (parentWidth - child.measuredWidth) / 2 else (parentWidth * lp.leftPercent).toInt()
            val childTop = if (lp.centerVertical) (parentHeight - child.measuredHeight) / 2 else (parentHeight * lp.topPercent).toInt()
            child.layout(childLeft, childTop, childLeft + child.measuredWidth, childTop + child.measuredHeight)
        }
    }

    override fun onViewAdded(child: View?) {
        super.onViewAdded(child)
        child?.let { childViewMap.put(it.id, it) }
    }

    override fun onViewRemoved(child: View?) {
        super.onViewRemoved(child)
        child?.let { childViewMap.remove(it.id) }
    }

    class LayoutParam(source: ViewGroup.LayoutParams?) : ViewGroup.LayoutParams(source) {
        var leftPercent: Float = 0f
        var topPercent: Float = 0f
        var centerVertical = false
        var centerHorizontal = false

    }


}