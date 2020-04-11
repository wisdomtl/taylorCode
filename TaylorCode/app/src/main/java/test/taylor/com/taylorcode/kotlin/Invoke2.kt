package test.taylor.com.taylorcode.kotlin

import android.util.Log

class Invoke2:()->Int {
    override fun invoke(): Int {
        Log.v("ttaylor","tag=, Invoke2.invoke()  ")
        return 1
    }
}