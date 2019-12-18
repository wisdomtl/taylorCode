package test.taylor.com.taylorcode.ui.window

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.PixelFormat
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.view.animation.LinearInterpolator
import java.util.*

/**
 * a window shows above an activity.
 * it can be dragged by finger and if your finger leave the screen, it will stick to the nearest border of the screen
 * it can be clicked
 */
object FloatWindow : View.OnTouchListener {
    /**
     * several window content stored by String tag ;
     */
    private var windowInfoMap: HashMap<String, WindowInfo?> = HashMap()
    private var windowInfo: WindowInfo? = null
    private var lastTouchX: Int = 0
    private var lastTouchY: Int = 0
    private val lastWeltX: Int = 0
    private var screenWidth: Int = 0
    private var screenHeight: Int = 0
    private var context: Context? = null
    private var gestureDetector: GestureDetector = GestureDetector(context, GestureListener())
    private var clickListener: WindowClickListener? = null
    private var windowStateListener: WindowStateListener? = null
    private var dragEnable: Boolean = false
    /**
     * this list records the activities which shows this window
     */
    private var whiteList: List<Class<Any>>? = mutableListOf()
    /**
     * if true,whiteList will be used to depend which activity could show window
     * if false,all activities in app is allow to show window
     */
    private var enableWhileList: Boolean = false
    /**
     * the animation make window stick to the left or right side of screen
     */
    private var weltAnimator: ValueAnimator? = null

    val layoutParam: WindowManager.LayoutParams?
        get() = windowInfo?.layoutParams

    val isShowing: Boolean
        get() = windowInfo?.view?.parent != null

    /**
     * listener invoked when touch outside of [FloatWindow]
     */
    private var onTouchOutside: (() -> Unit)? = null


    private fun getNavigationBarHeight(context: Context?): Int {
        val rid = context?.resources?.getIdentifier("config_showNavigationBar", "bool", "android")
        return if (rid != 0) {
            context?.resources?.getIdentifier("navigation_bar_height", "dimen", "android")?.let {
                context.resources?.getDimensionPixelSize(it)
            } ?: 0
        } else {
            0
        }
    }

    fun updateWindowView(updater: IWindowUpdater?) {
        updater?.updateWindowView(windowInfo?.view)
        val windowManager = context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        if (windowInfo?.hasParent().value()) {
            windowManager.updateViewLayout(windowInfo?.view, windowInfo?.layoutParams)
        }
    }

    fun setClickListener(listener: WindowClickListener) {
        clickListener = listener
    }

    fun setEnable(enable: Boolean, tag: String) {
        val windowInfo = windowInfoMap[tag]
            ?: throw RuntimeException("no such window view,please invoke setView() first")
        windowInfo.enable = enable
    }

    fun setWindowStateListener(windowStateListener: WindowStateListener) {
        this.windowStateListener = windowStateListener
    }

    /**
     * show float window, every window has a tag
     * @param tag a unique tag for a window, if showing the previous window without providing [windowInfo], we will looking for it in [windowInfoMap]
     * @param windowInfo the necessary information for showing float window, it will be kept in [windowInfoMap] with the key [tag]
     * @param x the horizontal position of float window according to the left top of screen
     * @param y the vertical position of float window according to the left top of screen
     */
    fun show(
        context: Context,
        tag: String,
        windowInfo: WindowInfo? = windowInfoMap[tag],
        x: Int = windowInfo?.layoutParams?.x.value(),
        y: Int = windowInfo?.layoutParams?.y.value(),
        dragEnable: Boolean
    ) {
        if (windowInfo == null) {
            Log.v("ttaylor", "there is no view to show,please creating the right WindowInfo object")
            return
        }
        if (!windowInfo.enable) {
            return
        }
        if (windowInfo.view == null) {
            return
        }
        this.windowInfo = windowInfo
        this.dragEnable = dragEnable
        windowInfoMap[tag] = windowInfo
        windowInfo.view?.setOnTouchListener(this)
        this.context = context
        windowInfo.layoutParams = createLayoutParam(x, y)
        //in case of "IllegalStateException :has already been added to the window manager."
        if (!windowInfo.hasParent().value()) {
            val windowManager =
                this.context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            prepareScreenDimension(windowManager)
            windowManager.addView(windowInfo.view, windowInfo.layoutParams)
            windowStateListener?.onWindowShow()
        }
    }

