package test.taylor.com.taylorcode.concurrent

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicInteger

class ThreadActivity: AppCompatActivity() {

    /**
     * ThreadFactory case: the classic implementation of ThreadFactory
     */
    private class DbAccessThreadFactory internal constructor(private val mGroup: ThreadGroup, private val mReadOrWrite: Byte) :
        ThreadFactory {
        private val threadNumber = AtomicInteger(1)
        override fun newThread(r: Runnable): Thread {
            val t = Thread(
                mGroup, r,
                THREAD_PREFIX + (if (mReadOrWrite.toInt() != 0) "write" else "read") + "-" + threadNumber.getAndIncrement(),
                0
            )
            t.isDaemon = false
            t.priority = Thread.NORM_PRIORITY
            t.uncaughtExceptionHandler = Thread.UncaughtExceptionHandler { t, e ->
                Log.v("ttaylor","tag=thread-exception, DbAccessThreadFactory.newThread()  error=$e")
            }
            return t
        }

        companion object {
            const val THREAD_PREFIX = "thread-taylor-db-"
        }

    }
}