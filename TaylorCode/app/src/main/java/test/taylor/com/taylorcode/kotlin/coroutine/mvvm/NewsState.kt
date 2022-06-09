package test.taylor.com.taylorcode.kotlin.coroutine.mvvm

import test.taylor.com.taylorcode.retrofit.News


data class NewsState(
    val data: List<News>,
    val isLoading: Boolean,
    val isLoadingMore: Boolean,
    val errorMessage: String,
) {
    companion object {
        val initial =
            NewsState(data = emptyList(), isLoading = true, isLoadingMore = false, errorMessage = "")
    }
}