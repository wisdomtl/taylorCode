//package com.bilibili.studio.search.template
//
//import android.annotation.SuppressLint
//import android.os.Bundle
//import android.text.InputType
//import android.view.MotionEvent
//import android.view.View
//import android.view.inputmethod.EditorInfo
//import android.widget.EditText
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.core.os.bundleOf
//import androidx.lifecycle.ViewModelProvider
//import androidx.navigation.findNavController
//import androidx.navigation.fragment.NavHostFragment
//import com.bcut.clickFlow
//import com.bcut.textChangeFlow
//import com.bilibili.baseui.extension.*
//import com.bilibili.lib.blrouter.Route
//import com.bilibili.lib.blrouter.Routes
//import com.bilibili.studio.constans.RouterConstant
//import com.bilibili.studio.report.StudioReport
//import com.bilibili.studio.search.R
//import com.bilibili.studio.search.SearchViewModel
//import com.bilibili.studio.search.SearchViewModelFactory
//import com.bilibili.studio.search.base.BaseSearchActivity
//import com.bilibili.studio.search.base.SearchEvent
//import com.bilibili.studio.search.base.SearchFrom
//import com.bilibili.studio.search.base.SearchIntent
//import com.bilibili.studio.search.template.data.*
//import com.bilibili.studio.search.template.repository.SearchRepository
//import com.bilibili.studio.utils.KeyboardUtils
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.debounce
//import kotlinx.coroutines.flow.merge
//import java.lang.reflect.Field
//
//@Routes(
//    value = [Route(
//        scheme = [RouterConstant.SCHEME_BCUT],
//        host = RouterConstant.HOST_STUDIO,
//        path = RouterConstant.PATH_TEMPLATE_SEARCH
//    )]
//)
//class TemplateSearchActivity : BaseSearchActivity<TemplateSearchViewState>() {
//
//    private lateinit var etSearch: EditText
//    private lateinit var tvSearch: TextView
//    private lateinit var ivClear: ImageView
//    private lateinit var ivBack: ImageView
//    private lateinit var vInputBg: View
//
//    override val searchBar: View
//        get() = ConstraintLayout {
//            layout_width = match_parent
//            layout_height = wrap_content
//
//            ivBack = ImageView {
//                layout_id = "ivSearchBack"
//                layout_width = 7
//                layout_height = 14
//                scaleType = scale_fit_xy
//                start_toStartOf = parent_id
//                top_toTopOf = parent_id
//                margin_start = 22
//                margin_top = 20
//                src = R.drawable.search_back
//                onClick = { onBackPressed() }
//            }
//
//            vInputBg = View {
//                layout_id = "vSearchBarBg"
//                layout_width = 0
//                layout_height = 36
//                start_toEndOf = "ivSearchBack"
//                align_vertical_to = "ivSearchBack"
//                end_toStartOf = ID_SEARCH
//                margin_start = 19.76
//                margin_end = 16
//                transitionName = "transitionInputBg"
//                shape = shape {
//                    corner_radius = 54
//                    solid_color = "#1AB8BCF1"
//                }
//            }
//
//            ImageView {
//                layout_id = "ivSearchIcon"
//                layout_width = 16
//                layout_height = 16
//                scaleType = scale_fit_xy
//                start_toStartOf = "vSearchBarBg"
//                align_vertical_to = "vSearchBarBg"
//                transitionName = "transitionSearchIcon"
//                margin_start = 16
//                src = R.drawable.template_search_icon
//            }
//
//            etSearch = EditText {
//                layout_id = "etSearch"
//                layout_width = 0
//                layout_height = wrap_content
//                start_toEndOf = "ivSearchIcon"
//                end_toStartOf = ID_CLEAR_SEARCH
//                align_vertical_to = "vSearchBarBg"
//                margin_start = 7
//                margin_end = 12
//                textSize = 14f
//                textColor = "#F2F4FF"
//                imeOptions = EditorInfo.IME_ACTION_SEARCH
//                hint = "输入您想搜索的模板"
//                hint_color = "#686A72"
//                background = null
//                maxLines = 1
//                transitionName = "transitionEtSearch"
//                inputType = InputType.TYPE_CLASS_TEXT
//                setOnEditorActionListener { v, actionId, event ->
//                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                        val input = etSearch.text?.toString() ?: ""
//                        if (input.isNotEmpty()) searchViewModel.send(SearchIntent.GotoSearchPage(input, SearchFrom.BUTTON))
//                        true
//                    } else false
//                }
//                try {
//                    val f: Field = android.widget.TextView::class.java.getDeclaredField("mCursorDrawableRes")
//                    f.isAccessible = true
//                    f.set(this, R.drawable.search_cursor_shape)
//                } catch (e: Exception) {
//                }
//            }
//
//            ivClear = ImageView {
//                layout_id = ID_CLEAR_SEARCH
//                layout_width = 20
//                layout_height = 20
//                scaleType = scale_fit_xy
//                align_vertical_to = "vSearchBarBg"
//                end_toEndOf = "vSearchBarBg"
//                margin_end = 12
//                src = R.drawable.template_search_clear
//                visibility = gone
//                onClick = {
//                    etSearch.clearFocus()
//                    searchViewModel.send(SearchIntent.ClearKeyword)
//                    searchViewModel.send(SearchIntent.PopBack)
//                }
//            }
//
//            tvSearch = BTextView {
//                layout_id = ID_SEARCH
//                layout_width = wrap_content
//                layout_height = wrap_content
//                textSize = 14f
//                textColor = "#686A72"
//                text = "搜索"
//                gravity = gravity_center
//                align_vertical_to = "ivSearchBack"
//                end_toEndOf = parent_id
//                margin_end = 16
//            }
//        }
//
//    override val intents: Flow<SearchIntent>
//        get() = merge(
//            etSearch.textChangeFlow { isUserInput, char -> SearchIntent.InputKeyword(char?.toString().orEmpty(), isUserInput) },
//            tvSearch.clickFlow { listOf(SearchIntent.GotoSearchPage(etSearch.text?.toString().orEmpty(), SearchFrom.BUTTON)) }
//        )
//    override val searchViewModel: SearchViewModel<*, *>
//        get() = ViewModelProvider(this, SearchViewModelFactory(SearchRepository(), TemplateSearchViewState.Init))[SearchViewModel::class.java]
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        initView()
//    }
//
//    override fun onBackPressed() {
//        super.onBackPressed()
//        searchViewModel.send(SearchIntent.PopBack)
//    }
//
//    @SuppressLint("ClickableViewAccessibility")
//    private fun initView() {
//        ivBack.expand(20, 20)
//        ivClear.expand(40, 40)
//
//        etSearch.setOnTouchListener { v, event ->
//            if (event.action == MotionEvent.ACTION_DOWN) {
//                if (etSearch.text.toString().isNotEmpty()) searchViewModel.send(SearchIntent.InputKeyword(etSearch.text.toString(), true))
//            }
//            false
//        }
//    }
//
//    override fun showEvent(event: SearchEvent) {
//        if (event is SearchEvent.ClearKeyword.NoKeyword) {
//            etSearch.text = null
//            etSearch.requestFocus()
//            KeyboardUtils.showSoftInputWithDelay(etSearch, 300)
//        }
//        if (event is SearchEvent.HideKeyboard.Hide) {
//            KeyboardUtils.hideSoftInput(etSearch)
//        }
//        if (event is SearchEvent.Init.ShowKeyboard) KeyboardUtils.showSoftInputWithDelay(etSearch, 300)
//        if (event is SearchEvent.GotoSearchPage.Navigate) {
//            runCatching {
//                findNavController(NAV_HOST_ID.toLayoutId()).navigate(
//                    R.id.action_to_result,
//                    bundleOf("keywords" to event.keyword)
//                )
//            }
//            etSearch.apply {
//                if (this.text.toString() != event.keyword) {
//                    clearFocus()
//                    setText(event.keyword, TextView.BufferType.EDITABLE)
//                }
//            }
//            KeyboardUtils.hideSoftInput(etSearch)
//            StudioReport.reportSearchButtonClick(event.keyword, event.searchFrom.typeInt)
//        }
//        if (event is SearchEvent.GoToHistory.Navigate) {
//            runCatching {
//                findNavController(NAV_HOST_ID.toLayoutId()).navigate(R.id.action_to_history)
//            }
//        }
//        if (event is SearchEvent.GotoHintPage.Navigate) {
//            runCatching {
//                findNavController(NAV_HOST_ID.toLayoutId()).navigate(R.id.action_to_hint)
//            }
//        }
//        if (event is SearchEvent.PopBack.Pop) {
//            runCatching { findNavController(NAV_HOST_ID.toLayoutId()).popBackStack() }
//            if (etSearch.text.isNotEmpty()) {
//                etSearch.clearFocus()
//                KeyboardUtils.hideSoftInput(etSearch)
//                etSearch.text = null
//            }
//        }
//    }
//
//    override fun showState(state: TemplateSearchViewState) {
//        tvSearch.apply {
//            textColor = if (state.highlightSearch) "#F2F4FF" else "#484951"
//            isEnabled = state.highlightSearch
//            tvSearch.visibility = if (state.hideSearch) gone else visible
//        }
//        ivClear.visibility = if (state.showClear) visible else gone
//        vInputBg.apply {
//            if (state.hideSearch) end_toEndOf = parent_id
//            else end_toStartOf = ID_SEARCH
//        }
//    }
//
//    override fun onCreateNavHostFragment(id: Int) {
//        NavHostFragment.create(R.navigation.search_navigation).also {
//            supportFragmentManager.beginTransaction()
//                .replace(id, it)
//                .setPrimaryNavigationFragment(it)
//                .commit()
//        }
//    }
//
//    /**
//     * mmapID according to different biz
//     */
//    override val mmapID: String
//        get() = SearchViewModel.TEMPLATE_MMAPID
//}