package test.taylor.com.taylorcode.architecture

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.kotlin.coroutine.flow.collectIn
import test.taylor.com.taylorcode.kotlin.extension.onVisibilityChange

class StickyLiveDataActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProvider(this)[TestViewModel::class.java] }

    private val contentView by lazy {
        ConstraintLayout {
            layout_id = "container"
            layout_width = match_parent
            layout_height = match_parent
        }
    }

    private val intent = merge(
        flow { emit(StickyIntent.Init) }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)
        supportFragmentManager.beginTransaction().add("container".toLayoutId(), TrolleyFragment())
            .commit()

        viewModel.setValue("success")

        intent.onEach(viewModel::send)
            .launchIn(lifecycleScope)

        /**
         * case: the difference between LiveData and Flow
         * flow will re-collect when activity is resume from background, but LiveData wont
         */
        viewModel.liveData.observe(this) {
            Log.v("ttaylor", "[flow vs liveData] observe value=$it")
        }
        viewModel.viewState.collectIn(this) {
            Log.v("ttaylor", "[flow vs liveData] collectIn value=$it")
        }
    }

}

class TestViewModel : ViewModel() {
    val liveData = MutableLiveData<String>()
    private val _intent = MutableSharedFlow<StickyIntent>()

    val viewState =
        _intent
            .toPartialChange()
            .scan(ViewState.Initial) { value, partialChange -> partialChange.reduce(value) }
            .flowOn(Dispatchers.IO)
            .stateIn(viewModelScope, SharingStarted.Eagerly, ViewState.Initial)

    fun setValue(value: String) {
        liveData.value = value
    }

    fun send(intent: StickyIntent) {
        viewModelScope.launch {
            _intent.emit(intent)
        }
    }

    private fun Flow<StickyIntent>.toPartialChange(): Flow<StickyPartialChange> =
        merge(
            filterIsInstance<StickyIntent.Init>().flatMapConcat { it.toPartialChange() }
        )

    private fun StickyIntent.Init.toPartialChange(): Flow<StickyPartialChange> =
        flow {
            Log.v("ttaylor","[flow vs liveData] toPartialChange()")
            emit(StickyPartialChange.Init.Success) }
}


sealed interface StickyIntent {
    object Init : StickyIntent
}

sealed interface StickyPartialChange {
    fun reduce(oldState: ViewState): ViewState
    sealed interface Init : StickyPartialChange {
        override fun reduce(oldState: ViewState): ViewState {
           return when (this) {
                Loading -> oldState.copy(isLoading = true)
                Success -> oldState.copy(value = "success")
                Fail -> oldState.copy(value = "failed")
            }
        }

        object Loading : Init
        object Success : Init
        object Fail : Init
    }
}

data class ViewState(
    val value: String,
    val isLoading: Boolean
) {
    companion object {
        val Initial = ViewState("init", true)
    }
}