package test.taylor.com.taylorcode.retrofit.god_activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.retrofit.NewsAdapter
import test.taylor.com.taylorcode.retrofit.NewsApi
import test.taylor.com.taylorcode.retrofit.NewsBean

/**
 * god activity
 */
class GodActivity:AppCompatActivity() {

    private var rvNews: RecyclerView? = null

    private var newsAdapter = NewsAdapter()

    private val rootView by lazy {
        ConstraintLayout {
            TextView {
                layout_id = "tvTitle"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 25f
                padding_start = 20
                padding_end = 20
                center_horizontal = true
                text = "News"
                top_toTopOf = parent_id
            }

            rvNews = RecyclerView {
                layout_id = "rvNews"
                layout_width = match_parent
                layout_height = wrap_content
                top_toBottomOf = "tvTitle"
                margin_top = 10
                center_horizontal = true
            }
        }
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.apiopen.top")
        .addConverterFactory(MoshiConverterFactory.create())
        .client(OkHttpClient.Builder().build())
        .build()

    private val newsApi = retrofit.create(NewsApi::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(rootView)
        initView()
        initData()
    }

    private fun initData() {
       fetchJoke()
    }

    private fun initView() {
        rvNews?.layoutManager = LinearLayoutManager(this)
    }

    private fun fetchJoke() {
        newsApi.fetchNews(
            mapOf(
                "page" to "1",
                "count" to "4"
            )
        ).enqueue(object : Callback<NewsBean> {
            override fun onFailure(call: Call<NewsBean>, t: Throwable) {
                Toast.makeText(this@GodActivity,"network error",Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<NewsBean>, response: Response<NewsBean>) {
                response.body()?.result?.let {
                    newsAdapter.news = it
                    rvNews?.adapter = newsAdapter
                }
            }
        })
    }
}