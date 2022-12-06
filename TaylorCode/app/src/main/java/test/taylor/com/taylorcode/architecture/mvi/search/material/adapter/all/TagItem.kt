//package com.bilibili.studio.search.material.adapter.all
//
//import android.content.Context
//import android.os.Bundle
//import android.util.AttributeSet
//import android.view.LayoutInflater
//import android.view.View
//import android.widget.LinearLayout
//import android.widget.TextView
//import androidx.fragment.app.FragmentActivity
//import androidx.recyclerview.widget.GridLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.bilibili.studio.search.R
//import com.bilibili.studio.module.audio.model.AudioEffectChildrenBean
//import com.bilibili.studio.module.bgm.utils.UpperTimeFormat
//import com.bilibili.studio.module.caption.adapter.CommonAdapter
//import com.bilibili.studio.module.material.model.MaterialMarketResponse
//import com.bilibili.studio.report.StudioReport
//import com.bilibili.studio.search.base.SearchIntent
//import com.bilibili.studio.search.material.data.SearchResultListBean
//import com.bilibili.studio.search.material.listener.ISearchMaterialClickListener
//import com.bilibili.studio.search.material.repository.MaterialSearchRepository
//import com.bilibili.utils.*
//import jp.wasabeef.glide.transformations.internal.Utils
//import test.taylor.com.taylorcode.R
//
///**
// * 素材集市音乐库
// */
//class TagItem : LinearLayout, IItemView<AudioEffectChildrenBean> {
//
//    private var sectionIndex: Int = 0
//    private var floorTitle: String = ""
//    private lateinit var titleTv: TextView
//    private lateinit var moreTv: TextView
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var adapter: CommonAdapter<AudioEffectChildrenBean>
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
//            searchViewModel.send(SearchIntent.CheckWhole(MaterialSearchRepository.MATERIAL_TYPE_TAG))
//            StudioReport.reportMaterialCenterFloorMoreClick(floorTitle,sectionIndex)
//        }
//        recyclerView = findViewById(R.id.recycler_view)
//    }
//
//
//    private fun transformData(bean: AudioEffectChildrenBean): MaterialMarketResponse.MaterialItem {
//        val item = MaterialMarketResponse.MaterialItem()
//        item.id = bean.id
//        //  item.sid = bean.sid
//        item.name = bean.name
//        item.type = bean.type
//        item.duration = bean.duration.toLong()
//        item.downloadUrl = bean.download_url
//        item.playUrl = bean.download_url
//        return item
//    }
//
//    private fun onBindView(view: View, bean: AudioEffectChildrenBean, i: Int) {
//        view.findViewById<TextView>(R.id.audio_name).text = bean.name
//        view.findViewById<TextView>(R.id.audio_time).text = UpperTimeFormat.formatTimeWithHour((bean.duration).toLong())
//        StudioReport.reportMaterialCenterSingleIconShow(bean.type,
//            bean.id,
//            /*mCatId*/bean.cat_id.toLong(),
//            "search_detail",
//            bean.badge, position = i+1)
//    }
//
//    override fun setData(data: SearchResultListBean<AudioEffectChildrenBean>, section: Int, sectionIndex: Int) {
//        if (data == null) {
//            return
//        }
//        titleTv.text = "音效"
//        adapter = CommonAdapter<AudioEffectChildrenBean>().apply {
//            layout = R.layout.search_material_tags_item
//            onBind = ::onBindView
//            onClick = { view: View, audioEffectChildrenBean: AudioEffectChildrenBean, i: Int ->
//                clickListener?.onItemClickListener(transformData(audioEffectChildrenBean), MaterialMarketResponse.MUSIC, sectionIndex,position = i)
//            }
//        }
////        adapter.data = data.material_list?.let {
////            it.subList(0, if (it.size > 6) 6 else it.size)
////        }
//        adapter.data = data.material_list?.take(6)
//        recyclerView.adapter = adapter
//        this.sectionIndex = sectionIndex
//        this.floorTitle = "音效"
//        setSpanCount(data.material_list!!.size, 2)
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
//    override val activity: FragmentActivity
//        get() = context as FragmentActivity
//}