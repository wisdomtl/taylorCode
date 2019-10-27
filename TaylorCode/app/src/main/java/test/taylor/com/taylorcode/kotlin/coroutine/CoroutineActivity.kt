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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        runParallel()
//        Log.e("ttaylor", "tag=parallel, CoroutineActivity.onCreate()  after [runParallel]")
//        runParallelByLaunch()
//        Log.e("ttaylor", "tag=parallel, CoroutineActivity.onCreate()  after [runParallelByLaunch]")
        waitDeferred()
        Log.e("ttaylor", "tag=, CoroutineActivity.onCreate() after [waitDeferred]")
        oneRequest()
        twoRequest()
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

    private fun oneRequest() = GlobalScope.launch {
        val ret = "abc"
        oneDeferred = async {
            delay(1000)
            Log.v("ttaylor", "tag=, CoroutineActivity.oneRequest()  one request is done")
            ret
        }
    }

    private fun twoRequest() = GlobalScope.launch {
        val ret = "efg"
        twoDeferred = async {
            delay(3000)
            Log.v("ttaylor", "tag=, CoroutineActivity.twoRequest()  two request is done")
            ret
        }
    }

    private fun waitDeferred() = GlobalScope.launch(Dispatchers.Main) {
        //        Log.e("ttaylor", "tag=multiple ending, CoroutineActivity.waitDeferred()  user1=${user1?.await()},user2=${user2?.await()}")
        Log.e("ttaylor", "tag=fake async, CoroutineActivity.waitDeferred() oneDeferred=${oneDeferred?.await()} ,twoDeferrd=${twoDeferred?.await()}")
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