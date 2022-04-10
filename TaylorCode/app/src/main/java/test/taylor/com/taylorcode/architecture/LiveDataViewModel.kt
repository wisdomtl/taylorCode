package test.taylor.com.taylorcode.architecture

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LiveDataViewModel : ViewModel() {

    /**
     * case: cold LiveData ,a new way of creating liveData using coroutine
     */
    var stringLiveData = liveData{
        delay(1000)
        emit("done")
    }

    var livedata = MutableLiveData<String>()

    fun getData(){
        viewModelScope.launch {
            livedata.postValue("a")
        }
    }
}