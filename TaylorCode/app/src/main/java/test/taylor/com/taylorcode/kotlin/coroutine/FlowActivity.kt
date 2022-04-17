package test.taylor.com.taylorcode.kotlin.coroutine

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import test.taylor.com.taylorcode.kotlin.ConstraintLayout

import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.kotlin.extension.clickFlow
import test.taylor.com.taylorcode.kotlin.extension.textChangeFlow
import test.taylor.com.taylorcode.kotlin.extension.throttleFirst

class FlowActivity : AppCompatActivity() {
    var count = 0
    var flow: Flow<Int>? = null
    var job: Job? = null
    val mainScope = MainScope()

    private var nameCount = 0

    private lateinit var tv: TextView
    private val flowViewModel by lazy {
        ViewModelProvider(this)[FlowViewModel::class.java]
    }

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

            TextView {
                layout_id = "tvChange2"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 12f
                textColor = "#ffffff"
                text = "save"
                gravity = gravity_center
                top_toBottomOf = "tvChange"
                start_toStartOf = parent_id
                end_toEndOf = parent_id
                onClick = {
                    startActivity(Intent(this@FlowActivity, CoroutineActivity::class.java))
                }
                padding = 20
                shape = shape {
                    corner_radius = 20
                    solid_color = "#ff00ff"
                }
            }

            EditText {
                layout_id = "etContent"
                layout_width = match_parent
                layout_height = 50
                top_toTopOf = parent_id
                start_toStartOf = parent_id
                /**
                 *  case: debounce on EditText input
                 */
                this.textChangeFlow()
                    .filter { it.isNotEmpty() }
                    .debounce(300)
                    .flatMapLatest { searchFlow(it.toString()) }
                    .flowOn(Dispatchers.IO)
                    .onEach { updateUi(it) }
                    .launchIn(mainScope)
            }
        }
    }

    private fun updateUi(it: List<String>) {

    }

    private suspend fun search(key: String): List<String> {
        delay(200)
        return listOf("a", "b")
    }

    private fun searchFlow(key: String) = flow {
        emit(search(key))
    }

    private val coldFlow = flow {
        var count = 0
        repeat(100) {
            delay(1000)
            Log.w("ttaylor4", " cold flow emiting ${count}")
            emit("cold flow1(${count++})")
        }
    }

    private val coldFlow2 = flow {
        var count = 0
        repeat(100) {
            delay(1000)
            Log.w("ttaylor4", "cold flow2 emiting ${count}")
            emit("cold flow 2${count++}")
        }
    }

    override fun onStop() {
        super.onStop()
        Log.v("ttaylor", "onPause() lifecycle=${lifecycle.currentState}")
    }

    override fun onPause() {
        super.onPause()
        Log.v("ttaylor", "onPause() lifecycle=${lifecycle.currentState}")
    }


    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)

        /**
         * case: kotlin flow zip, combine the first value of flow1 with the first value of flow2, and so on
         * if flow2's value is more than flow1,than the remain value wont be emitted
         */
        val flowQ = (1..10).asFlow().onEach { delay(1000) }
        val flowW = listOf("A", "B", "C").asFlow().onEach { delay(1000) }
        lifecycleScope.launch {
            flowQ.zip(flowW) { int, string -> "$int$string" }
                .flowOn(Dispatchers.IO)
                .collect {
                    Log.v("ttaylor", "[zip] ret=$it")// 1A,2B,3C
                }
        }

        /**
         * case: takeUntil operator of Kotlin Flow
         * abort another flow when sth happened in current flow
         */
        val flowA = flow {
            repeat(30) {
                emit(it)
                Log.v("ttaylor", "[transformWhile] flowA emit ${it}")
                delay(1000)
            }
        }
        val flowB = flow {
            repeat(20) {
                emit(it * it)
                Log.v("ttaylor", "[transformWhile] flowB emit ${it * it}")
                delay(1000)
            }
        }
        lifecycleScope.launch {
            flowOf(flowA, flowB)
                .flattenMerge()
                .flowOn(Dispatchers.IO)
                .transformWhile {
                    emit(it)
                    it != 4
                }.collect {
                    Log.v("ttaylor", "[transformWhile] collect ${it}")
                }

        }


        /**
         * case: keep the upstream alive for 5000 ms when ui is not alive
         */
//        lifecycleScope.launch {
//            flowViewModel.hotStateFlow.flowWithLifecycle(lifecycle).collect {
//                Log.v("ttaylor4", "onCreate() num=${it}")
//            }
//        }

        /**
         * case: even if asLiveData is used ,the upstream cold flow will fire again when this activity is restart
         */
//        flowViewModel.flowLiveData.observe(this){
//            Log.v("ttaylor4","onCreate() num=$it")
//        }

        /**
         * case: multiple consumer for hot flow, the upstream cold flow will only be triggered once
         */
