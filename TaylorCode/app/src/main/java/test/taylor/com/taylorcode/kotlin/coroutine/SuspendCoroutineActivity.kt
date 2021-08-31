package test.taylor.com.taylorcode.kotlin.coroutine

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import test.taylor.com.taylorcode.retrofit.News
import test.taylor.com.taylorcode.retrofit.NewsApi
import test.taylor.com.taylorcode.retrofit.NewsBean
import kotlin.coroutines.suspendCoroutine

class SuspendCoroutineActivity : AppCompatActivity() {
    private val mainScope = MainScope()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.apiopen.top")
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(OkHttpClient.Builder().build())
        .build()

    private val newsApi = retrofit.create(NewsApi::class.java)

    fun fetchNewsSingle(): Single<List<News>?> =
        newsApi.fetchNewsSingle(
            mapOf("page" to "1", "count" to "4")
        ).map { it.result }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        fetchNewsSingle().subscribe(
//            { news ->
//                Log.v("ttaylor", "tag=asdf, suspendCoroutineActivity.onCreate()  news.size=${news?.size}")
//            },
//            { error ->
//                Log.v("ttaylor", "tag=asdf, suspendCoroutineActivity.onCreate()  error")
//            }
//        )

        mainScope.launch {
            val news = fetchNew2()
            Log.v("ttaylor", "tag=asdf, SuspendCoroutineActivity.onCreate()  news.size=${news.size}")
        }
    }

    /**
     * convert callback into suspend function
     * suspendCoroutine() obtain the current
     */
    suspend fun fetchNew2() = suspendCoroutine<List<News>> { continuation ->
        newsApi.fetchNews(mapOf("page" to "1", "count" to "4")).enqueue(object : Callback<NewsBean> {
            override fun onFailure(call: Call<NewsBean>, t: Throwable) {
                continuation.resumeWith(Result.failure(t))
            }

            override fun onResponse(call: Call<NewsBean>, response: Response<NewsBean>) {
                response.body()?.result?.let {
                    continuation.resumeWith(Result.success(it))
                }
            }
        })
    }

    suspend fun fetchNew3() = suspendCoroutine<List<News>?> { continuation ->
        newsApi.fetchNews(mapOf("page" to "1", "count" to "4")).execute().let { response ->
            if (response.isSuccessful) {
                response.body()?.result?.let {
                    continuation.resumeWith(Result.success(it))
                } ?: also { continuation.resumeWith(Result.success(null)) }
            } else {
                response.errorBody()?.let {
                    continuation.resumeWith(Result.failure(Throwable(it.toString())))
                }
            }
        }
    }
}