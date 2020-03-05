package test.taylor.com.taylorcode.util

import test.taylor.com.taylorcode.BuildConfig

/**
 * print method call stack by [deep]
 */
fun printCallStack(deep: Int): String {
    if (!BuildConfig.DEBUG) return ""
    return Throwable().stackTrace?.take(deep)?.print { it -> "${it.className}.${it.methodName} (line ${it.lineNumber})" } ?: ""
}

/**
 * print collection bean in which you interested defined by [map]
 */
fun <T> Collection<T>.print(map: (T) -> String): String {
    return this.let { c ->
        StringBuilder("\n[").apply {
            c.forEach { element -> append("\n\t${map.invoke(element)},") }
            append("\n]")
        }.toString()
    }
}