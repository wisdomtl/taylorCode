//package com.bilibili.studio.search.material.fragment
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.MotionEvent
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.activityViewModels
//import androidx.lifecycle.Lifecycle
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.bcut.collectIn
//import com.bilibili.baseui.extension.*
//import com.bilibili.studio.search.SearchViewModel
//import com.bilibili.studio.search.base.BaseSearchFragment
//import com.bilibili.studio.search.base.SearchFrom
//import com.bilibili.studio.search.base.SearchIntent
//import com.bilibili.studio.search.material.data.MaterialSearchViewState
//import com.bilibili.studio.search.material.data.MaterialTabListBeanWrapper
//import com.bilibili.studio.search.material.ui.MaterialSearchHintItemBuilder
//import com.bilibili.studio.search.template.data.SearchHint
//import com.bilibili.studio.search.template.ui.SearchHintItemBuilder
//import taylor.com.varietyadapter.VarietyAdapter
//import test.taylor.com.taylorcode.kotlin.match_parent
//import test.taylor.com.taylorcode.ui.recyclerview.variety.VarietyAdapter
//
//class MaterialSearchHintFragment : BaseSearchFragment() {
//
//    private lateinit var rvHints: RecyclerView
//
//    private val contentView by lazy(LazyThreadSafetyMode.NONE) {
//        ConstraintLayout {
//            layout_width = match_parent
//            layout_height = match_parent
//            margin_horizontal = 16
//
//            rvHints = RecyclerView {
//                layout_width = match_parent
//                layout_height = match_parent
//                margin_top = 11
//                adapter = hintsAdapter
//                layoutManager = LinearLayoutManager(context)
//                setOnTouchListener { v, event ->
//                    if(event.action == MotionEvent.ACTION_DOWN) {
//                        searchViewModel.send(SearchIntent.HideKeyboard)
//                    }
//                    false
//                }
//            }
//        }
//    }
//
//    private val hintsAdapter by lazy(LazyThreadSafetyMode.NONE) {
//        VarietyAdapter().apply { addItemBuilder(MaterialSearchHintItemBuilder()) }
//    }
//    override val searchViewModel: SearchViewModel<*, *> by activityViewModels<SearchViewModel<MaterialTabListBeanWrapper, MaterialSearchViewState>>()
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        return contentView
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        initView()
//
//        searchViewModel
//            .searchState
//            .collectIn(viewLifecycleOwner, minActiveState = Lifecycle.State.CREATED) { showState(it as MaterialSearchViewState) }
//    }
//
//    private fun initView() {
//        rvHints.setOnItemClickListener { view, index, x, y ->
//            val searchHint = hintsAdapter.dataList[index] as SearchHint
//            searchViewModel.send(
//                SearchIntent.GotoSearchPage(searchHint.hint, SearchFrom.HINT)
//            )
//            false
//        }
//    }
//
//    private fun showState(state: MaterialSearchViewState) {
//        hintsAdapter.dataList = state.hints
//    }
//}