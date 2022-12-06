//package com.bilibili.studio.search.template.fragment
//
//import android.net.Uri
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import android.widget.Toast
//import androidx.fragment.app.activityViewModels
//import androidx.lifecycle.Lifecycle
//import androidx.recyclerview.widget.RecyclerView
//import androidx.recyclerview.widget.StaggeredGridLayoutManager
//import com.bcut.collectIn
//import com.bcut.homepage.Extras
//import com.bilibili.baseui.extension.*
//import com.bilibili.droid.ToastHelper
//import com.bilibili.lib.blrouter.BLRouter
//import com.bilibili.lib.blrouter.RouteRequest
//import com.bilibili.studio.constans.RouterConstant
//import com.bilibili.studio.report.StudioReport
//import com.bilibili.studio.search.R
//import com.bilibili.studio.search.template.ui.GapItemDecoration
//import com.bilibili.studio.search.SearchViewModel
//import com.bilibili.studio.search.base.BaseSearchFragment
//import com.bilibili.studio.search.base.SearchEvent
//import com.bilibili.studio.search.base.SearchIntent
//import com.bilibili.studio.search.template.data.*
//import com.bilibili.studio.search.template.mapper.NETWORK_ERROR_STRING
//import com.bilibili.studio.search.template.mapper.templateSearchMapper
//import com.bilibili.studio.search.template.mapper.templateSearchMoreMapper
//import com.bilibili.studio.search.template.ui.SearchResultItemBuilder
//import test.taylor.com.taylorcode.ui.line_feed_layout.getOrZero
//import com.tab.bean.TemplateTabItemBean
//import com.tab.event.TemplateTabCollectionStateEvent
//import com.tab.event.TemplateTabGetInfoEvent
//import com.tab.event.TemplateTabGetInfoSucceedEvent
//import com.tab.event.TemplateTabScrollEvent
//import com.tab.presenter.TemplateTabConstants
//import org.greenrobot.eventbus.EventBus
//import org.greenrobot.eventbus.Subscribe
//import org.greenrobot.eventbus.ThreadMode
//import taylor.com.varietyadapter.VarietyAdapter
//import test.taylor.com.taylorcode.kotlin.match_parent
//
//class SearchResultFragment : BaseSearchFragment() {
//
//    private val keywords by Extras("keywords", "")
//    private lateinit var rvSearch: RecyclerView
//    private lateinit var tvEmpty: TextView
//    private lateinit var networkErrorContainer: View
//    private lateinit var tvError: TextView
//    private var hasMoreData = true
//
//    override val searchViewModel: SearchViewModel<*, *> by activityViewModels<SearchViewModel<TemplateTabListBeanWrapper, TemplateSearchViewState>>()
//
//    private val contentView by lazy(LazyThreadSafetyMode.NONE) {
//        ConstraintLayout {
//            layout_width = match_parent
//            layout_height = match_parent
//
//            tvEmpty = BTextView {
//                layout_id = "tvEmpty"
//                layout_width = wrap_content
//                layout_height = wrap_content
//                textSize = 12f
//                textColor = "#686A72"
//                text = "暂无搜索结果，为您推荐以下模板"
//                gravity = gravity_center
//                center_horizontal = true
//                top_toTopOf = parent_id
//                margin_top = 24
//                margin_bottom = 20
//                bottom_toTopOf = "rvTemplates"
//                visibility = gone
//            }
//
//            rvSearch = RecyclerView {
//                layout_id = "rvTemplates"
//                layout_width = match_parent
//                layout_height = 0
//                adapter = searchResultAdapter
//                top_toBottomOf = "tvEmpty"
//                bottom_toBottomOf = parent_id
//                margin_start = 9.5
//                margin_end = 9.5
//                margin_top = 4
//                addItemDecoration(GapItemDecoration(6.5.dp, 6.5.dp, 24.dp))
//                layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
//            }
//
//            networkErrorContainer = ConstraintLayout {
//                layout_id = "networkErrorContainer"
//                layout_width = match_parent
//                layout_height = match_parent
//                visibility = gone
//
//                ImageView {
//                    layout_id = "ivNetworkError"
//                    layout_width = match_parent
//                    layout_height = 250
//                    src = R.drawable.template_ic_network_error
//                    scaleType = scale_fit_xy
//                    center_horizontal = true
//                    top_toTopOf = parent_id
//                    vertical_bias = 0.38f
//                    bottom_toTopOf = "tvNetworkError"
//                    vertical_chain_style = packed
//                }
//
//                tvError = BTextView {
//                    layout_id = "tvNetworkError"
//                    layout_width = wrap_content
//                    layout_height = wrap_content
//                    textSize = 14f
//                    textColor = "#ffffff"
//                    gravity = gravity_center
//                    text = "网络异常，请检查网络"
//                    top_toBottomOf = "ivNetworkError"
//                    bottom_toTopOf = "tvRefresh"
//                    center_horizontal = true
//                    vertical_chain_style = packed
//                }
//
//                BTextView {
//                    layout_id = "tvRefresh"
//                    layout_width = 88
//                    layout_height = 32
//                    textSize = 14f
//                    textColor = "#ffffff"
//                    text = "重试"
//                    gravity = gravity_center
//                    padding_horizontal = 10
//                    padding_vertical = 5
//                    top_toBottomOf = "tvNetworkError"
//                    bottom_toBottomOf = parent_id
//                    vertical_chain_style = packed
//                    center_horizontal = true
//                    margin_top = 38
//                    shape = shape {
//                        solid_color = "#FF6490"
//                        corner_radius = 16
//                    }
//                    onClick = {
//                        searchViewModel.send(SearchIntent.Search(keywords, templateSearchMapper))
//                    }
//                }
//            }
//        }
//    }
//
//    private val searchResultAdapter by lazy {
//        VarietyAdapter().apply {
//            addItemBuilder(SearchResultItemBuilder())
//            onPreload = {
//                searchViewModel.send(SearchIntent.SearchMore(keywords, templateSearchMoreMapper))
//            }
//
//            action = { it ->
//                val data = it as? Pair<Int, TemplateTabItemBean>
//                data?.let { it ->
//                    val templateTabItemBean = it.second
//                    templateTabItemBean ?: return@let
//                    val templateType = StudioReport.getTemplateType(templateTabItemBean?.type ?: -1, templateTabItemBean?.templateFrom ?: -1)
//                    if (templateTabItemBean.is_operating == 1) {
//                        StudioReport.reportCategoryActivityShow(
//                            templateTabItemBean.id.toString(),
//                            templateTabItemBean.name ?: "",
//                            it.first.toString(),
//                            templateTabItemBean.imgCnt ?: 0,
//                            templateTabItemBean.videoCnt ?: 0
//                        )
//
//                    } else {
//                        StudioReport.reportTemplateItemShow(
//                            templateTabItemBean.id?.toString() ?: "",
//                            "",
//                            "",
//                            "search",
//                            it.first,
//                            templateTabItemBean.op_tag ?: "",
//                            templateType,
//                            templateTabItemBean?.imgCnt ?: 0,
//                            templateTabItemBean?.videoCnt ?: 0,
//                            (searchViewModel.bean as? TemplateTabListBeanWrapper)?.templateTabListBean?.exp?.exp_grp?.toString().orEmpty(),
//                            if (templateTabItemBean.fav == 1) "我的收藏" else ""
//                        )
//                    }
//                }
//            }
//        }
//    }
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        return contentView
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        searchViewModel.send(SearchIntent.Search(keywords, templateSearchMapper))
//        initListener()
//
//        searchViewModel
//            .searchSharedState
//            .collectIn(viewLifecycleOwner,minActiveState = Lifecycle.State.CREATED) { show(it as TemplateSearchViewState) }
//
//        searchViewModel
//            .searchEvent
//            .collectIn(viewLifecycleOwner, minActiveState = Lifecycle.State.CREATED) { showEvent(it) }
//    }
//
//    private fun showEvent(event: SearchEvent) {
//        if (event is SearchEvent.SearchMore.Success) {
//            EventBus.getDefault().post(TemplateTabGetInfoSucceedEvent(searchViewModel.categoryId, 1, true))
//        }
//        if (event is SearchEvent.SearchMore.Fail) {
//            EventBus.getDefault().post(TemplateTabGetInfoSucceedEvent(searchViewModel.categoryId, 1, false))
//            context?.applicationContext?.let {
//                ToastHelper.showToast(it, event.toast, Toast.LENGTH_SHORT)
//            }
//        }
//        if (event is SearchEvent.GotoDetailPage.Navigate) {
//            val index = event.map["index"] as? Int
//            val bean = event.map["bean"] as? TemplateTabItemBean
//            val categoryId = event.map["categoryId"] as? Int
//            val expString = event.map["expString"] as? String
//            if (bean?.is_operating == 1) {
//                jumpTemplateActivityPage(index.orZero, bean)
//            } else {
//                startTemplateDetailActivity(bean, categoryId.orZero, 1, index.orZero, expString.orEmpty(), "", "", hasMoreData)
//            }
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        EventBus.getDefault().unregister(this)
//    }
//
//    private fun initListener() {
//        rvSearch.setOnItemClickListener { view, index, x, y ->
//            searchResultAdapter.dataList.getOrNull(index)?.let { bean ->
//                searchViewModel.send(
//                    SearchIntent.GoToDetailPage(mapOf("index" to index, "bean" to bean)) {
//                        if (it is TemplateTabListBeanWrapper) {
//                            mapOf(
//                                "expString" to it.templateTabListBean.exp?.exp_grp?.toString().orEmpty(),
//                                "categoryId" to it.matchType.intValue
//                            )
//                        } else mapOf()
//                    }
//                )
//            }
//
//            false
//        }
//
//        EventBus.getDefault().register(this)
//    }
//
//    private fun jumpTemplateActivityPage(position: Int, itemBean: TemplateTabItemBean) {
//        BLRouter.routeTo(RouteRequest.Builder(Uri.parse(itemBean.deep_link)).build(), context)
//        StudioReport.reportCategoryActivityClick(
//            itemBean.id.toString(),
//            itemBean.name ?: "",
//            (position + 1).toString(),
//            itemBean.imgCnt,
//            itemBean.videoCnt
//        )
//
//    }
//
//    private fun show(state: TemplateSearchViewState) {
//        searchResultAdapter.dataList = state.templates
//        hasMoreData = state.templates.isNotEmpty()
//        tvEmpty.apply {
//            visibility = if (state.searchResultString.isEmpty() || state.searchResultString == NETWORK_ERROR_STRING) gone else visible
//            text = state.searchResultString
//        }
//        networkErrorContainer.visibility = if (state.searchResultString == NETWORK_ERROR_STRING) visible else gone
//        rvSearch.visibility = if (state.searchResultString == NETWORK_ERROR_STRING) gone else visible
//        tvError.text = state.searchResultString // redundant refresh
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun fetchMore(event: TemplateTabGetInfoEvent) {
//        searchViewModel.send(SearchIntent.SearchMore(keywords, templateSearchMoreMapper))
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public fun getTabListDataByEvent(event: TemplateTabScrollEvent) {
//        rvSearch.post { rvSearch.scrollToPosition(event.position) }
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun observeCollectStatus(event: TemplateTabCollectionStateEvent) {
//        val dataList = searchResultAdapter.dataList.map { it as TemplateTabItemBean }
//        dataList.find { it.id == event.templateBean.id }?.let {
//            it.fav = if (event.isCollected) 1 else 0
//            searchResultAdapter.notifyItemChanged(dataList.indexOf(it))
//        }
//    }
//
//    private fun startTemplateDetailActivity(
//        templateTabItemBean: TemplateTabItemBean?,
//        categoryId: Int,
//        feedsType: Int,
//        position: Int,
//        expString: String,
//        title: String,
//        id: String,
//        hasMoreData: Boolean
//    ) {
//        templateTabItemBean ?: return
//        BLRouter.routeTo(RouteRequest.Builder(Uri.parse(RouterConstant.TEMPLATE_ACTIVITY_PREVIEW_TEMPLATE_DETAIL)).extras {
//            put(TemplateTabConstants.TEMPLATE_TAB_PASS_DETAIL_DATA, Bundle().apply {
//                putInt(TemplateTabConstants.TEMPLATE_TAB_CATEGORY_ID, categoryId)
//                putInt(TemplateTabConstants.TEMPLATE_TAB_FEEDS_TYPE, feedsType)
//                putInt(TemplateTabConstants.TEMPLATE_TAB_FEEDS_CARD_POSITION, position)
//                putBoolean(TemplateTabConstants.TEMPLATE_TAB_FEEDS_NO_DATA, hasMoreData)
//                putString(TemplateTabConstants.TEMPLATE_ENTER_FROM, "search")
//                putString(TemplateTabConstants.TEMPLATE_EXP_GRP, expString)
//                putInt("bbs_key_templates_trace_v2", 1)
//            })
//        }.build(), context)
//
//        val templateType = StudioReport.getTemplateType(templateTabItemBean.type, templateTabItemBean.templateFrom)
//        StudioReport.reportTemplateItemClick(
//            templateTabItemBean.id.toString(),
//            title,
//            id,
//            "search",
//            expString,
//            position + 1,
//            templateTabItemBean.op_tag,
//            templateType,
//            templateTabItemBean.imgCnt,
//            templateTabItemBean.videoCnt,
//            expString,
//            if (templateTabItemBean.fav == 1) "我的收藏" else ""
//        )
//    }
//
//}