    private fun createLayoutParam(x: Int, y: Int): WindowManager.LayoutParams {
        if (context == null) {
            return WindowManager.LayoutParams()
        }

        return WindowManager.LayoutParams().apply {
            type = WindowManager.LayoutParams.TYPE_APPLICATION
            format = PixelFormat.TRANSLUCENT
            flags =
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            gravity = Gravity.START or Gravity.TOP
            width = windowInfo?.width.value()
            height = windowInfo?.height.value()
            this.x = x
            this.y = y
        }
    }

    fun dismiss() {
        val windowManager = context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        //in case of "IllegalStateException :not attached to window manager."
        if (windowInfo?.hasParent().value()) {
            windowManager.removeViewImmediate(windowInfo?.view)
            windowStateListener?.onWindowDismiss()
        }
    }

    fun setWhiteList(whiteList: List<Class<Any>>) {
        enableWhileList = true
        this.whiteList = whiteList
    }


    override fun onTouch(v: View, event: MotionEvent): Boolean {
        //handle outside touch event
        val x = event.x.toInt()
        val y = event.y.toInt()

        if (event.action == MotionEvent.ACTION_OUTSIDE) {
            onTouchOutside?.invoke()
            return true
        }

        //let GestureDetector take care of touch event,in order to parsing touch event into different gesture
        gestureDetector.onTouchEvent(event).takeIf { !it }?.also {
            //there is no ACTION_UP event in GestureDetector
            val action = event.action
            when (action) {
                MotionEvent.ACTION_UP -> onActionUp(event, screenWidth, windowInfo?.width ?: 0)
                else -> {
                }
            }
        }
        return true
    }

    private fun isTouchOutside(x: Int, y: Int) =
        x < 0 || x >= windowInfo?.view?.width ?: Int.MAX_VALUE || y < 0 || y >= windowInfo?.view?.height ?: Int.MAX_VALUE

    /**
     * set whether outside touch event could be detected
     */
    fun setOutsideTouchable(enable: Boolean, onTouchOutside: (() -> Unit)? = null) {
        if (enable) windowInfo?.layoutParams?.let { layoutParams ->
            layoutParams.flags =
                layoutParams.flags or WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
            this.onTouchOutside = onTouchOutside
        }
    }

    private fun onActionUp(event: MotionEvent, screenWidth: Int, width: Int) {
        if (!windowInfo?.hasView().value()) {
            return
        }
        val upX = event.rawX.toInt()
        val endX = if (upX > screenWidth / 2) {
            screenWidth - width
        } else {
            0
        }

        if (weltAnimator == null) {
            weltAnimator = ValueAnimator.ofInt(windowInfo?.layoutParams!!.x, endX).apply {
                interpolator = LinearInterpolator()
                duration = WELT_ANIMATION_DURATION
                addUpdateListener { animation ->
                    val x = animation.animatedValue as Int
                    if (windowInfo?.layoutParams != null) {
                        windowInfo?.layoutParams!!.x = x
                    }
                    val windowManager =
                        context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                    if (windowInfo?.hasParent().value()) {
                        windowManager.updateViewLayout(windowInfo?.view, windowInfo?.layoutParams)
                    }
                }
            }
        }
        weltAnimator?.setIntValues(windowInfo?.layoutParams!!.x, endX)
        weltAnimator?.start()
    }


