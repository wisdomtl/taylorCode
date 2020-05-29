package test.taylor.com.taylorcode.retrofit.repository_livedata

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import test.taylor.com.taylorcode.retrofit.News
import test.taylor.com.taylorcode.retrofit.NewsApi
import test.taylor.com.taylorcode.retrofit.NewsBean
import test.taylor.com.taylorcode.retrofit.repository_livedata.LiveDataCallAdapterFactory
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

    override fun fetchNewsLiveData(): LiveData<List<News>?> =
        newsApi.fetchNewsLiveData(
            mapOf("page" to "1", "count" to "4")
        ).let {
            Log.v("ttaylor","tag=, NewsRepositoryImpl.fetchNewsLiveData()  ")
            Transformations.map(it) { input: ApiResponse<NewsBean>? -> when(input){
                is ApiSuccessResponse -> input.body.result
                else -> null
            } }
        }
}