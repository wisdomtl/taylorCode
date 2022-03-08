package test.taylor.com.taylorcode.log.interceptor

import android.util.Log

class LogcatInterceptor : LogInterceptor {
    override fun log(priority: Int, tag: String, message: String): Boolean {
        Log.println(priority, tag, message)
        return true
    }
}