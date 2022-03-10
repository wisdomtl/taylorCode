package test.taylor.com.taylorcode.log.interceptor

import android.util.Log

open class LogcatInterceptor : LogInterceptor {
    override fun log(priority: Int, tag: String, log: String){
        Log.println(priority, tag, log)
    }

    override fun enable(): Boolean {
       return true
    }
}