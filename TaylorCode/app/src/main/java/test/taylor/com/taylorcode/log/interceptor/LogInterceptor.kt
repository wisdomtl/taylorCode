package test.taylor.com.taylorcode.log.interceptor

interface LogInterceptor {
    /**
     * print the log
     * @return whether terminate the responsibility chain
     */
    fun log(priority: Int, tag: String, log: String): Boolean
}