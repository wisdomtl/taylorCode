package test.taylor.com.taylorcode.ui.custom_view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import test.taylor.com.taylorcode.R;
import test.taylor.com.taylorcode.util.Timer;

public class CustomViewActivity extends Activity {

    private ProgressRing progressRing;
    private AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_view_activity);

        /**
         *
         custom view case1:update custom view periodically
         */
        progressRing = ((ProgressRing) findViewById(R.id.progress_ring));
        animationDrawable = createAnimationDrawable(this);
        progressRing.setBackground(animationDrawable);
        final float FULL_TIME_MILLISECOND = 10 * 1000;
        new Timer(new Timer.TimerListener() {
            @Override
            public void onTick(long pastMillisecond) {
                float mod = pastMillisecond % FULL_TIME_MILLISECOND;
                float i = mod == 0 ? 1 : mod;
                float progress = i / FULL_TIME_MILLISECOND;
                Log.v("ttaylor", "CustomViewActivity.onTick()" + " ,past time=" + pastMillisecond + "  mod=" + mod + " ,progress=" + progress);
                progressRing.setProgress((pastMillisecond % FULL_TIME_MILLISECOND) / FULL_TIME_MILLISECOND);
                if (mod == 0) {
                    if (animationDrawable.isRunning()) {
                        animationDrawable.stop();
                    }
                    animationDrawable.start();
                }
            }
        }).start(2000, 50);
    }

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
}
