package test.taylor.com.taylorcode.log

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import test.taylor.com.taylorcode.log.interceptor.DailyFileWriterLogInterceptor
import test.taylor.com.taylorcode.log.interceptor.LogcatInterceptor

class LogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EasyLog.addInterceptor(DailyFileWriterLogInterceptor.getInstance(this.filesDir.absolutePath))
        EasyLog.addInterceptor(LogcatInterceptor())


        val str = "tttyaylor %s %s"
        EasyLog.v(str, "dddd", "1111")
        /**
         * foreach case : return like break, return@forEach like continue
         */
        listOf(1, 2, 3, 4, 5).forEach {
            if (it == 2) return
            Log.v("ttaylor", "onCreate() foreach =$it")
        }
    }

    fun String.format(vararg args: Any?) =
        if (args.isNullOrEmpty()) this else String.format(this, *args)

}