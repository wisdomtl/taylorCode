package test.taylor.com.taylorcode.log.interceptor

import android.annotation.SuppressLint
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.android.asCoroutineDispatcher
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*

class DailyFileWriterNotFlushLogInterceptor private constructor(private var dir: String) : LogInterceptor {
    private val handlerThread = HandlerThread("log_to_file_thread")
    private val handler: Handler
    private val dispatcher: CoroutineDispatcher
    private var fileWriter: FileWriter? = null
    private var logFile = File(getFileName())

    private var startTime = System.currentTimeMillis()

    companion object {
        @Volatile
        private var INSTANCE: DailyFileWriterNotFlushLogInterceptor? = null

        fun getInstance(dir: String): DailyFileWriterNotFlushLogInterceptor =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: DailyFileWriterNotFlushLogInterceptor(dir)
            }
    }

    init {
        handlerThread.start()
        handler = Handler(handlerThread.looper)
        dispatcher = handler.asCoroutineDispatcher("log_to_file_dispatcher")
    }

    override fun log(priority: Int, tag: String, log: String) {
        GlobalScope.launch(dispatcher) {
            val fileWriter = checkSink()
            fileWriter.run {
                append(log)
                append("\n")
            }
            if (log == "work done") Log.v("ttaylor1","log() work is FileWriter not flush done=${System.currentTimeMillis() - startTime}")
        }
    }

    override fun enable(): Boolean {
        return true
    }

    @SuppressLint("SimpleDateFormat")
    private fun getToday(): String =
        SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().time)

    private fun getFileName() = "$dir${File.separator}${getToday()}.log"

    private fun checkSink(): FileWriter {
        if (fileWriter == null) {
            fileWriter = FileWriter(logFile,true)
        }
        return fileWriter!!
    }
}