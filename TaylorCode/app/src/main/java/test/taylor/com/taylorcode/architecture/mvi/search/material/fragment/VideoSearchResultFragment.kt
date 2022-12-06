//package com.bilibili.studio.search.material.fragment
//
//import android.content.Intent
//import android.media.browse.MediaBrowser
//import android.net.Uri
//import android.os.Bundle
//import android.text.TextUtils
//import android.view.View
//import androidx.recyclerview.widget.GridLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import bolts.Task
//import com.bilibili.baseui.extension.*
//import com.bilibili.downloader.*
//import com.bilibili.lib.blrouter.BLRouter
//import com.bilibili.lib.blrouter.RouteRequest
//import com.bilibili.studio.common.EditorPaths.materialRemote
//import com.bilibili.studio.constans.RouterConstant
//import com.bilibili.studio.constants.CommonConstant
//import com.bilibili.studio.constants.MaterialProtocol
//import com.bilibili.studio.module.album.MaterialConfig
//import com.bilibili.studio.module.album.MaterialRemoteMode
//import com.bilibili.studio.module.album.adapter.BaseMediaAdapter
//import com.bilibili.studio.module.album.adapter.Media2Adapter
//import com.bilibili.studio.module.album.adapter.MediaAdapter
//import com.bilibili.studio.module.album.bean.RemoteMaterialCategoryBean
//import com.bilibili.studio.module.album.bean.RemoteMediaItemUpdate
//import com.bilibili.studio.module.album.loader.MediaItem
//import com.bilibili.studio.module.album.material.business.contract.RemoteCenterBusinsessContract
//import com.bilibili.studio.module.album.material.business.livedata.MaterialViewModel
//import com.bilibili.studio.module.album.material.business.livedata.RemoteCenterPreviewInfo
//import com.bilibili.studio.module.album.material.component.MultiTabConfig
//import com.bilibili.studio.module.album.material.component.entity.TabEntity
//import com.bilibili.studio.module.album.material.component.interfaces.MultiTabUpdate
//import com.bilibili.studio.module.album.report.MaterialReportParams
//import com.bilibili.studio.module.album.ui.DeepLinkParamsParser
//import com.bilibili.studio.module.album.ui.MaterialFragment
//import com.bilibili.studio.module.album.util.FileUtil
//import com.bilibili.studio.module.album.util.MaterialFilterUtils
//import com.bilibili.studio.module.material.collect.MaterialCollectRepository
//import com.bilibili.studio.module.material.ui.MaterialPreviewActivity
//import com.bilibili.studio.module.templatelist.util.VideoTemplateHelper
//import com.bilibili.studio.report.StudioReport
//import com.bilibili.studio.search.material.adapter.SearchMaterialVideoAdapter
//import com.bilibili.studio.search.material.data.MaterialTabListBeanWrapper
//import com.bilibili.studio.search.material.repository.MaterialSearchRepository
//import com.bilibili.utils.LifecycleUtil.isAlive
//import com.bilibili.widgets.SnackBarBuilder
//import okhttp3.internal.concurrent.Task
//import org.greenrobot.eventbus.EventBus
//import org.greenrobot.eventbus.Subscribe
//import tv.danmaku.android.log.BLog
//import java.io.File
//import java.util.*
//
//
///**
// * copy from RemoteCenterBusinessFragment
// *  素材库列表分页展示页面
// *
// */
//class VideoSearchResultFragment : BaseSearchResultFragment(), RemoteCenterBusinsessContract.IRemoteView, MultiTabUpdate {
//
//    override val materialType: Int
//        get() = MaterialSearchRepository.MATERIAL_TYPE_VIDEO
//
//    companion object {
//        const val TAG = "VideoSearchResultFragment"
//
//        private const val KEY_TAB_ID = "key_tab_id"
//        private const val DEEPLINK_PARAMS_KEY = "deeplink_params_key"
//
//        fun newInstance(keywords: String): VideoSearchResultFragment {
//            val bundle = Bundle()
//            bundle.putString("keywords", keywords)
//            return VideoSearchResultFragment().apply {
//                arguments = bundle
//            }
//        }
//    }
//
//    var mTabConfig: MultiTabConfig? = null
//
//    var mAdapter: BaseMediaAdapter? = null
//
//    private val mFullItems: MutableList<MediaBrowser.MediaItem> = ArrayList()
//    private val mFullVideoItems: MutableList<MediaBrowser.MediaItem> = ArrayList()
//    private var mSelectedItems: ArrayList<MediaBrowser.MediaItem> = ArrayList()
//
//    private val mMimeType = MaterialFragment.MIME_VIDEO_IMAGE
//
//    private var mMaterialRemoteMode = MaterialRemoteMode.HORIZONTAL
//    private var mRouterFrom = 0
//
//    //图文生成视频的模板id
//    private var templateId = 0L
//    private var tuwenCatId = 0L
//    private var mMultiSelectMode = false
//    private var mMaxFootageNumber = -1
//    private var mUIStyle = 0
//    private var mDeeplinkParams = ""
//    private var mReportParams: MaterialReportParams? = null
//
//    private var mTabId = -1
//
//    private var mLastSelectedPreviewPosition = -1
//
//    private lateinit var mViewModel: MaterialViewModel
//
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        initData()
//        super.onViewCreated(view, savedInstanceState)
//        EventBus.getDefault().register(this)
//    }
//
//    override fun show(materials: MaterialTabListBeanWrapper?) {
//        if(materials?.materialTabListBean?.materials?.video?.material_list?.isNotEmpty() == true){
//            Task.callInBackground {
//                val set = FileUtil.allFilesInDir(
//                    materialRemote
//                ) as HashSet<String>
//
//                materials?.materialTabListBean?.materials?.video?.material_list?.map { source ->
//                    transformNetResponse(source, set)
//                } ?: arrayListOf()
//            }.continueWith({
//
//                val items = it.result as MutableList<MediaBrowser.MediaItem>
//                onRemoteDataLoaded(items, fromStart = true, hasMore = true)
//                null
//            }, Task.UI_THREAD_EXECUTOR)
//        }
//    }
//
//    private fun transformNetResponse(
//        source: RemoteMaterialCategoryBean.RemoteMaterialItemBean,
//        materials: MutableSet<String>
//    ):MediaItem{
//        val mediaItem = MediaItem()
//        mediaItem.id = source.id
//        mediaItem.cat_id = source.cat_id
//        mediaItem.resourceId = source.id.toInt()
//        mediaItem.cover = source.cover
//        mediaItem.tp = source.tp
//        mediaItem.downloadUrl = if (source.tp==1) source.download_url else source.videopre_url
//        val fileName = MaterialFilterUtils.getFileName(mediaItem.downloadUrl, true)
//        if (materials.contains(fileName)){
//            mediaItem.downloadStatus = DownloadTaskState.FINISH
//            mediaItem.path = materialRemote + File.separator + fileName
//        }else{
//            mediaItem.downloadStatus = DownloadTaskState.CREATE
//        }
//        mediaItem.biz_from = source.biz_from
//        mediaItem.name = source.name
//        mediaItem.badge = source.badge
//        mediaItem.dataType = MediaItem.DataType.REMOTE
//        mediaItem.viewType = MediaItem.MediaViewType.ITEM
//        mediaItem.mimeType = if (mediaItem.tp==1) MediaItem.MIME_TYPE_IMAGE_PREFIX else MediaItem.MIME_TYPE_VIDEO_PREFIX
//        mediaItem.duration = source.duration.toLong()
//        mediaItem.resourceType = CommonConstant.SERVER.TYPE_VIDEO
//        mediaItem.dataSource= MediaItem.Source.REMOTE
//        source.author?.let { author ->
//            mediaItem.authorName = author.name
//            mediaItem.authorAvatar = author.face
//        }
//        return mediaItem
//    }
//
//    private fun initData() {
//        val ctx = activity
//        arguments?.let { params ->
//            mTabId = arguments?.getInt(KEY_TAB_ID) ?: 0
//            mMaxFootageNumber = params.getInt(MaterialConfig.ARG_MATERIAL_MAX_FOOTAGE_NUMBER, -1)
//            mMultiSelectMode =
//                params.getBoolean(MaterialConfig.ARG_MATERIAL_SELECT_MULTI_MODE, true)
//            mUIStyle = params.getInt(MaterialConfig.EXTRA_MATERIAL_UI_STYLE, 0)
//            mMaterialRemoteMode =
//                params.getInt(MaterialConfig.ARG_MATERIAL_REMOTE_MODE, mMaterialRemoteMode)
//            mRouterFrom =
//                params.getInt(MaterialProtocol.MATERIAL_SOURCE_KEY, MaterialProtocol.SOURCE.UNKNOWN)
//            templateId = params.getLong(MaterialConfig.EXTRA_TUWEN_TEMPLATE_ID, 0L)
//            tuwenCatId = params.getLong(MaterialConfig.EXTRA_TUWEN_CAT_ID, 0)
//            mDeeplinkParams = arguments?.getString(DEEPLINK_PARAMS_KEY) ?: ""
//            mReportParams = arguments?.getParcelable(MaterialReportParams.MATERIAL_REPORT_PARAMS)
//        }
////        mViewModel = ViewModelProvider(requireActivity())[MaterialViewModel::class.java]
////        mSelectedItems = mViewModel.selectedItems
////        mViewModel.onSelectedItemsChanged.observeInFragment(this) {
////            if (it) {
////                updateSelectedItems()
////            }
////        }
//            mAdapter =
//                SearchMaterialVideoAdapter(mMultiSelectMode && mRouterFrom != MaterialProtocol.SOURCE.AUDIO_DUBBING)
//            if (mMaxFootageNumber > 0) {
//                mAdapter?.setSelectLimit(mMaxFootageNumber)
//            }
//        mAdapter?.setUIStyle(mUIStyle)
//        mAdapter?.setReportParams(mReportParams)
//        mAdapter?.tabId = mTabId
//        mAdapter?.let { it ->
//            it.setSendSpanEvent(mRouterFrom != MaterialProtocol.SOURCE.CLOUD_MATERIAL)
//            it.setOnMediaDownloadListener() { mediaItem, position, preview, isAutoSelect ->
//                if (mediaItem.downloadStatus == DownloadTaskState.DOWNLOADING) {
//                    return@setOnMediaDownloadListener
//                }
//                if (mediaItem.downloadStatus != DownloadTaskState.FINISH) {
//                    val observer: DownloadObserver = object : DefaultDownloadObserver() {
//                        override fun onFinish(taskId: Long, filePath: String, fileName: String) {
//                            if (!isAlive(this@VideoSearchResultFragment)) {
//                                return
//                            }
//                            mediaItem.path = filePath + File.separator + fileName
//                            mediaItem.downloadStatus = DownloadTaskState.FINISH
//                            it.notifyItemChanged(position)
//                            BLog.e(TAG, "on finish~")
//                            if (preview && mLastSelectedPreviewPosition == position) {
//                                mLastSelectedPreviewPosition = -1
//                                RemoteCenterPreviewInfo(mMimeType,
//                                    mFullVideoItems,
//                                    mFullVideoItems.indexOf(mediaItem)).let {
//                                    MaterialCollectRepository.getInstance().isCollectedMaterials(
//                                        CommonConstant.SERVER.TYPE_VIDEO,
//                                        it.items[it.position].id.toInt()) { isCollected ->
//                                        val intent = Intent(requireActivity(),
//                                            MaterialPreviewActivity::class.java)
//                                        intent.putExtra("MEDIA_ITEM", it.items[it.position].apply {
//                                            this.isCollected = isCollected
//                                            this.resourceId = this.id.toInt()
//                                            this.resourceType = CommonConstant.SERVER.TYPE_VIDEO
//                                        })
//                                        startActivity(intent)
//                                    }
//                                }
//                            }
//                            removeTaskObservers(taskId)
//                            if (isAutoSelect) {
//                                it.chooseItem(recyclerView, position)
//                            }
//                        }
//
//                        override fun onError(
//                            taskId: Long,
//                            error: String,
//                            totalSize: Long,
//                            loadedSize: Long,
//                        ) {
//                            if (!isAlive(this@VideoSearchResultFragment)) {
//                                return
//                            }
//                            mediaItem.downloadStatus = DownloadTaskState.ERROR
//                            it.notifyItemChanged(position)
//                            BLog.e(TAG, "on error~")
//                            removeTaskObservers(taskId)
//                        }
//
//                        override fun onCancel(taskId: Long) {
//                            if (!isAlive(this@VideoSearchResultFragment)) {
//                                return
//                            }
//                            removeTaskObservers(taskId)
//                            mediaItem.downloadStatus = DownloadTaskState.CANCEL
//                            it.notifyItemChanged(position)
//                            BLog.e(TAG, "on cancel~")
//                        }
//                    }
//                    val fileName = MaterialFilterUtils.getFileName(mediaItem.downloadUrl, true)
//                    val request =
//                        DownloadRequest.Builder().filePath(materialRemote).fileName(fileName)
//                            .url(mediaItem.downloadUrl).build()
//                    downloadTasks.add(request.taskId)
//                    BiliEditorDownloader.addTask(request, observer)
//                    BiliEditorDownloader.startTask(request.taskId)
//                    mediaItem.downloadStatus = DownloadTaskState.DOWNLOADING
//                    mLastSelectedPreviewPosition = position
//                    it.notifyItemChanged(position)
//                }
//
//            }
//            it.setOnItemClickListener(object : BaseMediaAdapter.OnItemClickListener {
//                override fun checkValidate(item: MediaItem?): Boolean {
//                    if (item != null && item.isImage) {
//                        if (mRouterFrom == MaterialProtocol.SOURCE.AUDIO_DUBBING) {
//                            SnackBarBuilder.make(this@VideoSearchResultFragment,
//                                "目前仅支持上传视频素材哦！",
//                                SnackBarBuilder.SHORT).show()
//                            return false
//                        }
//                    }
//                    return true
//                }
//
//                override fun onClick(position: Int) {
//                    mSelectedItems?.let {
//                        if(it != null && it.size > 0){
//                            val routeRequest =
//                                RouteRequest.Builder(Uri.parse(RouterConstant.MATERIAL_ROUTER_URI))
//                                    .extras {
//                                        val bundlePicker = Bundle()
//                                        //bundlePicker.putInt(CommonConstant.EFFECTCENTER.TYPE, it[0].type)
//                                        bundlePicker.putInt(CommonConstant.EFFECTCENTER.EFFECT_ID, it[0].id.toInt())
//                                        bundlePicker.putInt(MaterialConfig.EXTRA_KEY_MATERIAL_INDEX, MaterialConfig.LOCAL_TAB_INDEX)
//                                        bundlePicker.putSerializable(MaterialConfig.EXTRA_KEY_MATERIAL_FILE_PATH, it[0])
//                                        bundlePicker.putSerializable(MaterialProtocol.MATERIAL_SOURCE_KEY, MaterialProtocol.SOURCE.MATERIAL_MARKET)
//                                        put(RouterConstant.ROUTER_PARAM_CONTROL, bundlePicker)
//                                    }
//                                    .build()
//                            BLRouter.routeTo(routeRequest)
//                            it[0]?.let { select ->
//                                StudioReport.reportMaterialCenterSingleIconClick(19,
//                                    select.id,
//                                    select.cat_id.toLong(),
//                                    true,
//                                    "search_detail",
//                                    select.badge,
//                                    position = position + 1)
//                            }
//
//                        }
//                    }
////                    it.notifyItemChanged(position);
//                }
//
//                override fun onPreviewClick(position: Int) {
//                    mLastSelectedPreviewPosition = -1;
//
//                    RemoteCenterPreviewInfo(
//                        mMimeType,
//                        mFullVideoItems,
//                        mFullVideoItems.indexOf(mFullItems[position]))?.let {
//                        MaterialCollectRepository.getInstance().isCollectedMaterials(CommonConstant.SERVER.TYPE_VIDEO, it.items[it.position].id.toInt()) { isCollected ->
//                            if (!isAlive(this@VideoSearchResultFragment)) {
//                                return@isCollectedMaterials
//                            }
//                            val intent = Intent(requireActivity(), MaterialPreviewActivity::class.java)
//                            intent.putExtra("MEDIA_ITEM", it.items[it.position].apply {
//                                this.isCollected = isCollected
//                                this.resourceId = this.id.toInt()
//                                this.resourceType = CommonConstant.SERVER.TYPE_VIDEO
//                            })
//                            startActivity(intent)
//                        }
//                    }
//                }
//            })
//            it.setSelectDatas(mSelectedItems)
//            recyclerView.adapter = it
//            recyclerView.layoutManager =
//                GridLayoutManager(ctx, 3, RecyclerView.VERTICAL, false).also { manager ->
//                    manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
//                        override fun getSpanSize(position: Int): Int {
//                            return 1
//                        }
//                    }
//                }
//            recyclerView.closeDefaultAnimator()
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        BLog.e(TAG,"onDestroyView")
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        EventBus.getDefault().unregister(this)
//        downloadTasks.forEach { taskId ->
//            BiliEditorDownloader.removeTaskObservers(taskId)
//        }
//        downloadTasks.clear()
//    }
//
//    private fun getDeeplinkPosition(items: List<MediaItem>): Int {
//        if (!TextUtils.isEmpty(mDeeplinkParams)) {
//            var deepLinkParser = DeepLinkParamsParser(mDeeplinkParams)
//            if (!TextUtils.isEmpty(deepLinkParser.materialId)) {
//                var materialId = deepLinkParser.materialId
//                items.forEachIndexed { index, mediaItem ->
//                    if (mediaItem.id.toString() == materialId) {
//                        return index
//                    }
//                }
//            }
//        }
//        return -1
//    }
//
//
//    override fun onRemoteDataLoaded(items: List<MediaItem>, fromStart: Boolean, hasMore: Boolean) {
//        BLog.e("$TAG:onRemoteDataLoaded:", "$this size=" + items.size)
//        if (isAlive(this)) {
////            mLlNetworkRoot.gone()
//            recyclerView.visible()
//            if (fromStart) {
//                mFullItems.clear()
//                if (items.isNotEmpty()) {
//                    mFullItems.addAll(items)
//                }
//                mFullVideoItems.clear()
//                mAdapter?.setDatas(mFullItems)
//                mAdapter?.notifyDataSetChanged()
//                // deeplink自动下载/选中
//                var position = getDeeplinkPosition(items)
//                if (position >= 0) {
//                    mAdapter?.chooseItem(recyclerView, position)
//                }
//            } else {
//                val preSize = mFullItems.size
//                if (items.isNotEmpty()) {
//                    mFullItems.addAll(items)
//                    mAdapter?.notifyItemRangeInserted(preSize, items.size)
//                }
//            }
//            items.filter { item -> !TextUtils.isEmpty(item.downloadUrl) }?.takeIf {
//                it.isNotEmpty()
//            }?.let { videos ->
//                mFullVideoItems.addAll(videos)
//            }
//        }
//    }
//
//    override fun onRemoteDataLoadFailed(fromStart: Boolean, materialId: String) {
//        if (isAlive(this)) {
//            if (fromStart && !TextUtils.isEmpty(materialId)) {
////                mPresenter.fetchMaterialListById(cat_id = mTabId,
////                    mode = mMaterialRemoteMode,
////                    fromStart = true,
////                    materialId = "",
////                    routerFrom = mRouterFrom,
////                    templateId = templateId,
////                    tuwenCatId = tuwenCatId)
//            } else {
//                recyclerView.gone()
//            }
//        }
//    }
//
//
//    @Subscribe
//    fun updateItem(updateItem: RemoteMediaItemUpdate) {
//        if (isAlive(this)) {
//            for (i in mFullItems.indices) {
//                if (updateItem.mediaItemId == mFullItems[i].id) {
//                    mAdapter?.notifyItemChanged(i)
//                    break
//                }
//            }
//        }
//    }
//
//    override fun updateSelectedItems() {
//        if (isAlive(this)) {
//            mAdapter?.notifyDataSetChanged()
//        }
//    }
//
//    /**
//     * 移除下载监听
//     */
//    private fun removeTaskObservers(taskId: Long) {
//        downloadTasks.remove(taskId)
//        BiliEditorDownloader.removeTaskObservers(taskId)
//    }
//
//    private val downloadTasks = arrayListOf<Long>()
//
//}