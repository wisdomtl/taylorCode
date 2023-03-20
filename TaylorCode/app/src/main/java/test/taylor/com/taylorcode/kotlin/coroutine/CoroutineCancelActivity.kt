package test.taylor.com.taylorcode.kotlin.coroutine

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.util.Timer
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.reflect.jvm.internal.impl.storage.CancellableSimpleLock

/**
 * CancellationException will only cancel current coroutine and sub coroutine
 * non-CancellationException will cancel current coroutine and sub coroutine and parent coroutine, cancellation will be stop where exception is try-catch
 */
class CoroutineCancelActivity : AppCompatActivity() {

    private val TAG = "CoroutineCancel1"
    private val mainScope = MainScope()

    private var btn: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            LinearLayout {
                layout_width = match_parent
                layout_height = match_parent
                orientation = vertical

                Button {
                    layout_width = match_parent
                    layout_height = wrap_content
                    text = "coroutineScope + sub coroutine cancel exception"
                    onClick = coroutineScope_sub_coroutine_cancel_exception
                }

                Button {
                    layout_width = match_parent
                    layout_height = wrap_content
                    text = "coroutineScope + sub coroutine exception"
                    onClick = coroutineScope_sub_coroutine_exception
                }

                Button {
                    layout_width = match_parent
                    layout_height = wrap_content
                    text = "coroutineScope + sub coroutine exception + try catch"
                    onClick = coroutineScope_sub_coroutine_exception_try_catch
                }
                Button {
                    layout_width = match_parent
                    layout_height = wrap_content
                    text = "coroutineScope + sub coroutine exception +out try catch"
                    onClick = coroutineScope_sub_coroutine_exception_out_try_catch
                }
                Button {
                    layout_width = match_parent
                    layout_height = wrap_content
                    text = "GlobalScope.launch + sub coroutine exception +out try catch"
                    onClick = global_launch_sub_coroutine_exception_try_catch
                }

                Button {
                    layout_width = match_parent
                    layout_height = wrap_content
                    text = "mainScope.launch + sub coroutine exception +out try catch"
                    onClick = main_scope_sub_coroutine_exception_out_try_catch
                }

                Button {
                    layout_width = match_parent
                    layout_height = wrap_content
                    text = "coroutineScope cancel in sub launch"
                    onClick = coroutineScope_cancel
                }

                Button {
                    layout_width = match_parent
                    layout_height = wrap_content
                    text = "supervisorScope"
                    onClick = supervisorScope_cancel
                }

                btn = Button {
                    layout_width = match_parent
                    layout_height = wrap_content
                    text = "exception in launch"
                    onClick = normalExceptionInLaunch
                }
                Button {
                    layout_width = match_parent
                    layout_height = wrap_content
                    text = "suspendCoroutine"
                    onClick = suspendCoroutine
                }
                Button {
                    layout_width = match_parent
                    layout_height = wrap_content
                    text = "try-catch cancellation exception"
                    onClick = tryCatchCancellationException
                }
                Button {
                    layout_width = match_parent
                    layout_height = wrap_content
                    text = "withTimeout"
                    onClick = withTimeout
                }
            }
        )
    }

    val withTimeout = { _: View ->
        MainScope().launch {
            withTimeout(1000) {
                delay(4000)
            }
            Log.i("ttaylor", "withTimeout after withTimeout()");// this wont be printed due to TimeoutCancellationException throw by withTimeout()
        }
        Unit
    }

    val handler0 = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.i("ttaylor", "normalExceptionInLaunch.exception=${throwable}");
    }


    val normalExceptionInLaunch = { _: View ->
        MainScope().launch(handler0) {//handler prevent app from crash
            launch(Dispatchers.IO) {
                throw java.lang.RuntimeException()// will cancel the sibling coroutine and throw the exception upward to outer coroutine
//                throw CancellationException()// if throw CancellationException, nothing will be canceled and crash
            }
            launch {
                delay(1000)
                Log.i("ttaylor", "normalExceptionInLaunch 1"); // wont invoke due to the exception above and the suspend function above,if no suspend function coroutine cant be canceled
            }.invokeOnCompletion {
                Log.i("ttaylor", "normalExceptionInLaunch.invokeOnCompletion"); // will be invoked due to the exception above
            }
            Log.i("ttaylor", "normalExceptionInLaunch.2"); // invoke immediately
        }
        Unit
    }


    val handler00 = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.i("ttaylor", "suspendCoroutine.exception=${throwable}");
    }

    /**
     * case:suspendCoroutine cant be canceled
     */
    val suspendCoroutine = { _: View ->
        MainScope().launch(handler00) {
            launch {
                delay(6000L)
                Log.i("ttaylor", "suspendCoroutine 1");
            }.invokeOnCompletion {
                Log.i("ttaylor", "suspendCoroutine 3");
            }
            val job = launch(Dispatchers.IO) {
                while (true) {
                    suspendFunc()// there is a prerequisite for canceling the outer coroutine, it is cancelable suspend point.  but suspendCoroutine cant be canceled
                }
                Log.i("ttaylor", "suspendCoroutine 2");
            }
            delay(5)// make the job running or it will be canceled without running
            job.cancelAndJoin() // this have no effect because suspendCoroutine is not a qualified suspend point
            Log.i("ttaylor", "suspendCoroutine 4");
        }
        Unit
    }

    /**
     * if try-catch a suspend function, it breaks the cancellation mechanism of coroutine
     */
    val tryCatchCancellationException = { _: View ->
        MainScope().launch {
            val job = launch {
                try {
                    Log.i("ttaylor", "tryCatchCancellationException doing the work");
                    delay(Long.MAX_VALUE)
                } catch (e: java.lang.Exception) {
                    Log.i("ttaylor", "tryCatchCancellationException work is interrupted");
                    // rethrow the CancellationException recover the cancellation mechanism
//                    if(e is CancellationException){
//                        throw CancellationException()
//                    }
                }
                Log.i("ttaylor", "tryCatchCancellationException the work's coroutine has not been canceled");// this will be printed due to the try-catch for CancellationException
            }
            delay(5)
            job.cancelAndJoin()
            Log.i("ttaylor", "tryCatchCancellationException after cancel");
        }
        Unit
    }

    private suspend fun suspendFunc() {
        suspendCoroutine<Int> {// if use suspendCancellableCoroutine, the outer coroutine would be able to be canceled
            it.resume(222)
        }
        Log.i("ttaylor", "suspendCoroutine after resume");// this will print forever because suspendCoroutine cant be canceled
    }

    /**
     * case: sub coroutine in coroutineScope throw exception will cancel the sibling coroutine and coroutineScope and grandfather scope
     */
    val handler = CoroutineExceptionHandler { v1, v2 ->
        Log.v("ttaylor", "[coroutineScope.cancel] exception of sub coroutine")
    }
    val coroutineScope_cancel = { _: View ->
        MainScope().launch(handler) {
            coroutineScope {
                launch {// exception handle in this launch has no effect
                    Log.v("ttaylor", "[coroutineScope.cancel] launch 1()")
                    delay(2000)
                    throw Exception() // will cancel the sibling coroutine and father coroutineScope and grandfather scope
                }
                launch {
                    repeat(10) { // the sibling coroutine will be canceled due to Exception in another coroutine
                        delay(1000)
                        Log.v("ttaylor", "[coroutineScope.cancel]() num=$it, isActive=$isActive")
                    }
                }
                delay(5000)
                Log.v(
                    "ttaylor",
                    "[coroutineScope.cancel] end of coroutineScope"
                )// this wont be print due sub coroutine of coroutineScope throw an Exception
            }
            Log.v("ttaylor", "[coroutineScope.cancel] outer scope()")
        }
        Unit
    }

    /**
     * case: supervisorScope has the opposite effect compared to coroutineScope
     */
    val handler2 = CoroutineExceptionHandler { v1, v2 ->
        Log.v("ttaylor", "[supervisorScope.cancel] exception of sub coroutine")
    }
    val supervisorScope_cancel = { _: View ->
        MainScope().launch {
            supervisorScope {
                launch(handler2) {// exception handle in this launch do have  effect
                    Log.v("ttaylor", "[supervisorScope.cancel] launch 1()")
                    delay(2000)
                    throw Exception() // will not cancel the sibling coroutine and father coroutineScope and grandfather scope
                }
                launch {
                    repeat(10) { // the sibling coroutine wont canceled due to Exception in another coroutine
                        delay(1000)
                        Log.v("ttaylor", "[supervisorScope.cancel]() num=$it, isActive=$isActive")
                    }
                }
                delay(5000)
                Log.v(
                    "ttaylor",
                    "[supervisorScope.cancel] end of supervisorScope"
                )// this will be print
            }
            Log.v("ttaylor", "[supervisorScope.cancel] outer scope()")
        }
        Unit
    }

    /**
     * case: main scope will be cancel , coroutineScope will be cancel, another async wont be cancel
     */
    val coroutineScope_sub_coroutine_cancel_exception = { _: View ->
        mainScope.launch {
            Log.i(TAG, "main scope start ")
            loadDataByCoroutineScope()
            Log.i(TAG, "main scope end ")
        }
        Unit
    }

    /**
     * case: main scope and coroutineScope and two async task will be canceled
     */
    val coroutineScope_sub_coroutine_exception = { _: View ->
        mainScope.launch {
            Log.i(TAG, "main scope start ")
            loadDataByCoroutineScope_exception()
            Log.i(TAG, "main scope end ")
        }
        Unit
    }

    /**
     * case: only async which throw exception will be cancelled
     */
    val coroutineScope_sub_coroutine_exception_try_catch = { _: View ->
        mainScope.launch {
            Log.i(TAG, "main scope start ")
            loadDataByCoroutineScope_exception_try_catch()
            Log.i(TAG, "main scope end ")
        }
        Unit
    }

    /**
     * case: only main scope wont be canceled
     */
    val coroutineScope_sub_coroutine_exception_out_try_catch = { _: View ->
        mainScope.launch {
            Log.i(TAG, "main scope start ")
            try {
                loadDataByCoroutineScope_exception()
            } catch (e: Exception) {
            }
            Log.i(TAG, "main scope end ")
        }
        Unit
    }

    /**
     * case: app will crash ,i dont know why
     */
    val global_launch_sub_coroutine_exception_try_catch = { _: View ->
        mainScope.launch {
            Log.i(TAG, "main scope start ")
            try {
                loadDataByGlobalLaunch_exception()
            } catch (e: Exception) {
            }
            Log.i(TAG, "main scope end ")
        }
        Unit
    }

    /**
     * case: two async will be canceled and mainScope will be canceled
     */
    val main_scope_sub_coroutine_exception_out_try_catch = { _: View ->
        mainScope.launch {
            Log.i(TAG, "main scope start ")
            try {
                loadDataBy_main_scope_exception_out_try_catch_()
            } catch (e: Exception) {
            }
            Log.i(TAG, "main scope end ")
        }
        Unit
    }

    private suspend fun loadDataBy_main_scope_exception_out_try_catch_() = mainScope.launch {
        Log.i(TAG, "--loadDataByCoroutineScope_exception: start")
        val user = async(Dispatchers.IO) { queryUser("dkfdk", 6000) }
        val gift = async(Dispatchers.IO) { fetchGift(3000) }
        throw CancellationException()
        val ret = user.await() + gift.await()
        Log.i(TAG, "--loadDataByCoroutineScope_exception: end")
        ret
    }


    private suspend fun loadDataByGlobalLaunch_exception() = GlobalScope.launch {
        Log.i(TAG, "--loadDataByGlobalLaunch_exception: start")
        val user = async(Dispatchers.IO) { queryUser("dkfdk", 6000) }
        val gift = async(Dispatchers.IO) { fetchGiftThrowException(3000) }
        Log.i(TAG, "--loadDataByGlobalLaunch_exception: end")
    }


    private suspend fun loadDataByCoroutineScope_exception_try_catch() = coroutineScope {
        Log.i(TAG, "--loadDataByCoroutineScope_exception_try_catch: start")
        val user = async(Dispatchers.IO) { queryUser("dkfdk", 6000) }
        val gift = async(Dispatchers.IO) {
            try {
                fetchGiftThrowException(3000)
            } catch (e: Exception) {
            }
        }
        val ret = user.await() + gift.await()
        Log.i(TAG, "--loadDataByCoroutineScope_exception_try_catch: end")
        ret
    }

    private suspend fun loadDataByCoroutineScope_exception() = coroutineScope {
        Log.i(TAG, "--loadDataByCoroutineScope_exception: start")
        val user = async(Dispatchers.IO) { queryUser("dkfdk", 6000) }
        val gift = async(Dispatchers.IO) { fetchGiftThrowException(3000) }
        val ret = user.await() + gift.await()
        Log.i(TAG, "--loadDataByCoroutineScope_exception: end")
        ret
    }

    private suspend fun loadDataByCoroutineScope() = coroutineScope {
        Log.i(TAG, "--loadDataByCoroutineScope: start")
        val user = async(Dispatchers.IO) { queryUser("dkfdk", 6000) }
        val gift = async(Dispatchers.IO) { fetchGiftThrowCancelException(3000) }
        val ret = user.await() + gift.await()
        Log.i(TAG, "--loadDataByCoroutineScope: end")
        ret
    }

    private suspend fun queryUser(name: String, time: Long = 500): String {
        Log.d(TAG, "queryUser: start  name=${name}, time=${time} tid=${Thread.currentThread().id}")
        delay(time)
        Log.d(TAG, "queryUser: end  name=${name}, time=${time} tid=${Thread.currentThread().id}")
        return name
    }

    private suspend fun queryBill(time: Long): String {
        Log.d(TAG, "queryBill: start   time=${time} tid=${Thread.currentThread().id}")
        delay(time)
        Log.d(TAG, "queryBill: end   time=${time} tid=${Thread.currentThread().id}")
        return "bill"
    }

    private suspend fun fetchGiftThrowCancelException(time: Long): String {
        Log.d(TAG, "fetchGift: start tid=${Thread.currentThread().id}")
        delay(time)
        throw CancellationException()
        Log.d(TAG, "fetchGift: end tid=${Thread.currentThread().id}")
        return "gift"
    }

    private suspend fun fetchGiftThrowException(time: Long): String {
        Log.d(TAG, "fetchGift: start tid=${Thread.currentThread().id}")
        delay(time)
        throw Exception()
        Log.d(TAG, "fetchGift: end tid=${Thread.currentThread().id}")
        return "gift"
    }

    private suspend fun fetchGift(time: Long): String {
        Log.d(TAG, "fetchGift: start tid=${Thread.currentThread().id}")
        delay(time)
        Log.d(TAG, "fetchGift: end tid=${Thread.currentThread().id}")
        return "gift"
    }
}