package test.taylor.com.taylorcode.ui.pagers.paging

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import test.taylor.com.taylorcode.kotlin.coroutine.orNew

class PagingViewModel(repository: TextRepository):ViewModel() {

    val pagingData = Pager(
        config = PagingConfig(pageSize = 15, enablePlaceholders = false, initialLoadSize = 30, prefetchDistance = 1),
        pagingSourceFactory = { MyPagingSource(repository) }
    ).flow.onEach {
        Log.d("ttaylor", "PagingViewModel.onEach(): pagingData=${it} ")
    } .cachedIn(viewModelScope.orNew())

    private val _stateFlow = MutableSharedFlow<String>()

    val stateFlow =
        _stateFlow
            .onEach { Log.d("ttaylor[flow.fragment.test]", "PagingViewModel._stateFlow.onEach[str=$it]") }
            .shareIn(viewModelScope, started = SharingStarted.Eagerly,0)

    fun send(str:String){
        viewModelScope.launch {
            Log.d("ttaylor[flow.fragment.test]", "PagingViewModel.send[str=$str]: ")
            _stateFlow.emit(str) }
    }
}