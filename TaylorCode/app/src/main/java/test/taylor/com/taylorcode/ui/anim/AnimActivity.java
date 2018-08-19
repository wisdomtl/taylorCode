package test.taylor.com.taylorcode.ui.anim;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
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
import test.taylor.com.taylorcode.util.DimensionUtil;


public class AnimActivity extends Activity implements View.OnClickListener {
    private static final int BOMB_ANIM_DURATION_IN_MILLISECOND = 6 * 100;

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
     * frame anim case 1:create frame anim with compressed bitmap
     *
     * @param context
     * @return
     */
    private AnimationDrawable createAnimationDrawable(Context context) {
        AnimationDrawable drawable = new AnimationDrawable();
        int frameDuration = BOMB_ANIM_DURATION_IN_MILLISECOND / 21;
        drawable.addFrame(new BitmapDrawable(decodeSampledBitmapFromResource(context.getResources(), R.drawable.watch_reward_1, DimensionUtil.dp2px(54), DimensionUtil.dp2px(54))), frameDuration);
        drawable.addFrame(new BitmapDrawable(decodeSampledBitmapFromResource(context.getResources(), R.drawable.watch_reward_2, DimensionUtil.dp2px(54), DimensionUtil.dp2px(54))), frameDuration);
        drawable.addFrame(new BitmapDrawable(decodeSampledBitmapFromResource(context.getResources(), R.drawable.watch_reward_3, DimensionUtil.dp2px(54), DimensionUtil.dp2px(54))), frameDuration);
        drawable.addFrame(new BitmapDrawable(decodeSampledBitmapFromResource(context.getResources(), R.drawable.watch_reward_4, DimensionUtil.dp2px(54), DimensionUtil.dp2px(54))), frameDuration);
        drawable.addFrame(new BitmapDrawable(decodeSampledBitmapFromResource(context.getResources(), R.drawable.watch_reward_5, DimensionUtil.dp2px(54), DimensionUtil.dp2px(54))), frameDuration);
        drawable.addFrame(new BitmapDrawable(decodeSampledBitmapFromResource(context.getResources(), R.drawable.watch_reward_6, DimensionUtil.dp2px(54), DimensionUtil.dp2px(54))), frameDuration);
        drawable.addFrame(new BitmapDrawable(decodeSampledBitmapFromResource(context.getResources(), R.drawable.watch_reward_7, DimensionUtil.dp2px(54), DimensionUtil.dp2px(54))), frameDuration);
        drawable.addFrame(new BitmapDrawable(decodeSampledBitmapFromResource(context.getResources(), R.drawable.watch_reward_8, DimensionUtil.dp2px(54), DimensionUtil.dp2px(54))), frameDuration);
        drawable.addFrame(new BitmapDrawable(decodeSampledBitmapFromResource(context.getResources(), R.drawable.watch_reward_9, DimensionUtil.dp2px(54), DimensionUtil.dp2px(54))), frameDuration);
        drawable.addFrame(new BitmapDrawable(decodeSampledBitmapFromResource(context.getResources(), R.drawable.watch_reward_10, DimensionUtil.dp2px(54), DimensionUtil.dp2px(54))), frameDuration);
        drawable.addFrame(new BitmapDrawable(decodeSampledBitmapFromResource(context.getResources(), R.drawable.watch_reward_11, DimensionUtil.dp2px(54), DimensionUtil.dp2px(54))), frameDuration);
        drawable.addFrame(new BitmapDrawable(decodeSampledBitmapFromResource(context.getResources(), R.drawable.watch_reward_12, DimensionUtil.dp2px(54), DimensionUtil.dp2px(54))), frameDuration);
        drawable.addFrame(new BitmapDrawable(decodeSampledBitmapFromResource(context.getResources(), R.drawable.watch_reward_13, DimensionUtil.dp2px(54), DimensionUtil.dp2px(54))), frameDuration);
        drawable.addFrame(new BitmapDrawable(decodeSampledBitmapFromResource(context.getResources(), R.drawable.watch_reward_14, DimensionUtil.dp2px(54), DimensionUtil.dp2px(54))), frameDuration);
        drawable.addFrame(new BitmapDrawable(decodeSampledBitmapFromResource(context.getResources(), R.drawable.watch_reward_15, DimensionUtil.dp2px(54), DimensionUtil.dp2px(54))), frameDuration);
        drawable.addFrame(new BitmapDrawable(decodeSampledBitmapFromResource(context.getResources(), R.drawable.watch_reward_16, DimensionUtil.dp2px(54), DimensionUtil.dp2px(54))), frameDuration);
        drawable.addFrame(new BitmapDrawable(decodeSampledBitmapFromResource(context.getResources(), R.drawable.watch_reward_17, DimensionUtil.dp2px(54), DimensionUtil.dp2px(54))), frameDuration);
        drawable.addFrame(new BitmapDrawable(decodeSampledBitmapFromResource(context.getResources(), R.drawable.watch_reward_18, DimensionUtil.dp2px(54), DimensionUtil.dp2px(54))), frameDuration);
        drawable.addFrame(new BitmapDrawable(decodeSampledBitmapFromResource(context.getResources(), R.drawable.watch_reward_19, DimensionUtil.dp2px(54), DimensionUtil.dp2px(54))), frameDuration);
        drawable.addFrame(new BitmapDrawable(decodeSampledBitmapFromResource(context.getResources(), R.drawable.watch_reward_20, DimensionUtil.dp2px(54), DimensionUtil.dp2px(54))), frameDuration);
        drawable.addFrame(new BitmapDrawable(decodeSampledBitmapFromResource(context.getResources(), R.drawable.watch_reward_21, DimensionUtil.dp2px(54), DimensionUtil.dp2px(54))), frameDuration);
        drawable.addFrame(new BitmapDrawable(decodeSampledBitmapFromResource(context.getResources(), R.drawable.watch_reward_22, DimensionUtil.dp2px(54), DimensionUtil.dp2px(54))), frameDuration);
        drawable.setOneShot(true);
        return drawable;
    }


    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
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
