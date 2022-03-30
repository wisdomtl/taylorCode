package test.taylor.com.taylorcode.ui.material_design.nested

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.max
import kotlin.math.min

class RecyclerViewBehavior @JvmOverloads constructor(context:Context,  attrs: AttributeSet) : CoordinatorLayout.Behavior<RecyclerView>(context, attrs) {

    /**
     * determine whether RecyclerView's layout should be depended on another view , which is the direct child of CoordinateLayout
     * if true, the RecyclerView will be layout after the [dependency] view
     */
    override fun layoutDependsOn(parent: CoordinatorLayout, child: RecyclerView, dependency: View): Boolean {
        return dependency is TextView
    }

    /**
     * define how RecyclerView should be layout by dependency View
     */
    override fun onDependentViewChanged(parent: CoordinatorLayout, child: RecyclerView, dependency: View): Boolean {
        val y = dependency.height + dependency.translationY
        child.y = max(0f,y)
        return true
    }

    override fun onLayoutChild(
        parent: CoordinatorLayout,
        child: RecyclerView,
        layoutDirection: Int
    ): Boolean {
        return super.onLayoutChild(parent, child, layoutDirection)
    }
}