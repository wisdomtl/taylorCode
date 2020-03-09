package test.taylor.com.taylorcode.aysnc.workmanager

import android.content.Context
import android.util.Log
import androidx.annotation.NonNull
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class Counting @JvmOverloads constructor(@NonNull context: Context, @NonNull workerParams: WorkerParameters) : Worker(context, workerParams) {
    companion object {
        @JvmStatic
        val KEY_INIT = "int"
        val KEY_STEP = "step"
    }

    override fun doWork(): Result {
        val init = inputData.getInt(KEY_INIT, 0)
        val step = inputData.getInt(KEY_STEP, 2)
        var sum = init
        for (i in init..10 step step) {
            Thread.sleep(300)
            Log.v("ttaylor", "tag=couting work, Counting.doWork()  count=$i")
            sum += i
        }
        Log.v("ttaylor","tag=, Counting.doWork()  sum=${sum}")
        return Result.success(Data.Builder().putInt("sum", sum).build())
    }
}