package test.taylor.com.taylorcode.util


/**
 * print method call stack by [deep]
 */
fun printCallStack(deep: Int): String {
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


/**
 * @param space
 */
fun <K, V> Map<K, V?>.print(space: Int = 0): String {
    val indent = StringBuilder().apply {
        repeat(space) { append(" ") }
    }.toString()
    return StringBuilder("\n${indent}{").also { sb ->
        this.iterator().forEach { entry ->
            val value = entry.value.let { v ->
                (v as? Map<*, *>)?.print("${indent}${entry.key} = ".length) ?: v.toString()
            }
            sb.append("\n\t${indent}[${entry.key}] = $value,")
        }
        sb.append("\n${indent}}")
    }.toString()
}
