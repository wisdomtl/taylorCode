package test.taylor.com.taylorcode.kotlin.coroutine.mvi

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
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
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import test.taylor.com.taylorcode.kotlin.ConstraintLayout
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.kotlin.coroutine.flow.collectIn
import test.taylor.com.taylorcode.kotlin.coroutine.mvvm.UserInfoModel
import test.taylor.com.taylorcode.kotlin.extension.contentView
import test.taylor.com.taylorcode.kotlin.layout_width
import test.taylor.com.taylorcode.kotlin.match_parent
import test.taylor.com.taylorcode.retrofit.NewsAdapter
import test.taylor.com.taylorcode.ui.ConstraintLayoutActivity
import test.taylor.com.taylorcode.ui.custom_view.progress_view.ProgressBar
import test.taylor.com.taylorcode.ui.recyclerview.variety.VarietyAdapter2

class StateFlowActivity : AppCompatActivity() {

    private val newsViewModel by lazy {
        ViewModelProvider(
            this,
            NewsViewModelFactory(NewsRepo(this))
        )[NewsViewModel::class.java]
    }

    private val newsAdapter2 by lazy {
        VarietyAdapter2().apply {
            addItemBuilder(NewsProxy())
        }
    }

    private lateinit var tv: TextView
    private lateinit var loadMoreTv: TextView
    private lateinit var rvNews: RecyclerView
    private var newsAdapter = NewsAdapter()

    /**
     * case: MVI flow style
     */
    private val intents by lazy {
        merge(
            flowOf(FeedsIntent.Init(1, 5)),
            loadMoreFlow(),
            reportFlow()
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

            loadMoreTv = TextView {
                layout_id = "tvChange2"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 40f
                textColor = "#000000"
                text = "load more"
                gravity = gravity_center
                top_toBottomOf = "tvChange"
                center_horizontal = true
            }

            rvNews = RecyclerView {
                layout_id = "rvNews"
                layout_width = match_parent
                layout_height = 0
                center_horizontal = true
                top_toBottomOf = "tvChange2"
                bottom_toBottomOf = parent_id
                layoutManager = LinearLayoutManager(this@StateFlowActivity)
                adapter = newsAdapter2
            }
        }
    }

    private fun loadMoreFlow() = callbackFlow {
        loadMoreTv.setOnClickListener {
            trySend(FeedsIntent.More(111L, 2))
        }
        awaitClose {
            loadMoreTv.setOnClickListener(null)
        }
    }

    private fun reportFlow() = callbackFlow {
        rvNews.setOnItemClickListener { view, i, fl, fl2 ->
            val news = newsAdapter2.dataList[i] as? test.taylor.com.taylorcode.retrofit.News
            news?.id?.let {
                trySend(FeedsIntent.Report(it))
            }
            false
        }

        awaitClose { }
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
         * case: MVI flow style with PartialChange and reduce
         */
//        newsViewModel.newsState
//            .collectIn(this) { showNews(it) }

        newsViewModel.newState2
            .collectIn(this) { showNews(it) }

        newsViewModel.eventFlow.collectIn(this) { showEvent(it) }
    }

    private fun showEvent(event: FeedsEvent) {
        when (event) {
            is FeedsEvent.Report.Result -> Toast.makeText(
                this@StateFlowActivity,
                event.reportToast,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onStart() {
        super.onStart()
    }

    private fun showUserName(userInfo: UserInfoModel) {
        if (userInfo.loading) {
            showLoading()
        } else {
            dismissLoading()
            tv.text = userInfo.userName
        }
    }

    private fun showNews(state: NewsState) {
        Log.v(
            "ttaylor",
            "showNews() state=${state}"
        )
        state.apply {
            if (isLoading) showLoading() else dismissLoading()
            if (isLoadingMore) showLoadingMore() else dismissLoadingMore()
            if (errorMessage.isNotEmpty()) tv.text = state.errorMessage
            if (data.isNotEmpty()) newsAdapter2.dataList = state.data
        }
    }
}

fun Activity.showLoadingMore() {
    contentView()?.apply {
        TextView {
            layout_id = "loading more"
            layout_width = wrap_content
            layout_height = wrap_content
            textSize = 50f
            textColor = "#00ff00"
            text = "loading more..."
            gravity = gravity_center
            layout_gravity2 = gravity_center
            shape = shape {
                solid_color = "#ff00ff"
                corner_radius = 20
            }
        }
    }
}

fun Activity.dismissLoadingMore() {
    val tv = contentView()?.find<TextView>("loading more")
    tv?.let { contentView()?.removeView(it) }
}

fun Activity.showLoading() {
    contentView()?.apply {
        ProgressBar {
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