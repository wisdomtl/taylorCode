//package com.bilibili.studio.search.material.adapter.all
//
//import android.annotation.SuppressLint
//import android.content.Context
//import android.graphics.Rect
//import android.text.TextUtils
//import android.util.AttributeSet
//import android.view.LayoutInflater
//import android.widget.LinearLayout
//import android.widget.TextView
//import androidx.recyclerview.widget.GridLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import bolts.Task
//import com.bilibili.downloader.DownloadTaskState
//import com.bilibili.studio.common.EditorPaths.materialRemote
//import com.bilibili.studio.constants.CommonConstant
//import com.bilibili.studio.module.album.bean.RemoteMaterialCategoryBean
//import com.bilibili.studio.module.album.util.FileUtil
//import com.bilibili.studio.module.album.util.MaterialFilterUtils
//import com.bilibili.studio.module.material.model.MaterialMarketResponse
//import com.bilibili.studio.search.material.data.SearchResultListBean
//import com.bilibili.studio.search.material.listener.ISearchMaterialClickListener
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.FragmentActivity
//import com.bilibili.baseui.extension.*
//import com.bilibili.studio.module.material.model.MaterialMarketResponse.MaterialItem
//import com.bilibili.studio.module.material.view.MaterialProductView
//import com.bilibili.studio.report.StudioReport
//import com.bilibili.studio.search.R
//import com.bilibili.studio.search.base.SearchIntent
//import com.bilibili.studio.search.material.repository.MaterialSearchRepository
//import com.bilibili.utils.DensityUtil
//import com.bilibili.utils.ScreenUtil
//import com.bilibili.utils.Utils
//import jp.wasabeef.glide.transformations.internal.Utils
//import okhttp3.internal.concurrent.Task
//import test.taylor.com.taylorcode.R
//import test.taylor.com.taylorcode.kotlin.drawable_end
//import test.taylor.com.taylorcode.kotlin.padding_end
//import test.taylor.com.taylorcode.kotlin.padding_start
//
///**
// * 素材集市热门视频
// */
//class VideoItem : LinearLayout, IItemView<RemoteMaterialCategoryBean.RemoteMaterialItemBean>  {
//
//    private lateinit var titleTv: TextView
//    private lateinit var moreTv: TextView
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var layoutManager: GridLayoutManager
//    private lateinit var adapter: SubVideoAdapter
//    var clickListener: ISearchMaterialClickListener? = null
//    var from: String = ""
//
//
//    constructor(ctx: Context) : this(ctx, null)
//
//    constructor(ctx: Context, attrs: AttributeSet?) : this(ctx, attrs, 0)
//
//    constructor(ctx: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
//        ctx,
//        attrs,
//        defStyleAttr
//    ) {
//        LayoutInflater.from(ctx).inflate(R.layout.material_market_hotstem, this, true)
//        orientation = VERTICAL
//        initView(ctx)
//    }
//
//    fun initView(ctx: Context) {
//        titleTv = findViewById(R.id.title_tv)
//        moreTv = findViewById(R.id.more_iv)
//        moreTv.text ="查看全部"
//        moreTv.drawable_end = R.drawable.search_material_arrow_right
//        recyclerView = findViewById(R.id.recycler_view)
//        recyclerView.padding_start = 16
//        recyclerView.padding_end = 5
//        layoutManager = GridLayoutManager(ctx, 3, RecyclerView.VERTICAL, false)
//        recyclerView.addItemDecoration(object :RecyclerView.ItemDecoration() {
//            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
//                outRect.set(0,0,ScreenUtil.dip2px(11f),0)
//            }
//        })
//        recyclerView.layoutManager = layoutManager
//        adapter = SubVideoAdapter()
//        recyclerView.adapter = adapter
//    }
//
//    @SuppressLint("NotifyDataSetChanged")
//    override fun setData(data: SearchResultListBean<RemoteMaterialCategoryBean.RemoteMaterialItemBean>, section: Int, sectionIndex: Int) {
//        if (data == null) return
//        titleTv.textSize = 14f
//        titleTv.text = "视频库"
//        adapter.clickListener = clickListener
//        if (!TextUtils.isEmpty(from)) adapter.setFrom(from)
//
//        Task.callInBackground {
//            val set = FileUtil.allFilesInDir(
//                materialRemote
//            ) as HashSet<String>
//
//            data?.material_list?.map { source ->
//                transformData(source, set)
//            }
//        }.continueWith({
//            val items = it.result as MutableList<MaterialItem>
//            adapter.setData(items, section, sectionIndex)
//            null
//        }, Task.UI_THREAD_EXECUTOR)
//        moreTv.setOnClickListener {
//            //TODO()
////            StudioReport.reportMaterialCenterNewMaterialShow(data.name)
////            StudioReport.reportMaterialCenterFloorMoreClick(data.name,sectionIndex)
//            if(Utils.isFastClick()){
//                return@setOnClickListener
//            }
//            searchViewModel.send(SearchIntent.CheckWhole(MaterialSearchRepository.MATERIAL_TYPE_VIDEO))
//            StudioReport.reportMaterialCenterNewMaterialShow("视频库")
//        }
//    }
//
//    private fun transformData(
//        source: RemoteMaterialCategoryBean.RemoteMaterialItemBean,
//        materials: MutableSet<String>
//    ): MaterialMarketResponse.MaterialItem {
//        val mediaItem = MaterialMarketResponse.MaterialItem()
//        mediaItem.id = source.id
////        mediaItem.resourceId = source.id.toInt()
//        mediaItem.cover = source.cover
////        mediaItem.tp = source.tp
//        mediaItem.downloadUrl = if (source.tp==1) source.download_url else source.videopre_url
//        val fileName = MaterialFilterUtils.getFileName(mediaItem.downloadUrl, true)
//        if (materials.contains(fileName)){
//            mediaItem.downloadStatus = DownloadTaskState.FINISH
////            mediaItem.path = materialRemote + File.separator + fileName
//        }else{
//            mediaItem.downloadStatus = DownloadTaskState.CREATE
//        }
////        mediaItem.biz_from = source.biz_from
//        mediaItem.name = source.name
//        mediaItem.badge = source.badge
////        mediaItem.dataType = MediaItem.DataType.REMOTE
////        mediaItem.viewType = MediaItem.MediaViewType.ITEM
////        mediaItem.mimeType = if (mediaItem.tp==1) MediaItem.MIME_TYPE_IMAGE_PREFIX else MediaItem.MIME_TYPE_VIDEO_PREFIX
//        mediaItem.duration = source.duration.toLong()
//        mediaItem.type = CommonConstant.SERVER.TYPE_VIDEO
////        mediaItem.dataSource= MediaItem.Source.REMOTE
////        source.author?.let { author ->
////            mediaItem.authorName = author.name
////            mediaItem.authorAvatar = author.face
////        }
//        return mediaItem
//    }
//
//    companion object {
//
//        private const val DEFAULT_COUNT = 2
//
//    }
//
//    class SubVideoAdapter : RecyclerView.Adapter<SubVideoAdapter.ViewHolder>() {
//
//        private var section: Int = 0
//        private var sectionIndex: Int = 0
//        private var floorTitle: String = ""
//        var albumSectionIndex:Int?=null //专辑/主题页素材集合的楼层数
//        var albumSectionTitle:String?=null
//        private var floorLineType: Int = 0
//        private var products: List<MaterialItem>? = null
//        var clickListener: ISearchMaterialClickListener? = null
//        var mFrom: String? = null
//        var mIsLimitSize: Boolean = true
//
//        private val dp10 = DensityUtil.dp2px(10f)
//        private val dp12 = DensityUtil.dp2px(12f)
//
//        private var mCount = 2
//
//        fun setFrom(from: String?) {
//            mFrom = from
//        }
//
//        fun setLimitSize(isLimitSize: Boolean) {
//            mIsLimitSize = isLimitSize
//        }
//
//        @SuppressLint("NotifyDataSetChanged")
//        fun setData(list: MutableList<MaterialItem>, section: Int, sectionIndex: Int){
//            products = list
//            this.section = section
//            this.sectionIndex = sectionIndex
//            notifyDataSetChanged()
//        }
//
//        fun setFloorInfo(floorTitle: String?, floorLineType: Int?) {
//            this.floorTitle = floorTitle ?: ""
//            this.floorLineType = floorLineType ?: -1
//        }
//
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//            val inflate = LayoutInflater.from(parent.context).inflate(R.layout.material_search_product_view, parent, false)
//            return ViewHolder(inflate)
//        }
//
//        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//            val item = products?.get(position)!!
//            (holder.itemView as MaterialProductView).setData(item)
//            val id = if (item.type == CommonConstant.SERVER.TYPE_BGM) item.sid else item.id
//            StudioReport.reportMaterialCenterSingleIconShow(item.type, id, item.catId.toLong(), "search_detail", item.badge,floorTitle,sectionIndex,position+1,albumSectionTitle,albumSectionIndex)
//        }
//
//        override fun getItemCount(): Int {
//            products?.let {
//                if (mIsLimitSize) {
//                    return if(it.size > 6) 6 else it.size
//                }
//                return it.size
//            }
//            return 0
//        }
//
//        inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
//            init{
//                itemView.setOnClickListener{
//                    if (Utils.isFastClick()) return@setOnClickListener
//                    val item = products?.get(adapterPosition)!!
//                    clickListener?.onItemClickListener(item, section, albumSectionIndex?:sectionIndex, position =  adapterPosition)
//                }
//            }
//        }
//
//    }
//
//    override val activity: FragmentActivity
//        get() = context as FragmentActivity
//
//}