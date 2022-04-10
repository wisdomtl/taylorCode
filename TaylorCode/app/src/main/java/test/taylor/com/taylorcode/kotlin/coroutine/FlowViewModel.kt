package test.taylor.com.taylorcode.kotlin.coroutine

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet

class FlowViewModel : ViewModel() {

    private val _counter = MutableStateFlow(0)

    val counter = _counter.asStateFlow()

    fun inc() {
        _counter.update { value -> value + 1 }
    }

    fun inc2() {
    }
}