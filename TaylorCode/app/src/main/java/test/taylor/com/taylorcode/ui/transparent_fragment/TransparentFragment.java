package test.taylor.com.taylorcode.ui.transparent_fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;

import test.taylor.com.taylorcode.R;

public class TransparentFragment extends Fragment {

    private static final int OPENING_ANIM_DURATION = 170;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.transparent_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showOpeningAnim(view.findViewById(R.id.tvTop), view.findViewById(R.id.tvBottom));

    }

    private void showOpeningAnim(View topView, View bottomView) {
        bottomView.setAlpha(0f);
        topView.setAlpha(0f);
        bottomView.post(() -> {
            int height = bottomView.getHeight();
            PropertyValuesHolder translationHolder = PropertyValuesHolder.ofFloat("translationY", 0, -height);
            PropertyValuesHolder alphaValueHolder = PropertyValuesHolder.ofFloat("alpha", 0, 1.0f);
            ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(bottomView, translationHolder, alphaValueHolder);
            objectAnimator.setDuration(OPENING_ANIM_DURATION);
            objectAnimator.setInterpolator(new AccelerateInterpolator());

//            ValueAnimator animator = ValueAnimator.ofFloat(0, 1.0f);
//            animator.setInterpolator(new AccelerateInterpolator());
//            animator.setDuration(OPENING_ANIM_DURATION);
//            animator.addUpdateListener(animation -> {
//                float size = (Float) animation.getAnimatedValue();
//                bottomView.setAlpha(size);
//            });
//
//            ValueAnimator animator2 = ValueAnimator.ofFloat(0, -height);
//            animator2.setInterpolator(new AccelerateDecelerateInterpolator());
//            animator2.setDuration(OPENING_ANIM_DURATION);
//            animator2.addUpdateListener(animation -> {
//                float size = (Float) animation.getAnimatedValue();
//                bottomView.setTranslationY(size);
//            });
            PropertyValuesHolder alphaValueHolder2 = PropertyValuesHolder.ofFloat("alpha", 0, 1.0f);
            ObjectAnimator objectAnimator1 = ObjectAnimator.ofPropertyValuesHolder(topView, alphaValueHolder2);
            objectAnimator1.setDuration(OPENING_ANIM_DURATION);
            objectAnimator1.setInterpolator(new AccelerateInterpolator());

//            ValueAnimator topAnim = ValueAnimator.ofFloat(0, 1.0f);
//            topAnim.setInterpolator(new AccelerateInterpolator());
//            topAnim.setDuration(OPENING_ANIM_DURATION);
//            topAnim.addUpdateListener(animation -> {
//                float size = (Float) animation.getAnimatedValue();
//                topView.setAlpha(size);
//            });

            AnimatorSet openingAnim = new AnimatorSet();
            openingAnim.playTogether(objectAnimator1, objectAnimator);
            openingAnim.start();
        });
    }
}