//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                flowViewModel.hotFlow.collect {
//                    Log.v("ttaylor4", "onCreate() collect 1 num=${it}")
//                }
//
//            }
//        }
//
//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED){
//                flowViewModel.hotFlow.collect{
//                    Log.v("ttaylor4","onCreate() collect 2 num=${it}")
//                }
//            }
//        }

        /**
         * case: the collect and emit will be stopped when another activity show and this activity go to the background
         * and the collect and emit will restart everytime this activity is show again
         */
//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED){
//                coldFlow.collect{
//                    Log.v("ttaylor4","onCreate() collect num=$it")
//                }
//            }
//        }

        /**
         * case: the collect and emit will stopped only when this activity is destroyed
         */
//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.CREATED){
//                coldFlow.collect{
//                    Log.v("ttaylor4","onCreate() collect num=$it")
//                }
//            }
//        }

        /**
         * case: the collect and emit will paused if another activity is show above
         * the collect and emit wont stop if home is click(app is in background)
         */
//        lifecycleScope.launch {
//           coldFlow.collect{
//               Log.v("ttaylor4","onCreate() collect num=${it}")
//           }
//        }

        /**
         * case: the collect and emit will stop when app is in background or onPause by another activity.and continue when this activity is restart
         */
//        lifecycleScope.launchWhenStarted {
//            coldFlow.collect{
//                Log.v("ttaylor4","cold flow collect num=$it")
//            }
//        }

        /**
         * case: flowWithLifecycle will cancel all upstream emitting value when lifecycle state is below RESUMED
         */
//        lifecycleScope.launch {
//            coldFlow.combine(coldFlow2) { v1, v2 ->
//                v1 + v2
//            }.flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED).collect {
//                Log.v("ttaylor4", "combine two flow =${it}")
//            }
//        }


        /**
         * case: flowWithLifecycle will cancel all upstream emitting value when lifecycle state is below RESUMED exclude downstream
         */
//        lifecycleScope.launch {
//            coldFlow.flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED)
//                .combine(coldFlow2) { v1, v2 ->
//                    v1 + v2
//                }.collect {
//                    Log.v("ttaylor4", "combine two flow =${it}")
//                }
//        }

        /**
         * case: collect cold flow multiple times, the emit logic will invoked multiple times
         *
         * case: if this activity finished, the consumer and producer will stop their work
         */
//        lifecycleScope.launch {
//            coldFlow.collect {
//                Log.v("ttaylor5", "cold flow it=${it}")
//            }
//
//        }
//        lifecycleScope.launch {
//            coldFlow.collect {
//                Log.v("ttaylor5","cold flow collect twice it=${it}")
//            }
//        }
//
        /**
         * case: cold flow is collected in CoroutineScope ,the producer and consumer wont stop even if activity finished
         */
