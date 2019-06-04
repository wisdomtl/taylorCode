package test.taylor.com.taylorcode.ui.viewstub;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.AnimationUtilsCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;

import test.taylor.com.taylorcode.R;
import test.taylor.com.taylorcode.util.DimensionUtil;

public class ViewStubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewstub_activity);
        findViewById(R.id.btnShowVs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showViewStub();
            }
        });
    }

    private void showViewStub() {
        ViewStub vs = findViewById(R.id.vs);
        View vsRoot = vs.inflate();
        vsRoot.setVisibility(View.VISIBLE);
        doBottomInAnim(vsRoot);
    }

    private void doBottomInAnim(View view) {
        final int duration = 100;
        view.postDelayed(() -> {
            int height = view.getHeight();
            int bottom = view.getBottom();
            Log.v("ttaylor", "ViewStubActivity.doBottomInAnim()" + "  bottom=" + bottom + " height=" + height);
            AnimatorSet animatorSet = new AnimatorSet();
//            ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 0, 255);
//            alpha.setDuration(duration);
//            ObjectAnimator translationY = ObjectAnimator.ofFloat(view, "translationY", top, top - height);
//            translationY.setDuration(duration);
            ValueAnimator animator = ValueAnimator.ofFloat(0, 1.0f);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.setDuration(duration);
            animator.addUpdateListener(animation -> {
                float size = (Float) animation.getAnimatedValue();
                view.setAlpha(size);
            });


            ValueAnimator animator2 = ValueAnimator.ofFloat(0, -height);
            animator2.setInterpolator(new AccelerateDecelerateInterpolator());
            animator2.setDuration(duration);
            animator2.addUpdateListener(animation -> {
                float size = (Float) animation.getAnimatedValue();
                view.setTranslationY(size);
            });
            animatorSet.playTogether(animator, animator2);
            animatorSet.start();
        },0);
    }

}
