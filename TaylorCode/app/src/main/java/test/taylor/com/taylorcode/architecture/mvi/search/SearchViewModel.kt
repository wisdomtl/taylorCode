package com.bilibili.studio.search

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bilibili.studio.search.base.SearchEffect
import com.bilibili.studio.search.base.SearchEvent
import com.bilibili.studio.search.base.SearchIntent
import com.bilibili.studio.search.base.SearchViewState
import com.bilibili.studio.search.base.BaseSearchRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import test.taylor.com.taylorcode.BuildConfig

/**
 * The core search biz logic is here.
 * [SearchViewModel] accepts [SearchIntent] from UI, map it to [SearchEffect] and produce [SearchViewState] accordingly to UI.
 * One [SearchIntent] corresponds to several [SearchEffect], one [SearchEffect] correspond to one [SearchViewState]
 *
 * [BEAN] represents the data returned from server by [SearchIntent.Search] and [SearchIntent.SearchMore]
 */
class SearchViewModel<BEAN, T : SearchViewState>(
    private val searchRepository: BaseSearchRepository<BEAN>,
    private val initViewState: T
) : ViewModel() {

    companion object {
        const val TEMPLATE_MMAPID = "template-search"
        const val MATERIAL_MMAPID = "material-search"

    }

    private var lastEvent: SearchEvent? = null
    private val _searchIntent = MutableSharedFlow<SearchIntent>()

    /**
     * the magic is here, map Intent into ViewState
     */
    val searchSharedState =
        _searchIntent
            .log()
            .toEffectFlow()
            .log()
            .sendEvent()
            .scan(initViewState) { oldState, reaction -> reaction.reduce(oldState) } // turn Effect into ViewState by scan
            .log()
            .flowOn(Dispatchers.IO)
            .catch { Log.e(SearchViewModel::class.java.simpleName, "${it.message}``````${it.stackTrace}") }
            .shareIn(viewModelScope, SharingStarted.Eagerly)

    /**
     * a sticky version of [searchSharedState]
     */
    val searchState = searchSharedState.shareIn(viewModelScope, SharingStarted.Eagerly, 1)

    private val _eventFlow = MutableSharedFlow<SearchEvent>()
    val searchEvent: SharedFlow<SearchEvent> = _eventFlow
    var bean: BEAN? = null

    var categoryId = "dd" // not in MVI style,better way is keep it in template item bean

    /**
     * the key used to store search keyword locally by mmkv.
     * it is the class name of actual [SearchViewState]
     */
    private val mmkvKey by lazy { initViewState::class.java.simpleName }

    /**
     * The [SearchIntent] entrance
     */
    fun send(intent: SearchIntent) {
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.v(this@SearchViewModel.javaClass.simpleName, "send() exception=$throwable")
        }
        viewModelScope.launch(exceptionHandler) { _searchIntent.emit(intent) }
    }

    private fun <T> Flow<T>.log(): Flow<T> = onEach {
        if (BuildConfig.DEBUG)
            when (it) {
                is SearchIntent -> Log.v(this@SearchViewModel.javaClass.simpleName, "$it")
                is SearchEffect<*> -> Log.d(this@SearchViewModel.javaClass.simpleName, "$it")
                is SearchViewState -> Log.e(this@SearchViewModel.javaClass.simpleName, "$it")
            }
    }

    /**
     * Map [SearchIntent] into [SearchEffect] according to the type
     */
    private fun Flow<SearchIntent>.toEffectFlow(): Flow<SearchEffect<T>> = merge(
        filterIsInstance<SearchIntent.Init>().initTransform(),
        // highlight search button immediately if input is not empty
        filterIsInstance<SearchIntent.InputKeyword>().map { SearchEffect.InputKeyword.Highlight<T>(it.keyword, it.keyword.isNotEmpty()) },
        // always show inputted keyword on the top hints list
        filterIsInstance<SearchIntent.InputKeyword>().filter { it.isUserInput && it.keyword.isNotEmpty() }.map { SearchEffect.InputKeyword.Hints<T>(it.keyword, emptyList()) },
        // debounce hints request by 300 ms
        filterIsInstance<SearchIntent.InputKeyword>().filter { it.isUserInput }.debounce(300).flatMapLatest {
            if (it.keyword.isEmpty()) flow { emit(SearchEffect.PopBack.Pop<T>()) }
            else if (it.keyword.length <= 20) flow { emit(SearchEffect.InputKeyword.Hints<T>(it.keyword, searchRepository.fetchSearchHint(it.keyword),true)) }
            else flow { emit(SearchEffect.InputKeyword.Hints<T>(it.keyword, emptyList(),true)) }
        },
        filterIsInstance<SearchIntent.ClearKeyword>().map { SearchEffect.ClearKeyword.NoKeyword() },
        filterIsInstance<SearchIntent.GotoSearchPage>().map { SearchEffect.GotoSearchPage.Navigate(it.keyword, it.type) },
        filterIsInstance<SearchIntent.ClearHistory>().clearAndMap(),
        filterIsInstance<SearchIntent.Search<BEAN, T>>().flatMapConcat { it.toEffectFlow() },
        filterIsInstance<SearchIntent.SearchMore<BEAN, T>>().flatMapConcat { it.toEffectFlow() },
        filterIsInstance<SearchIntent.SearchByMaterialType<BEAN, T>>().flatMapConcat { it.toEffectFlow() },
        filterIsInstance<SearchIntent.CheckWhole>().flatMapConcat { flow { emit(SearchEffect.CheckWhole.Goto<T>(it.materialType)) } },
        filterIsInstance<SearchIntent.GoToHistoryPage>().map { SearchEffect.GoToHistoryPage.Back<T>() }.onEach { searchRepository.clear() },
        filterIsInstance<SearchIntent.HideKeyboard>().map { SearchEffect.HideKeyboard.Hide() },
        filterIsInstance<SearchIntent.HistorySwitch>().map { SearchEffect.HistorySwitch.Switch(it.on) },
        filterIsInstance<SearchIntent.PopBack>().map { SearchEffect.PopBack.Pop<T>() }.onEach { searchRepository.clear() },
        filterIsInstance<SearchIntent.GoToDetailPage>().map { intent -> SearchEffect.GotoDetailPage.Navigate(intent.block?.invoke(bean ?: Unit)?.plus(intent.map).orEmpty()) }
    )

    private fun Flow<SearchIntent.ClearHistory>.clearAndMap(): Flow<SearchEffect<T>> = map {
//        MMKV.mmkvWithID(it.mmapID)?.clearAll()
        SearchEffect.ClearHistory.NoHistory()
    }

    private fun Flow<SearchIntent.Init>.initTransform(): Flow<SearchEffect<T>> = transform {
        emit(SearchEffect.Init.ShowKeyboard())
//        val historyBundle = MMKV.mmkvWithID(it.mmapID)?.decodeParcelable("search-history", Bundle::class.java)
//        historyBundle?.let {
//            val historys = it.getStringArray(mmkvKey) ?: emptyArray()
//            emit(SearchEffect.Init.LoadHistory<T>(historys.toList()))
//        }
    }

    private fun SearchIntent.Search<BEAN, T>.toEffectFlow(): Flow<SearchEffect<T>> =
        suspend { searchRepository.search(keyword, mapOf("isFirstTime" to true)) }.asFlow()
            .onEach { bean = it }
            .map { mapper(it) }
            .catch { emit(SearchEffect.Search.Fail("网络错误，请重试")) }

    private fun SearchIntent.SearchMore<BEAN, T>.toEffectFlow() =
        suspend { searchRepository.searchMore(keyword) }.asFlow()
            .onEach { bean = it }
            .map { mapper(it) }
            .catch { emit(SearchEffect.SearchMore.Fail("没有更多了")) }

    private fun SearchIntent.SearchByMaterialType<BEAN, T>.toEffectFlow() =
        suspend { searchRepository.searchMore(keyword, mapOf("materialType" to materialType)) }.asFlow()
            .onEach { bean = it }
            .map { mapper(it) }
            .catch { emit(SearchEffect.SearchByMaterialType.Fail("没有更多了")) }

    fun hasMore(materialType: Int):Boolean {
        return searchRepository.hasMore(materialType)
    }

    fun isLoading(materialType: Int):Boolean {
        return searchRepository.isLoading(materialType)
    }

    /**
     * map Effect into Event
     */
    private fun Flow<SearchEffect<T>>.sendEvent(): Flow<SearchEffect<T>> =
        onEach {
            if(it is SearchEffect.CheckWhole.Goto){
                _eventFlow.emit(SearchEvent.CheckWhole(it.materialType))
                return@onEach
            }
        }.distinctUntilChanged()
            .onEach { effect ->
                val event = when (effect) {
                    is SearchEffect.ClearKeyword.NoKeyword -> SearchEvent.ClearKeyword.NoKeyword
                    is SearchEffect.HideKeyboard.Hide -> SearchEvent.HideKeyboard.Hide
                    is SearchEffect.Init.ShowKeyboard -> SearchEvent.Init.ShowKeyboard
                    is SearchEffect.GotoSearchPage.Navigate -> SearchEvent.GotoSearchPage.Navigate(effect.keyword, effect.searchFrom)
                    is SearchEffect.GoToHistoryPage.Back -> SearchEvent.GoToHistory.Navigate
                    is SearchEffect.SearchMore.Success -> SearchEvent.SearchMore.Success
                    is SearchEffect.SearchMore.Fail -> SearchEvent.SearchMore.Fail(effect.toast)
                    is SearchEffect.GotoDetailPage.Navigate -> SearchEvent.GotoDetailPage.Navigate(effect.map)
                    is SearchEffect.InputKeyword.Hints -> {
                        if (effect.isFromDebounce && lastEvent != null && lastEvent is SearchEvent.GotoSearchPage.Navigate) {
                            return@onEach
                        }
                        SearchEvent.GotoHintPage.Navigate
                    }
                    is SearchEffect.PopBack.Pop -> SearchEvent.PopBack.Pop
                    else -> return@onEach
                }
                _eventFlow.emit(event)
                lastEvent = event
                Log.w(this@SearchViewModel.javaClass.simpleName, "$event")
            }
}

class SearchViewModelFactory<BEAN, V : SearchViewState>(
    private val searchRepository: BaseSearchRepository<BEAN>,
    private val initViewState: V
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchViewModel(searchRepository, initViewState) as T
    }
}