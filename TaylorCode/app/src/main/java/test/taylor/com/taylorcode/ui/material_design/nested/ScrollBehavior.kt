package test.taylor.com.taylorcode.ui.material_design.nested

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import kotlin.math.max
import kotlin.math.min

/**
 * the Behavior set to the TextView, which is the direct child of CoordinateLayout
 *
 * the siblings of TextView is RecyclerView,which trigger the the nested scroll
 *
 * the TextView has the priority to consume the scroll event on [onNestedPreScroll], on the condition that [onStartNestedScroll] return true, which means this Behavior wishes to deal the the nested scroll event
 */
class ScrollBehavior @JvmOverloads constructor(context: Context, attrs: AttributeSet) :
    CoordinatorLayout.Behavior<TextView>(context, attrs) {

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: TextView,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        val ret = axes and ViewCompat.SCROLL_AXIS_VERTICAL != 0
        Log.v("ttaylor", "onStartNestedScroll ret = $ret")
        return axes and ViewCompat.SCROLL_AXIS_VERTICAL != 0
    }

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: TextView,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)

        Log.v("ttaylor", "onNestedPreScroll() dy=${dy} translate = ${child.translationY}")
        val topTextViewCouldMove = (child.translationY != -child.height.toFloat() && dy > 0)
                || (dy < 0 && child.translationY != 0f)
        if (topTextViewCouldMove) {
            val translationY = (child.translationY - dy).toInt()
            child.translationY = max(-child.height, min(translationY, 0)).toFloat()
            consumed[1] = dy //let TextView consume the dy
        }
    }

    override fun onNestedPreFling(
        coordinatorLayout: CoordinatorLayout,
        child: TextView,
        target: View,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY)

    }

}