//package com.bilibili.studio.search.material.fragment
//
//import android.net.Uri
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import android.widget.Toast
//import androidx.fragment.app.activityViewModels
//import androidx.lifecycle.Lifecycle
//import androidx.viewpager2.widget.ViewPager2
//import com.bcut.collectIn
//import com.bcut.homepage.Extras
//import com.bilibili.baseui.extension.*
//import com.bilibili.droid.ToastHelper
//import com.bilibili.lib.blrouter.BLRouter
//import com.bilibili.lib.blrouter.RouteRequest
//import com.bilibili.studio.constans.RouterConstant
//import com.bilibili.studio.report.StudioReport
//import com.bilibili.studio.search.R
//import com.bilibili.studio.search.SearchViewModel
//import com.bilibili.studio.search.base.BaseSearchFragment
//import com.bilibili.studio.search.base.SearchEvent
//import com.bilibili.studio.search.base.SearchIntent
//import com.bilibili.studio.search.material.adapter.SimpleFragmentStatePagerAdapter2
//import com.bilibili.studio.search.material.data.MaterialSearchViewState
//import com.bilibili.studio.search.material.data.MaterialTabListBeanWrapper
//import com.bilibili.studio.search.material.mapper.*
//import com.bilibili.studio.search.material.repository.MaterialSearchRepository
//
//import com.google.android.material.tabs.TabLayout
//import com.google.android.material.tabs.TabLayoutMediator
//import com.tab.bean.TemplateTabItemBean
//import com.tab.presenter.TemplateTabConstants
//import org.greenrobot.eventbus.EventBus
//import test.taylor.com.taylorcode.R
//import test.taylor.com.taylorcode.kotlin.match_parent
//import tv.danmaku.android.log.BLog
//
//class MaterialSearchResultFragment : BaseSearchFragment() {
//
//    override val searchViewModel: SearchViewModel<MaterialTabListBeanWrapper, MaterialSearchViewState> by activityViewModels()
//
//    private val keywords by Extras("keywords", "")
//
//    private lateinit var networkErrorContainer: View
//    private lateinit var tvError: TextView
//    private lateinit var tvRetry: TextView
//    private lateinit var ivError: ImageView
//    private lateinit var mVpEffectContent: ViewPager2
//    private lateinit var mTlEffectTabs: TabLayout
//    private var hasMoreData = true
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?,
//    ): View? {
//        return ConstraintLayout {
//            layout_width = match_parent
//            layout_height = match_parent
//            background_color = "#FFFFFF"
//
//
//
//            mTlEffectTabs = (layoutInflater.inflate(R.layout.search_tab_layout, null) as TabLayout)
//
//            TabLayout(mTlEffectTabs) {
//                layout_id = "tlTabs"
//            }
//
//
//            mVpEffectContent = ViewPager2 {
//                layout_id = "vpContent"
//                layout_width = match_parent
//                layout_height = 0
//                top_toBottomOf = "tlTabs"
//                bottom_toBottomOf = parent_id
//                margin_top = 4
//            }
//
//            networkErrorContainer = ConstraintLayout {
//                layout_id = "networkErrorContainer"
//                layout_width = match_parent
//                layout_height = match_parent
//                visibility = gone
//
//                ivError = ImageView {
//                    layout_id = "ivNetworkError"
//                    layout_width = wrap_content
//                    layout_height = wrap_content
//                    src = R.drawable.search_no_data
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
//                    textColor = "#9FA1B2"
//                    gravity = gravity_center
//                    text = NETWORK_ERROR_STRING
//                    top_toBottomOf = "ivNetworkError"
//                    margin_top = 4
//                    bottom_toTopOf = "tvRefresh"
//                    center_horizontal = true
//                    vertical_chain_style = packed
//                }
//
//                tvRetry = BTextView {
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
//                    margin_top = 18
//                    shape = shape {
//                        solid_color = "#FF6490"
//                        corner_radius = 16
//                    }
//                    onClick = {
//                        searchViewModel.send(SearchIntent.SearchByMaterialType(keywords,
//                            MaterialSearchRepository.MATERIAL_TYPE_ALL,
//                            materialSearchMapper))
//                    }
//                }
//            }
//        }
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//
//        searchViewModel.send(SearchIntent.SearchByMaterialType(keywords,
//            MaterialSearchRepository.MATERIAL_TYPE_ALL,
//            materialSearchMapper))
//
//        searchViewModel.searchSharedState.collectIn(viewLifecycleOwner,
//            minActiveState = Lifecycle.State.RESUMED) { show(it) }
//
//        searchViewModel.searchEvent.collectIn(viewLifecycleOwner,
//            minActiveState = Lifecycle.State.RESUMED) { showEvent(it) }
//
//    }
//
//    private fun showEffectListContent(data: MaterialTabListBeanWrapper?) {
//        if (data?.materialType != MaterialSearchRepository.MATERIAL_TYPE_ALL) {
//            return
//        }
////        val titles = mutableListOf<String>()
//
//        val titles = mutableListOf("全部")
//        val materialTypes = mutableListOf(MaterialSearchRepository.MATERIAL_TYPE_ALL)
//
//        mVpEffectContent.adapter = SimpleFragmentStatePagerAdapter2(this).apply {
//            data.materialTabListBean?.materials?.apply {
//                if (video?.material_list?.isNotEmpty() == true) {
//                    titles.add("视频库")
//                    materialTypes.add(MaterialSearchRepository.MATERIAL_TYPE_VIDEO)
//                }
//                if (bgm?.material_list?.isNotEmpty() == true) {
//                    titles.add("音乐")
//                    materialTypes.add(MaterialSearchRepository.MATERIAL_TYPE_BGM)
//                }
//                if (tag?.material_list?.isNotEmpty() == true) {
//                    titles.add("音效")
//                    materialTypes.add(MaterialSearchRepository.MATERIAL_TYPE_TAG)
//                }
//                if (picture?.material_list?.isNotEmpty() == true) {
//                    titles.add("贴纸")
//                    materialTypes.add(MaterialSearchRepository.MATERIAL_TYPE_PICTURE)
//                }
//            }
//
//            mTitles = titles.toTypedArray()
//            mMaterialTypes = materialTypes
//
//            createFragment = { position ->
//                when (mTitles!![position]) {
//                    "全部" -> AllSearchResultFragment.newInstance(keywords)
//                    "视频库" -> VideoSearchResultFragment.newInstance(keywords)
//                    "音乐" -> BgmSearchResultFragment.newInstance(keywords)
//                    "音效" -> TagSearchResultFragment.newInstance(keywords)
//                    "贴纸" -> StickySearchResultFragment.newInstance(keywords)
//                    else -> AllSearchResultFragment.newInstance(keywords)
//                }
//            }
//        }
//        mVpEffectContent.offscreenPageLimit = 10
//        TabLayoutMediator(mTlEffectTabs, mVpEffectContent) { tab, position ->
//            tab.text = titles[position]
//        }.also { it.attach() }
//    }
//
//    private fun showEvent(event: SearchEvent) {
//        if (event is SearchEvent.SearchMore.Success) {
//        }
//
//        if (event is SearchEvent.CheckWhole) {
//            (mVpEffectContent.adapter as? SimpleFragmentStatePagerAdapter2)?.apply {
//                kotlin.runCatching {
//                    mVpEffectContent.setCurrentItem(getPositionByType(event.materialType), false)
//                    StudioReport.reportMaterialCenterCheckmoreClick(getTitleByType(event.materialType))
//                }
//            }
//        }
//
//        if (event is SearchEvent.SearchMore.Fail) {
//            context?.applicationContext?.let {
//                ToastHelper.showToast(it, event.toast, Toast.LENGTH_SHORT)
//            }
//        }
//        if (event is SearchEvent.GotoDetailPage.Navigate) {
//            val index = event.map["index"] as Int
//            val bean = event.map["bean"] as TemplateTabItemBean
//            val categoryId = event.map["categoryId"] as Int
//            val expString = event.map["expString"] as String
//            if (bean.is_operating == 1) {
//                jumpTemplateActivityPage(index, bean)
//            } else {
//                startTemplateDetailActivity(bean,
//                    categoryId,
//                    1,
//                    index,
//                    expString,
//                    "",
//                    "",
//                    hasMoreData)
//            }
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        EventBus.getDefault().unregister(this)
//    }
//
//    private fun jumpTemplateActivityPage(position: Int, itemBean: TemplateTabItemBean) {
//        BLRouter.routeTo(RouteRequest.Builder(Uri.parse(itemBean.deep_link)).build(), context)
//        StudioReport.reportCategoryActivityClick(itemBean.id.toString(),
//            itemBean.name ?: "",
//            (position + 1).toString(),
//            itemBean.imgCnt,
//            itemBean.videoCnt)
//
//    }
//
//    private fun show(state: MaterialSearchViewState) {
//        BLog.e("search flow show:",
//            this.toString() + "~~~~~~" + state.materials?.materialType.toString())
//
//        when (state.searchResultString) {
//            NETWORK_ERROR_STRING -> {
//                networkErrorContainer.visibility = visible
//                mTlEffectTabs.visibility = gone
//                mVpEffectContent.visibility = gone
//                tvRetry.visibility = visible
//                tvError.text = state.searchResultString // redundant refresh
//                ivError.src = R.drawable.search_no_network
//            }
//            HAS_NO_DATA -> {
//                networkErrorContainer.visibility = visible
//                mTlEffectTabs.visibility = gone
//                mVpEffectContent.visibility = gone
//                tvRetry.visibility = gone
//                tvError.text = state.searchResultString // redundant refresh
//                ivError.src = R.drawable.search_no_data
//            }
//            SEARCHMORE_NETWORK_ERROR_RETRY, HAS_NO_MORE -> {
//                ToastHelper.showToast(context, state.searchResultString, Toast.LENGTH_SHORT)
//            }
//            else -> {
//                if (state.materials != null) {
//                    networkErrorContainer.visibility = gone
//                    mTlEffectTabs.visibility = visible
//                    mVpEffectContent.visibility = visible
//                    showEffectListContent(state.materials)
//                }
//            }
//        }
//    }
//
//
//    private fun startTemplateDetailActivity(
//        templateTabItemBean: TemplateTabItemBean,
//        categoryId: Int,
//        feedsType: Int,
//        position: Int,
//        expString: String,
//        title: String,
//        id: String,
//        hasMoreData: Boolean,
//    ) {
//        BLRouter.routeTo(RouteRequest.Builder(Uri.parse(RouterConstant.TEMPLATE_ACTIVITY_PREVIEW_TEMPLATE_DETAIL))
//            .extras {
//                put(TemplateTabConstants.TEMPLATE_TAB_PASS_DETAIL_DATA, Bundle().apply {
//                    putInt(TemplateTabConstants.TEMPLATE_TAB_CATEGORY_ID, categoryId)
//                    putInt(TemplateTabConstants.TEMPLATE_TAB_FEEDS_TYPE, feedsType)
//                    putInt(TemplateTabConstants.TEMPLATE_TAB_FEEDS_CARD_POSITION, position)
//                    putBoolean(TemplateTabConstants.TEMPLATE_TAB_FEEDS_NO_DATA, hasMoreData)
//                    putString(TemplateTabConstants.TEMPLATE_ENTER_FROM, "search")
//                    putString(TemplateTabConstants.TEMPLATE_EXP_GRP, expString)
//                    putInt("bbs_key_templates_trace_v2", 1)
//                })
//            }.build(), context)
//
//        val templateType =
//            StudioReport.getTemplateType(templateTabItemBean.type, templateTabItemBean.templateFrom)
//        StudioReport.reportTemplateItemClick(templateTabItemBean.id.toString(),
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
//            if (templateTabItemBean.fav == 1) "我的收藏" else "")
//    }
//
//}