package test.taylor.com.taylorcode.kotlin.coroutine.mvvm

import android.content.Context
import android.os.StrictMode
import android.util.Log
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import test.taylor.com.taylorcode.retrofit.News
import test.taylor.com.taylorcode.retrofit.NewsBean
import test.taylor.com.taylorcode.retrofit.NewsFlowWrapper
import test.taylor.com.taylorcode.retrofit.repository_livedata.room.NewsDatabase

class NewsRepo(context: Context) {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.apiopen.top")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
    }

    private val newApi by lazy { retrofit.create(NewsApi::class.java) }
    private val newDatabase = NewsDatabase.getInstance(context)
    private val newsDao = newDatabase.newsDao()
    private val dataStore = context.dataStore

    val remoteUserInfoFlow = flow {
        delay(2000)
        emit(UserInfo("remote name"))
    }

    val localUserInfoFlow =
        dataStore.data
            .map { UserInfo(it[stringPreferencesKey("name")] ?: "") }
            .onEach { delay(1000) }


    val localNewsOneShotFlow = flow {
        val news = newsDao.queryNewsSuspend()
        val newsList = news.map { News(it.path, it.image, it.title, it.passtime) }
        Log.v("ttaylor", "[collect news] local one shot flow =${news.size}")
        emit(NewsFlowWrapper(newsList, false))
    }

    /**
     * case: retrofit request/response is one-shot, so the api return value is not supposed to be Flow,
     * wrap the enqueue callback with callbackFlow
     */
//    val remoteNewsFlow = callbackFlow {
//        newApi.fetchNews(mapOf("page" to "1", "count" to "8")).enqueue(object : Callback<NewsBean> {
//            override fun onResponse(call: Call<NewsBean>, response: Response<NewsBean>) {
//                if (response.isSuccessful && response.body() != null) {
//                    trySend(response.body()!!)
//                } else {
//                    throw Throwable()
//                }
//            }
//
//            override fun onFailure(call: Call<NewsBean>, t: Throwable) {
//                throw t
//            }
//        })
//        awaitClose {  }
//    }.map { newsBean ->
//        Log.v("ttaylor", "remote new flow()")
//        if (newsBean.code == 200) {
//            if (!newsBean.result.isNullOrEmpty()) {
//                newsDao.deleteAllNews()
//                newsDao.insertAll(newsBean.result.map { it.toNews() })
//                newsBean.result
//            } else {
//                emptyList()
//            }
//        } else {
//            throw Exception(newsBean.message)
//        }
//    }

    val remoteNewsFlow = flow {
        val newsBean = newApi.fetchNews(mapOf("page" to "1", "count" to "8"))
        Log.v("ttaylor", "[collect news] remote new flow thread=${Thread.currentThread().id}")
        emit(newsBean)
    }.map { newsBean ->
        if (newsBean.code == 200) {
            if (!newsBean.result.isNullOrEmpty()) {
                newsDao.deleteAllNews()
                newsDao.insertAll(newsBean.result.map { it.toNews() })
                NewsFlowWrapper(newsBean.result, true)
            } else {
                NewsFlowWrapper(emptyList(), false)
            }
        } else {
            throw Exception(newsBean.message)
        }
    }
}

