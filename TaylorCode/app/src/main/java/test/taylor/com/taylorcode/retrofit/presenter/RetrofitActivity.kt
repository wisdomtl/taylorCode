package test.taylor.com.taylorcode.retrofit.presenter

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.retrofit.News
import test.taylor.com.taylorcode.retrofit.NewsAdapter

class RetrofitActivity : AppCompatActivity(), NewsView {

    private val newsBusiness = NewsPresenter(this)

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(rootView)
        initView()
        newsBusiness.fetchNews()
    }

    private fun initView() {
        rvNews?.layoutManager = LinearLayoutManager(this)
    }

    override fun showNews(news: List<News>?) {
        newsAdapter.news = news
        rvNews?.adapter = newsAdapter
    }

    override val newsContext: Context
        get() = this
}