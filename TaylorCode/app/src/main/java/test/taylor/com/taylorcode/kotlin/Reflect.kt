package test.taylor.com.taylorcode.kotlin

/**
 * create a new instance of [T] with [params]
 */
inline fun <reified T> new(vararg params: Any): T =
    T::class.java.getDeclaredConstructor(*params.map { it::class.java }.toTypedArray()).also { it.isAccessible = true }.newInstance(*params)
