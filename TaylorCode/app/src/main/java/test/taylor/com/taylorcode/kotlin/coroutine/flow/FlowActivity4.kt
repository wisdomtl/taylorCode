package test.taylor.com.taylorcode.kotlin.coroutine.flow

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch

class FlowActivity4 : AppCompatActivity() {
    private val numbers = listOf(
        AdSource("1", 20, 10),
        AdSource("2", 100, 20),
        AdSource("3", 200, 1),
        AdSource("4", 150, 3),
        AdSource("5", 50, 15),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            numbers.asFlow().filtered({ it.name.toInt() % 2 == 0 }) { Log.d("ttaylor", "FlowActivity4.onCreate[]: filterd = ${it}")}
                .collect {
                    Log.d("ttaylor", "FlowActivity4.onCreate[]: not filtered=${it}")
                }
        }
    }
}

fun <T> Flow<T>.filtered(predicate: (T) -> Boolean, action: (T) -> Unit): Flow<T> = transform {
    if (predicate(it)) emit(it)
    else action(it)
}