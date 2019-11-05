package test.taylor.com.taylorcode.kotlin.coroutine

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

class CoroutineActivity : AppCompatActivity() {
    private var user1: Deferred<String>? = null
    private var user2: Deferred<String>? = null
    private var oneDeferred: Deferred<String>? = null
    private var twoDeferred: Deferred<String>? = null
    private var completableDeferred1: CompletableDeferred<String> = CompletableDeferred()
    private var completableDeferred2: CompletableDeferred<String> = CompletableDeferred()
    private var job: Job? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        runParallel()
//        Log.e("ttaylor", "tag=parallel, CoroutineActivity.onCreate()  after [runParallel]")
//        runParallelByLaunch()
//        Log.e("ttaylor", "tag=parallel, CoroutineActivity.onCreate()  after [runParallelByLaunch]")
//        waitDeferred()
        Log.e("ttaylor", "tag=, CoroutineActivity.onCreate() after [waitDeferred]")
        waitCompletableDeferred()
        Log.v("ttaylor", "tag=, CoroutineActivity.onCreate()  after [waitCompletableDeferred]")
        //this is not work for observing two task ending
//        window.decorView.postDelayed({
//            oneRequest()
//            twoRequest()
//        }, 4000)


        //this works for observing two task ending
        window.decorView.postDelayed({
            completeDeferred2()
        }, 1000)

        window.decorView.postDelayed({
            completeDeferred1()
        }, 3000)


        //another way to observing two task end
        joinJob()
        createJob()


        //cancel a coroutine  in a wrong way
        val job = cancelCoroutineInWrongWay()
        Log.e("ttaylor", "tag=cancelable, CoroutineActivity.onCreate()  before cancel")
        job.cancel()
        Log.e("ttaylor", "tag=cancelable, CoroutineActivity.onCreate() after cancel")


        //cancel a coroutine in right way
        val job2 = cancelCoroutine()
        Log.e("ttaylor", "tag=cancel2, CoroutineActivity.onCreate()  before cancel")
        window.decorView.postDelayed({
            job2.cancel()
            Log.e("ttaylor", "tag=cancel2, CoroutineActivity.onCreate() after cancel")
        }, 500)

        //timeoutByNull and cancel
//        timeoutByNull()
        timeoutByException()
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

    fun cancelCoroutineInWrongWay() = GlobalScope.launch {
        repeat(100) { i ->
            Log.v("ttaylor", "tag=cancelable, CoroutineActivity.cancelCoroutineInWrongWay()  count times=${i}")
        }
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
                    Log.v("ttaylor", "tag=cancel2, CoroutineActivity.cancelCoroutine()  time = ${i}")
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
                Log.e("ttaylor","tag=timeout2, CoroutineActivity.timeoutByException()  ")
            }
        }
        Log.v("ttaylor", "tag=timeout2, CoroutineActivity.timeoutByNull()  ret=${ret}")
    }
    /**
     * case1:
     * runBlocking will block the current thread
     * runBlocking will create a main coroutine
     * async will create a sub coroutine
     */
    private fun runParallel() = runBlocking {
        val user1 = async { queryUser("taylor", 1000) }
        val user2 = async { queryUser("titi", 5000) }


        /**
         * case2: Deferred.await will block the current coroutine
         */
        Log.v("ttaylor", "tag=parallel, CoroutineActivity.runParallel()  user1=${user1.await()},user2=${user2.await()}")
        Log.e("ttaylor", "tag=parallel, CoroutineActivity.runParallel() after [Deferred.await]")
    }

    /**
     * case: GlobalScope.launch() will not block the current thread
     */
    private fun runParallelByLaunch() = GlobalScope.launch {
        val ret = "abc"
        user1 = async { queryUser("taylor", 1000) }
        user2 = async { queryUser("titi", 5000) }

        Log.v("ttaylor", "tag=parallel, CoroutineActivity.runParallelByLaunch()  user1=${user1?.await()},user2=${user2?.await()}")
        Log.e("ttaylor", "tag=parallel, CoroutineActivity.runParallelByLaunch() after [Deferred.await]")
    }


    /**
     * mock one server return
     */
    private fun oneRequest() = GlobalScope.launch {
        val ret = "abc"
        //wrap server return in a [Deferred]
        oneDeferred = async {
            delay(1000)
            Log.v("ttaylor", "tag=, CoroutineActivity.oneRequest()  one request is done")
            ret
        }

    }


    private fun completeDeferred1() {
        val ret = "abc"
        //wrap server return in CompletableDeferred
        val result = completableDeferred1.complete(ret)
        Log.v("ttaylor", "tag=completableDeferred1, CoroutineActivity.completeDeferred1()  result=${result}")

    }

    /**
     * mock one server return
     */
    private fun twoRequest() = GlobalScope.launch {
        val ret = "efg"
        //wrap server return in a [Deferred]
        twoDeferred = async {
            delay(3000)
            Log.v("ttaylor", "tag=, CoroutineActivity.twoRequest()  two request is done")
            ret
        }
    }

    private fun completeDeferred2() {
        val ret = "efg"
        //wrap server return in CompletableDeferred
        val result = completableDeferred2.complete(ret)
        Log.v("ttaylor", "tag=completableDeferred2, CoroutineActivity.completeDeferred2()  result=${result}")
    }

    private fun waitDeferred() = GlobalScope.launch(Dispatchers.Main) {
        Log.e("ttaylor", "tag=multiple ending, CoroutineActivity.waitDeferred()  user1=${user1?.await()},user2=${user2?.await()}")
//        Log.e("ttaylor", "tag=fake async, CoroutineActivity.waitDeferred() oneDeferred=${oneDeferred?.await()} ,twoDeferrd=${twoDeferred?.await()}")
    }

    private fun waitCompletableDeferred() = GlobalScope.launch(Dispatchers.Main) {

        completableDeferred1.await()
        completableDeferred2.await()
        Log.v("ttaylor", "tag=completable deferred, CoroutineActivity.waitDeferred()  1=${completableDeferred1.await()},2=${completableDeferred2.await()}")
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