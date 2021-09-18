package test.taylor.com.taylorcode.kotlin

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import test.taylor.com.taylorcode.util.Timer

class KotlinActivity2 : AppCompatActivity() {
    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * case: callbackFlow wont stop emitting values even if Activity is destroyed
         */
        job = lifecycleScope.launchWhenResumed {
            timerFlow().collect {
                Log.v("ttaylor", "timer flow long=${it.toString()}")
            }
        }
    }

    override fun onStop() {
        super.onStop()
        /**
         * case: cancel the callbackFlow when activity is stopped is a must or task will keep running in the background
         */
        job?.cancel()
    }
}

fun timerFlow() = callbackFlow<Long> {
    val timerListener = object : Timer.TimerListener {
        override fun onTick(pastMillisecond: Long) {
            trySend(pastMillisecond)
        }
    }
    Timer(timerListener).start(0, 500)
    awaitClose { }
}