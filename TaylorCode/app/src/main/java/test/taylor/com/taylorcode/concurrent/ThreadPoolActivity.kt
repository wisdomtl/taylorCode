package test.taylor.com.taylorcode.concurrent

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class ThreadPoolActivity : AppCompatActivity() {

    private val end = Long.MAX_VALUE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        /**
         * threadpool case: test single thread pool back-pressure
         */
        val executor = ThreadPoolExecutor(
            1, 1,
            0L, TimeUnit.MILLISECONDS,
            LinkedBlockingQueue()
        ).apply {
            rejectedExecutionHandler = object : ThreadPoolExecutor.AbortPolicy() {
                override fun rejectedExecution(r: Runnable?, e: ThreadPoolExecutor?) {
                    (r as? LogRunnable)?.let { logRunnable ->
                        Log.v("ttaylor", "tag=printt, ThreadPoolActivity.rejectedExecution()  ${logRunnable.i} is rejected")
                    }
                }
            }
        }

        Thread{
            for (i in 1..end) {
                executor.execute(LogRunnable(i))
            }
        }.start()
    }

    class LogRunnable(var i: Long) : Runnable {
        override fun run() {
            try {
                Thread.sleep(10)
            } catch (e: Exception) {
                Log.e("ttaylor","tag=printt, LogRunnable.run() $e")
            }
            Log.v("ttaylor", "tag=printt, LogRunnable.run() thread id=${Thread.currentThread().id} count=$i")
        }
    }
}