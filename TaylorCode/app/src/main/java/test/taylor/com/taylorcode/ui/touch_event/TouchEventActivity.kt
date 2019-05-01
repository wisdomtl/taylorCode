package test.taylor.com.taylorcode.ui.touch_event

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import kotlinx.android.synthetic.main.touch_event_activity.*

import test.taylor.com.taylorcode.R

class TouchEventActivity : Activity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.touch_event_activity)
        initView()
    }

    private fun initView() {
        v_te.setOnClickListener({})
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        Log.e("ttaylor", "TouchEventActivity.dispatchTouchEvent()" + " event=" + ev.action)
//        Log.v("ttaylor", "TouchEventActivity.dispatchTouchEvent()" + "  return " + b);
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        Log.e("ttaylor", "TouchEventActivity.onTouchEvent()" + " event=" + event.action)
//        Log.v("ttaylor", "TouchEventActivity.onTouchEvent()" + "  return " + b);
        return super.onTouchEvent(event)
    }

}
