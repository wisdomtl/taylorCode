package test.taylor.com.taylorcode.ui.viewstub;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.graphics.drawable.AnimationUtilsCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
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
    private boolean show = false;
    private ConstraintLayout root;
    private View tvooo;
    private ConstraintSet set;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewstub_activity);
        root = findViewById(R.id.croot);
        tvooo = findViewById(R.id.tvooo);
        findViewById(R.id.btnShowVs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showViewStub();
            }
        });
        findViewById(R.id.btnHide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (show) {
                    hideView();
                    show = !show;
                } else {
                    showView();
                    show = !show;
                }
            }
        });

    }

    private void showView() {
        AutoTransition autoTransition = new AutoTransition();
        autoTransition.setInterpolator(new AccelerateDecelerateInterpolator());
        autoTransition.setDuration(280);
        set = new ConstraintSet();
        set.clone(root);
        TransitionManager.beginDelayedTransition(root, autoTransition);
        set.clear(R.id.tvooo,ConstraintSet.TOP);
        set.connect(R.id.tvooo, ConstraintSet.BOTTOM, R.id.vDivider, ConstraintSet.BOTTOM);
        set.applyTo(root);
    }

    private void hideView() {
        AutoTransition autoTransition = new AutoTransition();
        autoTransition.setInterpolator(new AccelerateDecelerateInterpolator());
        autoTransition.setDuration(280);
        set = new ConstraintSet();
        set.clone(root);
        TransitionManager.beginDelayedTransition(root, autoTransition);
        set.clear(R.id.tvooo,ConstraintSet.BOTTOM);
        set.connect(R.id.tvooo, ConstraintSet.TOP, R.id.vDivider, ConstraintSet.BOTTOM);
        set.applyTo(root);

    }

    private void showViewStub() {
        ViewStub vs = findViewById(R.id.vs);
        View vsRoot = vs.inflate();
        vsRoot.setVisibility(View.VISIBLE);
        doBottomInAnim(vsRoot);
        hideBottomView(findViewById(R.id.tvooo), null);
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
        }, 0);
    }

    private void hideBottomView(View view, View subView) {
        view.postDelayed(() -> {
            int height = view.getHeight();
            Log.v("ttaylor", "ViewStubActivity.hideBottomView()" + "  height=" + height);
            AnimatorSet animatorSet = new AnimatorSet();
            ValueAnimator animator = ValueAnimator.ofFloat(1.0f, 0);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.setDuration(duration);
            animator.addUpdateListener(animation -> {
                float size = (Float) animation.getAnimatedValue();
                view.setAlpha(size);
                if (subView != null) {
                    subView.setAlpha(size);
                }
            });


            ValueAnimator animator2 = ValueAnimator.ofFloat(0, height);
            animator2.setInterpolator(new AccelerateDecelerateInterpolator());
            animator2.setDuration(duration);
            animator2.addUpdateListener(animation -> {
                float size = (Float) animation.getAnimatedValue();
                view.setTranslationY(size);
                if (subView != null) {
                    subView.setTranslationY(size);
                }
            });
            animatorSet.playTogether(animator, animator2);
            animatorSet.start();
        }, 0);

    }

}
