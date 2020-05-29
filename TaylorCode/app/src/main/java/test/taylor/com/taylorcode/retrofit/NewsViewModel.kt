package test.taylor.com.taylorcode.retrofit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * level 2: business is in ViewModel
 */
class NewsViewModel : ViewModel() {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.apiopen.top")
        .addConverterFactory(MoshiConverterFactory.create())
        .client(OkHttpClient.Builder().build())
        .build()

    private val newsApi = retrofit.create(NewsApi::class.java)

    var newsLiveData = MutableLiveData<List<News>>()

    fun fetchNews() {
        newsApi.fetchNews(
            mapOf(
                "page" to "1",
                "count" to "4"
            )
        ).enqueue(object : Callback<NewsBean> {
            override fun onFailure(call: Call<NewsBean>, t: Throwable) {
                newsLiveData.value = null
            }

            override fun onResponse(call: Call<NewsBean>, response: Response<NewsBean>) {
                response.body()?.result?.let { newsLiveData.value = it }
            }
        })
    }
}