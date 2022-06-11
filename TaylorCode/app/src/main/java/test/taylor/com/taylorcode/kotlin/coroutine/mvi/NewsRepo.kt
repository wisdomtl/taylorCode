package test.taylor.com.taylorcode.kotlin.coroutine.mvi

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import test.taylor.com.taylorcode.kotlin.coroutine.mvvm.NewsApi
import test.taylor.com.taylorcode.kotlin.coroutine.mvvm.UserInfo
import test.taylor.com.taylorcode.retrofit.News
import test.taylor.com.taylorcode.retrofit.NewsBean
import test.taylor.com.taylorcode.retrofit.NewsFlowWrapper
import test.taylor.com.taylorcode.retrofit.repository_livedata.room.NewsDatabase

class NewsRepo(context: Context) {

    private var count = -1

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
        val newsList = news.map { News(it.path, it.image, it.title, it.passtime,0L) }
        Log.v("ttaylor", "[collect news] local one shot flow =${news.size}")
        delay(1000)
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

    fun reportNews(id:Long) =
        suspend {
            delay(1000)
            id
        }.asFlow()

    fun remoteNewsFlow(page: String, count: String) =
        suspend {
//            val newsBean = newApi.fetchNews(mapOf("page" to page, "count" to count))
//            Log.v("ttaylor", "[collect news] remote new flow thread=${Thread.currentThread().id}")
//            newsBean
            delay(2000)
            getLocalNewsBean()
        }
            .asFlow()
//            .catch {
//                delay(2000)
//                emit(getLocalNewsBean())
//            }
            .map { newsBean ->
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

    fun getLocalNewsBean() = NewsBean(
        200, "success", listOf(
            News(
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fppt.chnlib.com%2FFileUpload%2F2018-11%2F7-Cai_Se_Re_1i_1iu_Gao-110740_129.png&refer=http%3A%2F%2Fppt.chnlib.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1654343732&t=06d9d1091d3bf3ff9211e0cb27e0afe0",
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fppt.chnlib.com%2FFileUpload%2F2018-11%2F7-Cai_Se_Re_1i_1iu_Gao-110740_129.png&refer=http%3A%2F%2Fppt.chnlib.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1654343732&t=06d9d1091d3bf3ff9211e0cb27e0afe0",
                "title${count++}", System.currentTimeMillis().toString(),count.toLong()
            ),
            News(
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fppt.chnlib.com%2FFileUpload%2F2018-11%2F7-Cai_Se_Re_1i_1iu_Gao-110740_129.png&refer=http%3A%2F%2Fppt.chnlib.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1654343732&t=06d9d1091d3bf3ff9211e0cb27e0afe0",
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fppt.chnlib.com%2FFileUpload%2F2018-11%2F7-Cai_Se_Re_1i_1iu_Gao-110740_129.png&refer=http%3A%2F%2Fppt.chnlib.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1654343732&t=06d9d1091d3bf3ff9211e0cb27e0afe0",
                "title${count++}", System.currentTimeMillis().toString(),count.toLong()
            ),
            News(
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fppt.chnlib.com%2FFileUpload%2F2018-11%2F7-Cai_Se_Re_1i_1iu_Gao-110740_129.png&refer=http%3A%2F%2Fppt.chnlib.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1654343732&t=06d9d1091d3bf3ff9211e0cb27e0afe0",
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fppt.chnlib.com%2FFileUpload%2F2018-11%2F7-Cai_Se_Re_1i_1iu_Gao-110740_129.png&refer=http%3A%2F%2Fppt.chnlib.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1654343732&t=06d9d1091d3bf3ff9211e0cb27e0afe0",
                "title${count++}", System.currentTimeMillis().toString(),count.toLong()
            ),
            News(
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fppt.chnlib.com%2FFileUpload%2F2018-11%2F7-Cai_Se_Re_1i_1iu_Gao-110740_129.png&refer=http%3A%2F%2Fppt.chnlib.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1654343732&t=06d9d1091d3bf3ff9211e0cb27e0afe0",
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fppt.chnlib.com%2FFileUpload%2F2018-11%2F7-Cai_Se_Re_1i_1iu_Gao-110740_129.png&refer=http%3A%2F%2Fppt.chnlib.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1654343732&t=06d9d1091d3bf3ff9211e0cb27e0afe0",
                "title${count++}", System.currentTimeMillis().toString(),count.toLong()
            ),
            News(
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fppt.chnlib.com%2FFileUpload%2F2018-11%2F7-Cai_Se_Re_1i_1iu_Gao-110740_129.png&refer=http%3A%2F%2Fppt.chnlib.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1654343732&t=06d9d1091d3bf3ff9211e0cb27e0afe0",
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fppt.chnlib.com%2FFileUpload%2F2018-11%2F7-Cai_Se_Re_1i_1iu_Gao-110740_129.png&refer=http%3A%2F%2Fppt.chnlib.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1654343732&t=06d9d1091d3bf3ff9211e0cb27e0afe0",
                "title${count++}", System.currentTimeMillis().toString(),count.toLong()
            ),
            News(
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fppt.chnlib.com%2FFileUpload%2F2018-11%2F7-Cai_Se_Re_1i_1iu_Gao-110740_129.png&refer=http%3A%2F%2Fppt.chnlib.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1654343732&t=06d9d1091d3bf3ff9211e0cb27e0afe0",
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fppt.chnlib.com%2FFileUpload%2F2018-11%2F7-Cai_Se_Re_1i_1iu_Gao-110740_129.png&refer=http%3A%2F%2Fppt.chnlib.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1654343732&t=06d9d1091d3bf3ff9211e0cb27e0afe0",
                "title${count++}", System.currentTimeMillis().toString(),count.toLong()
            ),
        )
    )
}

