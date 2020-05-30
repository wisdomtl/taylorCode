package test.taylor.com.taylorcode.retrofit.repository_livedata

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import test.taylor.com.taylorcode.retrofit.News
import test.taylor.com.taylorcode.retrofit.NewsApi
import test.taylor.com.taylorcode.retrofit.NewsBean
import test.taylor.com.taylorcode.retrofit.repository_livedata.room.NewsDatabase
import java.util.concurrent.Executors

class NewsRepositoryImpl(context: Context) : NewsRepository {
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


    /**
     * todo: network data parse is here?
     */
    override fun fetchNewsLiveData(): LiveData<List<News>?> {
        newsDao.queryNews()

        return newsApi.fetchNewsLiveData(
            mapOf("page" to "1", "count" to "4")
        ).let {
            Transformations.map(it) { input: ApiResponse<NewsBean>? ->
                when (input) {
                    is ApiSuccessResponse -> input.body.result
                    else -> null
                }
            }
        }

    }
}