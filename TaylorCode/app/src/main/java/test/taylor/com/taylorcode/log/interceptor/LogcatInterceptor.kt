package test.taylor.com.taylorcode.log.interceptor

import android.util.Log

class LogcatInterceptor : LogInterceptor {
    override fun log(priority: Int, tag: String, log: String): Boolean {
        Log.println(priority, tag, log)
        return true
    }
}