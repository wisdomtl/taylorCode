package test.taylor.com.taylorcode.kotlin.coroutine

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import test.taylor.com.taylorcode.kotlin.*

/**
 * CancellationException will only cancel current coroutine and sub coroutine
 * non-CancellationException will cancel current coroutine and sub coroutine and parent coroutine, cancellation will be stop where exception is try-catch
 */
class CoroutineCancelActivity : AppCompatActivity() {

    private val TAG = "CoroutineCancel1"
    private val mainScope = MainScope()
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
            }
        )
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
        val gift = async (Dispatchers.IO) { fetchGiftThrowException(3000) }
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