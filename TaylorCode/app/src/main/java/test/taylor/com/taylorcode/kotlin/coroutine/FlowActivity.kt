package test.taylor.com.taylorcode.kotlin.coroutine

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import test.taylor.com.taylorcode.kotlin.ConstraintLayout

import test.taylor.com.taylorcode.kotlin.*

class FlowActivity : AppCompatActivity() {
    var count = 0

    var flow: Flow<Int>? = null
    var job: Job? = null

    val stateFlow = MutableStateFlow("")

    private val contentView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent

            TextView {
                layout_id = "tvChange"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 12f
                textColor = "#ffffff"
                text = "new Message"
                gravity = gravity_center
                center_horizontal = true
                center_vertical = true
                padding = 20
                shape = shape {
                    corner_radius = 20
                    solid_color = "#ff00ff"
                }
                onClick = {
                    stateFlow.value = (count++).toString()
                }
            }
        }
    }

    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)

        /**
         * case: StateFlow
         */
        GlobalScope.launch {
            stateFlow.buffer()?.collect {
                Log.v("ttaylor", "collect() string=${it}")
            }
        }

        /**
         * case
         * flow() to create a flow
         * collect() to collect a flow
         */
        GlobalScope.launch {
            flow {
                // define the logic invoked when collect() invoked
                (1 .. 3).forEach {
                    delay(1000)
                    emit(it)
                }
            }.map { }.collect {
                // define the logic invoked when emit() invoked
                Log.v("ttaylor", "print() num=${it}")
            }
        }

    }
}

/**
 * case: customize operator of Flow
 */
fun <T, R> Flow<T>.filterMap(predicate: (T) -> Boolean, transform: suspend (T) -> R): Flow<R> = transform { value ->
    if (predicate(value)) emit(transform(value))
}
