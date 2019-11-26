package test.taylor.com.taylorcode.ui.window

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.PixelFormat
import android.util.DisplayMetrics
import android.util.Log
import android.view.Display
import android.view.GestureDetector
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.animation.LinearInterpolator

import java.util.ArrayList
import java.util.HashMap

/**
 * suspending window in app,it shows throughout the whole app
 */
class FloatWindow private constructor() : View.OnTouchListener {
    /**
     * several window content stored by String tag ;
     */
    private var windowContentMap: HashMap<String, WindowContent>? = null
    private var windowContent: WindowContent? = null
    private var lastTouchX: Int = 0
    private var lastTouchY: Int = 0
    private val lastWeltX: Int = 0
    private var screenWidth: Int = 0
    private var screenHeight: Int = 0
    private var context: Context? = null
    private var gestureDetector: GestureDetector? = null
    private var clickListener: OnWindowViewClickListener? = null
    private var onWindowStatusChangeListener: OnWindowStatusChangeListener? = null
    /**
     * this list records the activities which shows this window
     */
    private var whiteList: List<Class<Any>>? = null
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
        get() = if (windowContent != null) {
            windowContent!!.layoutParams
        } else null

    val isShowing: Boolean
        get() {
            if (windowContent == null) {
                return false
            }
            if (windowContent!!.windowView == null) {
                return false
            }
            return if (windowContent!!.windowView!!.parent == null) {
                false
            } else {
                true
            }
        }

    fun init(context: Context): FloatWindow {
        whiteList = ArrayList<Class<Any>>()
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        prepareScreenDimension(windowManager)
        gestureDetector = GestureDetector(context, GestureListener())
        return this
    }


    private fun getNavigationBarHeight(context: Context): Int {
        val result = 0
        var resourceId = 0
        val rid = context.resources.getIdentifier("config_showNavigationBar", "bool", "android")
        if (rid != 0) {
            resourceId = context.resources.getIdentifier("navigation_bar_height", "dimen", "android")
            return context.resources.getDimensionPixelSize(resourceId)
        } else {
            return 0
        }
    }

    fun updateWindowView(updater: IWindowUpdater?) {
        updater?.updateWindowView(windowContent!!.windowView)
        val windowManager = context!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        if (windowManager != null && windowContent != null && windowContent!!.windowView != null && windowContent!!.windowView!!.parent != null) {
            windowManager.updateViewLayout(windowContent!!.windowView, windowContent!!.layoutParams)
        }
    }


    fun setOnClickListener(listener: OnWindowViewClickListener) {
        clickListener = listener
    }

    fun setWidth(width: Int, tag: String) {
        if (windowContentMap == null) {
            return
        }
        val windowContent = windowContentMap!![tag]
        if (windowContent != null) {
            windowContent.width = width
        }
    }

    fun setHeight(height: Int, tag: String) {
        if (windowContentMap == null) {
            return
        }
        val windowContent = windowContentMap!![tag]
        if (windowContent != null) {
            windowContent.height = height
        }
    }

    fun setEnable(enable: Boolean, tag: String) {
        if (windowContentMap == null) {
            return
        }
        val windowContent = windowContentMap!![tag]
                ?: throw RuntimeException("no such window view,please invoke setView() first")
        windowContent.enable = enable
    }

    fun setOnWindowStatusChangeListener(onWindowStatusChangeListener: OnWindowStatusChangeListener) {
        this.onWindowStatusChangeListener = onWindowStatusChangeListener
    }

