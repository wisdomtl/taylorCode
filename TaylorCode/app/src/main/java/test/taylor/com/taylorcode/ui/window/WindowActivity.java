package test.taylor.com.taylorcode.ui.window;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.Toast;

import test.taylor.com.taylorcode.R;
import test.taylor.com.taylorcode.launch_mode.ActivityB;
import test.taylor.com.taylorcode.rxjava.TimeoutActivity;
import test.taylor.com.taylorcode.ui.custom_view.selector.ProgressRing;
import test.taylor.com.taylorcode.util.DimensionUtil;
import test.taylor.com.taylorcode.util.Timer;

public class WindowActivity extends Activity implements View.OnClickListener, CustomPopupWindow.OnItemClickListener {
    public static final int VALUE_ANIM_DURATION = 800;
    private final float FULL_TIME_MILLISECOND = 6 * 1000;
    private static final int BOMB_ANIM_DURATION_IN_MILLISECOND = 6 * 100;

    public static final String TAG_WINDOW_A = "A";
    public static final String TAG_WINDOW_B = "B";
    //    private PopupWindow popupWindow;
    private CustomPopupWindow popupWindow;
    private Timer timer;
    private int d1 = 400;
    private int d2 = 400;
    private View floatWindowPartnerView ;
    private AnimationDrawable animationDrawable ;
    private ProgressRing progressRing ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View content = LayoutInflater.from(this).inflate(R.layout.window_activity, null);
        setContentView(content);
//        findViewById(R.id.btn_show_window).setOnClickListener(this);
        findViewById(R.id.btn_show_popup_window).setOnClickListener(this);
        findViewById(R.id.btn_outside).setOnClickListener(this);
//        findViewById(R.id.btn_application_window).setOnClickListener(this);
//        findViewById(R.id.btn_start_activity).setOnClickListener(this);
//        findViewById(R.id.btn_start_activityB).setOnClickListener(this);
//        findViewById(R.id.btn_stop_timer).setOnClickListener(this);
//        findViewById(R.id.btn_start_timer).setOnClickListener(this);
//        findViewById(R.id.start_window_partner).setOnClickListener(this);


        FloatWindow.getInstance().setView(generateWindowView(), TAG_WINDOW_A);
        FloatWindow.getInstance().setWidth(DimensionUtil.dp2px(54), WindowActivity.TAG_WINDOW_A);
        FloatWindow.getInstance().setHeight(DimensionUtil.dp2px(54), WindowActivity.TAG_WINDOW_A);
        FloatWindow.getInstance().setOnClickListener(new FloatWindow.OnWindowViewClickListener() {
            @Override
            public void onWindowViewClick() {
                Log.v("ttaylor", "WindowActivity.onWindowViewClick()" + "  ");
            }
        });
        FloatWindow.getInstance().init(this).show(this, TAG_WINDOW_A);

    }

    private View getWindowView(Context context, int layoutId) {
        View view = LayoutInflater.from(context).inflate(layoutId, null);
        return view;
    }


    /**
     * window case1:show window
     * TBC: how to dismiss
     *
     * @param context
     */
    private void showWindow(Context context) {
        if (context == null) {
            Log.v("ttaylor", "WindowActivity.showWindow()" + "  context is null");
            return;
        }
        WindowManager windowManager = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE));
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        //this flag allow touching outside window
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        //android.permission.SYSTEM_ALERT_WINDOW is needed,or permission denied for window type 2003
//        layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION;
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.CENTER;
        windowManager.addView(getWindowView(context, R.layout.window_content), layoutParams);
    }

    //transition is much more customizable than setAnimationStyle() but there is api level constraint
