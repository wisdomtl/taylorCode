package test.taylor.com.taylorcode.ui.touch_event

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import kotlinx.android.synthetic.main.touch_event_activity.*

import test.taylor.com.taylorcode.R
import test.taylor.com.taylorcode.util.print

class TouchEventActivity : Activity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.touch_event_activity)
        initView()
    }

    private fun initView() {
//        v_te.setOnClickListener({})
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        val actionmask = event.action and MotionEvent.ACTION_MASK
        (0 until event.pointerCount).map { event.getPointerId(it) }.print { it.toString() }
            .let { Log.v("ttaylor", "TouchEventActivity.dispatchTouchEvent() event = ${event.action} ,isMutiple=${actionmask == MotionEvent.ACTION_POINTER_DOWN}, index = ${event.actionIndex} ,pointer ids=${it}") }
//        Log.v("ttaylor", "TouchEventActivity.dispatchTouchEvent()" + "  return " + b);
        return super.dispatchTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
Log.v("ttaylor","TouchEventActivity.onTouchEvent()  event = ${event.action}")
//        Log.v("ttaylor", "TouchEventActivity.onTouchEvent()" + "  return " + b);
        return super.onTouchEvent(event)
    }

}