    fun show(context: Context, tag: String) {
        if (windowContentMap == null) {
            return
        }
        windowContent = windowContentMap!![tag]
        if (windowContent == null) {
            Log.v("ttaylor", "there is no view to show,please invoke setView() first")
            return
        }
        if (!windowContent!!.enable) {
            return
        }
        if (windowContent!!.windowView == null) {
            return
        }
        this.context = context
        windowContent!!.layoutParams = generateLayoutParam(screenWidth, screenHeight)
        //in case of "IllegalStateException :has already been added to the window manager."
        if (windowContent!!.windowView!!.parent == null) {
            val windowManager = this.context!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            windowManager.addView(windowContent!!.windowView, windowContent!!.layoutParams)
            if (onWindowStatusChangeListener != null) {
                onWindowStatusChangeListener!!.onShow()
            }
        }
    }

    private fun generateLayoutParam(screenWidth: Int, screenHeight: Int): WindowManager.LayoutParams {
        if (context == null) {
            return WindowManager.LayoutParams()
        }

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION
        layoutParams.format = PixelFormat.TRANSLUCENT
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        layoutParams.gravity = Gravity.LEFT or Gravity.TOP
        layoutParams.width = if (windowContent!!.width == 0) DEFAULT_WIDTH else windowContent!!.width
        layoutParams.height = if (windowContent!!.height == 0) DEFAULT_HEIGHT else windowContent!!.height
        val defaultX = screenWidth - layoutParams.width
        val defaultY = 2 * screenHeight / 3
        layoutParams.x = if (windowContent!!.layoutParams == null) defaultX else windowContent!!.layoutParams!!.x
        layoutParams.y = if (windowContent!!.layoutParams == null) defaultY else windowContent!!.layoutParams!!.y
        return layoutParams
    }

    fun dismiss() {
        val windowManager = context!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        if (windowManager != null) {
            //in case of "IllegalStateException :not attached to window manager."
            if (windowContent != null && windowContent!!.windowView != null && windowContent!!.windowView!!.parent != null) {
                windowManager.removeViewImmediate(windowContent!!.windowView)
                if (onWindowStatusChangeListener != null) {
                    onWindowStatusChangeListener!!.onDismiss()
                }
            }
        }
    }

    fun setWhiteList(whiteList: List<Class<Any>>) {
        enableWhileList = true
        this.whiteList = whiteList
    }

    /**
     * invoking this method is a must ,or window has no content to show
     *
     * @param view the content view of window
     * @param tag
     * @return
     */
    fun setView(view: View, tag: String): FloatWindow {
        if (windowContentMap == null) {
            windowContentMap = HashMap()
        }
        val windowContent = WindowContent()
        windowContent.windowView = view
        windowContentMap!![tag] = windowContent
        windowContent.windowView!!.setOnTouchListener(this)
        return this
    }


    override fun onTouch(v: View, event: MotionEvent): Boolean {
        //let GestureDetector take care of touch event,in order to parsing touch event into different gesture
        gestureDetector!!.onTouchEvent(event)
        //there is no ACTION_UP event in GestureDetector
        val action = event.action
        when (action) {
            MotionEvent.ACTION_UP -> onActionUp(event, screenWidth, windowContent!!.width)
            else -> {
            }
        }
        return true
    }

    private fun onActionUp(event: MotionEvent, screenWidth: Int, width: Int) {
        if (windowContentMap == null || windowContent!!.windowView == null || windowContent!!.layoutParams == null) {
            return
        }
        val upX = event.rawX.toInt()
        val endX: Int
        if (upX > screenWidth / 2) {
            endX = screenWidth - width
        } else {
            endX = 0
        }

        if (weltAnimator == null) {
            weltAnimator = ValueAnimator.ofInt(windowContent!!.layoutParams!!.x, endX)
            weltAnimator!!.interpolator = LinearInterpolator()
            weltAnimator!!.duration = WELT_ANIMATION_DURATION
            weltAnimator!!.addUpdateListener { animation ->
                val x = animation.animatedValue as Int
                if (windowContent!!.layoutParams != null) {
                    windowContent!!.layoutParams!!.x = x
                }
                val windowManager = context!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                if (windowManager != null && windowContent!!.windowView!!.parent != null) {
                    windowManager.updateViewLayout(windowContent!!.windowView, windowContent!!.layoutParams)
                }
            }
        }
        weltAnimator!!.setIntValues(windowContent!!.layoutParams!!.x, endX)
        weltAnimator!!.start()
    }


