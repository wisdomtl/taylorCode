package test.taylor.com.taylorcode.kotlin.coroutine

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.util.Timer
import kotlin.coroutines.resume

class CoroutineActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    private var user1: Deferred<String>? = null
    private var user2: Deferred<String>? = null
    private var completableDeferred1: CompletableDeferred<String> = CompletableDeferred()
    private var completableDeferred2: CompletableDeferred<String> = CompletableDeferred()
    private var job: Job? = null
    private val mainScope = MainScope()

    private val TAG = "CoroutineActivity"

    private lateinit var tvCountdown: TextView

//    private val mainScope = MainScope()

    private val rootView by lazy {
        LinearLayout {
            layout_width = match_parent
            layout_height = match_parent
            orientation = vertical

            tvCountdown = TextView {
                layout_width = match_parent
                layout_height = wrap_content
                textSize = 15f
            }

            Button {
                layout_width = match_parent
                layout_height = wrap_content
                textSize = 15f
                text = "start long run coroutine by MainScope"
                textAllCaps = false
                onClick = startLongRunCoroutineByMainScope
            }

            Button {
                layout_width = match_parent
                layout_height = wrap_content
                textSize = 15f
                text = "start coroutine by MainScope"
                textAllCaps = false
                onClick = startCoroutineByMainScope
            }

            Button {
                layout_width = match_parent
                layout_height = wrap_content
                textSize = 15f
                text = "start coroutine in background"
                textAllCaps = false
                onClick = startCoroutineInBackground
            }

            Button {
                layout_width = match_parent
                layout_height = wrap_content
                textSize = 15f
                text = "start coroutine by default"
                textAllCaps = false
                onClick = startCoroutineByDefault
            }

            Button {
                layout_width = match_parent
                layout_height = wrap_content
                textSize = 15f
                text = "start coroutine in main thread"
                textAllCaps = false
                onClick = startCoroutineInMainThread
            }

            Button {
                layout_width = match_parent
                layout_height = wrap_content
                textSize = 15f
                text = "cancel coroutine"
                textAllCaps = false
                onClick = doCancelCoroutine
            }

            Button {
                layout_width = match_parent
                layout_height = wrap_content
                textSize = 15f
                textAllCaps = false
                text = "the end of two async task"
                onClick = twoTaskEnd
            }

            Button {
                layout_width = match_parent
                layout_height = wrap_content
                textSize = 15f
                textAllCaps = false
                text = "timeout"
                onClick = onTimeout
            }

            Button {
                layout_width = match_parent
                layout_height = wrap_content
                textSize = 15f
                textAllCaps = false
                text = "run task parallel"
                onClick = runTaskParallel
            }

            Button {
                layout_width = match_parent
                layout_height = wrap_content
                textSize = 15f
                textAllCaps = false
                text = "run task parallel by launch"
                onClick = runTaskParallelByLaunch
            }

            Button {
                layout_width = match_parent
                layout_height = wrap_content
                textSize = 15f
                textAllCaps = false
                text = "long run task(activity not destroy)"
                onClick = startLongRunTask
            }

            Button {
                layout_width = match_parent
                layout_height = wrap_content
                textSize = 15f
                textAllCaps = false
                text = "join"
                onClick = join
            }

            Button {
                layout_width = match_parent
                layout_height = wrap_content
                textSize = 15f
                textAllCaps = false
                text = "suspend coroutine"
                onClick = suspendCoroutine
            }
        }
    }

    /**
     * case: start coroutine by main scope
     */
    private val startLongRunCoroutineByMainScope = { _: View ->
        Log.d(TAG, "main thread id =${Thread.currentThread().id} ")
        launch {
            longRunTask()
        }
        Log.d(TAG, "the code after coroutine ")
        Unit
    }

    /**
     * case: start coroutine by main scope
     */
    private val startCoroutineByMainScope = { _: View ->
        Log.d(TAG, "main thread id =${Thread.currentThread().id} ")
        launch {
            Log.d(TAG, "start coroutine in thread (${Thread.currentThread().id}) start")
            logDelay(3000)
            Log.d(TAG, "start coroutine in thread (${Thread.currentThread().id}) end ")
        }
        Log.d(TAG, "the code after coroutine ")
        Unit
    }

    /**
     * case: run part coroutine code before suspend function in main thread without block it. after the coroutine resume, the remain code is dispatched to another thread
     */
    @ExperimentalCoroutinesApi
    private val startCoroutineInMainThread = { _: View ->
        Log.d(TAG, "main thread id =${Thread.currentThread().id} ")
        GlobalScope.launch(start = CoroutineStart.UNDISPATCHED) {
            Log.d(TAG, "start coroutine in thread (${Thread.currentThread().id}) start")
            logDelay(3000)
            Log.d(TAG, "start coroutine in thread (${Thread.currentThread().id}) end ")
        }
        Log.d(TAG, "the code after coroutine ")
        Unit
    }


    /**
     * case: run whole coroutine code in another thread
     */
    @ExperimentalCoroutinesApi
    private val startCoroutineByDefault = { _: View ->
        Log.d(TAG, "main thread id =${Thread.currentThread().id} ")
        GlobalScope.launch(start = CoroutineStart.DEFAULT) {
            Log.d(TAG, "start coroutine in thread (${Thread.currentThread().id}) start")
            logDelay(3000)
            Log.d(TAG, "start coroutine in thread (${Thread.currentThread().id}) end ")
        }
        Log.d(TAG, "the code after coroutine ")
        Unit
    }

    /**
     * case: run time-consuming code in background thread and update ui in main thread
     */
    @ExperimentalCoroutinesApi
    private val startCoroutineInBackground = { _: View ->
        Log.d(TAG, "main thread id =${Thread.currentThread().id} ")
        Thread {
            Log.d(TAG, "new thread id = ${Thread.currentThread().id} ")
            GlobalScope.launch(context = Dispatchers.Main, start = CoroutineStart.UNDISPATCHED) {
                Log.d(TAG, "start coroutine in thread (${Thread.currentThread().id}) start")
                logDelay(3000)
                Log.d(TAG, "start coroutine in thread (${Thread.currentThread().id}) end ")
            }
        }.start()
        Unit
    }

    private suspend fun logDelay(duration: Long) {
        Log.d(TAG, "delay: suspend fun thread id=${Thread.currentThread().id}")
        delay(duration)
    }


    /**
     * case1: cancel a coroutine in right way
     */
    private val doCancelCoroutine = { _: View ->
        val job2 = cancelCoroutine()
        Log.e("ttaylor", "tag=cancel2, CoroutineActivity.onCreate()  before cancel")
        window.decorView.postDelayed({
            job2.cancel()
            Log.e("ttaylor", "tag=cancel2, CoroutineActivity.onCreate() after cancel")
        }, 500)
        Unit
    }

    /**
     * case2: waitting for the ending of two async task by CompletableDeferred
     */
    private val twoTaskEnd = { _: View ->
        waitCompletableDeferred()
        Log.v("ttaylor", "tag=, CoroutineActivity.onCreate()  after [waitCompletableDeferred]")
        //this works for observing two task ending
        window.decorView.postDelayed({
            completeDeferred2()
        }, 1000)

        window.decorView.postDelayed({
            completeDeferred1()
        }, 3000)
        Unit
    }

    /**
     * case3: timeout
     */
    private val onTimeout = { _: View ->
        //        timeoutByNull()
        timeoutByException()
        Log.v(
                "ttaylor",
                "tag=timeout2, CoroutineActivity.onCreate()  after timeoutByException()"
        )
        Unit
    }

    /**
     * case4:8
     * runBlocking will block the current thread
     * runBlocking will create a main coroutine
     * async will create a sub coroutine
     */
    private val runTaskParallel = { _: View ->
        runParallel()
        Log.e("ttaylor", "tag=parallel, CoroutineActivity.onCreate()  after [runParallel]")
        Unit
    }


    private val runTaskParallelByLaunch = { _: View ->
        runParallelByLaunch()
        Log.e(
                "ttaylor",
                "tag=parallel, CoroutineActivity.onCreate()  after [runParallelByLaunch]"
        )
        Unit
    }

    /**
     * case6: join
     */
    private val join = { _: View ->
        //another way to observing two task end
        joinJob()
        createJob()
        Unit
    }

    /**
     * case7: memory leak of coroutine
     */
    private val startLongRunTask = { _: View ->
        longRunTaskAfterActivityFinished()
        Unit
    }

    /**
     * case8: suspend coroutine's memory leak
     */
    private val suspendCoroutine = { _: View ->
        suspendCoroutine()
        Unit
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(rootView)

        /**
         * case: coroutine body will be execute in main thread
         */
        mainScope.launch {
            Log.v("ttaylor", "tag=asdf EmptyCoroutineContext,  before suspend fun thread id=${Thread.currentThread().id}")
            queryUser("EmptyCoroutineContext")
            Log.d("ttaylor", "tag=asdf EmptyCoroutineContext,  after suspend fun thread thread id=${Thread.currentThread().id}")
        }

        /**
         * case: coroutine body will be execute in another thread
         */
        mainScope.launch(Dispatchers.IO) {
            Log.v("ttaylor", "tag=asdf Dispatchers.IO, CoroutineActivity.onCreate()  before suspend fun  thread id=${Thread.currentThread().id}")
            queryUser("Dispatchers.IO")
            Log.d("ttaylor", "tag=asdf Dispatchers.IO, CoroutineActivity.onCreate() after suspend fun thread id=${Thread.currentThread().id}")
        }

        /**
         * case: coroutine body will be execute in main thread
         */
        mainScope.launch(Dispatchers.Main) {
            Log.v("ttaylor", "tag=asdf Dispatchers.Main, CoroutineActivity.onCreate()  before suspend fun  thread id=${Thread.currentThread().id}")
            queryUser("Dispatchers.Main")
            Log.d("ttaylor", "tag=asdf Dispatchers.Main, CoroutineActivity.onCreate() after suspend fun thread id=${Thread.currentThread().id}")
        }

        /**
         * case: coroutine body will be execute in another thread
         */
        mainScope.launch(Dispatchers.Default) {
            Log.v("ttaylor", "tag=asdf Dispatchers.Default, CoroutineActivity.onCreate()  before suspend fun  thread id=${Thread.currentThread().id}")
            queryUser("Dispatchers.Default")
            Log.d("ttaylor", "tag=asdf Dispatchers.Default, CoroutineActivity.onCreate() after suspend fun thread id=${Thread.currentThread().id}")
        }

        /**
         * case: coroutine body will be execute in another thread
         */
        mainScope.launch(Dispatchers.Unconfined) {
            Log.v("ttaylor", "tag=asdf Dispatchers.Unconfined, CoroutineActivity.onCreate()  before suspend fun  thread id=${Thread.currentThread().id}")
            queryUser("Dispatchers.Unconfined")
            Log.d("ttaylor", "tag=asdf Dispatchers.Unconfined, CoroutineActivity.onCreate() after suspend fun thread id=${Thread.currentThread().id}")
        }

        /**
         * case: coroutine body will be execute in current thread(main thread)
         */
        mainScope.launch(context = Dispatchers.IO, start = CoroutineStart.UNDISPATCHED) {
            Log.v("ttaylor", "tag=asdf CoroutineStart.UNDISPATCHED, CoroutineActivity.onCreate()  before suspend fun  thread id=${Thread.currentThread().id}")
            queryUser("CoroutineStart.UNDISPATCHED")
            Log.d("ttaylor", "tag=asdf CoroutineStart.UNDISPATCHED, CoroutineActivity.onCreate() after suspend fun thread id=${Thread.currentThread().id}")
        }
    }

    fun suspendCoroutine() = GlobalScope.launch {
        Log.v("ttaylor", "tag=suspend coroutine, CoroutineActivity.suspendCoroutine() 0 ,thread id=${Thread.currentThread().id}")
        kotlin.coroutines.suspendCoroutine<String> { continuation ->
            Log.v("ttaylor", "tag=suspend coroutine, CoroutineActivity.suspendCoroutine()  1,thread id=${Thread.currentThread().id}")
            Timer(Timer.TimerListener { pastMillisecond ->
                //visiting the outer class's fun cause memory leak
                Log.w("ttaylor", "tag=suspend coroutine, CoroutineActivity.suspendCoroutine()  ${inttt()}  ${pastMillisecond} past")
                if (pastMillisecond == 100000L) continuation.resume("done")

            }).apply { start(0, 1000) }
            Log.v("ttaylor", "tag=suspend coroutine, CoroutineActivity.suspendCoroutine()  2,thread id=${Thread.currentThread().id}")
        }
        Log.v("ttaylor", "tag=suspend coroutine, CoroutineActivity.suspendCoroutine()  3,thread id=${Thread.currentThread().id}")

    }

    fun inttt() {

    }

    fun longRunTaskAfterActivityFinished() = GlobalScope.launch(Dispatchers.IO) {
        longRunTask()
    }

    private suspend fun longRunTask() {
        repeat(10000) { times ->
            delay(1000)
            Log.d(TAG, "longRunTask: times = ${times}")
            tvCountdown.text = times.toString()
        }
    }

    fun createJob() {
        job = GlobalScope.launch {
            logDelay(1100)
            Log.v("ttaylor", "tag=join, CoroutineActivity.join()  job is done")
        }
        Log.v("ttaylor", "tag=join, CoroutineActivity.join()  after launch")
    }

    fun joinJob() = GlobalScope.launch {
        job?.join()
        Log.v("ttaylor", "tag=join, CoroutineActivity.joinJob()  after join")
    }


    /**
     * case: cancel a job
     */
    fun cancelCoroutine() = GlobalScope.launch {
        try {
            repeat(100) { i ->
                logDelay(100)
                //isActive is the key point for canceling a job
                if (isActive) {
                    Log.v(
                            "ttaylor",
                            "tag=cancel2, CoroutineActivity.cancelCoroutine()  time = ${i}"
                    )
                } else {
                    Log.d("ttaylor", "tag=cancel2, CoroutineActivity.cancelCoroutine()  time=${i}")
                }
            }
        } finally {
//            delay(9900)//this suspend function wont work due to CancellationException throw, and the coroutine is canceled
            Log.e("ttaylor", "tag=cancel2, CoroutineActivity.cancelCoroutine() be canceled")
            //we could use this way to
            withContext(NonCancellable) {
                logDelay(5000)
                Log.e("ttaylor", "tag=cancel2, CoroutineActivity.cancelCoroutine()  OY")
            }
        }
    }

    /**
     * launch a coroutine with timeout and return null when time is up
     */
    fun timeoutByNull() = GlobalScope.launch {
        val ret = withTimeoutOrNull(2000) {
            repeat(100) { i ->
                logDelay(200)
                Log.v("ttaylor", "tag=timeout, CoroutineActivity.timeoutByNull()  i = ${i}")
            }
            "done"
        }
        Log.v("ttaylor", "tag=timeout, CoroutineActivity.timeoutByNull()  ret=${ret}")
    }

    /**
     * launch a coroutine with timeout and exception
     */
    fun timeoutByException() = GlobalScope.launch {
        val ret = withTimeout(2000) {
            try {
                repeat(100) { i ->
                    logDelay(200)
                    Log.v("ttaylor", "tag=timeout2, CoroutineActivity.timeoutByNull()  i = ${i}")
                }
                "done"
            } finally {
                Log.e("ttaylor", "tag=timeout2, CoroutineActivity.timeoutByException()  ")
            }
        }
        Log.v("ttaylor", "tag=timeout2, CoroutineActivity.timeoutByNull()  ret=${ret}")
    }


    private fun runParallel() = runBlocking {
        val user1 = async { queryUser("taylor", 1000) }
        val user2 = async { queryUser("titi", 5000) }


        /**
         * case2: Deferred.await will block the current coroutine
         */
        Log.v(
                "ttaylor",
                "tag=parallel, CoroutineActivity.runParallel()  user1=${user1.await()},user2=${user2.await()}"
        )
        Log.e("ttaylor", "tag=parallel, CoroutineActivity.runParallel() after [Deferred.await]")
    }

    /**
     * case: GlobalScope.launch() will not block the current thread
     */
    private fun runParallelByLaunch() = GlobalScope.launch {
        val ret = "abc"
        user1 = async { queryUser("taylor", 1000) }
        user2 = async { queryUser("titi", 5000) }

        Log.v(
                "ttaylor",
                "tag=parallel, CoroutineActivity.runParallelByLaunch()  user1=${user1?.await()},user2=${user2?.await()}"
        )
        Log.e(
                "ttaylor",
                "tag=parallel, CoroutineActivity.runParallelByLaunch() after [Deferred.await]"
        )
    }


    private fun completeDeferred1() {
        val ret = "abc"
        //wrap server return in CompletableDeferred
        val result = completableDeferred1.complete(ret)
        Log.v(
                "ttaylor",
                "tag=completableDeferred1, CoroutineActivity.completeDeferred1()  result=${result}"
        )

    }


    private fun completeDeferred2() {
        val ret = "efg"
        //wrap server return in CompletableDeferred
        val result = completableDeferred2.complete(ret)
        Log.v(
                "ttaylor",
                "tag=completableDeferred2, CoroutineActivity.completeDeferred2()  result=${result}"
        )
    }


    private fun waitCompletableDeferred() = GlobalScope.launch(Dispatchers.Main) {

        completableDeferred1.await()
        completableDeferred2.await()
        Log.v(
                "ttaylor",
                "tag=completable deferred, CoroutineActivity.waitDeferred()  1=${completableDeferred1.await()},2=${completableDeferred2.await()}"
        )
    }


    /**
     * case3: suspend fun could only be invoked in coroutine
     */
    private suspend fun queryUser(name: String, time: Long = 500): String {
        Log.w("ttaylor", "tag=parallel, CoroutineActivity.queryUser()  name=${name}, time=${time} thread id=${Thread.currentThread().id}")
        delay(time)
        Log.e("ttaylor", "tag=parallel, CoroutineActivity.queryUser()  name=${name}, time=${time} thread id=${Thread.currentThread().id}")
        return name
    }

    override fun onDestroy() {
        super.onDestroy()
        //if comment this, long run task will be executing event after activity exits
//        cancel(CancellationException("coroutine started by main scope is canceled"))
    }
}