package com.taylor.easylog

import android.annotation.SuppressLint
import android.os.Debug
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import okio.BufferedSink
import okio.appendingSink
import okio.buffer
import okio.gzip
import test.taylor.com.taylorcode.log.easylog.Chain
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class OkioLogInterceptor private constructor(private var dir: String) : LogInterceptor {
    private val handlerThread = HandlerThread("log_to_file_thread")
    private val handler: Handler
    private var startTime = System.currentTimeMillis()
    private var bufferedSink: BufferedSink? = null
    private var logFile = File(getFileName())
    private var avg = mutableListOf<Long>()

    val callback = Handler.Callback { message ->
        val sink = checkSink()
        avg.add(Runtime.getRuntime().totalMemory()/(1024*1024))
        when (message.what) {
            TYPE_FLUSH -> {
                sink.use {
                    it.flush()
                    bufferedSink = null
                }
                Log.v(
                    "ttaylor1",
                    "log() Okio work is ok done=${System.currentTimeMillis() - startTime} ,memory=${avg.average()}}"
                )

            }
            TYPE_LOG -> {
                val log = message.obj as String
                sink.writeUtf8(log)
                sink.writeUtf8("\n")
            }
        }
        false
    }

    companion object {
        private const val TYPE_FLUSH = -1
        private const val TYPE_LOG = 1
        private const val FLUSH_LOG_DELAY_MILLIS = 300L

        @Volatile
        private var INSTANCE: OkioLogInterceptor? = null

        fun getInstance(dir: String): OkioLogInterceptor =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: OkioLogInterceptor(dir).apply { INSTANCE = this }
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

    private fun getFileName() = "$dir${File.separator}${getToday()}.gz"

    private fun checkSink(): BufferedSink {
        if (bufferedSink == null) {
            bufferedSink = logFile.appendingSink().gzip().buffer()
        }
        return bufferedSink!!
    }
}