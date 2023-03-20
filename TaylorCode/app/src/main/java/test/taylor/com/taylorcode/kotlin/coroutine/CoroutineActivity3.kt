package test.taylor.com.taylorcode.kotlin.coroutine

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import kotlin.IllegalArgumentException

class CoroutineActivity3 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * case: CoroutineExceptionHandler and  invokeOnCompletion both catch the exception
         */
        val handler0 = CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.i("ttaylor", "CoroutineActivity3.invokeOnCompletion exception handler=${throwable}");//will be printed
        }
        lifecycleScope.launch(handler0) {// if no handler app wil crash
            throw IllegalArgumentException("ddd")
        }.invokeOnCompletion {
            Log.i("ttaylor", "CoroutineActivity3.invokeOnCompletion throwable=${it}"); // will be printed
        }

        /**
         * case: sub coroutine will throw exception to it's father
         */
        val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.v("ttaylor", "onCreate() coroutine exception ")
        }

        lifecycleScope.launch(handler) {
            launch {
                throw IllegalArgumentException()
            }
        }

        /**
         * case: async exception will throw in await()
         */
        val handler1 = CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.v("ttaylor", "onCreate() async exception ") // this wont invoked and app will crash
        }
        lifecycleScope.launch(handler1) {
            val value = async(handler1) { //this wont catch the exception
                delay(1000)
                throw IllegalArgumentException()
                1
            }

            /**
             * wrap await() by try-catch will work
             */
            value.await()
        }

        /**
         * case: one async crash will kill the sibling
         */
        val handler5 = CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.v("ttaylorExceptionByAsync", "onCreate() async exception ") // this wont invoked and app will crash
        }
        lifecycleScope.launch(handler5) {
            val d1 = async { throw IllegalArgumentException() }
            val d2 = async {
                delay(1000)
                Log.v("ttaylorExceptionByAsync", "delay")
            }

            d2.await()
            d1.await()
            Log.v("ttaylorExceptionByAsync", "all async is done")
        }

        /**
         * case: supervisorScope wont kill siblings if exception happened
         */
        lifecycleScope.launch(handler1) {
            supervisorScope {
                val d1 = async { throw IllegalArgumentException() }
                val d2 = async {
                    delay(1000)
                    Log.v("ttaylorExceptionByAsyncInSupervisor", "delay")
                }

                d2.await()
                d1.await()
                Log.v("ttaylorExceptionByAsync", "all async is done")
            }
        }


        /**
         * case: sub jobs wont survive even if supervisor job is used in father
         */
        val handler2 = CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.v(
                "ttaylor",
                "[supervisorJob] exception =$throwable"
            ) // this wont invoked and app will crash
        }
        MainScope().launch(handler2) {
            launch {
                repeat(10) {
                    delay(1000)
                    Log.v("ttaylor", "[supervisorJob] sibling jobs counting=${it}")
                }
            }
            launch {
                throw IllegalArgumentException()
            }
        }

        /**
         * case: supervisor job wont cancel sibling jobs
         */
        val handler3 = CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.v(
                "ttaylor",
                "[supervisorJob] exception =$throwable"
            ) // this wont invoked and app will crash
        }
        val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
        scope.launch(handler3) {
            throw java.lang.IllegalArgumentException()
        }

        scope.launch {
            repeat(10) {
                delay(1000)
                Log.v("ttaylor", "[supervisorJob] sibling jobs counting=${it}")
            }
        }

        /**
         * case: non-supervisor job will cancel sibling jobs
         */
        val handler4 = CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.v(
                "ttaylor",
                "[supervisorJob2] exception =$throwable"
            ) // this wont invoked and app will crash
        }
        val scope2 = CoroutineScope(Dispatchers.Default)
        scope2.launch(handler4) {
            throw java.lang.IllegalArgumentException()
        }

        scope2.launch {
            repeat(10) {
                delay(1000)
                Log.v("ttaylor", "[supervisorJob2] sibling jobs counting=${it}")
            }
        }

        /**
         * case: code in one Coroutine could be executed in different thread after suspend and resume
         */
        lifecycleScope.launch(Dispatchers.Default) {
            Log.v("ttaylor", "withContext() thread=${Thread.currentThread().name}")
            withContext(Dispatchers.IO) {
                delay(1000)
            }

            Log.v("ttaylor", "withContext() after thread=${Thread.currentThread().name}")
        }
    }


    /**
     * case: invokeOnCompletion will be invoked when job is completed or canceled
     */
    suspend fun main(): Unit = coroutineScope {
        val job = launch {
            delay(1000)
        }
        job.invokeOnCompletion { exception: Throwable? ->
            println("Finished")// invoked when job is canceled
        }
        delay(400)
        job.cancelAndJoin() // cancel the job
    }

}