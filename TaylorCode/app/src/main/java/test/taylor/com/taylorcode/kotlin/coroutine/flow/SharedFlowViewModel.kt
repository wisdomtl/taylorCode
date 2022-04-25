package test.taylor.com.taylorcode.kotlin.coroutine.flow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SharedFlowViewModel : ViewModel() {

    private val _intents = MutableSharedFlow<Intent>()

    private var count = 0;

    fun send(intent: Intent) {
        viewModelScope.launch {
            _intents.emit(intent)
        }
    }

    /**
     * case: SharedFlow will drop the value when there is no subscribers
     */
    val viewState =
        _intents
            .toViewState()
            .flowOn(Dispatchers.Default)
            .shareIn(viewModelScope, SharingStarted.Eagerly, 0)

    private fun Flow<Intent>.toViewState(): Flow<ViewState> = merge(
        filterIsInstance<Intent.ShowDialog>()
            .flatMapConcat { flow { emit(ViewState.ShowDialog(count++)) } }
    )
}