package test.taylor.com.taylorcode.log

import test.taylor.com.taylorcode.log.interceptor.LogInterceptor
import test.taylor.com.taylorcode.util.printCallStack
import java.lang.StringBuilder

class CallStackLogInterceptor(private val deep: Int) : LogInterceptor {
    companion object {
        private const val HEADER = "[------------------"
        private const val FOOTER = "------------------]"
    }

    override fun log(priority: Int, tag: String, log: String, chain: Chain) {
        val callStackLog = StringBuilder()
            .append(HEADER)
            .append("[$tag] $log")
            .append(printCallStack(deep))
            .append(FOOTER).toString()
        chain.proceed(priority, tag, callStackLog)
    }

    override fun enable(): Boolean {
        return true
    }
}