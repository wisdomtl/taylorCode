package test.taylor.com.taylorcode.kotlin.coroutine.mvi

import test.taylor.com.taylorcode.retrofit.News


data class NewsState(
    val data: List<News> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val errorMessage: String = "",
//    val reportToast: String = "",
) {
    companion object {
        val initial = NewsState(isLoading = true)
    }

    override fun toString(): String {
        return "NewsState(data.size=${data.size}, isLoading=$isLoading, isLoadingMore=$isLoadingMore, errorMessage='$errorMessage')"
    }

}