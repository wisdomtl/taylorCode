package test.taylor.com.taylorcode.ui.material_design;

import android.content.Context;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class FootmanBehavior extends CoordinatorLayout.Behavior<TextView> {// define a behavior and which View could it belongs to

    private int lastDependencyX;
    private int lastDependencyY;

    /**
     * this constructor is must for using behavior in layout xml
     *
     * @param context
     * @param attrs
     */
    public FootmanBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * determine which view to depend on, if return true, meaning that the child view will be informed when the dependency view's layout changes
     * @param parent
     * @param child
     * @param dependency
     * @return
     */
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, TextView child, View dependency) {
        return dependency instanceof TextView;
    }

    /**
     * what to do when the dependency view's layout is changing
     * @param parent
     * @param child
     * @param dependency
     * @return
     */
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, TextView child, View dependency) {
        int dx = ((int) dependency.getX()) - lastDependencyX;
        int dy = ((int) dependency.getY()) - lastDependencyY;
        child.setTranslationX(dx);
        child.setTranslationY(dy);
        return true;
    }
}
