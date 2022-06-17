package test.taylor.com.taylorcode.kotlin.coroutine.mvi


sealed class FeedsIntent {
    data class Init(val type: Int, val count: Int) : FeedsIntent()
    data class More(val timestamp: Long, val count: Int) : FeedsIntent()
    data class Report(val id: Long) : FeedsIntent()
}