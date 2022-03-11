package test.taylor.com.taylorcode.log

import test.taylor.com.taylorcode.log.interceptor.LogInterceptor
import java.io.PrintWriter
import java.io.StringWriter
import java.net.UnknownHostException

object EasyLog {

    /**
     * Priority constant for the println method; use Log.v.
     */
    private const val VERBOSE = 2

    /**
     * Priority constant for the println method; use Log.d.
     */
    private const val DEBUG = 3

    /**
     * Priority constant for the println method; use Log.i.
     */
    private const val INFO = 4

    /**
     * Priority constant for the println method; use Log.w.
     */
    private const val WARN = 5

    /**
     * Priority constant for the println method; use Log.e.
     */
    private const val ERROR = 6

    /**
     * Priority constant for the println method.
     */
    private const val ASSERT = 7

    private val logInterceptors = mutableListOf<LogInterceptor>()


    fun d(message: String, tag: String = "", vararg args: Any) {
        log(DEBUG, message, tag, *args)
    }

    fun e(message: String, tag: String = "", vararg args: Any, throwable: Throwable? = null) {
        log(ERROR, message, tag, *args, throwable = throwable)
    }

    fun w(message: String, tag: String = "", vararg args: Any) {
        log(WARN, message, tag, *args)
    }

    fun i(message: String, tag: String = "", vararg args: Any) {
        log(INFO, message, tag, *args)
    }

    fun v(message: String, tag: String = "", vararg args: Any) {
        log(VERBOSE, message, tag, *args)
    }

    fun wtf(message: String, tag: String = "", vararg args: Any) {
        log(ASSERT, message, tag, *args)
    }

    fun addInterceptor(interceptor: LogInterceptor) {
        logInterceptors.add(interceptor)
    }

    @Synchronized
    private fun log(
        priority: Int,
        message: String,
        tag: String,
        vararg args: Any,
        throwable: Throwable? = null
    ) {
        var logMessage = message.format(*args)
        if (throwable != null) {
            logMessage += getStackTraceString(throwable)
        }
        logInterceptors.forEach { interceptor ->
            // TODO: tag logic
            if (interceptor.enable()) interceptor.log(priority, tag, logMessage)
        }
    }

    fun String.format(vararg args: Any) =
        if (args.isNullOrEmpty()) this else String.format(this, *args)

    private fun getStackTraceString(tr: Throwable?): String {
        if (tr == null) {
            return ""
        }

        // This is to reduce the amount of log spew that apps do in the non-error
        // condition of the network being unavailable.
        var t = tr
        while (t != null) {
            if (t is UnknownHostException) {
                return ""
            }
            t = t.cause
        }
        val sw = StringWriter()
        val pw = PrintWriter(sw)
        tr.printStackTrace(pw)
        pw.flush()
        return sw.toString()
    }
}