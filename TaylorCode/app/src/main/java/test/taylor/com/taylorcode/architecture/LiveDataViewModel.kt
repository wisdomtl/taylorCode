package test.taylor.com.taylorcode.architecture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.delay

class LiveDataViewModel : ViewModel() {

    /**
     * case: cold LiveData ,a new way of creating liveData using coroutine
     */
    var stringLiveData = liveData{
        delay(1000)
        emit("done")
    }
}