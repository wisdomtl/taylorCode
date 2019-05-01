package test.taylor.com.taylorcode.extension

import android.animation.ValueAnimator
import android.view.MotionEvent
import android.view.View


//add extra methods into View

fun View.extraAnimClickListener(animator: ValueAnimator, action: (View) -> Unit) {
    setOnTouchListener { v, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> animator.start()
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> animator.reverse()
        }
        false
    }

    setOnClickListener { action(this) }
}