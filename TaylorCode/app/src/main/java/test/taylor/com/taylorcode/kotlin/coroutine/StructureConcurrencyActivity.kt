package test.taylor.com.taylorcode.kotlin.coroutine

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.IllegalArgumentException
import kotlin.coroutines.suspendCoroutine

class StructureConcurrencyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * case: async will be cancel when the father job is canceled
         */
        GlobalScope.launch {
            val job=  lifecycleScope.launch {
                val va1 = async {
                    repeat(10) {
                        delay(1000)
                        Log.v("ttaylor","[async cancel] task1 is running $it")
                    }
                    1
                }

                val va2 = async {
                    repeat(10) {
                        delay(1000)
                        Log.v("ttaylor","[async cancel] task2 is running $it")
                    }
                    2
                }

                val ret = va1.await() + va2.await()
                Log.v("ttaylor","[async cancel] ret=${ret}")
            }

            delay(5000)
            job.cancel()
        }
    }
}