//        CoroutineScope(SupervisorJob()).launch {
//            coldFlow.collect {
//                Log.v("ttaylor", "cold flow2 collect in CoroutineScope it=${it}")
//            }
//        }


        /**
         * case : throttle first of click
         */
        tv.clickFlow().throttleFirst(1000).onEach {
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
                (1..3).forEach {
                    delay(1000)
                    emit(it)
                }
            }.collect {
                // define the logic invoked when emit() invoked
                Log.v("ttaylor", "print() num=${it}")
            }
        }

        /**
         * flowOn() to switch emit data thread
         */
        GlobalScope.launch(Dispatchers.Main) {
            flow { // thread io
                (1..5).forEach {
                    Log.v("ttaylor", "flowOn(${it}) emit thread id=${Thread.currentThread().id}")
                    emit(it)
                }
            }.map {// thread io
                Log.v("ttaylor", "flowOn(${it}) map thread id=${Thread.currentThread().id}")
                it * it
            }.flowOn(Dispatchers.IO)
                .map { // thread main
                    Log.v(
                        "ttaylor",
                        "flowOn(${it}) map after flowOn thread id=${Thread.currentThread().id}"
                    )
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
                (1..10).forEach {
                    delay(10)
                    emit(it)
                }
            }.conflate()
                .onEach { delay(20) }
                .collect { Log.v("ttaylor", "conflate + onEach {delay()} ret=${it}") }
        }

        /**
         * case: launchIn() + onEach() + onCompletion(), a shorthand for scope.launch { flow.collect() }
         */
        flow {
            (1..10).forEach {
                delay(10)
                emit(it)
            }
        }
            .onEach { Log.v("ttaylor", "onEach() + onCompletion() + launchIn() ret=${it}") }
            .onCompletion {
                if (it == null) Log.v(
                    "ttaylor",
                    "onEach() + onCompletion() + launchIn() completion= successful"
                )
            }
            .launchIn(mainScope)

        /**
         * case: onCompletion is invoked after collect logic
         */
        lifecycleScope.launch {
            flow {
                emit(1)
            }
                .onCompletion { Log.v("ttaylor", "onCompletion") }
                .collect {
                    Log.v("ttaylor", "onCompletion collect()")
                }
        }

        /**
         * case: the onCompletion of each flow will be invoked when they are done
         * the final completion will be invoked after all the things done
         */
        lifecycleScope.launch {
            val flow1 = flow {
                emit(1)
            }.onCompletion { Log.v("ttaylor", "[completions] flow1 complete") }

            val flow2 = flow {
                delay(1000)
                emit(2)
            }.onCompletion { Log.v("ttaylor", "[completions] flow2 complete") }
            flowOf(flow1, flow2).flattenMerge()
                .onCompletion { Log.v("ttaylor", "[completions]final completion of 2 flows") }
                .collect {
                    Log.v("ttaylor", "[completions] collect")
                }
        }

        /**
         * case: flatMapConcat(), turn one emitted value into several values as Flow, and concat all values as sequence into one flow
         */
        val names = listOf(
            "taylor",
            "evian",
            "linda"
        )

        GlobalScope.launch {
            names.asFlow()
                .flatMapConcat { name -> flow { getPhoneNumber(name).forEach { emit(it) } } }
                .filter { number -> number.startsWith("1350") }
                .catch { }
                .collect {
                    Log.v(
                        "ttaylor",
                        "1350 numbers = $it thread id=${Thread.currentThread().id}"
                    )
                }
        }

        // do this in non-flow way, logs will print together, it means
        GlobalScope.launch {
            val ret = mutableListOf<String>()
            names.forEach { name ->
                ret.addAll(getPhoneNumber(name).filter { number -> number.startsWith("1350") })
            }
            ret.forEach { Log.v("ttaylor", "1350 numbers in non-flow way=$it") }
        }

        /**
         * case: sample(duration) which will take the last emitter in the time window of duration
         */
        generateUserId().filter { !it.isInShadow }
            .sample(1000)
            .onEach { Log.v("ttaylor", "user getting in room is ${it.id}") }
            .launchIn(mainScope)

        /**
         * case: flow countdown on background and collect on main thread
         */
        mainScope.launch {
            val ret = countdown2(10_000, 1_000) { io(it) }
                .onStart { Log.w("ttaylor", "on countdown2 start----------") }
                .onEach {
                    Log.v(
                        "ttaylor",
                        "on countdown2 ${it} thread id = ${Thread.currentThread().id}"
                    )
                }
                .onStart { Log.v("ttaylor", "on countdown2 start again") }
                .onCompletion { Log.w("ttaylor", "on countdown2 end--------") }
                .reduce { acc, value -> acc + value }
            Log.e("ttaylor", "countdown2 acc ret = $ret")
        }

        /**
         * case: conflate() : emitter never stop due to a slow collector, but the collector always get the recent emitter value
         */
        mainScope.launch {
            flow {
                (1..10).forEach {
                    delay(100)
                    emit(it)
                }
            }.conflate().collect {
                delay(200)
                Log.v("ttaylor", "conflate = $it")
            }
        }
    }

    private suspend fun io(time: Long): Long {
        Log.v(
            "ttaylor",
            "countdown2 io task($time) is started thread id=${Thread.currentThread().id}"
        )
        delay(3_000)
        Log.v("ttaylor", "countdown2 io task($time) is end thread id=${Thread.currentThread().id}")
        return time
    }

    private fun generateUserId() = flow {
        (1..100).forEach {
            delay(300)
            emit(User(it, it.rem(2) != 0))
        }
    }

    data class User(val id: Int, val isInShadow: Boolean = false)


    suspend fun getPhoneNumber(name: String): List<String> {
        delay(1000) // as if it is return from server
        Log.v("ttaylor", "getPhoneNumber() thread id=${Thread.currentThread().id}")
        return when (name) {
            "taylor" -> listOf("13508963547", "1409875645", "12298475439")
            "evian" -> listOf("13508376547", "18932749283", "12983783927")
            "linda" -> listOf("135038746388", "1898321732984", "13988475439")
            else -> emptyList()
        }
    }
}

/**
 * case: customize operator of Flow
 */
fun <T, R> Flow<T>.filterMap(predicate: (T) -> Boolean, transform: suspend (T) -> R): Flow<R> =
    transform { value ->
        if (predicate(value)) emit(transform(value))
    }


/**
 * case: ui show collect flow in this way
 * it will collect when lifecycle state is above [minActiveState] and stop when state is below
 */
fun <T> Flow<T>.collectIn(
    lifecycleOwner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    action: (T) -> Unit
): Job = lifecycleOwner.lifecycleScope.launch {
    flowWithLifecycle(lifecycleOwner.lifecycle, minActiveState).collect(action)
}
