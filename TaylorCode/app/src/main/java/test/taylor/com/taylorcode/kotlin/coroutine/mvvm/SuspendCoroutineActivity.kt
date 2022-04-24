package test.taylor.com.taylorcode.kotlin.coroutine.mvvm

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import test.taylor.com.taylorcode.kotlin.coroutine.LogContinuationInterceptor

class SuspendCoroutineActivity : AppCompatActivity() {

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
    }

    suspend fun ddd() = withContext(Dispatchers.Default)
    {
        delay(1000)
        Log.v("ttaylor", "2222()")
        delay(2000)
        Log.v("ttaylor", "3333()")
    }
}