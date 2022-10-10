package test.taylor.com.taylorcode.kotlin.coroutine.flow

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


/**
 * collect the flow according to the lifecycle event
 * the flow will collect when lifecycle state is above [minActiveState] and stop when state is below
 */
fun <T> Flow<T>.collectIn(
    lifecycleOwner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    action: (T) -> Unit
): Job = lifecycleOwner.lifecycleScope.launch {
    flowWithLifecycle(lifecycleOwner.lifecycle, minActiveState).onEach { action(it) }.collect()
}

/**
 * collect the latest value emitted by the flow according to the lifecycle event
 * the flow will collect when lifecycle state is above [minActiveState] and stop when state is below
 */
fun <T> Flow<T>.collectLatestIn(
    lifecycleOwner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    action: (T) -> Unit
): Job = lifecycleOwner.lifecycleScope.launch {
    flowWithLifecycle(lifecycleOwner.lifecycle, minActiveState).collectLatest { action(it) }
}

/**
 * turn input event of [EditText] into [Flow], make it easy to debounce
 */
fun <T> EditText.textChangeFlow(elementCreator: (Boolean, CharSequence?) -> T): Flow<T> = callbackFlow {
    val watcher = object : TextWatcher {
        private var isUserInput = true
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(char: CharSequence?, p1: Int, p2: Int, p3: Int) {
            isUserInput = this@textChangeFlow.hasFocus()
        }

        override fun afterTextChanged(p0: Editable?) {
            trySend(elementCreator(isUserInput, p0?.toString().orEmpty()))
        }

    }
    addTextChangedListener(watcher)
    awaitClose { removeTextChangedListener(watcher) }
}

/**
 * turn click event of [View] into [Flow], make it easy to debounce
 */
fun <T> View.clickFlow(elementCreator: () -> List<T>): Flow<T> = callbackFlow {
    setOnClickListener { elementCreator().forEach { intent ->
        trySend(intent) } }
    awaitClose { setOnClickListener(null) }
}

/**
 * Returns the flow if it is not null, or the empty flow otherwise
 * use this for a shorter expression
 */
fun <T> Flow<T>?.orEmpty(): Flow<T> = this ?: emptyFlow()

/**
 * Returns the flow if it is not null, or the [Flow] returned from [block] otherwise
 * use this for a shorter expression
 */
fun <T> Flow<T>?.orElse(block: () -> Flow<T>) = this ?: block()

/**
 * case: customize operator of Flow
 * filter + map the flow
 */
fun <T, R> Flow<T>.filterMap(predicate: (T) -> Boolean, transform: suspend (T) -> R): Flow<R> =
    transform { value ->
        if (predicate(value)) emit(transform(value))
    }
