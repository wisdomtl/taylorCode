//package com.bilibili.studio.search.material.adapter.all
//
//import android.content.Context
//import android.util.AttributeSet
//import android.view.LayoutInflater
//import android.view.View
//import android.widget.LinearLayout
//import android.widget.TextView
//import androidx.fragment.app.FragmentActivity
//import androidx.recyclerview.widget.GridLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.bilibili.baseui.extension.dp
//import com.bilibili.baseui.extension.margin_end
//import com.bilibili.baseui.extension.margin_start
//import com.bilibili.studio.model.EffectDataEntity
//import com.bilibili.studio.module.caption.adapter.CommonAdapter
//import com.bilibili.studio.search.R
//import com.bilibili.studio.module.imageloader.BCutImageLoader
//import com.bilibili.studio.module.material.model.MaterialMarketResponse
//import com.bilibili.studio.report.StudioReport
//import com.bilibili.studio.search.base.SearchIntent
//import com.bilibili.studio.search.material.data.SearchResultListBean
//import com.bilibili.studio.search.material.listener.ISearchMaterialClickListener
//import com.bilibili.studio.search.material.repository.MaterialSearchRepository
//import com.bilibili.utils.*
//import com.facebook.drawee.view.SimpleDraweeView
//import test.taylor.com.taylorcode.R
//
///**
// * 素材集市音乐库
// */
//class StickyItem : LinearLayout, IItemView<EffectDataEntity> {
//
//    private var mSize = 0
//    private var sectionIndex: Int = 0
//    private var floorTitle: String = ""
//    private lateinit var titleTv: TextView
//    private lateinit var moreTv: TextView
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var adapter: CommonAdapter<EffectDataEntity>
//    var clickListener: ISearchMaterialClickListener? = null
//
//    private var musicList: List<MaterialMarketResponse.MaterialItem>? = null
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
//            searchViewModel.send(SearchIntent.CheckWhole(MaterialSearchRepository.MATERIAL_TYPE_PICTURE))
//            StudioReport.reportMaterialCenterFloorMoreClick(floorTitle, sectionIndex)
//        }
//        recyclerView = findViewById(R.id.recycler_view)
//        val layoutManager = GridLayoutManager(ctx, 3,GridLayoutManager.VERTICAL,false)
//        recyclerView.layoutManager = layoutManager
//        recyclerView.margin_start = 5.dp
//        recyclerView.margin_end = 5.dp
//    }
//
//
//    private fun transformData(bean: EffectDataEntity): MaterialMarketResponse.MaterialItem {
//        val item = MaterialMarketResponse.MaterialItem()
//        item.id = bean.id.toLong()
//        item.name = bean.name
//        item.type = bean.type
//        item.cover = bean.cover
////        item.downloadUrl = bean.download_url
////        item.playUrl = bean.download_url
//        item.catId = bean.cat_id
//        return item
//    }
//
//    private fun onCreateView(view: View) {
//        if (mSize == 0) {
//            mSize = (ScreenUtil.getScreenWidth(context) -ScreenUtil.dip2px(context, 16f) * 2- ScreenUtil.dip2px(context, 11f) * 2) / 3
//        }
//        val layoutParams = view.findViewById<SimpleDraweeView>(R.id.siv_cover).layoutParams
//        layoutParams.width = mSize
//        layoutParams.height = mSize
//    }
//    private fun onBindView(view: View, effectDetailsEntity: EffectDataEntity, position: Int) {
//        view.findViewById<TextView>(R.id.tv_name).text = effectDetailsEntity?.name
//
//        BCutImageLoader.with<SimpleDraweeView>(context).url(effectDetailsEntity?.cover)
//            .load(view.findViewById(R.id.siv_cover))
//
//        StudioReport.reportMaterialCenterSingleIconShow(effectDetailsEntity.type,
//            effectDetailsEntity.id.toLong(),
//            /*mCatId*/effectDetailsEntity.cat_id.toLong(),
//            "search_detail",
//            effectDetailsEntity.badge,
//            position=position+1)
//    }
//
//    override fun setData(data: SearchResultListBean<EffectDataEntity>, section: Int, sectionIndex: Int) {
//        if (data == null) {
//            return
//        }
//        titleTv.text = "贴纸"
//        adapter = CommonAdapter<EffectDataEntity>().apply {
//            layout = R.layout.material_search_sticky_item
//            onCreateView = ::onCreateView
//            onBind = ::onBindView
//            onClick = { view: View, EffectDataEntity: EffectDataEntity, i: Int ->
//                clickListener?.onItemClickListener(transformData(EffectDataEntity), MaterialMarketResponse.CAT_DOG_STICKER, sectionIndex,position = i)
//            }
//        }
////        adapter.data = data.material_list?.let {
////            it.subList(0, if (it.size > 6) 6 else it.size)
////        }
//        adapter.data = data.material_list?.take(6)
//        recyclerView.adapter = adapter
//        this.sectionIndex = sectionIndex
//        this.floorTitle = "贴纸"
//    }
//    override val activity: FragmentActivity
//        get() = context as FragmentActivity
//}