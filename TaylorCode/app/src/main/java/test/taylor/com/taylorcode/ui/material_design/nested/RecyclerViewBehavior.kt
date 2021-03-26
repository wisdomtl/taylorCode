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

    override fun layoutDependsOn(parent: CoordinatorLayout, child: RecyclerView, dependency: View): Boolean {
        return dependency is TextView
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: RecyclerView, dependency: View): Boolean {
        val y = dependency.height + dependency.translationY
        child.y = max(0f,y)
        return true
    }
}