    private fun onActionMove(event: MotionEvent) {
        val currentX = event.rawX.toInt()
        val currentY = event.rawY.toInt()
        val dx = currentX - lastTouchX
        val dy = currentY - lastTouchY

        windowInfo?.layoutParams!!.x += dx
        windowInfo?.layoutParams!!.y += dy
        val windowManager = context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        var rightMost = screenWidth - windowInfo?.layoutParams!!.width
        var leftMost = 0
        val topMost = 0
        val bottomMost =
            screenHeight - windowInfo?.layoutParams!!.height - getNavigationBarHeight(context)
        var partnerParam: WindowManager.LayoutParams? = null
        partnerParam = windowStateListener?.onWindowMove(
            dx.toFloat(),
            dy.toFloat(),
            screenWidth,
            screenHeight,
            windowInfo?.layoutParams
        )
        //adjust move area according to partner window
        if (partnerParam != null) {
            if (partnerParam.x < windowInfo?.layoutParams!!.x) {
                leftMost = partnerParam.width - windowInfo?.layoutParams!!.width / 2
            } else if (partnerParam.x > windowInfo?.layoutParams!!.x) {
                rightMost =
                    screenWidth - (windowInfo?.layoutParams!!.width / 2 + partnerParam.width)
            }
        }

        //make window float inside screen
        if (windowInfo?.layoutParams!!.x < leftMost) {
            windowInfo?.layoutParams!!.x = leftMost
        }
        if (windowInfo?.layoutParams!!.x > rightMost) {
            windowInfo?.layoutParams!!.x = rightMost
        }
        if (windowInfo?.layoutParams!!.y < topMost) {
            windowInfo?.layoutParams!!.y = topMost
        }
        if (windowInfo?.layoutParams!!.y > bottomMost) {
            windowInfo?.layoutParams!!.y = bottomMost
        }
        windowManager.updateViewLayout(windowInfo?.view, windowInfo?.layoutParams)
        lastTouchX = currentX
        lastTouchY = currentY
    }

    private fun onActionDown(event: MotionEvent) {
        lastTouchX = event.rawX.toInt()
        lastTouchY = event.rawY.toInt()
    }

    private fun prepareScreenDimension(windowManager: WindowManager?) {
        if (screenWidth != 0 && screenHeight != 0) {
            return
        }
        if (windowManager != null) {
            val dm = DisplayMetrics()
            val display = windowManager.defaultDisplay
            if (display != null) {
                windowManager.defaultDisplay.getMetrics(dm)
                screenWidth = dm.widthPixels
                screenHeight = dm.heightPixels
            }
        }
    }

    /**
     * let ui decide how to update window
     */
    interface IWindowUpdater {
        fun updateWindowView(windowView: View?)
    }

    /**
     * let ui decide what to do after window clicked
     */
    interface WindowClickListener {
        fun onWindowClick(windowInfo: WindowInfo?): Boolean
    }

    interface WindowStateListener {
        fun onWindowShow()

        fun onWindowDismiss()

        fun onWindowMove(
            dx: Float,
            dy: Float,
            screenWidth: Int,
            screenHeight: Int,
            layoutParams: WindowManager.LayoutParams?
        ): WindowManager.LayoutParams
    }

    private class GestureListener : GestureDetector.OnGestureListener {

        override fun onDown(e: MotionEvent): Boolean {
            onActionDown(e)
            return false
        }

        override fun onShowPress(e: MotionEvent) {}

        override fun onSingleTapUp(e: MotionEvent): Boolean {
            return clickListener?.onWindowClick(windowInfo) ?: false
        }

        override fun onScroll(
            e1: MotionEvent,
            e2: MotionEvent,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            if (dragEnable) {
                onActionMove(e2)
                return true
            }
            return false
        }

        override fun onLongPress(e: MotionEvent) {}

        override fun onFling(
            e1: MotionEvent,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            return false
        }

    }

    fun onDestroy() {
        windowInfoMap.clear()
        context = null
    }

    class WindowInfo(var view: View?) {
        /**
         * the layout param of window content view
         */
        var layoutParams: WindowManager.LayoutParams? = null
        /**
         * whether this window content is allow to show
         */
        var enable = true
        /**
         * the width of window content
         */
        var width: Int = 0
        /**
         * the height of window content
         */
        var height: Int = 0

        fun hasView() = view != null && layoutParams != null

        fun hasParent() = hasView() && view?.parent != null

    }

    private val WELT_ANIMATION_DURATION: Long = 150

    fun Boolean?.value() = this ?: false
    fun Int?.value() = this ?: 0
}
