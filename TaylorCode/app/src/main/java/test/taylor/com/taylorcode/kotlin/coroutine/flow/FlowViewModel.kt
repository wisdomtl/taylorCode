package test.taylor.com.taylorcode.kotlin.coroutine.flow

import android.os.Build
import android.util.Log
import android.util.TimeUtils
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import java.time.Duration
import java.time.temporal.TemporalUnit
import java.util.concurrent.TimeUnit

class FlowViewModel : ViewModel() {

    private val _counter = MutableStateFlow(0)

    private val _sharedFlow = MutableSharedFlow<String>()

    /**
     * case: multiple-shot case, the cold flow upstream will emit never stopping
     */
    private val coldFlow = flow {
        var count = 0
        repeat(100) {
            delay(1000)
            Log.w("ttaylor4", " cold flow emiting ${count}")
            emit("cold flow1(${count++})")
        }
    }

    /**
     * case: one-shot case, the cold flow upstream will emit only one
     */
    private val coldFlow2 = flow {
        delay(2000)
        Log.v("ttaylor5","one shot emit")
        emit("one shot")
    }

    val oneShotHotFlow by lazy {
        coldFlow2.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), "start")
    }

    /**
     * case: best strategy for multiple-shot cold flow to be hot
     * 5000 keep upstream survive from screen rotation
     */
    val hotStateFlow by lazy {
        coldFlow.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "start")
    }
    val hotSharedFlow by lazy { coldFlow.shareIn(viewModelScope, SharingStarted.WhileSubscribed()) }

    @RequiresApi(Build.VERSION_CODES.O)
    val flowLiveData = coldFlow.asLiveData(timeout = Duration.ofMillis(0))

    val counter = _counter.asStateFlow()

    fun inc() {
        _counter.update { value -> value + 1 }
    }
}