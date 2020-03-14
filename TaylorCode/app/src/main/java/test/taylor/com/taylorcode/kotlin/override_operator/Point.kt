package test.taylor.com.taylorcode.kotlin.override_operator

class Point(var x: Int, var y: Int) {
    operator fun get(index: Int): Int = when (index) {
        0 -> x
        1 -> y
        else -> throw IllegalArgumentException("wrong arg")
    }

    operator fun set(x:Int,y:Int){
        this.x = x
        this.y = y
    }
}