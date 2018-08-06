package test.taylor.com.taylorcode.util;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * a timer runs in the background and notify the ui thread periodically(in millisecond)
 */
public class Timer {

    private int interval;
    private TimerListener listener;
    private CountdownHandler handler;
    private ScheduledExecutorService executor;

    public Timer(TimerListener timerListener) {
        executor = Executors.newSingleThreadScheduledExecutor();
        handler = new CountdownHandler(Looper.getMainLooper());
        this.listener = timerListener;
    }

    /**
     * start countdown
     */
    public void start(int delayMillisecond, int intervalMillisecond) {
        this.interval = intervalMillisecond;
        if (!executor.isShutdown()) {
            executor.scheduleAtFixedRate(new CountDownRunnable(), delayMillisecond, intervalMillisecond, TimeUnit.MILLISECONDS);
        }
    }

    /**
     * stop countdown
     */
    public void stop() {
        if (executor != null) {
            executor.shutdownNow();
        }
    }

    private class CountdownHandler extends Handler {

        private long pastMillisecond;

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

