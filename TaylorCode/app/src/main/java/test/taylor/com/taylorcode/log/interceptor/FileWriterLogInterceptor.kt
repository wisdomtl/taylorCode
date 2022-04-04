package test.taylor.com.taylorcode.log.interceptor

import android.annotation.SuppressLint
import android.os.Debug
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import test.taylor.com.taylorcode.log.easylog.Chain
import com.taylor.easylog.LogInterceptor
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.Writer
import java.text.SimpleDateFormat
import java.util.*

class FileWriterLogInterceptor private constructor(private var dir: String) : LogInterceptor {
    private val handlerThread = HandlerThread("log_to_file_thread")
    private val handler: Handler
    private var startTime = System.currentTimeMillis()
    private var fileWriter: Writer? = null
    private var logFile = File(getFileName())
    private var avgUsed = mutableListOf<Long>()

    val callback = Handler.Callback { message ->
        val sink = checkFileWriter()
        val use = Debug.getNativeHeapSize()/(1024*1204) - Debug.getNativeHeapFreeSize()/(1024*1204)
        avgUsed.add(use)
        when (message.what) {
            TYPE_FLUSH -> {
                sink.use {
                    it.flush()
                    fileWriter = null
                }
                Log.v(
                    "ttaylor1",
                    "log() fileWriter work is ok done=${System.currentTimeMillis() - startTime} , memory avg=${avgUsed.average()}"
                )
            }
            TYPE_LOG -> {
                val log = message.obj as String
                sink.write(log)
                sink.write("\n")
            }
        }
//        if ((message.obj as? String)?.contains("work done") == true)
        false
    }

    companion object {
        private const val TYPE_FLUSH = -1
        private const val TYPE_LOG = 1
        private const val FLUSH_LOG_DELAY_MILLIS = 1000L

        @Volatile
        private var INSTANCE: FileWriterLogInterceptor? = null

        fun getInstance(dir: String): FileWriterLogInterceptor =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: FileWriterLogInterceptor(dir).apply { INSTANCE = this }
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

    private fun checkFileWriter(): Writer {
        if (fileWriter == null) {
            fileWriter = FileWriter(logFile,true).buffered()
        }
        return fileWriter!!
    }
}