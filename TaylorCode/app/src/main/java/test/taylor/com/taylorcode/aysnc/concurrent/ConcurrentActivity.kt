package test.taylor.com.taylorcode.aysnc.concurrent

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class ConcurrentActivity : AppCompatActivity() {

    private val executor = Executors.newFixedThreadPool(64)

    private val cowArrayList = CopyOnWriteArrayList<CallInfo>()

    private val concurrentQueue = ConcurrentLinkedQueue<CallInfo>()

    private val mainScope = MainScope()
    private var start = 0L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        start = System.currentTimeMillis()
        repeat(1000) {
            executor.execute {
                getList().add(CallInfo(it.toLong(), "CALL_RESPONSE", 200))
                getList().add(CallInfo(it.toLong(), "CALL_URL", "https://www.ddd.com"))
                getList().add(CallInfo(it.toLong(), "CALL_RESPONSE_PROTOCOL", "QUIC"))
                getList().add(CallInfo(it.toLong(), "CALL_METHOD", "GET"))

//                concurrentQueue.add(CallInfo(it.toLong(),"CALL_RESPONSE",200))
//                concurrentQueue.add(CallInfo(it.toLong(),"CALL_URL","https://www.ddd.com"))
//                concurrentQueue.add(CallInfo(it.toLong(),"CALL_RESPONSE_PROTOCOL","QUIC"))
//                concurrentQueue.add(CallInfo(it.toLong(),"CALL_METHOD","GET"))
            }
        }
        mainScope.launch {
            repeat(1000) { callId ->
                executor.execute {
                    getList().add(CallInfo(callId.toLong(), "CALL_CONSUME", 10000))
                    get(callId.toLong())
                    getList().removeIf { it.first == callId.toLong() }
                }
            }

            executor.shutdown()
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS)
            val size = getList().size
            Log.v(
                "ttaylor",
                "onCreate() size=${size} consume=${System.currentTimeMillis() - start}"
            )

        }
    }

    private fun get(callId: Long): Map<String, Any> {
        return getList().filter { it.first == callId }
            .map { it.second to it.third }
            .let { mapOf(*it.toTypedArray()) }
    }

    private fun getList() = cowArrayList
}

typealias CallInfo = Triple<Long, String, Any>