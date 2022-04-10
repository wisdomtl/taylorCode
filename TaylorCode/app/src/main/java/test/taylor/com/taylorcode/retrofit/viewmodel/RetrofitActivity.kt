package test.taylor.com.taylorcode.retrofit.viewmodel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.retrofit.NewsAdapter

class RetrofitActivity : AppCompatActivity() {

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

    private val newsViewModel by lazy { ViewModelProvider(this).get(NewsViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(rootView)
        initView()
        initData()
    }

    private fun initData() {
        newsViewModel.newsLiveData.observe(this, Observer {
            newsAdapter.news = it
            rvNews?.adapter = newsAdapter
        })
        newsViewModel.fetchNews()
    }

    private fun initView() {
        rvNews?.layoutManager = LinearLayoutManager(this)
    }
}