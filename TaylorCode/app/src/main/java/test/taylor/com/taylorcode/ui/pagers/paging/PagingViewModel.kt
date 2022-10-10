package test.taylor.com.taylorcode.ui.pagers.paging

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.onEach

class PagingViewModel(repository: TextRepository):ViewModel() {

    val pagingData = Pager(
        config = PagingConfig(pageSize = 15, enablePlaceholders = false, initialLoadSize = 30, prefetchDistance = 1),
        pagingSourceFactory = { MyPagingSource(repository) }
    ).flow.onEach {
        Log.d("ttaylor", "PagingViewModel.(): pagingData=${it} ")
    }.cachedIn(viewModelScope)
}