package test.taylor.com.taylorcode.ui.window

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.PixelFormat
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.util.Pair
import android.view.*
import android.view.animation.AccelerateInterpolator
import android.widget.FrameLayout
import android.widget.Toast
import test.taylor.com.taylorcode.R
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.launch_mode.ActivityB
import test.taylor.com.taylorcode.rxjava.TimeoutActivity
import test.taylor.com.taylorcode.ui.custom_view.selector.ProgressRing
import test.taylor.com.taylorcode.ui.window.FloatWindow.WindowClickListener
import test.taylor.com.taylorcode.ui.window.FloatWindow.WindowInfo
import test.taylor.com.taylorcode.ui.window.FloatWindow.dismiss
import test.taylor.com.taylorcode.ui.window.FloatWindow.layoutParam
import test.taylor.com.taylorcode.ui.window.FloatWindow.setWindowStateListener
import test.taylor.com.taylorcode.util.DimensionUtil
import test.taylor.com.taylorcode.util.Timer
import test.taylor.com.taylorcode.util.Timer.TimerListener

class WindowActivity : Activity(), View.OnClickListener, CustomPopupWindow.OnItemClickListener {
    private val FULL_TIME_MILLISECOND = 6 * 1000.toFloat()
    //    private PopupWindow popupWindow;
    private var popupWindow: CustomPopupWindow? = null
    private var timer: Timer? = null
    private val d1 = 400
    private val d2 = 400
    private var floatWindowPartnerView: View? = null
    private var animationDrawable: AnimationDrawable? = null
    private var progressRing: ProgressRing? = null
    private var windowInfo: WindowInfo? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val content = LayoutInflater.from(this).inflate(R.layout.window_activity, null)
        setContentView(content)
        //        findViewById(R.id.btn_show_window).setOnClickListener(this);
        findViewById<View>(R.id.btn_show_popup_window).setOnClickListener(this)
        findViewById<View>(R.id.btn_outside).setOnClickListener(this)
        //        findViewById(R.id.btn_application_window).setOnClickListener(this);
//        findViewById(R.id.btn_start_activity).setOnClickListener(this);
//        findViewById(R.id.btn_start_activityB).setOnClickListener(this);
//        findViewById(R.id.btn_stop_timer).setOnClickListener(this);
//        findViewById(R.id.btn_start_timer).setOnClickListener(this);
//        findViewById(R.id.start_window_partner).setOnClickListener(this);
//        FloatWindow.INSTANCE.setView(generateWindowView(), TAG_WINDOW_A);
//        FloatWindow.INSTANCE.setWidth(DimensionUtil.dp2px(54), WindowActivity.TAG_WINDOW_A);
//        FloatWindow.INSTANCE.setHeight(DimensionUtil.dp2px(54), WindowActivity.TAG_WINDOW_A);
        findViewById<View>(R.id.ivArrow).setOnClickListener { v ->
            v.isSelected = !v.isSelected
        }
        FloatWindow.setClickListener(object : WindowClickListener {
            override fun onWindowClick(windowInfo: WindowInfo?): Boolean {
                return false
            }
        })
    }

    override fun onResume() {
        super.onResume()
        if (windowInfo == null) {
            windowInfo = WindowInfo(generateWindowView())
            windowInfo!!.width = DimensionUtil.dp2px(54.0)
            windowInfo!!.height = DimensionUtil.dp2px(54.0)
            FloatWindow.show(this, TAG_WINDOW_A, windowInfo, 0, 0, true)
        }
        FloatWindow.show(this, TAG_WINDOW_A, dragEnable = true)
        FloatWindow.setOutsideTouchable(true, {
            Log.v("ttaylor", "tag=touch outside, WindowActivity.onResume()  ")
        })
    }

    private fun getWindowView(context: Context, layoutId: Int): View {
        return LayoutInflater.from(context).inflate(layoutId, null)
    }

    /**
     * window case1:show window
     * TBC: how to dismiss
     *
     * @param context
     */
    private fun showWindow(context: Context?) {
        if (context == null) {
            Log.v("ttaylor", "WindowActivity.showWindow()" + "  context is null")
            return
        }
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val layoutParams = WindowManager.LayoutParams()
        //this flag allow touching outside window
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
        //android.permission.SYSTEM_ALERT_WINDOW is needed,or permission denied for window type 2003
//        layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        layoutParams.gravity = Gravity.CENTER
        windowManager.addView(getWindowView(context, R.layout.window_content), layoutParams)
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
    fun showBottomPopupWindow(context: Context?, rootView: View?) { //        if (popupWindow == null) {
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

    fun showBottomPopupWindow2(context: Context, layoutId: Int) {
        if (popupWindow == null) {
            val windowView = getWindowView(context, layoutId)
            popupWindow = CustomPopupWindow(
                windowView as ViewGroup,
                ViewGroup.LayoutParams.MATCH_PARENT,
                500, this
            )
            popupWindow!!.setOnItemClickListener(this)
        }
        if (popupWindow!!.isShowing) {
            popupWindow!!.dismiss()
        } else {
            popupWindow!!.showAtLocation(this.window.decorView, Gravity.BOTTOM, 0, 0)
        }

        val classWindow2: Class<*>? = popupWindow?.javaClass
        val father = classWindow2?.superclass
        val popupDecorView = father?.getDeclaredField("mDecorView")
        popupDecorView?.isAccessible = true
        val mask = View {
            layout_id = "mask"
            layout_width = match_parent
            layout_height = match_parent
            background_color = "#80ff00ff"
        }
        (popupDecorView?.get(popupWindow) as? FrameLayout)?.addView(mask)
    }

    private fun bindWindowClickListener(rootView: View) {
        rootView.findViewById<View>(R.id.btn_sexual_content).setOnClickListener(this)
        rootView.findViewById<View>(R.id.btn_violent).setOnClickListener(this)
        rootView.findViewById<View>(R.id.btn_spam).setOnClickListener(this)
        rootView.findViewById<View>(R.id.btn_cancel).setOnClickListener(this)
    }

    private fun changeBackgroundAlpha(activity: Activity, alpha: Float) {
        val window = activity.window
        val layoutParams = window.attributes
        layoutParams.alpha = alpha
        window.attributes = layoutParams
    }

    override fun onClick(view: View) {
        val id = view.id
        when (id) {
            R.id.btn_show_popup_window -> showBottomPopupWindow2(this, R.layout.window_content)
            R.id.btn_outside -> Toast.makeText(this, "btn out side of pop up window", Toast.LENGTH_SHORT).show()
            R.id.btn_violent -> {
                Log.v("ttaylor", "WindowActivity.onClick()" + "  violent")
                popupWindow!!.dismiss()
            }
            R.id.btn_sexual_content -> {
                Log.v("ttaylor", "WindowActivity.onClick()" + "  sexual")
                popupWindow!!.dismiss()
            }
            R.id.btn_spam -> {
                Log.v("ttaylor", "WindowActivity.onClick()" + "  spam")
                popupWindow!!.dismiss()
            }
            R.id.btn_cancel -> {
                Log.v("ttaylor", "WindowActivity.onClick()" + "  ")
                popupWindow!!.dismiss()
            }
            else -> {
            }
        }
    }

    private fun startTimer() {
        if (timer != null) {
            timer!!.start(0, 20)
        }
    }

    private fun stopTimer() {
        if (timer != null) {
            timer!!.pause()
        }
    }

    override fun onWindowItemClick(view: View): Boolean {
        Log.v("ttaylor", "WindowActivity.onWindowItemClick()" + "  ")
        val id = view.id
        when (id) {
            R.id.btn_violent -> Toast.makeText(this, "btn_violent", Toast.LENGTH_SHORT).show()
            R.id.btn_sexual_content -> Toast.makeText(this, "btn_sexual_content", Toast.LENGTH_SHORT).show()
            R.id.btn_spam -> {
                Toast.makeText(this, "btn_spam", Toast.LENGTH_SHORT).show()
                popupWindow = null
                view.postDelayed({ showBottomPopupWindow2(this@WindowActivity, R.layout.window_more_options) }, 500)
            }
            R.id.btn_cancel -> Toast.makeText(this, "btn_cancel", Toast.LENGTH_SHORT).show()
            else -> {
            }
        }
        return false
    }

    private fun generateWindowView(): View { //        TextView tv = new TextView(this);
//        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        tv.setLayoutParams(layoutParams);
//        tv.setText("window view");
//        tv.setTextSize(40);
//        return tv;
        progressRing = ProgressRing(this)
        animationDrawable = createAnimationDrawable(this)
        progressRing!!.setImageDrawable(animationDrawable)
        timer = Timer(object : TimerListener {
            override fun onTick(pastMillisecond: Long) {
                val mod = pastMillisecond % FULL_TIME_MILLISECOND
                Log.v("ttaylor", "CustomViewActivity.onTick() mod=$mod,past=$pastMillisecond")
                val progress = getProgress(mod, FULL_TIME_MILLISECOND)
                progressRing!!.setProgress(progress)
                if (mod == 0f) {
                    doFrameAnimation(animationDrawable)
                    doValueAnimator(10f, 62f, progressRing!!, VALUE_ANIM_DURATION)
                }
            }
        })
        timer!!.start(0, 100)
        return progressRing!!
    }

    private fun doFrameAnimation(animationDrawable: AnimationDrawable?) {
        progressRing!!.setImageDrawable(animationDrawable)
        if (animationDrawable!!.isRunning) {
            animationDrawable.stop()
        }
        animationDrawable.start()
    }

    private fun getProgress(mod: Float, totalTime: Float): Float {
        val i: Float = if (mod == 0f) 1f else mod
        return i / totalTime
    }

    private fun doValueAnimator(start: Float, end: Float, ring: ProgressRing, duration: Int) {
        ring.setTextAlpha(255)
        val animator = ValueAnimator.ofFloat(start, end)
        animator.interpolator = AccelerateInterpolator()
        animator.duration = duration.toLong()
        val animator1 = ValueAnimator.ofFloat(end, end)
        animator1.duration = d1.toLong()
        val animator2 = ValueAnimator.ofInt(255, 0)
        animator2.duration = d2.toLong()
        val set = AnimatorSet()
        set.playSequentially(animator, animator1, animator2)
        animator.addUpdateListener { animation ->
            val size = animation.animatedValue as Float
            ring.setTextSize(size)
        }
        animator1.addUpdateListener { animation ->
            val size = animation.animatedValue as Float
            ring.setTextSize(size)
        }
        animator2.addUpdateListener { animation ->
            val alpha = animation.animatedValue as Int
            ring.setTextAlpha(alpha)
            ring.setImageResource(R.drawable.watch_reward_1)
        }
        ring.setText("+8")
        set.start()
    }

    private fun createAnimationDrawable(context: Context): AnimationDrawable {
        val drawable = AnimationDrawable()
        val frameDuration = BOMB_ANIM_DURATION_IN_MILLISECOND / 21
        drawable.addFrame(BitmapDrawable(decodeSampledBitmapFromResource(context.resources, R.drawable.watch_reward_1, DimensionUtil.dp2px(54.0), DimensionUtil.dp2px(54.0))), frameDuration)
        drawable.addFrame(BitmapDrawable(decodeSampledBitmapFromResource(context.resources, R.drawable.watch_reward_2, DimensionUtil.dp2px(54.0), DimensionUtil.dp2px(54.0))), frameDuration)
        drawable.addFrame(BitmapDrawable(decodeSampledBitmapFromResource(context.resources, R.drawable.watch_reward_3, DimensionUtil.dp2px(54.0), DimensionUtil.dp2px(54.0))), frameDuration)
        drawable.addFrame(BitmapDrawable(decodeSampledBitmapFromResource(context.resources, R.drawable.watch_reward_4, DimensionUtil.dp2px(54.0), DimensionUtil.dp2px(54.0))), frameDuration)
        drawable.addFrame(BitmapDrawable(decodeSampledBitmapFromResource(context.resources, R.drawable.watch_reward_5, DimensionUtil.dp2px(54.0), DimensionUtil.dp2px(54.0))), frameDuration)
        drawable.addFrame(BitmapDrawable(decodeSampledBitmapFromResource(context.resources, R.drawable.watch_reward_6, DimensionUtil.dp2px(54.0), DimensionUtil.dp2px(54.0))), frameDuration)
        drawable.addFrame(BitmapDrawable(decodeSampledBitmapFromResource(context.resources, R.drawable.watch_reward_7, DimensionUtil.dp2px(54.0), DimensionUtil.dp2px(54.0))), frameDuration)
        drawable.addFrame(BitmapDrawable(decodeSampledBitmapFromResource(context.resources, R.drawable.watch_reward_8, DimensionUtil.dp2px(54.0), DimensionUtil.dp2px(54.0))), frameDuration)
        drawable.addFrame(BitmapDrawable(decodeSampledBitmapFromResource(context.resources, R.drawable.watch_reward_9, DimensionUtil.dp2px(54.0), DimensionUtil.dp2px(54.0))), frameDuration)
        drawable.addFrame(BitmapDrawable(decodeSampledBitmapFromResource(context.resources, R.drawable.watch_reward_10, DimensionUtil.dp2px(54.0), DimensionUtil.dp2px(54.0))), frameDuration)
        drawable.addFrame(BitmapDrawable(decodeSampledBitmapFromResource(context.resources, R.drawable.watch_reward_11, DimensionUtil.dp2px(54.0), DimensionUtil.dp2px(54.0))), frameDuration)
        drawable.addFrame(BitmapDrawable(decodeSampledBitmapFromResource(context.resources, R.drawable.watch_reward_12, DimensionUtil.dp2px(54.0), DimensionUtil.dp2px(54.0))), frameDuration)
        drawable.addFrame(BitmapDrawable(decodeSampledBitmapFromResource(context.resources, R.drawable.watch_reward_13, DimensionUtil.dp2px(54.0), DimensionUtil.dp2px(54.0))), frameDuration)
        drawable.addFrame(BitmapDrawable(decodeSampledBitmapFromResource(context.resources, R.drawable.watch_reward_14, DimensionUtil.dp2px(54.0), DimensionUtil.dp2px(54.0))), frameDuration)
        drawable.addFrame(BitmapDrawable(decodeSampledBitmapFromResource(context.resources, R.drawable.watch_reward_15, DimensionUtil.dp2px(54.0), DimensionUtil.dp2px(54.0))), frameDuration)
        drawable.addFrame(BitmapDrawable(decodeSampledBitmapFromResource(context.resources, R.drawable.watch_reward_16, DimensionUtil.dp2px(54.0), DimensionUtil.dp2px(54.0))), frameDuration)
        drawable.addFrame(BitmapDrawable(decodeSampledBitmapFromResource(context.resources, R.drawable.watch_reward_17, DimensionUtil.dp2px(54.0), DimensionUtil.dp2px(54.0))), frameDuration)
        drawable.addFrame(BitmapDrawable(decodeSampledBitmapFromResource(context.resources, R.drawable.watch_reward_18, DimensionUtil.dp2px(54.0), DimensionUtil.dp2px(54.0))), frameDuration)
        drawable.addFrame(BitmapDrawable(decodeSampledBitmapFromResource(context.resources, R.drawable.watch_reward_19, DimensionUtil.dp2px(54.0), DimensionUtil.dp2px(54.0))), frameDuration)
        drawable.addFrame(BitmapDrawable(decodeSampledBitmapFromResource(context.resources, R.drawable.watch_reward_20, DimensionUtil.dp2px(54.0), DimensionUtil.dp2px(54.0))), frameDuration)
        drawable.addFrame(BitmapDrawable(decodeSampledBitmapFromResource(context.resources, R.drawable.watch_reward_21, DimensionUtil.dp2px(54.0), DimensionUtil.dp2px(54.0))), frameDuration)
        drawable.addFrame(BitmapDrawable(decodeSampledBitmapFromResource(context.resources, R.drawable.watch_reward_22, DimensionUtil.dp2px(54.0), DimensionUtil.dp2px(54.0))), frameDuration)
        drawable.isOneShot = true
        return drawable
    }

    /**
     * window case3:show window in application above all activity
     *
     * @param context
     * @param windowView
     * @return
     */
    private fun generateWindowLayoutParam(context: Context, windowView: View): WindowManager.LayoutParams {
        val windowManager = application.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        val screenWidth = dm.widthPixels
        val screenHeight = dm.heightPixels
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE //this is the key point let window be above all activity
        //        layoutParams.token = getWindow().getDecorView().getWindowToken();
        layoutParams.format = PixelFormat.TRANSLUCENT
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        layoutParams.gravity = Gravity.LEFT or Gravity.TOP
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        layoutParams.x = 0
        layoutParams.y = screenHeight / 3
        return layoutParams
    }

    override fun onPause() {
        super.onPause()
        dismiss()
    }

    /**
     * window case3:show window in application above all activity
     *
     * @param context application context
     */
    private fun showApplicationWindow(context: Context, windowView: View, layoutParam: WindowManager.LayoutParams) {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.addView(windowView, layoutParam)
    }

    private fun startAnotherActivity() {
        val intent = Intent(this, TimeoutActivity::class.java)
        this.startActivity(intent)
    }

    private fun startAnotherActivityB() {
        val intent = Intent(this, ActivityB::class.java)
        this.startActivity(intent)
    }

    private fun showFloatWindowPartner(context: Context) {
        Log.v("ttaylor", "WindowActivity.showFloatWindowPartner()" + "  ")
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            ?: return
        if (floatWindowPartnerView == null) {
            floatWindowPartnerView = LayoutInflater.from(context).inflate(R.layout.float_window_partner, null)
        }
        val screenDimension = prepareScreenDimension(this)
        val layoutParams = layoutParam
        val params = createFloatWindowPartnerLayoutParam(screenDimension.first, screenDimension.second, layoutParams, floatWindowPartnerView)
        if (floatWindowPartnerView != null && floatWindowPartnerView!!.parent == null) {
            windowManager.addView(floatWindowPartnerView, params)
            setWindowStateListener(OnWindowStatusListener(windowManager, floatWindowPartnerView!!, params))
        }
    }

    private inner class OnWindowStatusListener(
        private val windowManager: WindowManager?,
        private val partnerView: View,
        private val layoutParams: WindowManager.LayoutParams
    ) : FloatWindow.WindowStateListener {
        override fun onWindowShow() {}
        override fun onWindowDismiss() {}
        override fun onWindowMove(dx: Float, dy: Float, screenWidth: Int, screenHeight: Int, layoutParams: WindowManager.LayoutParams?): WindowManager.LayoutParams {
            this.layoutParams.x += dx.toInt()
            this.layoutParams.y += dy.toInt()
            var rightMost = screenWidth - this.layoutParams.width
            var leftMost = 0
            var topMost = 0
            var bottomMost = screenHeight - this.layoutParams.height
            if (layoutParams != null) {
                if (this.layoutParams.x < layoutParams.x) {
                    rightMost = screenWidth - (this.layoutParams.width + layoutParams.width / 2)
                } else {
                    leftMost = layoutParams.width / 2
                }
                topMost = (layoutParams.height - this.layoutParams.height) / 2
                bottomMost = screenHeight - this.layoutParams.height - (layoutParams.height - this.layoutParams.height) / 2
            }
            if (this.layoutParams.x < leftMost) {
                this.layoutParams.x = leftMost
            }
            if (this.layoutParams.x > rightMost) {
                this.layoutParams.x = rightMost
            }
            if (this.layoutParams.y < topMost) {
                this.layoutParams.y = topMost
            }
            if (this.layoutParams.y > bottomMost) {
                this.layoutParams.y = bottomMost
            }
            windowManager?.updateViewLayout(partnerView, this.layoutParams)
            return this.layoutParams
        }

    }

    private fun createFloatWindowPartnerLayoutParam(screenWidth: Int, screenHeight: Int, rewardBallParam: WindowManager.LayoutParams?, floatWindowPartnerView: View?): WindowManager.LayoutParams {
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION
        layoutParams.format = PixelFormat.TRANSLUCENT
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        layoutParams.gravity = Gravity.LEFT or Gravity.TOP
        layoutParams.width = DimensionUtil.dp2px(140.0)
        layoutParams.height = DimensionUtil.dp2px(42.0)
        if (rewardBallParam!!.x + layoutParams.width - rewardBallParam.width / 2 > screenWidth) { //left side
            layoutParams.x = rewardBallParam.x - (layoutParams.width - rewardBallParam.width / 2)
            layoutParams.y = rewardBallParam.y + (rewardBallParam.height - layoutParams.height) / 2
            val left = DimensionUtil.dp2px(10.0)
            val top = DimensionUtil.dp2px(2.0)
            val right = DimensionUtil.dp2px(33.0)
            val bottom = DimensionUtil.dp2px(2.0)
            floatWindowPartnerView!!.setPadding(left, top, right, bottom)
        } else { //right side
            layoutParams.x = rewardBallParam.x + rewardBallParam.width / 2
            layoutParams.y = rewardBallParam.y + (rewardBallParam.height - layoutParams.height) / 2
            val left = DimensionUtil.dp2px(33.0)
            val top = DimensionUtil.dp2px(2.0)
            val right = DimensionUtil.dp2px(5.0)
            val bottom = DimensionUtil.dp2px(2.0)
            floatWindowPartnerView!!.setPadding(left, top, right, bottom)
        }
        return layoutParams
    }

    private fun prepareScreenDimension(activity: Context): Pair<Int, Int> {
        var screenWidth = 0
        var screenHeight = 0
        val windowManager = activity.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        if (windowManager != null) {
            val dm = DisplayMetrics()
            val display = windowManager.defaultDisplay
            if (display != null) {
                windowManager.defaultDisplay.getMetrics(dm)
                screenWidth = dm.widthPixels
                screenHeight = dm.heightPixels
            }
        }
        return Pair(screenWidth, screenHeight)
    }

    companion object {
        const val VALUE_ANIM_DURATION = 800
        private const val BOMB_ANIM_DURATION_IN_MILLISECOND = 6 * 100
        const val TAG_WINDOW_A = "A"
        const val TAG_WINDOW_B = "B"
        fun decodeSampledBitmapFromResource(res: Resources?, resId: Int, reqWidth: Int, reqHeight: Int): Bitmap {
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeResource(res, resId, options)
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
            // 使用获取到的inSampleSize值再次解析图片
            options.inJustDecodeBounds = false
            options.inPreferredConfig = Bitmap.Config.RGB_565
            return BitmapFactory.decodeResource(res, resId, options)
        }

        private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int { // 源图片的高度和宽度
            val height = options.outHeight
            val width = options.outWidth
            var inSampleSize = 1
            if (height > reqHeight || width > reqWidth) {
                val halfHeight = height / 2
                val halfWidth = width / 2
                // Calculate the largest inSampleSize value that is a power of 2 and keeps both
// height and width larger than the requested height and width.
                while (halfHeight / inSampleSize > reqHeight && halfWidth / inSampleSize > reqWidth) {
                    inSampleSize *= 2
                }
            }
            return inSampleSize
        }
    }
}