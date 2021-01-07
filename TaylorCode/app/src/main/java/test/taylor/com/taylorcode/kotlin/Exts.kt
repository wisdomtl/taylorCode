package test.taylor.com.taylorcode.kotlin

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager

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

fun String?.safeToInt(): Int = this?.let {
    var result = 0
    try {
        result = Integer.parseInt(it)
    } catch (e: NumberFormatException) {
        e.printStackTrace()
    }
    return result
} ?: 0

fun String?.safeToDouble(): Double = this?.let {
    var result = 0.0
    try {
        result = java.lang.Double.parseDouble(it)
    } catch (e: NumberFormatException) {
        e.printStackTrace()
    }
    return result
} ?: 0.0

fun String?.safeToLong(): Long = this?.let {
    var result = 0L
    try {
        result = java.lang.Long.parseLong(it)
    } catch (e: NumberFormatException) {
        e.printStackTrace()
    }
    return result
} ?: 0L
