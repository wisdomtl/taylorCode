//package com.bilibili.studio.search.material.fragment
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.fragment.app.activityViewModels
//import androidx.lifecycle.Lifecycle
//import androidx.recyclerview.widget.RecyclerView
//import com.bcut.collectIn
//import com.bcut.homepage.Extras
//import com.bilibili.baseui.extension.*
//import com.bilibili.studio.module.course.listener.EndlessScrollListener
//import com.bilibili.studio.search.SearchViewModel
//import com.bilibili.studio.search.base.BaseSearchFragment
//import com.bilibili.studio.search.base.SearchIntent
//import com.bilibili.studio.search.material.data.MaterialSearchViewState
//import com.bilibili.studio.search.material.data.MaterialTabListBeanWrapper
//import com.bilibili.studio.search.material.mapper.HAS_NO_MORE
//import com.bilibili.studio.search.material.mapper.materialSearchMoreMapper
//import com.bilibili.studio.search.material.repository.EmptyMaterialTabListBean
//
///**
// * The father of search fragment,
// * There is a [SearchViewModel] here, search fragment should use it to send [SearchIntent]
// */
//abstract class BaseSearchResultFragment : BaseSearchFragment() {
//
//    private val keywords by Extras("keywords", "")
//
//    abstract val materialType: Int
//    override val searchViewModel: SearchViewModel<MaterialTabListBeanWrapper, MaterialSearchViewState> by activityViewModels()
//
//    protected lateinit var recyclerView: RecyclerView
//    protected lateinit var tvEmpty: TextView
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        searchViewModel.searchSharedState.collectIn(viewLifecycleOwner,
//            minActiveState = Lifecycle.State.RESUMED) {
//            if (it.materials?.materialType == materialType) {
////                tvEmpty.visibleOrGone(!searchViewModel.hasMore(materialType))
//                show(it.materials)
//            }
//        }
//        tvEmpty.visibleOrGone(false)
//        show(searchViewModel.bean)
//
//        recyclerView.addOnScrollListener(object :
//            EndlessScrollListener(recyclerView.layoutManager) {
//            override fun onLoadMore(totalItemsCount: Int, view: RecyclerView) {
//                checkSearchMore()
//            }
//        })
//    }
//
//    protected open fun show(bean: MaterialTabListBeanWrapper?) {}
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?,
//    ): View? {
//        return LinearLayout {
//            layout_width = match_parent
//            layout_height = match_parent
//            orientation = vertical
//
//            tvEmpty = BTextView {
//                layout_id = "tvEmpty"
//                layout_width = wrap_content
//                layout_height = wrap_content
//                textSize = 12f
//                textColor = "#686A72"
//                text = HAS_NO_MORE
//                gravity = gravity_center
//                center_horizontal = true
//                margin_top = 24
//                margin_bottom = 20
//                visibility = gone
//            }
//
//            recyclerView = RecyclerView {
//                layout_id = "rlRecyclerView"
//                layout_width = match_parent
//                layout_height = match_parent
//                margin_start = 9.5
//                margin_end = 9.5
//                overScrollMode = 2
//            }
//        }
//    }
//
//    protected open fun checkSearchMore() {
//        if (searchViewModel.hasMore(materialType) && !searchViewModel.isLoading(materialType)) {
//            searchViewModel.send(SearchIntent.SearchByMaterialType(keywords,
//                materialType,
//                materialSearchMoreMapper))
//        }
//    }
//}