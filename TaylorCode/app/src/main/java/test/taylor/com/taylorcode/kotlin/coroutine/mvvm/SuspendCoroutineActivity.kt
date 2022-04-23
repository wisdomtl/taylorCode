package test.taylor.com.taylorcode.kotlin.coroutine.mvvm

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SuspendCoroutineActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            Log.v("ttaylor","aaaa")
            ddd()
            Log.v("ttaylor","eeeee")
        }
    }

    suspend fun ddd(){
        delay(10000)
        Log.v("ttaylor","ddd()")
    }
}