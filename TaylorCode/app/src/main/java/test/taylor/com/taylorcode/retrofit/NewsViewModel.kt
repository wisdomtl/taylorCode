package test.taylor.com.taylorcode.retrofit

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class NewsViewModel : ViewModel() {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.apiopen.top")
        .addConverterFactory(MoshiConverterFactory.create())
        .client(OkHttpClient.Builder().build())
        .build()

    private val newsApi = retrofit.create(NewsApi::class.java)

    var newsLiveData = MutableLiveData<List<News>>()

    fun fetchJoke() {
        newsApi.fetchNews(
            mapOf(
                "page" to "1",
                "count" to "4"
            )
        ).enqueue(object : Callback<NewsBean> {
            override fun onFailure(call: Call<NewsBean>, t: Throwable) {
                Log.v("ttaylor", "tag=, NewsViewModel.onFailure()  ")
                newsLiveData.value = null
            }

            override fun onResponse(call: Call<NewsBean>, response: Response<NewsBean>) {
                Log.v("ttaylor", "tag=, NewsViewModel.onResponse()  news=${response.body()}")
                response.body()?.result?.let { newsLiveData.value = it }
            }
        })
    }
}