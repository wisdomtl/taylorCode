package test.taylor.com.taylorcode.aysnc.workmanager;

import android.support.annotation.NonNull;
import android.util.Log;

import androidx.work.Data;
import androidx.work.Worker;

public class CountingByParam extends Worker {

    public static final String KEY_X = "X";
    public static final String KEY_Y = "Y";

    @NonNull
    @Override
    public WorkerResult doWork() {
        Data param = getInputData();
        int x = param.getInt(KEY_X, 10);
        int y = param.getInt(KEY_Y, 0);
        Log.v("ttaylor", "CountingByParam.doWork()" + "  thread id=" + Thread.currentThread().getId());
        for (int i = x; i >= y; i--) {
            Log.v("ttaylor", "CountingByParam.doWork()" + i);
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
