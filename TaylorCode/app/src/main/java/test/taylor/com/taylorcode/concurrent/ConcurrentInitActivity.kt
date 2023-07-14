package test.taylor.com.taylorcode.concurrent

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import okhttp3.internal.platform.android.AndroidLogHandler.flush
import test.taylor.com.taylorcode.R
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.util.print
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import kotlin.random.Random

class ConcurrentInitActivity : AppCompatActivity() {

    private var hasInit = AtomicBoolean(false)

    private val list =ConcurrentLinkedQueue<Int>()
//    private val list = mutableListOf<Int>()
    private var lastFlushTime = 0L
    private var flushJob: Job? = null
    private val scope = CoroutineScope(SupervisorJob())
    private val channel = Channel<Int>(50)
    private val logDispatcher = Executors.newFixedThreadPool(1).asCoroutineDispatcher()
    private val scope2 = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val contentView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent

            TextView {
                layout_id = "tvChange"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 50f
                text = "test"
                fontFamily = R.font.pingfang
                gravity = gravity_center
                centerInParent = true
                onClick = {
                    Log.i("ttaylor", "ConcurrentInitActivity.[]: testArray=${testArray.any { it == -1 }}")

                }
            }
        }
    }
    private val testArray = Array(1000) { -1 }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)
        repeat(10) {
            MainScope().launch { init() }
        }

        consume()
    }

    private fun log(value: Int) {
        list.add(value)
        flushJob?.cancel()
        if (list.size >= 5 || System.currentTimeMillis() - lastFlushTime >= 50) {
            flushList()
        } else {
            flushJob = delayFlushList()
        }
    }

    private fun delayFlushList() = scope2.launch(logDispatcher) {
        delay(50)
        Log.d("ttaylor", "ConcurrentInitActivity.delayFlushList[]: threadId=${Thread.currentThread().id}")
        flushList()
    }

    private fun flushList() {
        val batch = list.map { Value(it) }
        process(batch)
        list.clear()
        lastFlushTime = System.currentTimeMillis()
    }

    private fun process(batch: List<Value>) {
        Log.e("ttaylor", "ConcurrentInitActivity.process[batch]: batch=${batch.print { it.value.toString() }}")
        batch.forEach {
            testArray[it.value] = it.value
        }
    }

    private val count = AtomicInteger(0)
    private var countConsume = 0
    private fun consume() {
        repeat(1000) {
            scope.launch(Dispatchers.Default) {
                delay((25L..100L).random())
                val countdd = count.getAndIncrement()
                Log.i("ttaylor", "ConcurrentInitActivity.produce[]: threadId=${Thread.currentThread().id} count=${countdd}")
                channel.send(countdd)
            }
        }

        scope.launch(logDispatcher) {
            channel.consumeEach {
                /**
                 * Channel is sequence, so it is thread-safe
                 */
                //                countConsume++
                //                Log.i("ttaylor", "ConcurrentInitActivity.consume[]: threadId=${Thread.currentThread().id} countConsume=${countConsume}")
                delay((25L..100L).random())
                Log.d("ttaylor", "ConcurrentInitActivity.consume[]:  threadId=${Thread.currentThread().id} count=${it}")
                log(it)
            }
        }
    }

    /**
     * case: init once by multiple thread
     */
    fun init() {
        if (hasInit.compareAndSet(false, true)) {
            doInit()
        }
    }

    fun doInit() {
        Log.v("ttaylor", "doInit()")
    }
}

data class Value(val value: Int)