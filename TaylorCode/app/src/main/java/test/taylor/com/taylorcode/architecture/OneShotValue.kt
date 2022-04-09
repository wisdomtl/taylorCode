package test.taylor.com.taylorcode.architecture

open class OneShotValue<out T>(private val value: T) {

    private var handled = false

    fun getValue(): T? {
        return if (handled) {
            null
        } else {
            handled = true
            value
        }
    }

    fun peekValue(): T = value
}