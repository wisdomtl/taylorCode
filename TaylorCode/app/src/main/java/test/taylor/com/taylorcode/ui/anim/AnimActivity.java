package test.taylor.com.taylorcode.ui.anim;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import test.taylor.com.taylorcode.R;


public class AnimActivity extends Activity implements View.OnClickListener {

    private ImageView ivFrameAnim;
    private AnimationDrawable animationDrawable;

    private TextView tvScaleAnim;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anim_activity);
        initView();

        createValueAnimator();
    }



    private void initView() {
        ivFrameAnim = ((ImageView) findViewById(R.id.frame_anim));
        animationDrawable = createAnimationDrawable(this);
        ivFrameAnim.setImageDrawable(animationDrawable);
        ivFrameAnim.setOnClickListener(this);

        tvScaleAnim = createTextView(this) ;
        ((LinearLayout) findViewById(R.id.ll_animi_activity_root)).addView(tvScaleAnim);
        tvScaleAnim.startAnimation(createScaleAnimation());
    }

    /**
     * frame anim case 1:create frame anim
     *
     * @param context
     * @return
     */
    public AnimationDrawable createAnimationDrawable(Context context) {
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
        drawable.addFrame(ContextCompat.getDrawable(context, R.drawable.watch_reward_22), 23);
        drawable.addFrame(ContextCompat.getDrawable(context, R.drawable.watch_reward_1), 23);
        drawable.setOneShot(true);
        return drawable;
    }

    private TextView createTextView(Context context){
        TextView tv = new TextView(context) ;
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tv.setText("+1");
        tv.setTextSize(20);
        tv.setTextColor(Color.parseColor("#FFDD00"));
        tv.setTypeface(Typeface.DEFAULT_BOLD);
        tv.setLayoutParams(params);
        return tv ;
    }

    /**
     * scale anim case1: create scale anim
     * @return
     */
    private ScaleAnimation createScaleAnimation() {
        ScaleAnimation animation = new ScaleAnimation(1.0f, 2.0f, 1.0f, 2.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setFillBefore(true);
        animation.setDuration(300);
        animation.setRepeatCount(100);
        animation.setRepeatMode(Animation.INFINITE);
        return animation;
    }

    /**
     * value animator case1:create value animator
     */
    private void createValueAnimator() {
        ValueAnimator animator = ValueAnimator.ofFloat(15,25);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.v("ttaylor", "AnimActivity.onAnimationUpdate()" + "  value="+animation.getAnimatedValue());
            }
        });
        animator.start();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.frame_anim:
                //stop first is must or start wont work when clicking twice
                if (animationDrawable.isRunning()) {
                    animationDrawable.stop();
                }
                animationDrawable.start();
                break;
            default:
                break;
        }
    }
}
