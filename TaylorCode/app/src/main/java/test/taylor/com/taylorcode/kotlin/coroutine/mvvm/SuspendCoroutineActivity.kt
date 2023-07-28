package test.taylor.com.taylorcode.kotlin.coroutine.mvvm

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import test.taylor.com.taylorcode.kotlin.coroutine.LogContinuationInterceptor

class SuspendCoroutineActivity : AppCompatActivity() {

    private val scope = CoroutineScope(SupervisorJob()+Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * case: the coroutine body is a big continuation ,so the code below will resume twice
         */
        lifecycleScope.launch(LogContinuationInterceptor()) {
            Log.v("ttaylor", "1111")
            ddd()
            Log.v("ttaylor", "4444")
        }

        /**
         *case: log.e will be printed after delay resume
         */
       scope.launch {
           delay()
           repeat(30){
               Log.e("ttaylor", "SuspendCoroutineActivity.onCreate[]: after outer delay(${it}) ")
           }
       }
    }

    suspend fun ddd() = withContext(Dispatchers.Default)
    {
        delay(1000)
        Log.v("ttaylor", "2222()")
        delay(2000)
        Log.v("ttaylor", "3333()")
    }

    /**
     * case: log.d will be printed after delay resume and before out delay return
     */
    private suspend fun delay(){
        delay(3000)
        repeat(30){
            Log.d("ttaylor", "SuspendCoroutineActivity.delay[]: after delay($it)")
        }
    }
}