    private fun onActionMove(event: MotionEvent) {
        val currentX = event.rawX.toInt()
        val currentY = event.rawY.toInt()
        val dx = currentX - lastTouchX
        val dy = currentY - lastTouchY

        windowContent!!.layoutParams!!.x += dx
        windowContent!!.layoutParams!!.y += dy
        val windowManager = context!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        if (windowManager != null) {
            var rightMost = screenWidth - windowContent!!.layoutParams!!.width
            var leftMost = 0
            val topMost = 0
            val bottomMost = screenHeight - windowContent!!.layoutParams!!.height - getNavigationBarHeight(context!!)
            var partnerParam: WindowManager.LayoutParams? = null
            if (onWindowStatusChangeListener != null) {
                partnerParam = onWindowStatusChangeListener!!.onWindowMove(dx.toFloat(), dy.toFloat(), screenWidth, screenHeight, windowContent!!.layoutParams)
            }
            //adjust move area according to partner window
            if (partnerParam != null) {
                if (partnerParam.x < windowContent!!.layoutParams!!.x) {
                    leftMost = partnerParam.width - windowContent!!.layoutParams!!.width / 2
                } else if (partnerParam.x > windowContent!!.layoutParams!!.x) {
                    rightMost = screenWidth - (windowContent!!.layoutParams!!.width / 2 + partnerParam.width)
                }
            }

            //make window float inside screen
            if (windowContent!!.layoutParams!!.x < leftMost) {
                windowContent!!.layoutParams!!.x = leftMost
            }
            if (windowContent!!.layoutParams!!.x > rightMost) {
                windowContent!!.layoutParams!!.x = rightMost
            }
            if (windowContent!!.layoutParams!!.y < topMost) {
                windowContent!!.layoutParams!!.y = topMost
            }
            if (windowContent!!.layoutParams!!.y > bottomMost) {
                windowContent!!.layoutParams!!.y = bottomMost
            }
            windowManager.updateViewLayout(windowContent!!.windowView, windowContent!!.layoutParams)
        }
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

    interface OnWindowViewClickListener {
        fun onWindowViewClick()
    }

    interface OnWindowStatusChangeListener {
        fun onShow()

        fun onDismiss()

        fun onWindowMove(dx: Float, dy: Float, screenWidth: Int, screenHeight: Int, layoutParams: WindowManager.LayoutParams?): WindowManager.LayoutParams
    }

    private inner class GestureListener : GestureDetector.OnGestureListener {

        override fun onDown(e: MotionEvent): Boolean {
            onActionDown(e)
            return false
        }

        override fun onShowPress(e: MotionEvent) {}

        override fun onSingleTapUp(e: MotionEvent): Boolean {
            if (clickListener != null) {
                clickListener!!.onWindowViewClick()
            }
            return false
        }

        override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
            onActionMove(e2)
            return true
        }

        override fun onLongPress(e: MotionEvent) {}

        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            return false
        }

    }

    fun onDestroy() {
        if (windowContentMap != null) {
            windowContentMap!!.clear()
        }
    }

    private inner class WindowContent {
        /**
         * the content view of window
         */
        var windowView: View? = null
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

    }

    companion object {

        private val DEFAULT_WIDTH = 100
        private val DEFAULT_HEIGHT = 100
        private val WELT_ANIMATION_DURATION: Long = 150

        @Volatile
        private var INSTANCE: FloatWindow? = null

        //in case of memory leak for singleton
        val instance: FloatWindow?
            get() {
                if (INSTANCE == null) {
                    synchronized(FloatWindow::class.java) {
                        if (INSTANCE == null) {
                            INSTANCE = FloatWindow()
                        }
                    }
                }
                return INSTANCE
            }
    }
}