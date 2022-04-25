package test.taylor.com.taylorcode.kotlin.coroutine

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*

class CoroutineJobActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * case: sub coroutine inherit father's CoroutineContext,but different job, Every coroutine creates its own Job,and the job become the child of father job
         * print job state by job.toString()
         */
        lifecycleScope.launch(CoroutineName("taylor")) {
            launch {
                val name = coroutineContext[CoroutineName]?.name
                val job = coroutineContext[Job]?.toString()
                Log.v("ttaylor","[coroutine name] sub coroutine1 name=$name job state=$job")// taylor
            }

            launch {
                delay(1000)
                val name = coroutineContext[CoroutineName]?.name
                val job = coroutineContext[Job]?.toString()
                Log.v("ttaylor","[coroutine name] sub coroutine2 name=$name job state=$job")// taylor
            }
            val name = coroutineContext[CoroutineName]?.name
            val job = coroutineContext[Job]?.toString()
            Log.v("ttaylor","[coroutine name] father coroutine name=$name job state=$job")// taylor
        }



    }
}