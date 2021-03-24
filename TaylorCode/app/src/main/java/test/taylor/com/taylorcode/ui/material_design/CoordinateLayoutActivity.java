package test.taylor.com.taylorcode.ui.material_design;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;

import test.taylor.com.taylorcode.R;

public class CoordinateLayoutActivity extends Activity {

    private View tvDependency;
    private int lastX;
    private int lastY;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coordinate_activity);
        tvDependency = findViewById(R.id.tv_dependency);
        makeViewMoveWithFinger();
    }

    private void makeViewMoveWithFinger() {
        tvDependency.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = ((int) event.getRawX());
                        lastY = ((int) event.getRawY());
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int dx = ((int) event.getRawX()) - lastX;
                        int dy = ((int) event.getRawY()) - lastY;
                        tvDependency.setTranslationX(dx);
                        tvDependency.setTranslationY(dy);
                        break;
                }
                return true;
            }
        });
    }


}
