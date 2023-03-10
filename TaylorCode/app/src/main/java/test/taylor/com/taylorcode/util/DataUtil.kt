package test.taylor.com.taylorcode.util

import kotlin.reflect.KProperty

//fun Any.ofMap(): Map<String, Any?>? {
//    return this::class.takeIf { it.isData }
//        ?.members?.filterIsInstance<KProperty<Any>>()
//        ?.map { it.name to it.call(this) }
//        ?.toMap()
//}


fun Any.ofMap(): Map<String, Any?>? {
    return this::class
        .takeIf { it.isData }
        ?.members?.filterIsInstance<KProperty<Any>>()?.associate { member ->
            val value = member.call(this).let { v ->
                if (v::class.isData) v.ofMap()
                else v
            }
            member.name to value
        }
}

