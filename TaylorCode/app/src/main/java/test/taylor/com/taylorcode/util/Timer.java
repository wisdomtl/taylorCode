package test.taylor.com.taylorcode.util;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * a timer runs in the background and notify the ui thread periodically(in millisecond)
 */
public class Timer {

    private int interval;
    /**
     * the past time after Timer started
     */
    private long pastMillisecond;
    private TimerListener listener;
    private CountdownHandler handler;
    private ScheduledExecutorService executor;
    private ScheduledFuture scheduledFuture;
    private CountDownRunnable countDownRunnable;

    public Timer(TimerListener timerListener) {
        executor = Executors.newSingleThreadScheduledExecutor();
        handler = new CountdownHandler(Looper.getMainLooper());
        this.listener = timerListener;
    }

    /**
     * start ticktock
     */
    public void start(int delayMillisecond, int intervalMillisecond) {
        this.interval = intervalMillisecond;
        if (!executor.isShutdown()) {
            if (countDownRunnable == null) {
                countDownRunnable = new CountDownRunnable();
            }
            //avoid to schedule several runnable into executor
            if (scheduledFuture == null || scheduledFuture.isCancelled()) {
                scheduledFuture = executor.scheduleAtFixedRate(countDownRunnable, delayMillisecond, intervalMillisecond, TimeUnit.MILLISECONDS);
            }
        }
    }

    /**
     * pause ticktock
     *
     * @return
     */
    public void pause() {
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
        }
    }

    /**
     * stop ticktock,Timer is no longer need
     */
    public void stop() {
        if (executor != null) {
            executor.shutdownNow();
        }
    }

    private class CountdownHandler extends Handler {

        private CountdownHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            pastMillisecond += interval;
            if (listener != null) {
                listener.onTick(pastMillisecond);
            }
        }
    }

    private class CountDownRunnable implements Runnable {

        @Override
        public void run() {
            handler.obtainMessage().sendToTarget();
        }
    }

    public interface TimerListener {
        /**
         * this callback will be invoked continuously as time ticking
         *
         * @param pastMillisecond the total time has been past in millisecond
         */
        void onTick(long pastMillisecond);
    }
}

