package test.taylor.com.taylorcode.util

import android.os.Handler
import android.os.Looper
import android.os.Message
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

/**
 * a timer runs in the background and notify the ui thread periodically(in millisecond)
 */
class Timer(private var timerListener: TimerListener?) {
    private var interval = 0L

    /**
     * the past time after Timer started
     */
    private var pastMillisecond: Long = 0
    private val handler: CountdownHandler = CountdownHandler(Looper.getMainLooper())
    private val executor: ScheduledExecutorService? = Executors.newSingleThreadScheduledExecutor()
    private var scheduledFuture: ScheduledFuture<*>? = null
    private var countDownRunnable: CountDownRunnable? = null

    /**
     * start ticktock
     */
    fun start(delayMillisecond: Long, intervalMillisecond: Long) {
        interval = intervalMillisecond
        if (!executor!!.isShutdown) {
            if (countDownRunnable == null) {
                countDownRunnable = CountDownRunnable()
            }
            //avoid to schedule several runnable into executor
            if (scheduledFuture == null || scheduledFuture!!.isCancelled) {
                scheduledFuture = executor.scheduleAtFixedRate(countDownRunnable, delayMillisecond, intervalMillisecond, TimeUnit.MILLISECONDS)
            }
        }
    }

    /**
     * pause ticktock
     *
     * @return
     */
    fun pause() {
        if (scheduledFuture != null) {
            scheduledFuture!!.cancel(true)
        }
    }

    /**
     * stop ticktock,Timer is no longer need
     */
    fun stop() {
        executor?.shutdownNow()
    }

    private inner class CountdownHandler constructor(looper: Looper) : Handler(looper) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            pastMillisecond += interval
            timerListener?.onTick(pastMillisecond)
        }
    }

    private inner class CountDownRunnable : Runnable {
        override fun run() {
            handler.obtainMessage().sendToTarget()
        }
    }

    interface TimerListener {
        /**
         * this callback will be invoked continuously as time ticking
         *
         * @param pastMillisecond the total time has been past in millisecond
         */
        fun onTick(pastMillisecond: Long)
    }

}