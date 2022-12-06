package com.bilibili.studio.search.base

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.core.transition.doOnEnd
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.bilibili.studio.search.SearchViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.kotlin.coroutine.flow.collectIn
import test.taylor.com.taylorcode.kotlin.coroutine.flow.collectLatestIn
import test.taylor.com.taylorcode.ui.night_mode.BaseActivity


/**
 * The base activity of search biz
 * Different search biz should have it's own search activity
 */
abstract class BaseSearchActivity<T : SearchViewState> : BaseActivity() {
    companion object {
        const val NAV_HOST_ID = "searchFragmentContainer"
        const val ID_CLEAR_SEARCH = "ivSearchClear"
        const val ID_SEARCH = "tvSearch"
    }

    /**
     * mmapID according to different biz
     */
    abstract val mmapID: String

    /**
     * create search bar view according to different biz
     */
    abstract val searchBar: View

    /**
     * create intents of search according to different biz
     */
    abstract val intents: Flow<SearchIntent>

    /**
     * a ViewModel which search biz settle in
     */
    abstract val searchViewModel: SearchViewModel<*, *>

    /**
     * show event according to [SearchEvent], which should be consumed only once
     */
    abstract fun showEvent(event: SearchEvent)

    /**
     * show UI state according to [SearchViewState]
     */
    abstract fun showState(state: T)

    /**
     * create [NavHostFragment] according to different biz
     */
    abstract fun onCreateNavHostFragment(@IdRes id: Int)

    open val contentView by lazy(LazyThreadSafetyMode.NONE) {
        LinearLayout {
            layout_width = match_parent
            layout_height = match_parent
            orientation = vertical
            background_color = "#0C0D14"
            fitsSystemWindows = true

            searchBar.also { addView(it) }

            FragmentContainerView {
                layout_id = NAV_HOST_ID
                layout_width = match_parent
                layout_height = match_parent
                onCreateNavHostFragment(NAV_HOST_ID.toLayoutId())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)

        searchViewModel
            .searchSharedState
            .collectIn(this, minActiveState = Lifecycle.State.CREATED) { showState(it as T) }

        searchViewModel
            .searchEvent
            .collectLatestIn(this, minActiveState = Lifecycle.State.CREATED) { showEvent(it) }

        window?.sharedElementEnterTransition?.doOnEnd {
            searchViewModel.send(SearchIntent.Init(mmapID))
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        intents
            .onEach(searchViewModel::send)
            .launchIn(lifecycleScope)
    }
}