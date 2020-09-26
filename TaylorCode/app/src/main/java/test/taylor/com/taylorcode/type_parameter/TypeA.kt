package test.taylor.com.taylorcode.type_parameter

abstract class TypeA<T, B> {
    abstract fun get(): T

    fun set(item: T) {

    }
}

class  TypeB<T>:TypeA<String,String>(){
    var b:T? = null
    override fun get(): String {
        return "bb"
    }
}