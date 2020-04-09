package test.taylor.com.taylorcode.util

import kotlin.reflect.KProperty

fun Any.ofMap() = this::class.takeIf { it.isData }?.let { kClass ->
    kClass.members.filterIsInstance<KProperty<Any>>().map {
        it.name to it.call(this)
    }.toMap()
}

