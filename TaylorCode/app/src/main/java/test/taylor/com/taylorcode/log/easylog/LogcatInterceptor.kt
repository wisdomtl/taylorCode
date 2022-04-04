package com.taylor.easylog

import android.util.Log
import test.taylor.com.taylorcode.log.easylog.Chain

open class LogcatInterceptor : LogInterceptor {
    override fun log(priority: Int, tag: String, log: String, chain: Chain) {
        if (enable()) {
            Log.println(priority, tag, log)
        }
        chain.proceed(priority, tag, log)
    }

    override fun enable(): Boolean {
        return true
    }
}