package test.taylor.com.taylorcode.kotlin.coroutine

import android.os.Build
import android.view.View
import kotlinx.coroutines.Job


/**
 * avoid memory leak for View and activity when activity has finished while coroutine is still running
 */
fun Job.autoDispose(view: View) {
    val isAttached = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && view.isAttachedToWindow || view.windowToken != null
    if (!isAttached) {
        cancel()
    }

    val listener = object : View.OnAttachStateChangeListener {
        override fun onViewDetachedFromWindow(v: View?) {
            cancel()
            v?.removeOnAttachStateChangeListener(this)
        }

        override fun onViewAttachedToWindow(v: View?) = Unit
    }

    view.addOnAttachStateChangeListener(listener)
    invokeOnCompletion {
        view.removeOnAttachStateChangeListener(listener)
    }
}