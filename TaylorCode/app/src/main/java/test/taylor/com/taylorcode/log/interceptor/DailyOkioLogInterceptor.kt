package test.taylor.com.taylorcode.log.interceptor

import android.annotation.SuppressLint
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.android.asCoroutineDispatcher
import kotlinx.coroutines.launch
import okio.buffer
import okio.sink
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*

class DailyOkioLogInterceptor private constructor(private var dir: String) : LogInterceptor {
    private val handlerThread = HandlerThread("log_to_file_thread")
    private val handler: Handler
    private val dispatcher: CoroutineDispatcher

    var startTime = System.currentTimeMillis()

    companion object {
        @Volatile
        private var INSTANCE: DailyOkioLogInterceptor? = null

        fun getInstance(dir: String): DailyOkioLogInterceptor =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: DailyOkioLogInterceptor(dir)
            }
    }

    init {
        handlerThread.start()
        handler = Handler(handlerThread.looper)
        dispatcher = handler.asCoroutineDispatcher("log_to_file_dispatcher")
    }

    override fun log(priority: Int, tag: String, message: String): Boolean {
        GlobalScope.launch(dispatcher) {
            val file = File(getFileName())
            file.sink(true).buffer().use {
                it.writeUtf8(message)
                it.writeUtf8("\n")
            }
            if (message == "work done") Log.v("ttaylor","log() work is ok done=${System.currentTimeMillis() - startTime}")
        }
        return false
    }

    @SuppressLint("SimpleDateFormat")
    private fun getToday(): String =
        SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().time)

    private fun getFileName() = "$dir${File.separator}${getToday()}.log"
}