package test.taylor.com.taylorcode.kotlin.coroutine

import android.os.Build
import android.view.View
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * start counting down from [duration] to 0 in a background thread and invoking the [onCountdown] every [interval] in main thread
 */
fun <T> countdown2(duration: Long, interval: Long, onCountdown: suspend (Long) -> T): Flow<T> =
    flow { (duration - interval downTo 0 step interval).forEach { emit(it) } }
        .onEach { delay(interval) }
        .onStart { emit(duration) }
        .map { onCountdown(it) }
        .flowOn(Dispatchers.Default)


/**
 * avoid memory leak for View and activity when activity has finished while coroutine is still running
 */
fun Job.autoDispose(view: View?): Job {
    view ?: return this

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
    return this
}

/**
 * avoid memory leak
 */
fun <T> SendChannel<T>.autoDispose(view: View?): SendChannel<T> {
    view ?: return this

    val isAttached = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && view.isAttachedToWindow || view.windowToken != null
    val listener = object : View.OnAttachStateChangeListener {
        override fun onViewDetachedFromWindow(v: View?) {
            close()
            v?.removeOnAttachStateChangeListener(this)
        }

        override fun onViewAttachedToWindow(v: View?) = Unit
    }

    view.addOnAttachStateChangeListener(listener)
    invokeOnClose {
        view.removeOnAttachStateChangeListener(listener)
    }
    return this
}
