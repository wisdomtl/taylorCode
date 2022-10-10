package test.taylor.com.taylorcode.kotlin.coroutine.flow

import android.util.Log
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
            /**
             * onEach wont print anything if there is no subscribers
             */
            .onEach { Log.d("ttaylor[SharedFlow test]", "SharedFlowViewModel._intent.onEach[intent=${it}]: ") }
            .toViewState()
            .flowOn(Dispatchers.Default)
            /**
             * replay = 1 cant save "dropping value when there is no subscribers", the old value will deliver to new collector and new value never comes
             */
            .shareIn(viewModelScope, SharingStarted.Eagerly, 1)

    private fun Flow<Intent>.toViewState(): Flow<ViewState> = merge(
        filterIsInstance<Intent.ShowDialog>()
            .flatMapConcat { flow { emit(ViewState.ShowDialog(count++)) } }
    )
}