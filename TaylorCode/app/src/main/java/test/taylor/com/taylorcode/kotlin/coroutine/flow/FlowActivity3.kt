package test.taylor.com.taylorcode.kotlin.coroutine.flow

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FlowActivity3 : AppCompatActivity() {

    private val numbers = listOf(
        AdSource("1", 20, 10),
        AdSource("2", 100, 20),
        AdSource("3", 200, 1),
        AdSource("4", 150, 3),
        AdSource("5", 50, 15),
    )

    private val alphas = listOf(
        AdSource("a", 300, 11),
        AdSource("b", 100, 9),
        AdSource("c", 150, 8),
        AdSource("d", 500, 7),
        AdSource("e", 80, 6),
        AdSource("f", 200, 5),
        AdSource("g", 100, 5),
        AdSource("h", 30, 4),
        AdSource("i", 200, 3),
    )

    private val alphaFlow = alphas.asFlow().map { AdSourceState(loadAlpha(it), false) }

    private val numberFlow = flow {
        val maxAdSource = numbers.asFlow()
            .flatMapMerge { adSource -> flow { emit(loadNumber(adSource)) } }
            .reduce { max, cur -> if (cur.price > max.price) cur else max }
        emit(AdSourceState(maxAdSource, true))
    }

    private suspend fun loadAlpha(adSource: AdSource): AdSource {
        delay(adSource.delay)
        Log.d("ttaylor", "FlowActivity3.loadAlpha[adSource]: source(${adSource.name}).price=${adSource.price}")
        return adSource
    }

    private suspend fun loadNumber(adSource: AdSource): AdSource {
        delay(adSource.delay)
        Log.i("ttaylor", "FlowActivity3.loadNumber[adSource]: source(${adSource.name}).price=${adSource.price}")
        return adSource
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            val maxAdSource = flowOf(alphaFlow, numberFlow)
                .flattenMerge()
                .transformWhile<AdSourceState, AdSource> {
                    emit(it.adSource)
                    !it.isDone
                }
                .reduce { accumulator: AdSource, value: AdSource -> if (value.price > accumulator.price) value else accumulator }
            Log.e("ttaylor", "FlowActivity3.onCreate[]: max AdSource=${maxAdSource}")
        }
    }
}

data class AdSource(val name: String, val delay: Long, val price: Int)


data class AdSourceState(val adSource: AdSource, val isDone: Boolean)