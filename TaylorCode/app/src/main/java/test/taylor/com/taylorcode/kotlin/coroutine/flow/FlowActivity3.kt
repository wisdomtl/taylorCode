package test.taylor.com.taylorcode.kotlin.coroutine.flow

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FlowActivity3 : AppCompatActivity() {

    private val parallelList = listOf(
        Request("parallel1", 1600, 10),
        Request("parallel2", 2900, 20),
        Request("parallel3", 2000, 11),
        Request("parallel4", 3000, 30),
        Request("parallel5", 5000, 50),
        Request("parallel6", 10000, 60),
    )

    private val sequenceList = listOf(
        Request("sequence1", 2100, 30, 30),
        Request("sequence2", 1100, 19, 20),
        Request("sequence3", 2000, 15, 16),
        Request("sequence4", 2200, 7, 10),
        Request("sequence5", 3000, 6, 5),
        Request("sequence6", 2000, 5, 5),
        Request("sequence7", 400, 5, 5),
    )

    /**
     * case: sequence in flow
     */
    private val sequenceFlow = sequenceList.asFlow().map {
        val isDone = it.run { price >= bottomPrice }
        RequestSwitch(load(it), isDone)
    }.transformWhile {
        emit(RequestSwitch(it.request, false))
        !it.isDone
    }

    /**
     * case: parallel in flow
     */
    private val parallelFlow = flow {
        parallelList.asFlow()
            .flatMapMerge { request -> flow { emit(load(request)) } } //case: parallel in flow, suspend function must in floatMapMerge()
            .reduce { max, cur -> if (cur.price > max.price) cur else max }
            .also { emit(RequestSwitch(it, true)) }
    }

    private suspend fun load(request: Request): Request {
        delay(request.delay)
        Log.i("test", "source(${request.name}).price=${request.price}")
        return request
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * case: parallel cut sequence in flow
         */
        lifecycleScope.launch {
            val maxRequest = flowOf(sequenceFlow, parallelFlow)
                .flattenMerge()
                .transformWhile<RequestSwitch, Request> {
                    emit(it.request)
                    !it.isDone
                }
                .reduce { accumulator: Request, value: Request -> if (value.price > accumulator.price) value else accumulator }
            Log.e("FlowActivity3", "FlowActivity3.onCreate[]: max AdSource=${maxRequest}")
        }
    }
}

data class Request(val name: String, val delay: Long, val price: Int, val bottomPrice: Int = 0)


data class RequestSwitch(val request: Request, val isDone: Boolean)