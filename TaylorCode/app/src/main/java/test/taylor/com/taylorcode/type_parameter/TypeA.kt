package test.taylor.com.taylorcode.type_parameter

abstract class TypeA< T, B> {
    abstract fun get(): T

    fun set(item: T) {

    }
    inline  fun <reified T> getType() = T::class.java
}

class  TypeB<T>:TypeA<CharSequence,String>(){
    var b:T? = null
    override fun get(): CharSequence {
        return "bb"
    }
}