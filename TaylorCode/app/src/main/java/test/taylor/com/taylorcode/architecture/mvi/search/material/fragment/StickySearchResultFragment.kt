//package com.bilibili.studio.search.material.fragment
//
//import android.os.Bundle
//import android.view.View
//import androidx.recyclerview.widget.GridLayoutManager
//import com.bilibili.baseui.extension.showAllowingStateLoss
//import com.bilibili.studio.constants.CommonConstant
//import com.bilibili.studio.module.album.ui.GridItemDecoration
//import com.bilibili.studio.model.EffectDataEntity
//import com.bilibili.studio.module.material.collect.MaterialCollectRepository
//import com.bilibili.studio.module.material.ui.MaterialStickDialogFragment
//import com.bilibili.studio.report.StudioReport
//import com.bilibili.studio.search.material.adapter.SearchMaterialStickAdapter
//import com.bilibili.studio.search.material.data.MaterialTabListBeanWrapper
//import com.bilibili.studio.search.material.repository.MaterialSearchRepository
//import com.bilibili.utils.ScreenUtil
//import java.util.*
//
///**
// * Created by xf on 2020/5/22
// * email: yanglinfeng@bilibili.com
// * since: 1.0.0
// */
//class StickySearchResultFragment : BaseSearchResultFragment() {
//
//    override val materialType: Int
//        get() = MaterialSearchRepository.MATERIAL_TYPE_PICTURE
//
//    private lateinit var mEffectDetailsAdapter: SearchMaterialStickAdapter
//    private var mCurrentType: Int = 0
//    private var mOrder: Int = 0
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        initConfig(savedInstanceState)
//        super.onViewCreated(view, savedInstanceState)
//        initEvent()
//    }
//
//    override fun show(bean: MaterialTabListBeanWrapper?) {
//        bean?.materialTabListBean?.materials?.picture?.material_list?.let {
//            showEffectDetailsView(it)
//        }
//    }
//
//    private fun initConfig(savedInstanceState: Bundle?) {
//        mCurrentType = arguments?.getInt(CommonConstant.EFFECTCENTER.TYPE) ?: 0
//        mOrder = arguments?.getInt(ORDER) ?: 0
//        initDetailsContent()
//    }
//
//    fun initEvent() {
//        mEffectDetailsAdapter.addOnItemClickListener(object :
//            SearchMaterialStickAdapter.OnItemClickListener {
//            override fun onItemClick(entity: EffectDataEntity?, adapterPosition: Int) {
//                entity?.let {
//                    StudioReport.reportMaterialCenterSingleIconClick(it.type,
//                        it.id.toLong(),
//                        it.cat_id.toLong(),
//                        true,
//                        "search_detail",
//                        it.badge,
//                        position = adapterPosition + 1)
//
//                    //单素材？
//                    MaterialCollectRepository.getInstance()
//                        .isCollectedMaterials(it.type, it.id) { isCollected ->
//                            if (activity == null || activity!!.supportFragmentManager.isDestroyed) return@isCollectedMaterials
//                            val text = it.name
//                            val from = "rec_list"
//                            val dialogFragment = MaterialStickDialogFragment.newInstance(it.type,
//                                it.id,
//                                isCollected,
//                                text,
//                                it.cover,
//                                from)
//                            dialogFragment.showAllowingStateLoss(activity!!.supportFragmentManager,
//                                "EffectDetailDialog")
//                        }
//
////                    activity?.takeIf { t ->
////                        !t.isFinishing
////                    }?.let {
////                        EffectCenterHelper.toEffectDetailPage(it,
////                            entity,
////                            StudioReportConstants.ORIGINAL_OPEN_FROM_MATERIAL_CENTER)
////                    }
//                }
//            }
//        })
//    }
//
//    private val delayExposure = ArrayDeque<Runnable>()
//
//    fun flushExposureQueue() {
//        delayExposure.takeIf {
//            it.isNotEmpty()
//        }?.let { it ->
//            it.forEach {
//                it.run()
//            }
//            it.clear()
//        }
//    }
//
//    private fun initDetailsContent() {
//        mEffectDetailsAdapter = SearchMaterialStickAdapter().apply {
//            attachToWindowCallback = { r ->
//                if (userVisibleHint && parentFragment?.userVisibleHint == true) {
//                    r.run()
//                } else {
//                    delayExposure.add(r)
//                }
//            }
//        }
//        mEffectDetailsAdapter.setShowTag(mCurrentType == CommonConstant.EFFECTCENTER.TYPE_ALL)
//        recyclerView.adapter = mEffectDetailsAdapter
//        recyclerView.layoutManager = GridLayoutManager(applicationContext, 3)
//        val decoration =
//            GridItemDecoration.Builder(context).verSize(ScreenUtil.dip2px(applicationContext, 9f))
//                .horSize(ScreenUtil.dip2px(applicationContext, 16f)).build()
//        recyclerView.addItemDecoration(decoration)
//    }
//
//    private fun showEffectDetailsView(details: List<EffectDataEntity>) {
//        recyclerView.visibility = View.VISIBLE
//        mEffectDetailsAdapter.setData(details)
//        mEffectDetailsAdapter.setCurrentType(mCurrentType)
//        mEffectDetailsAdapter.setOrder(mOrder)
//        mEffectDetailsAdapter.notifyDataSetChanged()
//    }
//
//
//    companion object {
//        const val ORDER: String = "order"
//        fun newInstance(type: Int, order: Int): StickySearchResultFragment {
//            return StickySearchResultFragment().apply {
//                arguments = Bundle().apply {
//                    putInt(CommonConstant.EFFECTCENTER.TYPE, type)
//                    putInt(ORDER, order)
//                }
//            }
//        }
//
//        fun newInstance(keywords: String): StickySearchResultFragment {
//            val bundle = Bundle()
//            bundle.putString("keywords", keywords)
//            return StickySearchResultFragment().apply {
//                arguments = bundle
//            }
//        }
//    }
//}