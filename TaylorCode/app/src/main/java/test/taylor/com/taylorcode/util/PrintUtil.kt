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
fun <T> Collection<T>.print(map: (T) -> String) =
    StringBuilder("\n[").also { sb ->
        this.forEach { e -> sb.append("\n\t${map(e)},") }
        sb.append("\n]")
    }.toString()


fun <K, V> Map<K, V?>.print(map: (V?) -> String): String =
    StringBuilder("\n{").also { sb ->
        this.iterator().forEach { entry ->
            sb.append("\n\t[${entry.key}] = ${map(entry.value)}")
        }
        sb.append("\n}")
    }.toString()

