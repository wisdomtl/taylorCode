package test.taylor.com.taylorcode.util

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class Countdown<T>(
    private var duration: Long,
    private var interval: Long,
    private val action: (Long) -> T
) {
    private val executor by lazy { Executors.newSingleThreadScheduledExecutor() }
    private val countdownRunnable by lazy { CountDownRunnable() }
    private var remainTime = duration
    private val handler by lazy { Handler(Looper.getMainLooper()) }
    var onStart: (() -> Unit)? = null
    var onEnd: ((T?) -> Unit)? = null
    var accumulator: ((T, T) -> T)? = null
    var acc: Any? = null

    fun start(delay: Long = 0) {
        if (!executor.isShutdown) {
            handler.post(onStart)
            executor.scheduleAtFixedRate(countdownRunnable, delay, interval, TimeUnit.MILLISECONDS)
        }
    }

    private inner class CountDownRunnable : Runnable {
        override fun run() {
            remainTime -= interval
            val value = action(remainTime)
            acc = if (acc == null) value else accumulator?.invoke(acc as T, value)
            if (remainTime <= 0) {
                executor?.shutdown()
                handler.post { onEnd?.invoke(acc as? T) }
            }
        }
    }
}