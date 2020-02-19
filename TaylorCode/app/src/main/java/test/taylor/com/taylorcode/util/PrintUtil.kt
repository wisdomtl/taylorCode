package test.taylor.com.taylorcode.util

import android.util.Log
import test.taylor.com.taylorcode.BuildConfig

fun printCallStack(tag: String, deep: Int) {
    if (!BuildConfig.DEBUG) return
    Throwable().stackTrace?.take(deep)?.print(tag) { it -> "${it.className}.${it.methodName} (line ${it.lineNumber})" }
}

fun <T> Collection<T>.print(tag: String, map: (T) -> String) {
    if (!BuildConfig.DEBUG) return
    this.let { c ->
        StringBuilder("$tag: [").apply {
            c.forEach { element -> append("\n\t${map.invoke(element)},") }
            append("]")
        }.also { Log.d("", it.toString()) }
    }
}