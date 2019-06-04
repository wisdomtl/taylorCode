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
    private int duration = 400;

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
        hideBottomView(findViewById(R.id.tvooo));
    }

    private void doBottomInAnim(View view) {
        view.postDelayed(() -> {
            int height = view.getHeight();
            int bottom = view.getBottom();
            Log.v("ttaylor", "ViewStubActivity.doBottomInAnim()" + "  bottom=" + bottom + " height=" + height);
            AnimatorSet animatorSet = new AnimatorSet();
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

    private void hideBottomView(View view){
        view.postDelayed(() -> {
            int height = view.getHeight();
            Log.v("ttaylor", "ViewStubActivity.hideBottomView()" + "  height="+height);
            AnimatorSet animatorSet = new AnimatorSet();
            ValueAnimator animator = ValueAnimator.ofFloat(1.0f, 0);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.setDuration(duration);
            animator.addUpdateListener(animation -> {
                float size = (Float) animation.getAnimatedValue();
                view.setAlpha(size);
            });


            ValueAnimator animator2 = ValueAnimator.ofFloat(0, height);
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