//    private Transition getTransition(int gravity){
//        Slide slide = new Slide(gravity) ;
//        slide.setDuration(800) ;
//        return slide ;
//    }


    /**
     * window case2:popup window shown at the bottom of screen
     * TBC:how to use interpolation
     *
     * @param context
     * @param rootView the root view of the hole activity
     */
    public void showBottomPopupWindow(Context context, View rootView) {
//        if (popupWindow == null) {
//            View windowView = getWindowView(context);
//            bindWindowClickListener(windowView);
//            popupWindow = new PopupWindow(windowView,
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT, true);
//
//            popupWindow.setBackgroundDrawable(new BitmapDrawable());
//            //TBC:interceptor touch event on popupwindow
//            popupWindow.setTouchInterceptor(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    return false;
//                }
//            });
//            //popup animation
////            popupWindow.setAnimationStyle(R.style.popup_window_anim);
//            //transition is much more customizable than setAnimationStyle() but there is api level constraint
////            popupWindow.setEnterTransition(getTransition(Gravity.BOTTOM));
////            popupWindow.setExitTransition(getTransition(Gravity.RIGHT));
//            //listen popup window dismiss event
//            PopupWindow.OnDismissListener onDismissListener = new PopupWindow.OnDismissListener() {
//                @Override
//                public void onDismiss() {
//                    Log.v("ttaylor", "WindowActivity.onDismiss()" + "  ");
//                    changeBackgroundAlpha(WindowActivity.this, 1.0f);
//                }
//            };
//            popupWindow.setOnDismissListener(onDismissListener);
//        }
//
//        if (popupWindow.isShowing()) {
//            popupWindow.dismiss();
//            changeBackgroundAlpha(this, 1.0f);
//        } else {
//            popupWindow.showAtLocation(this.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
//            changeBackgroundAlpha(this, 0.5f);
//        }

    }


    public void showBottomPopupWindow2(Context context, int layoutId) {
        if (popupWindow == null) {
            View windowView = getWindowView(context, layoutId);
            popupWindow = new CustomPopupWindow((ViewGroup) windowView,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, this);

            popupWindow.setOnItemClickListener(this);
        }

        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            popupWindow.showAtLocation(this.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        }

    }

    private void bindWindowClickListener(View rootView) {
        rootView.findViewById(R.id.btn_sexual_content).setOnClickListener(this);
        rootView.findViewById(R.id.btn_violent).setOnClickListener(this);
        rootView.findViewById(R.id.btn_spam).setOnClickListener(this);
        rootView.findViewById(R.id.btn_cancel).setOnClickListener(this);

    }

    private void changeBackgroundAlpha(Activity activity, float alpha) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.alpha = alpha;
        window.setAttributes(layoutParams);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
