package test.taylor.com.taylorcode.retrofit.repository

import io.reactivex.Single
import test.taylor.com.taylorcode.retrofit.News

interface NewsRepository {
    fun fetchNews(): Single<List<News>?>
}