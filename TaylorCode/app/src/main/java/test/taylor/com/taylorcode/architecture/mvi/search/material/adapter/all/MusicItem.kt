//package com.bilibili.studio.search.material.adapter.all
//
//import android.content.Context
//import android.os.Bundle
//import android.util.AttributeSet
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.LinearLayout
//import android.widget.TextView
//import androidx.fragment.app.FragmentActivity
//import androidx.recyclerview.widget.GridLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.bilibili.baseui.extension.dp
//import com.bilibili.droid.ScreenUtil.getScreenWidth
//import com.bilibili.lib.blrouter.BLRouter
//import com.bilibili.lib.blrouter.RouteRequest
//import com.bilibili.studio.constans.RouterConstant
//import com.bilibili.studio.constants.CommonConstant
//import com.bilibili.studio.model.Bgm
//import com.bilibili.studio.module.imageloader.BCutImageLoader
//import com.bilibili.studio.module.material.model.MaterialMarketResponse
//import com.bilibili.studio.module.material.view.MaterialTagContainer
//import com.bilibili.studio.report.StudioReport
//import com.bilibili.studio.search.R
//import com.bilibili.studio.search.base.SearchIntent
//import com.bilibili.studio.search.material.data.SearchResultListBean
//import com.bilibili.studio.search.material.listener.ISearchMaterialClickListener
//import com.bilibili.studio.search.material.repository.MaterialSearchRepository
//import com.bilibili.utils.*
//import com.facebook.drawee.view.SimpleDraweeView
//import jp.wasabeef.glide.transformations.internal.Utils
//import test.taylor.com.taylorcode.R
//import test.taylor.com.taylorcode.util.DateUtil
//import test.taylor.com.taylorcode.util.DimensionUtil.getScreenWidth
//
///**
// * 素材集市音乐库
// */
//class MusicItem : LinearLayout, IItemView<Bgm> {
//
//    private var sectionIndex: Int = 0
//    private var floorTitle: String = ""
//    private lateinit var titleTv: TextView
//    private lateinit var moreTv: TextView
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var adapter: MusicAdapter
//    var clickListener: ISearchMaterialClickListener? = null
//
//    private var musicList: List<Bgm>? = null
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
//        LayoutInflater.from(ctx).inflate(R.layout.search_material_market_music, this, true)
//        orientation = VERTICAL
//        initView(ctx)
//    }
//
//    fun initView(ctx: Context) {
//        titleTv = findViewById(R.id.title_tv)
//        moreTv = findViewById(R.id.more_iv)
//        moreTv.visibility = View.VISIBLE
//        moreTv.setOnClickListener {
//            if(Utils.isFastClick()){
//                return@setOnClickListener
//            }
//            searchViewModel.send(SearchIntent.CheckWhole(MaterialSearchRepository.MATERIAL_TYPE_BGM))
//            StudioReport.reportMaterialCenterFloorMoreClick(floorTitle,sectionIndex)
//        }
//        recyclerView = findViewById(R.id.recycler_view)
//
//        adapter = MusicAdapter()
//        recyclerView.adapter = adapter
//    }
//
//    override fun setData(data: SearchResultListBean<Bgm>, section: Int, sectionIndex: Int) {
//        if (data == null) {
//            return
//        }
//        titleTv.text = "音乐"
//        musicList = data.material_list
//        this.sectionIndex = sectionIndex
//        this.floorTitle = "音乐"
//        setSpanCount(data.material_list!!.size,2)
//    }
//
//    private fun setSpanCount(size: Int, spanCount: Int) {
//        val layoutManager: RecyclerView.LayoutManager = if (size <= 3) {
//            GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, false)
//            //            adapter.setSpanCount(1)
//        } else {
//            GridLayoutManager(context, spanCount, GridLayoutManager.HORIZONTAL, false)
//            //            adapter.setSpanCount(spanCount)
//        }
//        recyclerView.layoutManager = layoutManager
//    }
//
//
//    inner class MusicAdapter : RecyclerView.Adapter<ViewHolder>() {
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//            return ViewHolder(
//                LayoutInflater.from(parent.context)
//                    .inflate(R.layout.search_market_music, parent, false)
//            )
//        }
//
//        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
////            holder.itemView.layoutParams = RecyclerView.LayoutParams(ScreenUtil.getScreenWidth(holder.itemView.context) - ScreenUtil.dip2px(60f), RecyclerView.LayoutParams.WRAP_CONTENT)
//            val music = musicList?.get(position)
//            music ?: return
//            BCutImageLoader.with<SimpleDraweeView>(holder.imageView.context)
//                .url(music.cover)
//                .size(135.dp, 56.dp)
//                .load(holder.imageView)
////            val num = position + 1
////            holder.numberView.visixbleOrGone(num in 1..3).let { if (it.isVisible()) it.text = "NO. $num" }
//            holder.nameView.text = music.name
//            holder.creatorView.text = music.musicians
//            holder.durationView.text = DateUtil.duration2time(music.duration * 1000)
//            holder.tagParent.measure(
//                MeasureSpec.makeMeasureSpec(
//                    getScreenWidth(context) - DensityUtil.dp2px(90f),
//                    MeasureSpec.EXACTLY
//                ), MeasureSpec.UNSPECIFIED
//            )
//            holder.tagView.removeAllViews()
//            music.tags?.let {
//                holder.tagView.updateTags(it, ScreenUtil.dip2px(6f))
//            }
//            StudioReport.reportMaterialCenterSingleIconShow(CommonConstant.SERVER.TYPE_BGM, music.id, music.cat_id.toLong(), "search_detail", music.badge,floorTitle,sectionIndex,position=position+1)
////            StudioReport.reportOnlyMaterialCenterSingleIconShow(
////                "${music.type}",
////                floorTitle,
////                "3",
////                "${music.cat_id}",
////                "${music.id}",
////                music.badge ?: ""
////            )
//        }
//
//        override fun getItemCount(): Int {
//            musicList?.let {
//                return if (it.size > 6) 6 else it.size
//            }
//            return 0
//        }
//    }
//
//    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
////        val numberView: TextView = itemView.findViewById(R.id.number_tv)
//        val imageView: SimpleDraweeView = itemView.findViewById(R.id.music_iv)
//        val nameView: TextView = itemView.findViewById(R.id.music_tv)
//        val creatorView: TextView = itemView.findViewById(R.id.creator_tv)
//        val tagView: MaterialTagContainer = itemView.findViewById(R.id.tag_contain)
//        val durationView: TextView = itemView.findViewById(R.id.duration_tv)
//        val tagParent: LinearLayout = itemView.findViewById(R.id.tag_parent)
//        init {
////            val typeface = FontUtils.getFont(itemView.context, CommonConstant.FONT.STUDIO_MEDIUM)
////            numberView.typeface = typeface
//            itemView.setOnClickListener {
//                if (Utils.isFastClick()) return@setOnClickListener
//                val music = musicList?.get(adapterPosition)!!
//                clickListener?.onItemClickListener(transformData(music), MaterialMarketResponse.MUSIC, sectionIndex,position = adapterPosition)
//            }
//        }
//    }
//
//
//    private fun transformData(bean: Bgm): MaterialMarketResponse.MaterialItem {
//        val item = MaterialMarketResponse.MaterialItem()
//        item.id = bean.id
//        item.sid = bean.sid
//        item.name = bean.name
//        item.duration = bean.duration * 1000
//        item.downloadUrl = bean.playurl
//        item.cover = bean.cover
//        item.musicians = bean.musicians
//        item.type = CommonConstant.SERVER.TYPE_BGM
//        return item
//    }
//
//    override val activity: FragmentActivity
//        get() = context as FragmentActivity
//}