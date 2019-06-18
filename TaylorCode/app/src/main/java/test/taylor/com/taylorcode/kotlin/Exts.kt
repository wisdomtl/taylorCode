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