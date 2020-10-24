package test.taylor.com.taylorcode.ui.custom_view.progress_view

import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader

class StateGradientProgress(var stateMap: Map<IntRange, IntArray>) : Progress {
    private var currentColors: IntArray? = null
    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }
    private val DEFAULT_COLORS = intArrayOf(0xFFFF00FF.toInt(), 0xFF0000FF.toInt())

    override fun draw(canvas: Canvas?, progressBar: ProgressBar) {
        progressBar.run {
            paint.shader = LinearGradient(
                progressRectF.left,
                progressRectF.top,
                progressRectF.right,
                progressRectF.bottom,
                currentColors,
                null,
                Shader.TileMode.CLAMP
            )
            canvas?.drawRoundRect(progressRectF, progressRx, progressRy, paint)
        }
    }

    override fun onPercentageChange(old: Int, new: Int) {
        currentColors = stateMap.find { new in it.key } ?: DEFAULT_COLORS
    }
}

inline fun <K, V> Map<K, V>.find(predicate: (Map.Entry<K, V>) -> Boolean): V? {
    forEach { entry ->
        if (predicate(entry)) return entry.value
    }
    return null
}

fun Array<out Long>.toIntArray(): IntArray {
    return IntArray(size) { index -> this[index].toInt() }
}

fun stateListOf(vararg states: Pair<IntRange, Array<Long>>) =
    StateGradientProgress(
        mutableMapOf<IntRange, IntArray>().apply {
            states.forEach { state -> put(state.first, state.second.toIntArray()) }
        }
    )

