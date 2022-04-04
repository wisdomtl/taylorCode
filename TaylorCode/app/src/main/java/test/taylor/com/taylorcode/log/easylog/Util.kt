package com.taylor.easylog

/**
 * print method call stack
 */
fun getCallStack(blackList: List<String>): List<String> {
    return Thread.currentThread()
        .stackTrace.drop(3)
        .filter { it.className !in blackList }
        .map { "${it.className}.${it.methodName}(${it.fileName}:${it.lineNumber})" }
}
