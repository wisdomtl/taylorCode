package test.taylor.com.taylorcode.ui.anim;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;

import test.taylor.com.taylorcode.R;


public class AnimActivity extends Activity implements View.OnClickListener {

    private ImageView ivFrameAnim;
    private AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anim_activity);
        initView();
    }

    private void initView() {
        ivFrameAnim = ((ImageView) findViewById(R.id.frame_anim));
        animationDrawable = createAnimationDrawable(this);
        ivFrameAnim.setBackground(animationDrawable);
        ivFrameAnim.setOnClickListener(this);
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
