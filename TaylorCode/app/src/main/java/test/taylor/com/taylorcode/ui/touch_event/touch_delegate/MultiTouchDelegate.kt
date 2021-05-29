package test.taylor.com.taylorcode.ui.touch_event.touch_delegate

import android.graphics.Rect
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_DOWN
import android.view.MotionEvent.ACTION_CANCEL
import android.view.TouchDelegate
import android.view.View

class MultiTouchDelegate(bound: Rect? = null, delegateView: View) : TouchDelegate(bound, delegateView) {

    private val delegateViewMap = mutableMapOf<View, Rect>()
    private var delegateView: View? = null

    fun addDelegateView(delegateView: View, rect: Rect):MultiTouchDelegate {
        delegateViewMap[delegateView] = rect
        return this
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x.toInt()
        val y = event.y.toInt()
        var handled = false

        when (event.actionMasked) {
            ACTION_DOWN -> {
                delegateView = findDelegateViewUnder(x, y)
            }
            ACTION_CANCEL -> {
                delegateView = null
            }
        }

        delegateView?.let {
            event.setLocation(it.width / 2f, it.height / 2f)
            handled = it.dispatchTouchEvent(event)
        }

        return handled
    }

    fun findDelegateViewUnder(x: Int, y: Int): View? {
        delegateViewMap.forEach { entry -> if (entry.value.contains(x, y)) return entry.key }
        return null
    }
}