package test.taylor.com.taylorcode.kotlin.extension

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

/**
 * take the first data in every [periodMillis] and drop the rest
 */
fun <T> Flow<T>.throttleFirst(periodMillis: Long): Flow<T> {
    var lastTime = 0L
    return transform { upstream ->
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastTime > periodMillis) {
            lastTime = currentTime
            emit(upstream)
        }
    }
}