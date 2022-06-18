package test.taylor.com.taylorcode.kotlin.coroutine.mvi

import test.taylor.com.taylorcode.retrofit.News

sealed interface FeedsPartialChange {
    fun reduce(oldState: NewsState): NewsState
}

sealed class Init : FeedsPartialChange {
    override fun reduce(oldState: NewsState): NewsState = when (this) {
        Loading -> oldState.copy(isLoading = true)
        is Success -> oldState.copy(
            data = news,
            isLoading = false,
            isLoadingMore = false,
            errorMessage = ""
        )
        is Fail -> oldState.copy(
            data = emptyList(),
            isLoading = false,
            isLoadingMore = false,
            errorMessage = error
        )
    }

    object Loading : Init()
    data class Success(val news: List<News>) : Init()
    data class Fail(val error: String) : Init()
}

sealed class More : FeedsPartialChange {
    override fun reduce(oldState: NewsState): NewsState = when (this) {
        Loading -> oldState.copy(
            isLoading = false,
            isLoadingMore = true,
            errorMessage = ""
        )
        is Success -> oldState.copy(
            data = oldState.data + news,
            isLoading = false,
            isLoadingMore = false,
            errorMessage = ""
        )
        is Fail -> oldState.copy(
            isLoadingMore = false,
            isLoading = false,
            errorMessage = error
        )
    }

    object Loading : More()
    data class Success(val news: List<News>) : More()
    data class Fail(val error: String) : More()
}

sealed class Report : FeedsPartialChange {
    override fun reduce(oldState: NewsState): NewsState = when (this) {
        is Success -> oldState.copy(
            data = oldState.data.filterNot { it.id == id },
        )
        Fail -> oldState
    }

    class Success(val id: Long) : Report()
    object Fail : Report()
}