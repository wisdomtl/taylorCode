package test.taylor.com.taylorcode.kotlin.coroutine.mvvm

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import test.taylor.com.taylorcode.kotlin.ConstraintLayout
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.kotlin.coroutine.collectIn
import test.taylor.com.taylorcode.kotlin.extension.contentView
import test.taylor.com.taylorcode.kotlin.layout_width
import test.taylor.com.taylorcode.kotlin.match_parent
import test.taylor.com.taylorcode.retrofit.NewsAdapter
import test.taylor.com.taylorcode.ui.ConstraintLayoutActivity

class StateFlowActivity : AppCompatActivity() {

    private val newsViewModel by lazy {
        ViewModelProvider(
            this,
            NewsViewModelFactory(NewsRepo(this))
        )[NewsViewModel::class.java]
    }

    private lateinit var tv: TextView
    private lateinit var rvNews: RecyclerView
    private var newsAdapter = NewsAdapter()

    /**
     * case: MVI flow style
     */
    private val intents by lazy {
        merge(
            flowOf(FeedsIntent.InitIntent(1, 5))
        )
    }

    private val contentView by lazy {
        ConstraintLayout {
            layout_id = "container"
            layout_width = match_parent
            layout_height = match_parent

            tv = TextView {
                layout_id = "tvChange"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 30f
                textColor = "#000000"
                top_toTopOf = parent_id
                start_toStartOf = parent_id
                text = "got to another activity"
                end_toEndOf = parent_id
                onClick = {
                    startActivity(
                        Intent(
                            this@StateFlowActivity,
                            ConstraintLayoutActivity::class.java
                        )
                    )
                }
            }

            rvNews = RecyclerView {
                layout_id = "rvNews"
                layout_width = match_parent
                layout_height = wrap_content
                center_horizontal = true
                top_toBottomOf = "tvChange"
                bottom_toBottomOf = parent_id
                layoutManager = LinearLayoutManager(this@StateFlowActivity)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            dataStore.edit { it[stringPreferencesKey("name")] = "local name" }
        }
        setContentView(contentView)

        /**
         * use StateFlow like LiveData
         */
//        userInfoViewModel.fetchUserInfo()
//        lifecycleScope.launch {
//            userInfoViewModel.userInfoStateFlow.collect {
//                if (it.loading) {
//                    showLoading()
//                } else {
//                    dismissLoading()
//                    tv.text = it.userName
//                }
//            }
//        }

//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.CREATED) {
//                userInfoViewModel.userInfoSharedFlow.collect {
//                    showUserName(it) }
//            }
//        }

//        newsViewModel.newsFlow.collectIn(this) { showNews(it) }

        /**
         * case: MVI flow style
         */
        intents
            .onEach(newsViewModel::send)
            .launchIn(lifecycleScope)

        /**
         * case: MVI flow style
         */
        newsViewModel.newsState
            .collectIn(this) { showNews(it) }
    }

    private fun showUserName(userInfo: UserInfoModel) {
        if (userInfo.loading) {
            showLoading()
        } else {
            dismissLoading()
            tv.text = userInfo.userName
        }
    }

    private fun showNews(newsModel: NewsState) {
        when {
            newsModel.loading -> {
                showLoading()
            }
            newsModel.errorMessage.isEmpty() -> {
                dismissLoading()
                newsAdapter.news = newsModel.news
                rvNews.adapter = newsAdapter
            }
            else -> {
                dismissLoading()
                tv.text = newsModel.errorMessage
            }
        }
    }
}

fun Activity.showLoading() {
    contentView()?.apply {
        ProgressBar2 {
            layout_id = "pb"
            layout_width = 50
            layout_height = 50
            layout_gravity2 = gravity_center
        }
    }
}

fun Activity.dismissLoading() {
    val pb = contentView()?.find<ProgressBar>("pb")
    pb?.let { contentView()?.removeView(it) }
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user-info")