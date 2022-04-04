package com.taylor.easylog

import test.taylor.com.taylorcode.log.easylog.Chain
import java.lang.StringBuilder

class CallStackLogInterceptor : LogInterceptor {
    companion object {
        private const val HEADER =
            "┌──────────────────────────────────────────────────────────────────────────────────────────────────────"
        private const val FOOTER =
            "└──────────────────────────────────────────────────────────────────────────────────────────────────────"
        private const val LEFT_BORDER = '│'
        private val blackList = listOf(
            CallStackLogInterceptor::class.java.name,
            EasyLog::class.java.name,
            Chain::class.java.name,
        )
    }

    override fun log(priority: Int, tag: String, log: String, chain: Chain) {
        chain.proceed(priority, tag, HEADER)
        chain.proceed(priority, tag, "$LEFT_BORDER$log")
        getCallStack(blackList).forEach {
            val callStack = StringBuilder()
                .append(LEFT_BORDER)
                .append("\t${it}").toString()
            chain.proceed(priority, tag, callStack)
        }
        chain.proceed(priority, tag, FOOTER)
    }

    override fun enable(): Boolean {
        return true
    }
}