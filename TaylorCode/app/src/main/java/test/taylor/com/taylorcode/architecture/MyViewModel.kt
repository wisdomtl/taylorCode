package test.taylor.com.taylorcode.architecture

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
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

    private val selectedListStateFlow = MutableStateFlow("")
    val openStateFLow: StateFlow<String> = selectedListStateFlow


    private val coldFlow = flow {
        var count = 0
        repeat(100) {
            delay(1000)
            Log.w("ttaylor4", " cold flow emiting ${count}")
            emit("cold flow1(${count++})")
        }
    }

    val hotFlow by lazy { coldFlow.shareIn(viewModelScope, SharingStarted.WhileSubscribed()) }

    /**
     * case: SharingStarted.Lazily is the only choice for one-shot flow
     */
    val oneShotStateFlow by lazy {
        flow {
            delay(2000)
            Log.v("ttaylor6", "one shot emit 1111")
            emit("1111")
        }.stateIn(viewModelScope, SharingStarted.Lazily, "start")
    }

    val oneShotSharedFlow by lazy {
        flow {
            delay(2000)
            Log.v("ttaylor7", "one shot emit 2222")
            emit("2222")
        }.shareIn(viewModelScope, SharingStarted.Lazily, 0)
    }

    fun setSelectsList2(goods: List<String>) {
        viewModelScope.launch {
            selectsListFlow.emit(goods)
        }
    }

    fun testFlow() {
        selectedListStateFlow.update { "1" }
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