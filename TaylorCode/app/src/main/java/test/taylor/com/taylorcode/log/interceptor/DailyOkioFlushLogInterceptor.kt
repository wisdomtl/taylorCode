package test.taylor.com.taylorcode.log.interceptor

import android.annotation.SuppressLint
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import test.taylor.com.taylorcode.log.easylog.Chain
import okio.appendingSink
import okio.buffer
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class DailyOkioFlushLogInterceptor private constructor(private var dir: String) : LogInterceptor {
    private val handlerThread = HandlerThread("log_to_file_thread")
    private val handler: Handler
    private var startTime = System.currentTimeMillis()
    private var logFile = File(getFileName())

    val callback = Handler.Callback { message ->
        val file = File(getFileName())
        file.appendingSink().buffer().use {
            val log = message.obj as String
            it.writeUtf8(log)
            it.writeUtf8("\n")
        }
        if (message.obj as? String == "work done") Log.v(
            "ttaylor1",
            "log() work is ok flush done=${System.currentTimeMillis() - startTime}"
        )
        false
    }

    companion object {

        @Volatile
        private var INSTANCE: DailyOkioFlushLogInterceptor? = null

        fun getInstance(dir: String): DailyOkioFlushLogInterceptor =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: DailyOkioFlushLogInterceptor(dir)
            }
    }

    init {
        handlerThread.start()
        handler = Handler(handlerThread.looper, callback)
    }

    override fun log(priority: Int, tag: String, log: String, chain: Chain) {
        val message = handler.obtainMessage()
        message.obj = "[$tag] $log"
        message.sendToTarget()
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