package test.taylor.com.taylorcode.retrofit.presenter

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import test.taylor.com.taylorcode.retrofit.NewsApi
import test.taylor.com.taylorcode.retrofit.NewsBean
import test.taylor.com.taylorcode.retrofit.repository_livedata.room.NewsDatabase
import java.util.concurrent.Executors

class NewsPresenter(var newsView: NewsView): NewsBusiness {

    private val retrofit = Retrofit.Builder()
            .baseUrl("https://api.apiopen.top")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()

    private val newsApi = retrofit.create(NewsApi::class.java)

    private var executor = Executors.newSingleThreadExecutor()
    private var newsDatabase = NewsDatabase.getInstance(newsView.newsContext)
    private var newsDao = newsDatabase.newsDao()

    override fun fetchNews() {
        newsApi.fetchNews(
                mapOf("page" to "1", "count" to "4")
        ).enqueue(object : Callback<NewsBean> {
            override fun onFailure(call: Call<NewsBean>, t: Throwable) {
                newsView.showNews(null)
            }

            override fun onResponse(call: Call<NewsBean>, response: Response<NewsBean>) {
                response.body()?.result?.let { newsView.showNews(it) }
            }
        })
    }
}