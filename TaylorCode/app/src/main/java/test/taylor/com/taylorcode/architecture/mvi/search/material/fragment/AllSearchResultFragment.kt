//package com.bilibili.studio.search.material.fragment
//
//import android.content.Context
//import android.content.Intent
//import android.net.Uri
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.lifecycle.ViewModelProvider
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.bilibili.baseui.extension.*
//import com.bilibili.downloader.DownloadTaskState
//import com.bilibili.lib.blrouter.BLRouter
//import com.bilibili.lib.blrouter.RouteRequest
//import com.bilibili.studio.constans.RouterConstant
//import com.bilibili.studio.constants.CommonConstant
//import com.bilibili.studio.model.Bgm
//import com.bilibili.studio.module.album.MaterialConfig
//import com.bilibili.studio.module.audio.util.AudioEditUtils
//import com.bilibili.studio.module.bgm.BgmConstant
//import com.bilibili.studio.module.material.collect.MaterialCollectRepository
//import com.bilibili.studio.module.material.interfaces.IMaterialClickListener
//import com.bilibili.studio.module.material.model.MaterialMarketResponse
//import com.bilibili.studio.module.material.ui.MaterialMusicDialogFragment
//import com.bilibili.studio.module.material.ui.MaterialPreviewActivity
//import com.bilibili.studio.module.material.ui.MaterialStickDialogFragment
//import com.bilibili.studio.module.material.viewmodel.MaterialMarketViewModel
//import com.bilibili.studio.report.StudioReport
//import com.bilibili.studio.search.material.adapter.AllSearchMaterialAdapter
//import com.bilibili.studio.search.material.data.MaterialTabListBeanWrapper
//import com.bilibili.studio.search.material.listener.ISearchMaterialClickListener
//import com.bilibili.studio.search.material.mapper.HAS_NO_MORE
//import com.bilibili.studio.search.material.repository.MaterialSearchRepository
//import com.bilibili.utils.FileUtil
//import test.taylor.com.taylorcode.kotlin.match_parent
//import java.io.File
//
//class AllSearchResultFragment : BaseSearchResultFragment() {
//
//    override val materialType: Int
//        get() = MaterialSearchRepository.MATERIAL_TYPE_ALL
//
//    private lateinit var mAdapter: AllSearchMaterialAdapter
//
//    private lateinit var mViewModel: MaterialMarketViewModel
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
//            BTextView {
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
//            }.also { tvEmpty = it }
//
//            recyclerView = RecyclerView {
//                layout_id = "rlRecyclerView"
//                layout_width = match_parent
//                layout_height = match_parent
//                overScrollMode = 2
//            }
//        }
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        initView(view)
//        super.onViewCreated(view, savedInstanceState)
//        mViewModel = ViewModelProvider(requireActivity())[MaterialMarketViewModel::class.java]
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        mViewModel.cancelDownload()
//    }
//
//    override fun show(bean: MaterialTabListBeanWrapper?) {
//        bean?.materialTabListBean?.materials?.let {
//            mAdapter.setData(it)
//        }
//    }
//
//    private fun initView(view: View) {
//        recyclerView.layoutManager =
//            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//        mAdapter = AllSearchMaterialAdapter()
//        mAdapter.clickListener = onItemClickListener
//        recyclerView.adapter = mAdapter
//    }
//
//    //do nothing
//    override fun checkSearchMore() {
//    }
//
//    override fun setUserVisibleCompat(isVisibleToUser: Boolean) {
//        super.setUserVisibleCompat(isVisibleToUser)
//        if (isVisibleToUser) {
//            StudioReport.reportMaterialCenterMusicLibraryCatShow(0)
//            mViewModel?.refreshDownloadStatus()
//        }
////        if(isVisibleToUser && dataLoad){
////            mViewModel?.refreshDownloadStatus()
////        }
//    }
//
//
//
//
//    companion object {
//        const val BGM_TAB = "bgm_tab"
//        const val NEED_PLAY = "need_play"
//
//        fun newInstance(keywords: String): AllSearchResultFragment {
//            val bundle = Bundle()
//            bundle.putString("keywords", keywords)
//            return AllSearchResultFragment().apply {
//                arguments = bundle
//            }
//        }
//    }
//
//
//    //点击事件处理
//    private val onItemClickListener = object : ISearchMaterialClickListener {
//        override fun onItemClickListener(data: Any, section: Int, sectionIndex: Int, play: Boolean,position:Int) {
//            data as MaterialMarketResponse.MaterialItem
//            when(data.type){
//                //视频
//                CommonConstant.SERVER.TYPE_VIDEO -> {
//                    if (!mViewModel.isDownloaded(data)) {
//                        if (data.downloadStatus != DownloadTaskState.DOWNLOADING) {
//                            mViewModel.download(data)
//                        }
//                    } else {
//                        MaterialCollectRepository.getInstance().isCollectedMaterials(data.type, data.id.toInt()) { isCollected ->
//                            if (play) {
//                                context?.let {
//                                    val intent = Intent(it, MaterialPreviewActivity::class.java)
//                                    intent.putExtra("MEDIA_ITEM", mViewModel.toMediaItem(data, isCollected))
//                                    intent.putExtra(CommonConstant.EFFECTCENTER.ITEM_POSITION,position)
//                                    startActivity(intent)
//                                }
//                            } else {
//                                val routeRequest =
//                                    RouteRequest.Builder(Uri.parse(RouterConstant.MATERIAL_ROUTER_URI))
//                                        .extras {
//                                            val bundlePicker = Bundle()
//                                            bundlePicker.putInt(CommonConstant.EFFECTCENTER.TYPE, data.type)
//                                            bundlePicker.putInt(CommonConstant.EFFECTCENTER.EFFECT_ID, data.id.toInt())
//                                            bundlePicker.putInt(CommonConstant.EFFECTCENTER.ITEM_POSITION,position)
//                                            bundlePicker.putInt(MaterialConfig.EXTRA_KEY_MATERIAL_INDEX, MaterialConfig.LOCAL_TAB_INDEX)
//                                            bundlePicker.putSerializable(MaterialConfig.EXTRA_KEY_MATERIAL_FILE_PATH, mViewModel.toMediaItem(data, isCollected))
//                                            put(RouterConstant.ROUTER_PARAM_CONTROL, bundlePicker)
//                                        }
//                                        .build()
//                                BLRouter.routeTo(routeRequest)
//                            }
//                        }
//                    }
//                }
//                //音乐
//                CommonConstant.SERVER.TYPE_BGM -> {
//                    activity?.let {
//                        MaterialCollectRepository.getInstance().isCollectedMaterials(data.type, data.id.toInt()) { isCollected ->
//                            if (activity == null || activity!!.supportFragmentManager.isDestroyed) return@isCollectedMaterials
//                            val dialogFragment = MaterialMusicDialogFragment.newInstance(data, isCollected, "rank", 0,position)
//                            dialogFragment.showAllowingStateLoss(activity!!.supportFragmentManager, "MusicDialog")
//                        }
//                    }
//                }
//                //音效
//                CommonConstant.SERVER.TYPE_AUDIO_EFFECT -> {
//                    activity?.let {
//                        MaterialCollectRepository.getInstance().isCollectedMaterials(data.type, data.id.toInt()) { isCollected ->
//                            if (activity == null || activity!!.supportFragmentManager.isDestroyed) return@isCollectedMaterials
//                            val dialogFragment = MaterialMusicDialogFragment.newInstance(data, isCollected, "rec_list", 0,position)
//                            dialogFragment.showAllowingStateLoss(activity!!.supportFragmentManager, "AudioDialog")
//                        }
//                    }
//                }
//                else -> {
//                    //单素材？
//                    MaterialCollectRepository.getInstance().isCollectedMaterials(data.type, data.id.toInt()) { isCollected ->
//                        if (activity == null || activity!!.supportFragmentManager.isDestroyed) return@isCollectedMaterials
//                        val text = if (data.name.isNullOrEmpty()) data.title else data.name
//                        val from = if (section == MaterialMarketResponse.HOT_STEM) "hot_topics" else "rec_list"
//                        val dialogFragment = MaterialStickDialogFragment.newInstance(data.type, data.id.toInt(), isCollected, text, data.cover, from, position = position)
//                        dialogFragment.showAllowingStateLoss(activity!!.supportFragmentManager, "EffectDetailDialog")
//                    }
//                }
//            }
//
//            //埋点
//            val from = "search_detail"
//            val id = if (data.type == CommonConstant.SERVER.TYPE_BGM) data.sid.toInt() else data.id.toInt()
//            val index= sectionIndex.coerceAtLeast(0)
////            val sectionItem = mAdapter.getMaterialSection(index)
//            StudioReport.reportMaterialCenterSingleIconClick(
//                data.type,
//                id.toLong(),
//                data.catId.toLong(),
//                mViewModel.isDownloaded(data),
//                from,
//                data.badge,
//                position = position+1
//            )
//        }
//    }
//
//}