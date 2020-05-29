package test.taylor.com.taylorcode.retrofit.repository

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import test.taylor.com.taylorcode.retrofit.News
import test.taylor.com.taylorcode.retrofit.NewsApi

class NewsRepositoryImpl : NewsRepository {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.apiopen.top")
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(OkHttpClient.Builder().build())
        .build()

    private val newsApi = retrofit.create(NewsApi::class.java)

    override fun fetchNews(): Single<List<News>?> =
        newsApi.fetchNewsSingle(
            mapOf("page" to "1", "count" to "4")
        ).map { it.result }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())

}