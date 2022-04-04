package test.taylor.com.taylorcode.log.interceptor

import android.annotation.SuppressLint
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import test.taylor.com.taylorcode.log.easylog.Chain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.android.asCoroutineDispatcher
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*

class DailyFileWriterLogInterceptor private constructor(private var dir: String) : LogInterceptor {
    private val handlerThread = HandlerThread("log_to_file_thread")
    private val handler: Handler
    private val dispatcher: CoroutineDispatcher

    private var startTime = System.currentTimeMillis()

    companion object {
        @Volatile
        private var INSTANCE: DailyFileWriterLogInterceptor? = null

        fun getInstance(dir: String): DailyFileWriterLogInterceptor =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: DailyFileWriterLogInterceptor(dir)
            }
    }

    init {
        handlerThread.start()
        handler = Handler(handlerThread.looper)
        dispatcher = handler.asCoroutineDispatcher("log_to_file_dispatcher")
    }

    override fun log(priority: Int, tag: String, log: String, chain: Chain) {
        GlobalScope.launch(dispatcher) {
            FileWriter(getFileName(), true).use {
                it.append(log)
                it.append("\n")
                it.flush()
            }
            if (log == "work done") Log.v(
                "ttaylor1",
                "log() work is done=${System.currentTimeMillis() - startTime}"
            )
        }
        chain.proceed(priority, tag, log)
    }

    override fun enable(): Boolean {
        return true
    }

    @SuppressLint("SimpleDateFormat")
    private fun getToday(): String =
        SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().time)

    private fun getFileName() = "$dir${File.separator}${getToday()}.log"
}