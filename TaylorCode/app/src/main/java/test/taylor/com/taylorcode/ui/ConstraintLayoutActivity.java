package test.taylor.com.taylorcode.ui;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.Toast;

import test.taylor.com.taylorcode.R;
import test.taylor.com.taylorcode.util.ViewUtil;

public class ConstraintLayoutActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.constraint_layout_activity);

        final Group group = ((Group) findViewById(R.id.group1));
        findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                group.setVisibility(View.GONE);
            }
        });

        //ConstraintLayout  case7:one member of chain is gone,the rest will full the chain
        findViewById(R.id.btn5).setVisibility(View.GONE);

        //spring anim in java way
        Button btn3 = findViewById(R.id.btn3);
        ValueAnimator animator = ValueAnimator.ofFloat(1.0f, 1.2f);
        animator.setDuration(100);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value = ((Float) animation.getAnimatedValue());
                btn3.setScaleX(value);
                btn3.setScaleY(value);
            }
        });
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ConstraintLayoutActivity.this, "spring anim", Toast.LENGTH_LONG).show();
            }
        };
        ViewUtil.addExtraAnimClickListener(btn3, animator, onClickListener);


    }
}
