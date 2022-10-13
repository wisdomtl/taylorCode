package test.taylor.com.taylorcode.kotlin.coroutine

import android.os.Build
import android.util.Log
import android.view.View
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.*
import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext

/**
 * start counting down from [duration] to 0 in a background thread and invoking the [onCountdown] every [interval] in main thread
 */
fun <T> countdown2(
    duration: Long,
    interval: Long,
    context: CoroutineContext = Dispatchers.Default,
    onCountdown: suspend (Long) -> T
): Flow<T> =
    flow { (duration - interval downTo 0 step interval).forEach { emit(it) } }
        .onEach { delay(interval) }
        .onStart { emit(duration) }
        .flatMapMerge { flow { emit(onCountdown(it)) } }
        .flowOn(context)


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

    val isAttached =
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && view.isAttachedToWindow || view.windowToken != null
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

private class ViewAutoDisposeInterceptor(private val view: View) : ContinuationInterceptor {
    companion object Key : CoroutineContext.Key<ViewAutoDisposeInterceptor>

    override val key: CoroutineContext.Key<*>
        get() = ViewAutoDisposeInterceptor

    override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> {
        continuation.context.job.autoDispose(view)
        return continuation
    }
}

/**
 * case: view could launch coroutine by it's own coroutineScope which will be canceled if view detach
 */
val View.autoDisposeScope: CoroutineScope
    get() {
        val tag = -999
        val exist = getTag(tag) as? CoroutineScope
        if (exist != null) return exist
        val newScope =
            CoroutineScope(
                SupervisorJob()
                        + Dispatchers.Main
                        + ViewAutoDisposeInterceptor(this)
            )
        setTag(tag, newScope)
        return newScope
    }

fun CoroutineScope.orNew(
    job: Job = SupervisorJob(),
    dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
    exceptionHandler: CoroutineExceptionHandler = CoroutineExceptionHandler { _, _ -> }
): CoroutineScope {
    return this.takeIf { it.isActive } ?: run { CoroutineScope(job + dispatcher + exceptionHandler) }
}