package test.taylor.com.taylorcode.kotlin.coroutine.mvi

sealed interface FeedsEvent {
    sealed interface Report : FeedsEvent {
        data class Result(val reportToast: String) : Report
    }
}