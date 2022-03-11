package test.taylor.com.taylorcode.log.interceptor

import android.annotation.SuppressLint
import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import android.util.Log
import okio.BufferedSink
import okio.appendingSink
import okio.buffer
import test.taylor.com.taylorcode.log.Chain
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class DailyOkioLogInterceptor private constructor(private var dir: String) : LogInterceptor {
    private val handlerThread = HandlerThread("log_to_file_thread")
    private val handler: Handler
    private var startTime = System.currentTimeMillis()
    private var bufferedSink: BufferedSink? = null
    private var logFile = File(getFileName())

    val callback = Handler.Callback { message ->
        val sink = checkSink()
        when (message.what) {
            TYPE_FLUSH -> {
                sink.use {
                    it.flush()
                    bufferedSink = null
                }
            }
            TYPE_LOG -> {
                val log = message.obj as String
                sink.writeUtf8(log)
                sink.writeUtf8("\n")
            }
        }
        if (message.obj as? String == "work done") Log.v(
            "ttaylor1",
            "log() work is ok done=${System.currentTimeMillis() - startTime}"
        )
        false
    }

    companion object {
        private const val TYPE_FLUSH = -1
        private const val TYPE_LOG = 1
        private const val FLUSH_LOG_DELAY_MILLIS = 3000L

        @Volatile
        private var INSTANCE: DailyOkioLogInterceptor? = null

        fun getInstance(dir: String): DailyOkioLogInterceptor =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: DailyOkioLogInterceptor(dir).apply { INSTANCE = this }
            }
    }

    init {
        handlerThread.start()
        handler = Handler(handlerThread.looper, callback)
    }
    override fun log(priority: Int, tag: String, log: String, chain: Chain) {
        // prevent HandlerThread being killed
        if (!handlerThread.isAlive) handlerThread.start()
        handler.run {
            removeMessages(TYPE_FLUSH)
            obtainMessage(TYPE_LOG, "[$tag] $log").sendToTarget()
            val flushMessage = handler.obtainMessage(TYPE_FLUSH)
            sendMessageDelayed(flushMessage, FLUSH_LOG_DELAY_MILLIS)
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

    private fun checkSink(): BufferedSink {
        if (bufferedSink == null) {
            bufferedSink = logFile.appendingSink().buffer()
        }
        return bufferedSink!!
    }
}