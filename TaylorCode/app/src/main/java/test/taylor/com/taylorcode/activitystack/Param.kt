package test.taylor.com.taylorcode.activitystack

import android.app.Activity

interface Param {
    val paramMap: Map<String, Any>
}

fun <T> Activity.getParam(key: String): T {
    val iterator = ActivityStack.stack.let { it.listIterator(it.size) }
    while (iterator.hasPrevious()) {
        val parameter = iterator.previous()
        parameter.paramMap.getOrDefault(key, null)?.also { return it as T }
    }
    throw IllegalArgumentException("missing Parameter for the previous Activity")
}