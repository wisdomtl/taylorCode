package test.taylor.com.taylorcode.kotlin.coroutine

import android.os.Build
import android.view.View
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * start counting down from [duration] to 0 in a background thread and invoking the [onCountdown] every [interval]
 */
@InternalCoroutinesApi
fun countdown(
    duration: Long,
    interval: Long,
    view: View?,
    producerDispatcher: CoroutineDispatcher = Dispatchers.Default,
    consumerDispatcher: CoroutineDispatcher = Dispatchers.Main,
    onCountdown: () -> Unit
) {
    GlobalScope.launch(consumerDispatcher) {
        var d = duration
        val flow = flow {
            while (isActive) {
                if (d <= 0) cancel()
                emit(Unit)
                delay(interval)
                d -= interval
            }
        }.flowOn(producerDispatcher)

        try {
            flow.collect { onCountdown() }
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }.autoDispose(view)
}


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
