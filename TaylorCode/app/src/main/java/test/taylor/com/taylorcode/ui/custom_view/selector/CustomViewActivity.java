package test.taylor.com.taylorcode.ui.custom_view.selector;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import test.taylor.com.taylorcode.R;
import test.taylor.com.taylorcode.util.DimensionUtil;
import test.taylor.com.taylorcode.util.Timer;

public class CustomViewActivity extends Activity implements View.OnClickListener {

    private final float FULL_TIME_MILLISECOND = 3 * 1000;
    public static final int VALUE_ANIM_DURATION = 800;
    private ProgressRing progressRing;
    private AnimationDrawable animationDrawable;
    private LinearLayout llContainer;
    private ImageView selector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_view_activity);

        addProgressRing();
//        initSelector();
        initCustomSelector();

    }

    /**
     * custom view case2:wrap business logic into a single view
     */
    private void initCustomSelector() {
        Selector selector = findViewById(R.id.select);
    }

    private void initSelector() {
        selector = findViewById(R.id.iv);
        selector.setOnClickListener(this);
        selector.setSelected(true);
    }

    /**
     * custom view case1:update custom view periodically
     */
    private void addProgressRing() {
        //        progressRing = ((ProgressRing) findViewById(R.id.progress_ring));
        progressRing = new ProgressRing(this);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(DimensionUtil.dp2px(54), DimensionUtil.dp2px(54));
        progressRing.setLayoutParams(params);
        animationDrawable = createAnimationDrawable(this);
        progressRing.setBackground(animationDrawable);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cootek_radiobutton_active);
        progressRing.setBitmap(bitmap);
        new Timer(new Timer.TimerListener() {
            @Override
            public void onTick(long pastMillisecond) {
                float mod = pastMillisecond % FULL_TIME_MILLISECOND;

                Log.v("ttaylor", "CustomViewActivity.onTick()" + " mod=" + mod + ",past=" + pastMillisecond);
                updateProgress(mod, FULL_TIME_MILLISECOND);

                if (mod == 0) {
                    doFrameAnimation();
                    doValueAnimator(15, 51, progressRing, VALUE_ANIM_DURATION);
                }
            }
        }).start(0, 50);
        llContainer = ((LinearLayout) findViewById(R.id.ll_custom_view));
        llContainer.addView(progressRing);
    }


    private void doFrameAnimation() {
        if (animationDrawable.isRunning()) {
            animationDrawable.stop();
        }
        animationDrawable.start();
    }

    private void updateProgress(float mod, float totalTime) {
        float i = mod == 0 ? 1 : mod;
        float progress = i / totalTime;
        progressRing.setProgress(progress);
    }

    private void doValueAnimator(float start, float end, final ProgressRing ring, int duration) {
        ValueAnimator animator = ValueAnimator.ofFloat(start, end);
        animator.setInterpolator(new AnticipateOvershootInterpolator());
        animator.setDuration(duration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.v("ttaylor", "AnimActivity.onAnimationUpdate()" + "  value=" + animation.getAnimatedValue());
                float size = (Float) animation.getAnimatedValue();
                ring.setTextSize(size);
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.v("ttaylor", "CustomViewActivity.onAnimationEnd()" + "  ");
                ring.setText("");
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        ring.setText("+1");
        animator.start();
    }


    /**
     * custom view case2:combine AnimationDrawable with customized onDraw()
     *
     * @param context
     * @return
     */
    private AnimationDrawable createAnimationDrawable(Context context) {
        AnimationDrawable drawable = new AnimationDrawable();
        drawable.addFrame(ContextCompat.getDrawable(context, R.drawable.watch_reward_1), 23);
        drawable.addFrame(ContextCompat.getDrawable(context, R.drawable.watch_reward_2), 23);
        drawable.addFrame(ContextCompat.getDrawable(context, R.drawable.watch_reward_3), 23);
        drawable.addFrame(ContextCompat.getDrawable(context, R.drawable.watch_reward_4), 23);
        drawable.addFrame(ContextCompat.getDrawable(context, R.drawable.watch_reward_5), 23);
        drawable.addFrame(ContextCompat.getDrawable(context, R.drawable.watch_reward_6), 23);
        drawable.addFrame(ContextCompat.getDrawable(context, R.drawable.watch_reward_7), 23);
        drawable.addFrame(ContextCompat.getDrawable(context, R.drawable.watch_reward_8), 23);
        drawable.addFrame(ContextCompat.getDrawable(context, R.drawable.watch_reward_9), 23);
        drawable.addFrame(ContextCompat.getDrawable(context, R.drawable.watch_reward_10), 23);
        drawable.addFrame(ContextCompat.getDrawable(context, R.drawable.watch_reward_12), 23);
        drawable.addFrame(ContextCompat.getDrawable(context, R.drawable.watch_reward_13), 23);
        drawable.addFrame(ContextCompat.getDrawable(context, R.drawable.watch_reward_14), 23);
        drawable.addFrame(ContextCompat.getDrawable(context, R.drawable.watch_reward_15), 23);
        drawable.addFrame(ContextCompat.getDrawable(context, R.drawable.watch_reward_16), 23);
        drawable.addFrame(ContextCompat.getDrawable(context, R.drawable.watch_reward_17), 23);
        drawable.addFrame(ContextCompat.getDrawable(context, R.drawable.watch_reward_18), 23);
        drawable.addFrame(ContextCompat.getDrawable(context, R.drawable.watch_reward_19), 23);
        drawable.addFrame(ContextCompat.getDrawable(context, R.drawable.watch_reward_20), 23);
        drawable.addFrame(ContextCompat.getDrawable(context, R.drawable.watch_reward_21), 23);
        drawable.addFrame(ContextCompat.getDrawable(context, R.drawable.watch_reward_22), VALUE_ANIM_DURATION);
        drawable.addFrame(ContextCompat.getDrawable(context, R.drawable.watch_reward_1), 23);
        drawable.setOneShot(true);
        return drawable;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv:
                switchSelector();
                break;
        }
    }

    private void switchSelector() {
        boolean isSelect = selector.isSelected();
        selector.setSelected(!isSelect);
    }
}
