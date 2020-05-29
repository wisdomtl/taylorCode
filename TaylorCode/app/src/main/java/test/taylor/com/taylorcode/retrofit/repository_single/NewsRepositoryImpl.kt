package test.taylor.com.taylorcode.retrofit.repository_single

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import test.taylor.com.taylorcode.retrofit.News
import test.taylor.com.taylorcode.retrofit.NewsApi
import test.taylor.com.taylorcode.retrofit.repository_livedata.room.NewsDatabase
import java.util.concurrent.Executors

class NewsRepositoryImpl(context: Context) : NewsRepository {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.apiopen.top")
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(OkHttpClient.Builder().build())
        .build()

    private val newsApi = retrofit.create(NewsApi::class.java)

    override fun fetchNewsSingle(): Single<List<News>?> =
        newsApi.fetchNewsSingle(
            mapOf("page" to "1", "count" to "4")
        ).map { it.result }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
}