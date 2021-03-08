package test.taylor.com.taylorcode.interview;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class InterViewActivity extends AppCompatActivity {

private static final int MSG_TASK_TIMEOUT = 0;
private static final int MSG_TASK_COUNTING = 1;
private static final int MSG_TASK_COMPLETE = 2;
private static final int TASK_TIMEOUT = 5000;

private Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
    @Override
    public boolean handleMessage(@NonNull Message msg) {
        switch (msg.what) {
            case MSG_TASK_TIMEOUT: {
                System.out.println("task time out");
                handler.removeCallbacksAndMessages(null);
                break;
            }
            case MSG_TASK_COUNTING: {
                int time = msg.arg1;
                System.out.println("task is executing for " + time + " ms");
                break;
            }
            case MSG_TASK_COMPLETE: {
                if (msg.arg1 < TASK_TIMEOUT) {
                    handler.removeMessages(MSG_TASK_TIMEOUT);
                }
                System.out.println("task completes");
                break;
            }
        }
        return false;
    }
});

@Override
protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Message timeoutMessage = handler.obtainMessage(MSG_TASK_TIMEOUT);
    handler.sendMessageDelayed(timeoutMessage, TASK_TIMEOUT);
    TaskRunnable taskRunnable = new TaskRunnable(6);
    handler.postDelayed(taskRunnable,1000);
}

class TaskRunnable implements Runnable {

    private int max;
    private int duration = 0;

    public TaskRunnable(int max) {
        this.max = max;
    }

    @Override
    public void run() {
        duration++;
        Message msg = handler.obtainMessage();
        msg.arg1 = duration * 1000;
        if (duration < max) {
            msg.what = MSG_TASK_COUNTING;
            handler.sendMessage(msg);
            handler.postDelayed(this, 1000);
        } else if (duration == max) {
            msg.what = MSG_TASK_COMPLETE;
            handler.sendMessage(msg);
        }
    }
}
}
