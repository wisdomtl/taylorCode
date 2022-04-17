package test.taylor.com.taylorcode.kotlin.coroutine.mvvm

import test.taylor.com.taylorcode.retrofit.News


data class NewsState(
    val news: List<News>,
    val loading: Boolean,
    val errorMessage: String = ""
)