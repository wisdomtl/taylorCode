package test.taylor.com.taylorcode.concurrent

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean

class ConcurrentInitActivity : AppCompatActivity() {

    private var hasInit = AtomicBoolean(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        repeat(10) {
            MainScope().launch { init() }
        }
    }

    /**
     * case: init once by multiple thread
     */
    fun init() {
        if (hasInit.compareAndSet(false, true)) {
            doInit()
        }
    }

    fun doInit() {
        Log.v("ttaylor", "doInit()")
    }
}