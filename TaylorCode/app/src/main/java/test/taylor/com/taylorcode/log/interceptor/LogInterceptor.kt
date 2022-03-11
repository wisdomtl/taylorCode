package test.taylor.com.taylorcode.log.interceptor

import test.taylor.com.taylorcode.log.Chain

interface LogInterceptor {
    /**
     * print the log
     * @return whether terminate the responsibility chain
     */
    fun log(priority: Int, tag: String, log: String, chain: Chain)

    /**
     * whether apply [log] logic
     * @return true means apply, otherwise false
     */
    fun enable(): Boolean
}