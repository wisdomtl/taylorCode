package test.taylor.com.taylorcode.log.easylog

import com.taylor.easylog.LogInterceptor

class Chain(
    private val interceptors: List<LogInterceptor>,
    private val index: Int = 0
) {
    fun proceed(priority: Int, tag: String, log: String) {
        val next = Chain(interceptors, index + 1)
        val interceptor = interceptors.getOrNull(index)
        interceptor?.log(priority, tag, log, next)
    }
}