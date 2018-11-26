package test.taylor.com.taylorcode.aysnc.workmanager;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.concurrent.TimeUnit;

import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkContinuation;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

public class WorkManagerActivity extends Activity {

    public static final String TAG_1 = "1";
    public static final String TAG_2 = "2";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        doBasicWork();
//
//        doWorkByParam();

//        cancelWorkByTag();

//        doWorkByConstraint();

//        doPeriodWork();

//        doWorkBySequence();
        combineWork();
    }

    /**
     * WorkManager case1:do the simplest background work
     */
    private void doBasicWork() {
        WorkRequest workRequest = new OneTimeWorkRequest.Builder(Counting.class).build();
        WorkManager.getInstance().enqueue(workRequest);
    }

    /**
     * WorkManager case2:do the background work with input param
     */
    private void doWorkByParam() {
        Data inputData = new Data.Builder().putInt(CountingByParam.KEY_X, 15).build();
        WorkRequest workRequest = new OneTimeWorkRequest.Builder(CountingByParam.class).setInputData(inputData).build();
        WorkManager.getInstance().enqueue(workRequest);
    }


    /**
     * WorkManager case3:cancel an enqueued work by tag
     */
    private void cancelWorkByTag() {
        //workRequest1 and worRequest2 will be started parallel
        Data input1 = new Data.Builder().putInt(CountingByParam.KEY_X, 10).build();
        WorkRequest workRequest1 = new OneTimeWorkRequest.Builder(CountingByParam.class).setInputData(input1).addTag(TAG_1).build();
        WorkManager.getInstance().enqueue(workRequest1);

        Data input2 = new Data.Builder().putInt(CountingByParam.KEY_X, 20).build();
        WorkRequest workRequest2 = new OneTimeWorkRequest.Builder(CountingByParam.class).setInputData(input2).addTag(TAG_2).build();
        WorkManager.getInstance().enqueue(workRequest2);

        WorkManager.getInstance().cancelAllWorkByTag(TAG_1);
    }

    /**
     * WorkManager case4:do work by constraint
     */
    private void doWorkByConstraint() {
        Data input1 = new Data.Builder().putInt(CountingByParam.KEY_X, 50).build();
        Constraints constraints = new Constraints.Builder().setRequiresCharging(true).build();
        WorkRequest workRequest1 = new OneTimeWorkRequest.Builder(CountingByParam.class).setInputData(input1).setConstraints(constraints).build();
        WorkManager.getInstance().enqueue(workRequest1);
    }

    /**
     * WorkManager case5:do work periodically
     */
    private void doPeriodWork() {
        PeriodicWorkRequest workRequest1 = new PeriodicWorkRequest.Builder(Counting.class, 15, TimeUnit.MINUTES).build();
        WorkManager.getInstance().enqueue(workRequest1);
    }

    /**
     * WorkManager case6:do work by the specific sequence
     */
    private void doWorkBySequence() {
        //work1
        Data input1 = new Data.Builder().putInt(CountingByParam.KEY_X, 3).putInt(CountingByParam.KEY_Y, 0).build();
        OneTimeWorkRequest workRequest1 = new OneTimeWorkRequest.Builder(CountingByParam.class).setInputData(input1).build();
        //work2
        Data input2 = new Data.Builder().putInt(CountingByParam.KEY_X, 8).putInt(CountingByParam.KEY_Y, 5).build();
        OneTimeWorkRequest workRequest2 = new OneTimeWorkRequest.Builder(CountingByParam.class).setInputData(input2).build();
        //work3
        Data input3 = new Data.Builder().putInt(CountingByParam.KEY_X, 13).putInt(CountingByParam.KEY_Y, 10).build();
        OneTimeWorkRequest workRequest3 = new OneTimeWorkRequest.Builder(CountingByParam.class).setInputData(input3).build();

        //do workRequest1 and workRequest2 parallel ,workRequest3 will not be started until workRequest1 and workRequest2 have finished
        WorkManager.getInstance().beginWith(workRequest1, workRequest2).then(workRequest3).enqueue();
    }

    private void combineWork() {
        //work1
        Data input1 = new Data.Builder().putInt(CountingByParam.KEY_X, 3).putInt(CountingByParam.KEY_Y, 0).build();
        OneTimeWorkRequest workRequest1 = new OneTimeWorkRequest.Builder(CountingByParam.class).setInputData(input1).build();
        //work2
        Data input2 = new Data.Builder().putInt(CountingByParam.KEY_X, 8).putInt(CountingByParam.KEY_Y, 5).build();
        OneTimeWorkRequest workRequest2 = new OneTimeWorkRequest.Builder(CountingByParam.class).setInputData(input2).build();
        //work3
        Data input3 = new Data.Builder().putInt(CountingByParam.KEY_X, 13).putInt(CountingByParam.KEY_Y, 10).build();
        OneTimeWorkRequest workRequest3 = new OneTimeWorkRequest.Builder(CountingByParam.class).setInputData(input3).build();
        //work4
        Data input4 = new Data.Builder().putInt(CountingByParam.KEY_X, 15).putInt(CountingByParam.KEY_Y, 14).build();
        OneTimeWorkRequest workRequest4 = new OneTimeWorkRequest.Builder(CountingByParam.class).setInputData(input4).build();
        //work5
        Data input5 = new Data.Builder().putInt(CountingByParam.KEY_X, 20).putInt(CountingByParam.KEY_Y, 16).build();
        OneTimeWorkRequest workRequest5 = new OneTimeWorkRequest.Builder(CountingByParam.class).setInputData(input5).build();

        WorkContinuation workContinuation1 = WorkManager.getInstance().beginWith(workRequest1).then(workRequest2);
        WorkContinuation workContinuation2 = WorkManager.getInstance().beginWith(workRequest3).then(workRequest4);
        WorkContinuation.combine(workContinuation1, workContinuation2).then(workRequest5).enqueue();
    }
}
