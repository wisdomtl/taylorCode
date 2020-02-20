package test.taylor.com.taylorcode.util

import android.util.Log
import test.taylor.com.taylorcode.BuildConfig

/**
 * print method call stack by [deep]
 */
fun printCallStack(tag: String, deep: Int) {
    if (!BuildConfig.DEBUG) return
    Throwable().stackTrace?.take(deep)?.print(tag) { it -> "${it.className}.${it.methodName} (line ${it.lineNumber})" }
}

/**
 * print collection
 */
fun <T> Collection<T>.print(tag: String, map: (T) -> String) {
    if (!BuildConfig.DEBUG) return
    this.let { c ->
        StringBuilder("\n[").apply {
            c.forEach { element -> append("\n\t${map.invoke(element)},") }
            append("\n]")
        }.also { Log.d(tag, it.toString()) }
    }
}