//            case R.id.btn_show_window:
//                showWindow(this);
//                break;
            case R.id.btn_show_popup_window:
                showBottomPopupWindow2(this, R.layout.window_content);
                break;
            case R.id.btn_outside:
                Toast.makeText(this, "btn out side of pop up window", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_violent:
                Log.v("ttaylor", "WindowActivity.onClick()" + "  violent");
                popupWindow.dismiss();
                break;
            case R.id.btn_sexual_content:
                Log.v("ttaylor", "WindowActivity.onClick()" + "  sexual");
                popupWindow.dismiss();
                break;
            case R.id.btn_spam:
                Log.v("ttaylor", "WindowActivity.onClick()" + "  spam");
                popupWindow.dismiss();
                break;
            case R.id.btn_cancel:
                Log.v("ttaylor", "WindowActivity.onClick()" + "  ");
                popupWindow.dismiss();
                break;
//            case R.id.btn_application_window:
//                View windowView = generateWindowView();
//                WindowManager.LayoutParams layoutParams = generateWindowLayoutParam(this, windowView);
//                showApplicationWindow(this, windowView, layoutParams);
//                break;
//            case R.id.btn_start_activity:
//                startAnotherActivity();
//                break;
//            case R.id.btn_start_activityB:
//                startAnotherActivityB();
//                break;
//            case R.id.btn_stop_timer:
//                stopTimer();
//                break;
//            case R.id.btn_start_timer:
//                startTimer();
//                break;
//            case R.id.start_window_partner:
//                showFloatWindowPartner(this);
//                break;
            default:
                break;
        }
    }

    private void startTimer() {
        if (timer != null) {
            timer.start(0, 20);
        }
    }

    private void stopTimer() {
        if (timer != null) {
            timer.pause();
        }
    }

    @Override
    public boolean onWindowItemClick(View view) {
        Log.v("ttaylor", "WindowActivity.onWindowItemClick()" + "  ");
        int id = view.getId();
        switch (id) {
            case R.id.btn_violent:
                Toast.makeText(this, "btn_violent", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_sexual_content:
                Toast.makeText(this, "btn_sexual_content", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_spam:
                Toast.makeText(this, "btn_spam", Toast.LENGTH_SHORT).show();
                popupWindow = null;
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        showBottomPopupWindow2(WindowActivity.this, R.layout.window_more_options);
                    }
                }, 500);
                break;
            case R.id.btn_cancel:
                Toast.makeText(this, "btn_cancel", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
        return false;
    }

    private View generateWindowView() {
//        TextView tv = new TextView(this);
//        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        tv.setLayoutParams(layoutParams);
//        tv.setText("window view");
//        tv.setTextSize(40);
//        return tv;
progressRing = new ProgressRing(this);
       animationDrawable = createAnimationDrawable(this);
        progressRing.setImageDrawable(animationDrawable);
        timer = new Timer(new Timer.TimerListener() {
            @Override
            public void onTick(long pastMillisecond) {
                float mod = pastMillisecond % FULL_TIME_MILLISECOND;

                Log.v("ttaylor", "CustomViewActivity.onTick()" + " mod=" + mod + ",past=" + pastMillisecond);
                float progress = getProgress(mod, FULL_TIME_MILLISECOND);
                progressRing.setProgress(progress);
                if (mod == 0) {
                    doFrameAnimation(animationDrawable);
                    doValueAnimator(10, 62, progressRing, VALUE_ANIM_DURATION);
                }
            }
        });
        timer.start(0, 100);
        return progressRing;
    }

    private void doFrameAnimation(AnimationDrawable animationDrawable) {
        progressRing.setImageDrawable(animationDrawable);
        if (animationDrawable.isRunning()) {
            animationDrawable.stop();
        }
        animationDrawable.start();
    }

    private float getProgress(float mod, float totalTime) {
        float i = mod == 0 ? 1 : mod;
        return i / totalTime;
    }

    private void doValueAnimator(float start, float end, final ProgressRing ring, int duration) {
        ring.setTextAlpha(255);
        ValueAnimator animator = ValueAnimator.ofFloat(start, end);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(duration);

        ValueAnimator animator1 = ValueAnimator.ofFloat(end, end);
        animator1.setDuration(d1);

        ValueAnimator animator2 = ValueAnimator.ofInt(255, 0);
        animator2.setDuration(d2);

        AnimatorSet set = new AnimatorSet();
        set.playSequentially(animator, animator1, animator2);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float size = (Float) animation.getAnimatedValue();
                ring.setTextSize(size);
            }
        });
        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float size = (Float) animation.getAnimatedValue();
                ring.setTextSize(size);
            }
        });
        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int alpha = (Integer) animation.getAnimatedValue();
                ring.setTextAlpha(alpha);
                ring.setImageResource(R.drawable.watch_reward_1);
            }
        });
        ring.setText("+8");
        set.start();
    }

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

    /**
     * window case3:show window in application above all activity
     *
     * @param context
     * @param windowView
     * @return
     */
    private WindowManager.LayoutParams generateWindowLayoutParam(Context context, View windowView) {
        WindowManager windowManager = (WindowManager) getApplication().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;//this is the key point let window be above all activity
//        layoutParams.token = getWindow().getDecorView().getWindowToken();
        layoutParams.format = PixelFormat.TRANSLUCENT;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.x = 0;
        layoutParams.y = screenHeight / 3;
        return layoutParams;
    }

    @Override
    protected void onPause() {
        super.onPause();
        FloatWindow.getInstance().dismiss();
    }

    /**
     * window case3:show window in application above all activity
     *
     * @param context application context
     */
    private void showApplicationWindow(Context context, View windowView, WindowManager.LayoutParams layoutParam) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.addView(windowView, layoutParam);
    }

    private void startAnotherActivity() {
        Intent intent = new Intent(this, TimeoutActivity.class);
        this.startActivity(intent);
    }

    private void startAnotherActivityB() {
        Intent intent = new Intent(this, ActivityB.class);
        this.startActivity(intent);
    }

    private void showFloatWindowPartner(final Context context) {
        Log.v("ttaylor", "WindowActivity.showFloatWindowPartner()" + "  ");
        final WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager == null) {
            return;
        }

        if (floatWindowPartnerView == null) {
            floatWindowPartnerView = LayoutInflater.from(context).inflate(R.layout.float_window_partner, null);
        }
        Pair<Integer, Integer> screenDimension = prepareScreenDimension(this);
        WindowManager.LayoutParams layoutParams = FloatWindow.getInstance().getLayoutParam();
        final WindowManager.LayoutParams params = createFloatWindowPartnerLayoutParam(screenDimension.first, screenDimension.second, layoutParams, floatWindowPartnerView);
        if (floatWindowPartnerView!=null && floatWindowPartnerView.getParent() == null) {
            windowManager.addView(floatWindowPartnerView, params);
            FloatWindow.getInstance().setOnWindowStatusChangeListener(new OnWindowStatusListener(windowManager, floatWindowPartnerView, params));
        }
    }

    private class OnWindowStatusListener implements FloatWindow.OnWindowStatusChangeListener {
        private View partnerView;
        private WindowManager windowManager;
        private WindowManager.LayoutParams layoutParams;

        public OnWindowStatusListener(WindowManager windowManager, View partnerView, WindowManager.LayoutParams layoutParams) {
            this.windowManager = windowManager;
            this.partnerView = partnerView;
            this.layoutParams = layoutParams;

        }

        @Override
        public void onShow() {

        }

        @Override
        public void onDismiss() {

        }

        @Override
        public WindowManager.LayoutParams onWindowMove(float dx, float dy, int screenWidth, int screenHeight, WindowManager.LayoutParams layoutParams) {
            this.layoutParams.x += dx;
            this.layoutParams.y += dy;


            int rightMost = screenWidth - this.layoutParams.width;
            int leftMost = 0;
            int topMost = 0;
            int bottomMost = screenHeight - this.layoutParams.height;
            if (layoutParams != null) {
                if (this.layoutParams.x < layoutParams.x) {
                    rightMost = screenWidth - (this.layoutParams.width + layoutParams.width / 2);
                } else {
                    leftMost = layoutParams.width / 2;
                }
                topMost = (layoutParams.height - this.layoutParams.height) / 2;
                bottomMost = screenHeight - this.layoutParams.height - (layoutParams.height - this.layoutParams.height) / 2;
            }

            if (this.layoutParams.x < leftMost) {
                this.layoutParams.x = leftMost;
            }
            if (this.layoutParams.x > rightMost) {
                this.layoutParams.x = rightMost;
            }
            if (this.layoutParams.y < topMost) {
                this.layoutParams.y = topMost;
            }
            if (this.layoutParams.y > bottomMost) {
                this.layoutParams.y = bottomMost;
            }
            if (windowManager != null) {
                windowManager.updateViewLayout(partnerView, this.layoutParams);
            }
            return this.layoutParams;
        }
    }

    private WindowManager.LayoutParams createFloatWindowPartnerLayoutParam(int screenWidth, int screenHeight, WindowManager.LayoutParams rewardBallParam, View floatWindowPartnerView) {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION;
        layoutParams.format = PixelFormat.TRANSLUCENT;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        layoutParams.width = DimensionUtil.dp2px(140);
        layoutParams.height = DimensionUtil.dp2px(42);

        if ((rewardBallParam.x + layoutParams.width - rewardBallParam.width / 2) > screenWidth) {
            //left side
            layoutParams.x = rewardBallParam.x - (layoutParams.width - rewardBallParam.width / 2);
            layoutParams.y = rewardBallParam.y + (rewardBallParam.height - layoutParams.height) / 2;
            int left = DimensionUtil.dp2px(10);
            int top = DimensionUtil.dp2px(2);
            int right = DimensionUtil.dp2px(33);
            int bottom = DimensionUtil.dp2px(2);
            floatWindowPartnerView.setPadding(left, top, right, bottom);
        } else {
            //right side
            layoutParams.x = rewardBallParam.x + rewardBallParam.width / 2;
            layoutParams.y = rewardBallParam.y + (rewardBallParam.height - layoutParams.height) / 2;
            int left = DimensionUtil.dp2px(33);
            int top = DimensionUtil.dp2px(2);
            int right = DimensionUtil.dp2px(5);
            int bottom = DimensionUtil.dp2px(2);
            floatWindowPartnerView.setPadding(left, top, right, bottom);
        }
        return layoutParams;
    }

    private Pair<Integer, Integer> prepareScreenDimension(Context activity) {
        int screenWidth = 0;
        int screenHeight = 0;
        WindowManager windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            DisplayMetrics dm = new DisplayMetrics();
            Display display = windowManager.getDefaultDisplay();
            if (display != null) {
                windowManager.getDefaultDisplay().getMetrics(dm);
                screenWidth = dm.widthPixels;
                screenHeight = dm.heightPixels;
            }
        }

        Pair<Integer, Integer> screenDimension = new Pair<>(screenWidth, screenHeight);
        return screenDimension;
    }
}
