package test.taylor.com.taylorcode.retrofit.repository_livedata

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import test.taylor.com.taylorcode.retrofit.News
import test.taylor.com.taylorcode.retrofit.NewsApi
import test.taylor.com.taylorcode.retrofit.NewsBean
import test.taylor.com.taylorcode.retrofit.repository_livedata.room.NewsDatabase
import test.taylor.com.taylorcode.util.print
import java.util.concurrent.Executors

class NewsRepositoryImpl(context: Context) : NewsRepository {
    private val TAG = "NewsRepositoryImpl"
    private val retrofit = Retrofit.Builder()
            .baseUrl("https://api.apiopen.top")
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .client(OkHttpClient.Builder().build())
            .build()

    private val newsApi = retrofit.create(NewsApi::class.java)

    private var executor = Executors.newSingleThreadExecutor()
    private var newsDatabase = NewsDatabase.getInstance(context)
    private var newsDao = newsDatabase.newsDao()


    private var newsLiveData = MediatorLiveData<List<News>>()

    override fun fetchNewsLiveData(): LiveData<List<News>?> {
        val localNews = newsDao.queryNews()

        val remoteNews = newsApi.fetchNewsLiveData(
                mapOf("page" to "1", "count" to "4")
        ).let {
            Transformations.map(it) { response: ApiResponse<NewsBean>? ->
                when (response) {
                    is ApiSuccessResponse -> {
                        val news = response.body.result
                        news?.map { it.toNews() }?.let {
                            Log.d(TAG, "fetchNewsLiveData: add new data into db")
                            executor.submit { newsDao.insertAll(it) }
                        }
                        news
                    }
                    else -> null
                }
            }
        }
        newsLiveData.addSource(localNews) { news ->
            Log.d(TAG, "fetchNewsLiveData: hit db news=${news?.print { it.toString() }}")
            newsLiveData.value = news?.map { News(it.path, it.image, it.title, it.passtime) }
        }

        newsLiveData.addSource(remoteNews) {
            newsLiveData.value = it
        }

        return newsLiveData
    }
}