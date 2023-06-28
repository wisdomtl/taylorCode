package test.taylor.com.taylorcode.kotlin

fun Int.formatNums(): String = this.let {
    return when {
        it >= 10000 -> {    //1w以上
            val w = it.div(10000)
            val decimal = it.rem(10000).div(1000)
            "$w.${decimal}w"
        }
        it >= 1000 -> {   //1k以上
            val thousand = it / 1000
            val digit = it % 1000
            "%d,%03d".format(thousand, digit)
        }
        else -> it.toString()
    }
}

/**
 * convert [String] to [Int] in safe way
 */
fun String?.safeToInt(): Int = this?.let {
    try {
        Integer.parseInt(this)
    } catch (e: NumberFormatException) {
        e.printStackTrace()
        0
    }
} ?: 0

/**
 * convert [String] to [Double] in safe way
 */
fun String?.safeToDouble(): Double = this?.let {
    try {
        java.lang.Double.parseDouble(this)
    } catch (e: NumberFormatException) {
        e.printStackTrace()
        0.0
    }
} ?: 0.0

/**
 * convert [String] to [Long] in safe way
 */
fun String?.safeToLong(): Long = this?.let {

    try {
        java.lang.Long.parseLong(this)
    } catch (e: NumberFormatException) {
        e.printStackTrace()
        0L
    }
} ?: 0L

/**
 * convert [String] to [Float] in safe way
 */
fun String?.safeToFloat(): Float = this?.let {
    try {
        java.lang.Float.parseFloat(this)
    } catch (e: NumberFormatException) {
        e.printStackTrace()
        0f
    }
} ?: 0f
