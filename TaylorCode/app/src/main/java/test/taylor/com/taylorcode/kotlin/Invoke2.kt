package test.taylor.com.taylorcode.kotlin

import android.util.Log

/**
 * case:invoke 约定，类实现lambda并重写invoke函数
 */
class Invoke2:()->Int {
    override fun invoke(): Int {
        Log.v("ttaylor","tag=, Invoke2.invoke()  ")
        return 1
    }
}