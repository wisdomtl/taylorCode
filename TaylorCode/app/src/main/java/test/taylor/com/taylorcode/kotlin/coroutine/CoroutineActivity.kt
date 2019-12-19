package test.taylor.com.taylorcode.kotlin.coroutine

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import test.taylor.com.taylorcode.R
import test.taylor.com.taylorcode.util.Timer
import kotlin.coroutines.resume

class CoroutineActivity : AppCompatActivity() {
    private var user1: Deferred<String>? = null
    private var user2: Deferred<String>? = null
    private var completableDeferred1: CompletableDeferred<String> = CompletableDeferred()
    private var completableDeferred2: CompletableDeferred<String> = CompletableDeferred()
    private var job: Job? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.coroutine_activity)
        /**
         * case1: cancel a coroutine in right way
         */
        findViewById<Button>(R.id.btnCancel).setOnClickListener {

            val job2 = cancelCoroutine()
            Log.e("ttaylor", "tag=cancel2, CoroutineActivity.onCreate()  before cancel")
            window.decorView.postDelayed({
                job2.cancel()
                Log.e("ttaylor", "tag=cancel2, CoroutineActivity.onCreate() after cancel")
            }, 500)
        }

        /**
         * case2: waitting for the ending of two async task by CompletableDeferred
         */
        findViewById<Button>(R.id.btnTwoAsyncEnd).setOnClickListener {
            waitCompletableDeferred()
            Log.v("ttaylor", "tag=, CoroutineActivity.onCreate()  after [waitCompletableDeferred]")
            //this works for observing two task ending
            window.decorView.postDelayed({
                completeDeferred2()
            }, 1000)

            window.decorView.postDelayed({
                completeDeferred1()
            }, 3000)
        }

        /**
         * case3: timeout
         */
        findViewById<Button>(R.id.btnTimeout).setOnClickListener {
            //        timeoutByNull()
            timeoutByException()
            Log.v(
                "ttaylor",
                "tag=timeout2, CoroutineActivity.onCreate()  after timeoutByException()"
            )
        }

        /**
         * case4:8
         * runBlocking will block the current thread
         * runBlocking will create a main coroutine
         * async will create a sub coroutine
         */
        findViewById<Button>(R.id.btnRunTaskParallel).setOnClickListener {
            runParallel()
            Log.e("ttaylor", "tag=parallel, CoroutineActivity.onCreate()  after [runParallel]")
        }

        /**
         * case5: create coroutine by launch
         */
        findViewById<Button>(R.id.btnRunParallelByLaunch).setOnClickListener {
            runParallelByLaunch()
            Log.e(
                "ttaylor",
                "tag=parallel, CoroutineActivity.onCreate()  after [runParallelByLaunch]"
            )
        }

        /**
         * case6: join
         */
        findViewById<Button>(R.id.btnJoin).setOnClickListener {
            //another way to observing two task end
            joinJob()
            createJob()
        }

        /**
         * case7: memory leak of coroutine
         */
        findViewById<Button>(R.id.btnLongRunTask).setOnClickListener {
            longRunTaskAfterActivityFinished()
        }

        /**
         * case8: suspend coroutine's memory leak
         */
        findViewById<Button>(R.id.btnSuspendCoroutine).setOnClickListener {
            suspendCoroutine()
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
        repeat(10000) { times ->
            delay(500)
            Log.e("ttaylor", "tag=long run, CoroutineActivity.longRunTaskAfterActivityFinished() times=${times}")
        }
    }

    fun createJob() {
        job = GlobalScope.launch {
            delay(1100)
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
                delay(100)
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
                delay(5000)
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
                delay(200)
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
                    delay(200)
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
    private suspend fun queryUser(name: String, time: Long): String {
        delay(time)
        Log.v("ttaylor", "tag=parallel, CoroutineActivity.queryUser()  name=${name}, time=${time}")
        return name
    }
}