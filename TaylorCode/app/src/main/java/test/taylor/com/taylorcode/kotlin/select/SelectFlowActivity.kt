package test.taylor.com.taylorcode.kotlin.select

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.merge

/**
 * case: use flow to implement [select]
 */
class SelectFlowActivity : AppCompatActivity() {
//    private val deferred1 = CompletableDeferred<Int>()
//    private val deferred2 = CompletableDeferred<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MainScope().launch {
            val deferred1 = async {
                delay(1000)
                1
            }

            val deferred2 = async {
                delay(2000)
                2
            }
            val first = listOf(deferred1, deferred2)
                .map { flow { emit(it.await()) } }
                .merge()
                .first()
            Log.i("ttaylor", "SelectFlowActivity.onCreate() first=${first}");
        }
    }
}