//package com.bilibili.studio.search.material.fragment
//
//import android.content.Context
//import android.os.Bundle
//import android.view.View
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.bilibili.studio.constants.CommonConstant
//import com.bilibili.studio.constants.MaterialProtocol
//import com.bilibili.studio.model.Bgm
//import com.bilibili.studio.module.audio.util.AudioEditUtils
//import com.bilibili.studio.module.bgm.BgmConstant
//import com.bilibili.studio.module.material.collect.MaterialCollectRepository
//import com.bilibili.studio.module.material.model.MaterialBgmTab
//import com.bilibili.studio.module.material.model.MaterialMarketResponse
//import com.bilibili.studio.module.material.ui.MaterialMusicDialogFragment
//import com.bilibili.studio.report.StudioReport
//import com.bilibili.studio.search.material.adapter.SearchMaterialMusicAdapter
//import com.bilibili.studio.search.material.data.MaterialTabListBeanWrapper
//import com.bilibili.studio.search.material.repository.MaterialSearchRepository
//import com.bilibili.utils.FileUtil
//import java.io.File
//
//class BgmSearchResultFragment : BaseSearchResultFragment() {
//
//    override val materialType: Int
//        get() = MaterialSearchRepository.MATERIAL_TYPE_BGM
//
//    private lateinit var mAdapter: SearchMaterialMusicAdapter
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        initView(view)
//        super.onViewCreated(view, savedInstanceState)
//    }
//
//    override fun show(bean: MaterialTabListBeanWrapper?) {
//        bean?.materialTabListBean?.materials?.bgm?.material_list?.let {
//            mAdapter.setData(it)
//        }
//    }
//
//    private fun initView(view: View) {
//        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//        mAdapter = SearchMaterialMusicAdapter()
//        mAdapter.clickListener = itemClickListener
//        recyclerView.adapter = mAdapter
//    }
//
//    private val itemClickListener = object : SearchMaterialMusicAdapter.ClickListener{
//        override fun onItemClick(index: Int, tab: Bgm) {
//            showDownloadDialog(tab)
//            val musicFile: String = getBgmFilePath(requireContext(), tab.name)
//            val isDownload = FileUtil.isFileExists(musicFile)
//            StudioReport.reportMaterialCenterSingleIconClick(3, tab.sid, tab.id, isDownload,"search_detail", tab.badge, position = index+1)
//        }
//    }
//
//    fun getBgmFilePath(context: Context, name: String): String{
//        return try {
//            AudioEditUtils.getMusicStorageFile(context.applicationContext).absolutePath + File.separator + name + BgmConstant.SUFFIX_MP3
//        } catch (e: Exception) {
//            ""
//        }
//    }
//
//    private fun showDownloadDialog(bean: Bgm) {
//        val item = MaterialMarketResponse.MaterialItem()
//        item.id = bean.id
//        item.sid = bean.sid
//        item.name = bean.name
//        item.duration = bean.duration * 1000
//        item.downloadUrl = bean.playurl
//        item.cover = bean.cover
//        item.musicians = bean.musicians
//        item.type = CommonConstant.SERVER.TYPE_BGM
//
//        MaterialCollectRepository.getInstance().isCollectedMaterials(item.type, item.id.toInt()) { isCollected ->
//            if (isStateSaved || isRemoving || isDetached || view == null || requireActivity().isFinishing) return@isCollectedMaterials
//            val dialogFragment = MaterialMusicDialogFragment.newInstance(item, isCollected, "tab_detail", 0)
//            dialogFragment.show(requireActivity().supportFragmentManager, "AudioDialog")
//        }
//    }
//
//
//    override fun setUserVisibleCompat(isVisibleToUser: Boolean) {
//        super.setUserVisibleCompat(isVisibleToUser)
//        if(isVisibleToUser){
//            StudioReport.reportMaterialCenterMusicLibraryCatShow(0)
//        }
//    }
//
//
//    companion object {
//        const val BGM_TAB = "bgm_tab"
//        const val NEED_PLAY = "need_play"
//        fun newInstance(bgmTab:MaterialBgmTab,selectedModelId: Long): BgmSearchResultFragment {
//            return BgmSearchResultFragment().apply {
//                arguments = Bundle().apply {
//                    putSerializable(BGM_TAB, bgmTab)
//                    putLong(MaterialProtocol.KEY_MODEL_ID, selectedModelId)
//                }
//            }
//        }
//
//        fun newInstance(keywords: String): BgmSearchResultFragment {
//            val bundle = Bundle()
//            bundle.putString("keywords", keywords)
//            return BgmSearchResultFragment().apply {
//                arguments = bundle
//            }
//        }
//    }
//
//
//}