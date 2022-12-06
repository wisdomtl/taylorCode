//package com.bilibili.studio.search.material.fragment
//
//import android.content.Context
//import android.os.Bundle
//import android.view.View
//import android.widget.TextView
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.bilibili.studio.module.audio.model.AudioEffectChildrenBean
//import com.bilibili.studio.module.audio.util.AudioEditUtils
//import com.bilibili.studio.module.bgm.BgmConstant
//import com.bilibili.studio.module.bgm.utils.UpperTimeFormat
//import com.bilibili.studio.module.caption.adapter.CommonAdapter
//import com.bilibili.studio.module.material.collect.MaterialCollectRepository
//import com.bilibili.studio.module.material.model.MaterialMarketResponse.MaterialItem
//import com.bilibili.studio.report.StudioReport
//import com.bilibili.studio.constants.MaterialProtocol
//import com.bilibili.studio.module.material.ui.MaterialMusicDialogFragment
//import com.bilibili.studio.search.R
//import com.bilibili.studio.search.material.data.MaterialTabListBeanWrapper
//import com.bilibili.studio.search.material.repository.MaterialSearchRepository
//import com.bilibili.utils.FileUtil
//import test.taylor.com.taylorcode.R
//import java.io.File
//import java.util.*
//
//class TagSearchResultFragment : BaseSearchResultFragment() {
//
//    override val materialType: Int
//        get() = MaterialSearchRepository.MATERIAL_TYPE_TAG
//
//    private lateinit var mAdapter: CommonAdapter<AudioEffectChildrenBean>
//
//    private var mHasMoreData = true
//    private var mIsLoading = false
//    var mCatId = 0L
//    var mMaxRank = 0
//    var mVersion: Int = 0
//    var mSize: Int = 0
//    private var mListBean: ArrayList<AudioEffectChildrenBean>? = null
//
//    //运营活动DeepLink配置tab及其下面的某个模板
//    private var mSelectedModelId: Long = -1L
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        initView(view)
//        super.onViewCreated(view, savedInstanceState)
//        mCatId = arguments?.getLong(AUDIO_TAB_ID) ?: 0L
//        mSelectedModelId = arguments?.getLong(MaterialProtocol.KEY_MODEL_ID)!!
//    }
//
//    override fun show(bean: MaterialTabListBeanWrapper?) {
//        bean?.materialTabListBean?.materials?.tag?.material_list?.let {
//            mAdapter.data = it
//        }
//    }
//
//    private fun initView(view: View) {
//        val manager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//        recyclerView.layoutManager = manager
//        mAdapter = CommonAdapter<AudioEffectChildrenBean>().apply {
//            data = mListBean
//            layout = R.layout.search_material_audio_item
//            onBind = ::onBindView
//            onClick = { view: View, audioEffectChildrenBean: AudioEffectChildrenBean, i: Int ->
//                showDownloadDialog(audioEffectChildrenBean)
//                val musicFile: String =
//                    getBgmFilePath(requireContext(), audioEffectChildrenBean.name)
//                val isDownload = FileUtil.isFileExists(musicFile)
//                StudioReport.reportMaterialCenterSingleIconClick(audioEffectChildrenBean.type,
//                    audioEffectChildrenBean.id,
//                    mCatId,
//                    isDownload,
//                    "search_detail",
//                    audioEffectChildrenBean.badge,
//                    position = i+1)
//            }
//        }
//        recyclerView.adapter = mAdapter
//    }
//
//
//    private fun showDownloadDialog(bean: AudioEffectChildrenBean) {
//        val item = MaterialItem()
//        item.id = bean.id
//        //  item.sid = bean.sid
//        item.name = bean.name
//        item.type = bean.type
//        item.duration = bean.duration.toLong()
//        item.downloadUrl = bean.download_url
//        item.playUrl = bean.download_url
//
//        MaterialCollectRepository.getInstance()
//            .isCollectedMaterials(item.type, item.id.toInt()) { isCollected ->
//                val dialogFragment =
//                    MaterialMusicDialogFragment.newInstance(item, isCollected, "tab_detail", mCatId)
//                dialogFragment.show(requireActivity().supportFragmentManager, "MusicDialog")
//            }
//    }
//
//    private fun onBindView(view: View, bean: AudioEffectChildrenBean, i: Int) {
//        view.findViewById<TextView>(R.id.audio_name).text = bean.name
//        view.findViewById<TextView>(R.id.audio_time).text = UpperTimeFormat.formatTimeWithHour((bean.duration).toLong())
//        StudioReport.reportMaterialCenterSingleIconShow(bean.type,
//            bean.id,
//            bean.cat_id.toLong(),
//            "search_detail",
//            bean.badge, position = i+1)
//    }
//
//    fun getBgmFilePath(context: Context, name: String): String {
//        return try {
//            AudioEditUtils.getMusicStorageFile(context.applicationContext).absolutePath + File.separator + name + BgmConstant.SUFFIX_MP3
//        } catch (e: Exception) {
//            ""
//        }
//    }
//
//    override fun setUserVisibleCompat(isVisibleToUser: Boolean) {
//        super.setUserVisibleCompat(isVisibleToUser)
//        if (isVisibleToUser) {
//            StudioReport.reportMaterialCenterSoundLibraryShow(mCatId.toString())
//        }
//    }
//
//
//    companion object {
//        private const val AUDIO_TAB_ID = "audio_tab_id"
//        fun newInstance(tabId: Long, selectedModelId: Long): TagSearchResultFragment {
//            return TagSearchResultFragment().apply {
//                arguments = Bundle().apply {
//                    putLong(AUDIO_TAB_ID, tabId)
//                    putLong(MaterialProtocol.KEY_MODEL_ID, selectedModelId)
//                }
//            }
//        }
//
//        fun newInstance(keywords: String): TagSearchResultFragment {
//            val bundle = Bundle()
//            bundle.putString("keywords", keywords)
//            return TagSearchResultFragment().apply {
//                arguments = bundle
//            }
//        }
//    }
//
//
//}