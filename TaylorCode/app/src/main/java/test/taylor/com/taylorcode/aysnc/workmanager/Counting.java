package test.taylor.com.taylorcode.aysnc.workmanager;

import android.support.annotation.NonNull;
import android.util.Log;


import androidx.work.Worker;

public class Counting extends Worker {

    @NonNull
    @Override
    public WorkerResult doWork() {
        Log.v("ttaylor", "Counting.doWork()" + "  thread id="+Thread.currentThread().getId());
        for (int j = 0; j < 20; j++) {
            Log.v("ttaylor", "Counting.doWork()" + j);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return WorkerResult.FAILURE;
            }
        }
        return WorkerResult.SUCCESS;
    }
}
