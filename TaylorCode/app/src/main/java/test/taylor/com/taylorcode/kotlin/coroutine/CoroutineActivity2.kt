package test.taylor.com.taylorcode.kotlin.coroutine

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import test.taylor.com.taylorcode.kotlin.extension.decorView
import kotlin.coroutines.*

class CoroutineActivity2 : AppCompatActivity(), CoroutineScope by MainScope() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v("ttaylor", "CoroutineActivity2.onCreate()")

        /**
         * case1: cancel parent, but inner coroutine not stop(due to inner coroutine is not the child of outer)
         */
        val job1 = launch { loadImage() }
        decorView?.postDelayed({ job1.cancel() }, 3000)
    }


    /**
     * case: create coroutine by createCoroutine()
     */
    private fun createCoroutine() {
        val continuation = suspend {
            Log.v("ttaylor", "coroutine body is started")
            "done"
        }.createCoroutine(object : Continuation<String> {
            override val context: CoroutineContext = EmptyCoroutineContext

            override fun resumeWith(result: Result<String>) {
                Log.v("ttaylor", "resumeWith() coroutine result=${result.getOrNull()} ")
            }
        })

        continuation.resume(Unit)
    }

    /**
     * case: start coroutine by startCourtine()
     */
    private fun startCoroutine() {
        val coroutine = suspend {
            Log.v("ttaylor", "startCoroutine()")
            "done"
        }.startCoroutine(object : Continuation<String> {
            override val context: CoroutineContext = EmptyCoroutineContext

            override fun resumeWith(result: Result<String>) {
                Log.v("ttaylor", "resumeWith()")
            }
        })
    }

    fun loadImage() {
        suspend {
            Log.v("ttaylor","loadImage()")
            val image1 = async {
                delay(5000)
                "image1"
            }

            val image2 = async {
                delay(2000)
                "image2"
            }
            "combing image = ${image1.await()} + ${image2.await()}"
        }.startCoroutine(object :Continuation<String>{
            override val context: CoroutineContext = EmptyCoroutineContext

            override fun resumeWith(result: Result<String>) {
                Log.v("ttaylor","resumeWith() result=${result.getOrNull()}")
            }
        })
    }
}