package test.taylor.com.taylorcode.exception

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class ExceptionActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    class TaylorHandler: Thread.UncaughtExceptionHandler{
        private var defaultHandler:Thread.UncaughtExceptionHandler? = null
        private var context:Context? = null

        public fun init(context:Context){
           this.context = context
            defaultHandler = Thread.getDefaultUncaughtExceptionHandler()

            Thread.setDefaultUncaughtExceptionHandler(this)
        }
        override fun uncaughtException(t: Thread?, e: Throwable?) {
            Log.v("ttaylor","tag=, TaylorHandler.uncaughtException()  e=${e?.message}")
        }
    }
}