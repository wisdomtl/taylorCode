package test.taylor.com.taylorcode.kotlin.coroutine

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.IllegalArgumentException

class CoroutineExceptionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * case: exception in the sub launch coroutine will spread up to root scope
         */
        val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.v("ttaylor","[handle exception in sub launch] throwable=$throwable")
        }

        lifecycleScope.launch(handler) {// handle exception here works
            launch {// handle exception here ,no use
               launch {// handle exception here ,no use
                   throw Exception()
               }
            }
        }


        /**
         * case: the Supervisor scope's sibling coroutine survive and sub coroutine died
         */
        val handler2 = CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.v("ttaylor","[sub coroutine in supervisor scope] throwable=$throwable")
        }
        val scope = CoroutineScope(SupervisorJob())
        scope.launch(handler2) {
            // the exception throw here will cancel the father scope
            launch {
                delay(2000)
                throw Exception() }
            // died
            launch {
                repeat(10){
                    delay(1000)
                    Log.v("ttaylor","[sub coroutine in supervisor scope] $it")
                }
            }
        }
        // survive
        scope.launch {
            repeat(10){
                delay(1000)
                Log.v("ttaylor","[sub coroutine in supervisor scope] sibling $it")
            }
        }

        scope.launch(handler2) {
            supervisorScope {
                launch { throw Exception() }
                // survive
                launch {
                    repeat(10){
                        delay(1000)
                        Log.v("ttaylor","[sub coroutine in supervisor scope] sibling launch in supervisorScope$it")
                    }
                }
            }
        }

        /**
         * case: the sibling delay the dying of father coroutine by withContext(NonCancellable) in the finally
         */
        val handler3 = CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.v("ttaylor","[action after sibling throw exception] throwable=$throwable")
        }
        lifecycleScope.launch(handler3) {
            launch {
                delay(2000)
                throw Exception() }

            launch {
                try {
                    repeat(10){
                        delay(1000)
                        Log.v("ttaylor","[action after sibling throw exception] $it")
                    }
                } catch (e:Exception){
                    Log.v("ttaylor","[action after sibling throw exception] catch=${e}")
                } finally {
                    Log.v("ttaylor","[action after sibling throw exception] finally")
                    withContext(NonCancellable){
                        delay(2000)
                        Log.v("ttaylor","[action after sibling throw exception] delay father coroutine dying")
                    }
                }
            }
        }

        /**
         * case : supervisor job spread the exception downward
         */
        val handler5 = CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.v("ttaylor","[supervisorScope exception downward] outer thorw=${throwable}")
        }

        val handler4 = CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.v("ttaylor","[supervisorScope exception downward] inner thorw=${throwable}")
        }
        val handler6 = CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.v("ttaylor","[supervisorScope exception downward] most inner thorw=${throwable}")
        }
        lifecycleScope.launch(handler5) { // if inner handle the exception ,here wont receive exception
            supervisorScope {
                launch(handler4) { // will receive exception
                    launch(handler6) { // wont receive exception
                        repeat(10){
                            delay(1000)
                            Log.v("ttaylor","[supervisorScope exception downward] $it")
                        }
                    }
                    delay(5000)
                    throw Exception()
                }
            }
        }

    }

}