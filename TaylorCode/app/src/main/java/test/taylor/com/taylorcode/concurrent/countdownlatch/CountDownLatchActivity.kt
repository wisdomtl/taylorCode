package test.taylor.com.taylorcode.concurrent.countdownlatch

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Scheduler
import kotlinx.coroutines.*
import rx.schedulers.Schedulers
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

class CountDownLatchActivity : AppCompatActivity() {


//    private val mainScope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadBackground()
        Log.v("ttaylor", "after load background")
    }

    private fun loadBackground() {
        val countDownLatch = CountDownLatch(1)
//        mainScope.launch(Executors.newFixedThreadPool(1).asCoroutineDispatcher()) {
//            try {
//                delay(3000)
//                throw Exception(" throw exception in thread 1")
//            } catch (e: Exception) {
//                Log.v("ttaylor", "catch exception in thread 1")
//            }
//            countDownLatch.countDown()
//        }
//        countDownLatch.await()
        io.reactivex.schedulers.Schedulers.io().scheduleDirect {
            try {
                Thread.sleep(2000)
                throw Exception(" throw exception in thread 1")
            } catch (e: java.lang.Exception) {
                Log.v("ttaylor", "catch exception in thread 1")
                e.printStackTrace()
                null
            }
            countDownLatch.countDown()
        }
        countDownLatch.await()
    }
}