package test.taylor.com.taylorcode.activitystack

import android.app.Activity

interface Param {
    val paramMap: Map<String, Any>
}

fun <T> Activity.getParam(key: String): T {
    val iterator = PageStack.stack.let { it.listIterator(it.size) }
    while (iterator.hasPrevious()) {
        val activity = iterator.previous()
        (activity as? Param)?.paramMap?.getOrDefault(key, null)?.also { return it as T }
        val paramFragment = PageStack.fragments[activity]?.firstOrNull { (it as? Param)?.paramMap?.getOrDefault(key, null) != null }
        if(paramFragment !=null) return (paramFragment as Param).paramMap[key] as T
    }
    throw IllegalArgumentException("missing Parameter for the previous Activity/Fragment")
}