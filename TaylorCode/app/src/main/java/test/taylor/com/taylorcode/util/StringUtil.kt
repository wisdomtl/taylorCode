package test.taylor.com.taylorcode.util

val String?.value:String
    get() {
       return this ?: ""
    }