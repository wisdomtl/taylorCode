package test.taylor.com.taylorcode.aysnc.workmanager

import android.os.Bundle
import android.util.Log
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.*
import java.util.concurrent.TimeUnit

class WorkManagerActivity : AppCompatActivity() {

    val TAG_1 = "1"
    val TAG_2 = "2"

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        doBasicWork();
//        doPeriodWork();
        returnValueFromWorkManager()
//
//        doWorkByParam();
//        cancelWorkByTag();
//        doWorkByConstraint();
//        doWorkBySequence();
//        combineWork()
    }

    /**
     * WorkManager case1:do the simplest background work
     */
    private fun doBasicWork() {
        val workRequest: WorkRequest = OneTimeWorkRequest.Builder(Counting::class.java).build()
        WorkManager.getInstance(this).enqueue(workRequest)
    }

    /**
     * WorkManager case2:do the background work with input param
     */
    private fun doWorkByParam() {
        val inputData = Data.Builder().putInt(Counting.KEY_INIT, 20).build()
        val workRequest: WorkRequest = OneTimeWorkRequest.Builder(Counting::class.java).setInputData(inputData).build()
        WorkManager.getInstance(this).enqueue(workRequest)
    }

    private fun returnValueFromWorkManager() {
        val workRequest: WorkRequest = OneTimeWorkRequest.Builder(Counting::class.java).build()
        WorkManager.getInstance(this).apply {
            enqueue(workRequest)
            getWorkInfoByIdLiveData(workRequest.id).observe(this@WorkManagerActivity, Observer { workInfo ->
                val sum = workInfo.outputData.getInt("sum",0)
                Log.v("ttaylor", "tag=sum, WorkManagerActivity.returnValueFromWorkManager() sum=$sum ")
            })
        }
    }


    /**
     * WorkManager case3:cancel an enqueued work by tag
     */
    private fun cancelWorkByTag() { //workRequest1 and worRequest2 will be started parallel
//        val input1 = Data.Builder().putInt(CountingByParam.KEY_X, 10).build()
//        val workRequest1: WorkRequest = OneTimeWorkRequest.Builder(CountingByParam::class.java).setInputData(input1).addTag(TAG_1).build()
//        WorkManager.getInstance().enqueue(workRequest1)
//        val input2 = Data.Builder().putInt(CountingByParam.KEY_X, 20).build()
//        val workRequest2: WorkRequest = OneTimeWorkRequest.Builder(CountingByParam::class.java).setInputData(input2).addTag(TAG_2).build()
//        WorkManager.getInstance().enqueue(workRequest2)
//        WorkManager.getInstance().cancelAllWorkByTag(TAG_1)
    }

    /**
     * WorkManager case4:do work by constraint
     */
    private fun doWorkByConstraint() {
//        val input1 = Data.Builder().putInt(CountingByParam.KEY_X, 50).build()
//        val constraints = Constraints.Builder().setRequiresCharging(true).build()
//        val workRequest1: WorkRequest =
//            OneTimeWorkRequest.Builder(CountingByParam::class.java).setInputData(input1).setConstraints(constraints).build()
//        WorkManager.getInstance().enqueue(workRequest1)
    }

    /**
     * WorkManager case5:do work periodically
     */
    private fun doPeriodWork() {
        //the interval cannot be less than 15 minutes
        val workRequest1 = PeriodicWorkRequest.Builder(Counting::class.java, 10, TimeUnit.SECONDS).build()
        WorkManager.getInstance(this).enqueue(workRequest1)
    }

    /**
     * WorkManager case6:do work by the specific sequence
     */
    private fun doWorkBySequence() { //work1
//        val input1 = Data.Builder().putInt(CountingByParam.KEY_X, 3).putInt(CountingByParam.KEY_Y, 0).build()
//        val workRequest1 = OneTimeWorkRequest.Builder(CountingByParam::class.java).setInputData(input1).build()
//        //work2
//        val input2 = Data.Builder().putInt(CountingByParam.KEY_X, 8).putInt(CountingByParam.KEY_Y, 5).build()
//        val workRequest2 = OneTimeWorkRequest.Builder(CountingByParam::class.java).setInputData(input2).build()
//        //work3
//        val input3 = Data.Builder().putInt(CountingByParam.KEY_X, 13).putInt(CountingByParam.KEY_Y, 10).build()
//        val workRequest3 = OneTimeWorkRequest.Builder(CountingByParam::class.java).setInputData(input3).build()
//        //do workRequest1 and workRequest2 parallel ,workRequest3 will not be started until workRequest1 and workRequest2 have finished
//        WorkManager.getInstance().beginWith(workRequest1, workRequest2).then(workRequest3).enqueue()
    }

    private fun combineWork() { //work1
//        val input1 = Data.Builder().putInt(CountingByParam.KEY_X, 3).putInt(CountingByParam.KEY_Y, 0).build()
//        val workRequest1 = OneTimeWorkRequest.Builder(CountingByParam::class.java).setInputData(input1).build()
//        //work2
//        val input2 = Data.Builder().putInt(CountingByParam.KEY_X, 8).putInt(CountingByParam.KEY_Y, 5).build()
//        val workRequest2 = OneTimeWorkRequest.Builder(CountingByParam::class.java).setInputData(input2).build()
//        //work3
//        val input3 = Data.Builder().putInt(CountingByParam.KEY_X, 13).putInt(CountingByParam.KEY_Y, 10).build()
//        val workRequest3 = OneTimeWorkRequest.Builder(CountingByParam::class.java).setInputData(input3).build()
//        //work4
//        val input4 = Data.Builder().putInt(CountingByParam.KEY_X, 15).putInt(CountingByParam.KEY_Y, 14).build()
//        val workRequest4 = OneTimeWorkRequest.Builder(CountingByParam::class.java).setInputData(input4).build()
//        //work5
//        val input5 = Data.Builder().putInt(CountingByParam.KEY_X, 20).putInt(CountingByParam.KEY_Y, 16).build()
//        val workRequest5 = OneTimeWorkRequest.Builder(CountingByParam::class.java).setInputData(input5).build()
//        val workContinuation1 = WorkManager.getInstance().beginWith(workRequest1).then(workRequest2)
//        val workContinuation2 = WorkManager.getInstance().beginWith(workRequest3).then(workRequest4)
//        WorkContinuation.combine(workContinuation1, workContinuation2).then(workRequest5).enqueue()
    }
}