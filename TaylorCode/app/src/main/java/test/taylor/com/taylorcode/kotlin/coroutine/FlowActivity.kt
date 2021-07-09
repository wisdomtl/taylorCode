package test.taylor.com.taylorcode.kotlin.coroutine

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import test.taylor.com.taylorcode.kotlin.ConstraintLayout

import test.taylor.com.taylorcode.kotlin.*

class FlowActivity : AppCompatActivity() {
    var count = 0

    var flow: Flow<Int>? = null
    var job: Job? = null

    val mainScope = MainScope()

    private lateinit var tv: TextView

    val stateFlow = MutableStateFlow("")

    private val contentView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent

            tv = TextView {
                layout_id = "tvChange"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 12f
                textColor = "#ffffff"
                text = "new Message"
                gravity = gravity_center
                center_horizontal = true
                center_vertical = true
                padding = 20
                shape = shape {
                    corner_radius = 20
                    solid_color = "#ff00ff"
                }
                onClick = {
                    stateFlow.value = (count++).toString()

                }
            }
        }
    }

    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)

        /**
         * case : throttle first of click
         */
        tv.clicks().throttleFirst(1000).onEach {
            Log.v("ttaylor", "click debounce ")
        }.launchIn(mainScope)

        /**
         * case: StateFlow
         */
        GlobalScope.launch {
            stateFlow.buffer()?.collect {
                Log.v("ttaylor", "collect() string=${it}")
            }
        }

        /**
         * case
         * flow() to create a flow
         * collect() to collect a flow
         */
        GlobalScope.launch {
            flow {
                // define the logic invoked when collect() invoked
                (1 .. 3).forEach {
                    delay(1000)
                    emit(it)
                }
            }.map { }.collect {
                // define the logic invoked when emit() invoked
                Log.v("ttaylor", "print() num=${it}")
            }
        }

        /**
         * flowOn() to switch emit data thread
         */
        GlobalScope.launch(Dispatchers.Main) {
            flow { // thread io
                (1 .. 5).forEach {
                    Log.v("ttaylor", "flowOn(${it}) emit thread id=${Thread.currentThread().id}")
                    emit(it)
                }
            }.map {// thread io
                Log.v("ttaylor", "flowOn(${it}) map thread id=${Thread.currentThread().id}")
                it * it
            }.flowOn(Dispatchers.IO)
                .map { // thread main
                    Log.v("ttaylor", "flowOn(${it}) map after flowOn thread id=${Thread.currentThread().id}")
                    it * it
                }
                .collect { // thread main
                    Log.v("ttaylor", "flowOn(${it}) collect thread id=${Thread.currentThread().id}")
                }
        }

        /**
         * case: operator conflate
         */
        GlobalScope.launch {
            flow {
                (1 .. 10).forEach {
                    delay(10)
                    emit(it)
                }
            }.conflate()
                .onEach { delay(20) }
                .collect { Log.v("ttaylor", "conflate + onEach {delay()} ret=${it}") }
        }

        /**
         * case: launchIn(), a shorthand for scope.launch { flow.collect() }
         */
        flow {
            (1 .. 10).forEach {
                delay(10)
                emit(it)
            }
        }.onEach { Log.v("ttaylor", "onEach() + onCompletion() + launchIn() ret=${it}") }
            .onCompletion { if (it == null) Log.v("ttaylor", "onEach() + onCompletion() + launchIn() completion= successful") }
            .launchIn(mainScope)
    }
}

/**
 * case: customize operator of Flow
 */
fun <T, R> Flow<T>.filterMap(predicate: (T) -> Boolean, transform: suspend (T) -> R): Flow<R> = transform { value ->
    if (predicate(value)) emit(transform(value))
}


fun View.clicks() = callbackFlow {
    setOnClickListener { offer(Unit) }
    awaitClose { setOnClickListener(null) }
}

/**
 * take the first data in every [windowDuration] and drop
 */
fun <T> Flow<T>.throttleFirst(windowDuration: Long): Flow<T> = flow {
    var lastEmissionTime = 0L
    collect { upstream ->
        val currentTime = System.currentTimeMillis()
        val mayEmit = currentTime - lastEmissionTime > windowDuration
        if (mayEmit)
        {
            lastEmissionTime = currentTime
            emit(upstream)
        }
    }
}