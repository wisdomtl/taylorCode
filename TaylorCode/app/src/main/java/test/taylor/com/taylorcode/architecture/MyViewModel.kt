package test.taylor.com.taylorcode.architecture

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean

class MyViewModel : ViewModel() {

    val selectsListLiveData = MutableLiveData<List<String>>()

    val singleListLiveData = SingleLiveEvent<List<String>>()

    /**
     * case:transform the value of liveData
     */
    val foodListLiveData = Transformations.map(selectsListLiveData) { list ->
        list.filter { it.startsWith("food") }
    }

    val asyncLiveData = selectsListLiveData.switchMap { list ->
        liveData(Dispatchers.Default) {
            Log.v("ttaylor", "thread id = ${Thread.currentThread().id}")
            emit(list.filter { it.startsWith("food") })
        }
    }

    val selectsListFlow = MutableSharedFlow<List<String>>(replay = 1)

    fun setSelectsList2(goods: List<String>) {
        viewModelScope.launch {
            selectsListFlow.emit(goods)
        }
    }

    /**
     * case:convert MutableLive data into LiveData
     */
    val sLiveData: LiveData<List<String>> by this::selectsListLiveData

    fun setSelectsList(goods: List<String>) {
        selectsListLiveData.value = goods
        singleListLiveData.value = goods
    }
}