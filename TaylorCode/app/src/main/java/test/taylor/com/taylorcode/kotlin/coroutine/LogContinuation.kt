package test.taylor.com.taylorcode.kotlin.coroutine

import android.util.Log
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext

class LogContinuation<T>(private val continuation: Continuation<T>) : Continuation<T> {
    override fun resumeWith(result: Result<T>) {
        Log.v("ttaylor", " LogContinuation.resumeWith()  result=${result}")
        continuation.resumeWith(result)
    }

    override val context: CoroutineContext
        get() = continuation.context
}