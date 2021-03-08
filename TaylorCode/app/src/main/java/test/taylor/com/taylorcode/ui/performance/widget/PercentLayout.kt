package test.taylor.com.taylorcode.ui.performance.widget

import android.content.Context
import android.util.AttributeSet
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import test.taylor.com.taylorcode.kotlin.parent_id
import test.taylor.com.taylorcode.kotlin.toLayoutId

/**
 * the child in [PercentLayout] use [LayoutParam.leftPercent] and [LayoutParam.topPercent] to locate itself, which is a percentage of the width and height of [PercentLayout].
 *
 * [PercentLayout] must have a specific width or height, or the children view of it will have no idea where to locate.
 */
class PercentLayout
@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) : ViewGroup(context, attrs, defStyleAttr, defStyleRes) {

    private val childMap = SparseArray<View>()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val parentWidth = right - left
        val parentHeight = bottom - top
        (0 until childCount).map { getChildAt(it) }.forEach { child ->
            val lp = child.layoutParams as LayoutParam
            val childLeft = getChildLeft(lp, parentWidth, child)
            val childTop = getChildTop(lp, parentHeight, child)
            child.layout(childLeft, childTop, childLeft + child.measuredWidth, childTop + child.measuredHeight)
        }
    }

    private fun getChildTop(lp: LayoutParam, parentHeight: Int, child: View): Int {
        if (lp.centerVertical) {
            return (parentHeight - child.measuredHeight) / 2
        } else {
            val parentId = parent_id.toLayoutId()
            if (lp.topToBottomOf != -1) {
                val b = if (lp.topToBottomOf == parentId) bottom else childMap.get(lp.topToBottomOf)?.bottom ?: 0
                return (b + lp.marginTop).toInt()
            }

            if (lp.topToTopOf != -1) {
                val t = if (lp.topToTopOf == parentId) top else childMap.get(lp.topToTopOf)?.top ?: 0
                return (t + lp.marginTop).toInt()
            }

            if (lp.bottomToTopOf != -1) {
                val t = if (lp.bottomToTopOf == parentId) top else childMap.get(lp.bottomToTopOf)?.top ?: 0
                return (t - lp.marginBottom).toInt() - child.measuredHeight
            }

            if (lp.bottomToBottomOf != -1) {
                val b = if (lp.bottomToBottomOf == parentId) bottom else childMap.get(lp.bottomToBottomOf)?.bottom ?: 0
                return (b - lp.marginBottom).toInt() - child.measuredHeight
            }

            return (parentHeight * lp.leftPercent).toInt()
        }
    }

    private fun getChildLeft(lp: LayoutParam, parentWidth: Int, child: View): Int {
        if (lp.centerHorizontal) {
            return (parentWidth - child.measuredWidth) / 2
        } else {
            val parentId = parent_id.toLayoutId()
            if (lp.startToEndOf != -1) {
                val r = if (lp.startToEndOf == parentId) right else childMap.get(lp.startToEndOf)?.right ?: 0
                return (r + lp.marginStart).toInt()
            }

            if (lp.startToStartOf != -1) {
                val l = if (lp.startToStartOf == parentId) left else childMap.get(lp.startToStartOf)?.left ?: 0
                return (l + lp.marginStart).toInt()
            }

            if (lp.endToStartOf != -1) {
                val l = if (lp.endToStartOf == parentId) left else childMap.get(lp.endToStartOf)?.left ?: 0
                return (l - lp.marginEnd).toInt() - child.measuredWidth
            }

            if (lp.endToEndOf != -1) {
                val r = if (lp.endToEndOf == parentId) right else childMap.get(lp.endToEndOf)?.right ?: 0
                return (r - lp.marginEnd).toInt() - child.measuredWidth
            }

            return (parentWidth * lp.leftPercent).toInt()
        }
    }

    override fun onViewAdded(child: View?) {
        super.onViewAdded(child)
        child?.let { childMap.put(it.id, it) }
    }

    override fun onViewRemoved(child: View?) {
        super.onViewRemoved(child)
        child?.let { childMap.remove(it.id) }
    }

    class LayoutParam(source: ViewGroup.LayoutParams?) : ViewGroup.LayoutParams(source) {
        var leftPercent: Float = 0f
        var topPercent: Float = 0f
        var centerVertical = false
        var centerHorizontal = false
        var startToStartOf: Int = -1
        var startToEndOf: Int = -1
        var endToEndOf: Int = -1
        var endToStartOf: Int = -1
        var topToTopOf: Int = -1
        var topToBottomOf: Int = -1
        var bottomToTopOf: Int = -1
        var bottomToBottomOf: Int = -1
        var marginStart: Float = 0f
        var marginTop: Float = 0f
        var marginBottom: Float = 0f
        var marginEnd: Float = 0f
    }


}