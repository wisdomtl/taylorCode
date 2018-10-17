package test.taylor.com.taylorcode.ui.material_design;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class FootmanBehavior extends CoordinatorLayout.Behavior<TextView> {//here is child which depend on dependency

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

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, TextView child, View dependency) {
        return dependency instanceof TextView;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, TextView child, View dependency) {
        int dx = ((int) dependency.getX()) - lastDependencyX;
        int dy = ((int) dependency.getY()) - lastDependencyY;
        child.setTranslationX(dx);
        child.setTranslationY(dy);
        return true;
    }
}
