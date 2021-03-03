package test.taylor.com.taylorcode.ui.performance.widget

import android.content.Context
import android.util.AttributeSet
import android.view.FrameMetrics
import android.view.ViewGroup
import android.widget.FrameLayout

class PercentLayout
@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) : ViewGroup(context, attrs, defStyleAttr, defStyleRes) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val parentWidth = right - left
        val parentHeight = bottom - top
        (0 until childCount).map { getChildAt(it) }.forEach { child ->
            val lp = child.layoutParams as LayoutParam
            val childLeft = (parentWidth * lp.leftPercent).toInt()
            val childTop = (parentHeight * lp.topPercent).toInt()
            child.layout(childLeft, childTop, childLeft + child.measuredWidth, childTop + child.measuredHeight)
        }
    }

    class LayoutParam(source: ViewGroup.LayoutParams?) : ViewGroup.LayoutParams(source) {
        var leftPercent: Float = 0f
        var topPercent: Float = 0f
    }


}