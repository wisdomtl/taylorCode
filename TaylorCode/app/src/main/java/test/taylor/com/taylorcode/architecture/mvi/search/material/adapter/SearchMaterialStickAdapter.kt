//package com.bilibili.studio.search.material.adapter
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.bilibili.studio.constants.CommonConstant
//import com.bilibili.studio.model.EffectDataEntity
//import com.bilibili.studio.module.imageloader.BCutImageLoader
//import com.bilibili.studio.report.StudioReport
//import com.bilibili.studio.search.R
//import com.bilibili.utils.ScreenUtil
//import com.facebook.drawee.view.SimpleDraweeView
//import test.taylor.com.taylorcode.R
//
///**
// * Created by xf on 2020/5/22
// * email: yanglinfeng@bilibili.com
// * since: 1.0.0
// */
//class SearchMaterialStickAdapter : RecyclerView.Adapter<SearchMaterialStickAdapter.ViewHolder>() {
//    private var mData: List<EffectDataEntity>? = null
//    private var mOnItemClickListener: OnItemClickListener? = null
//    private var mSize = 0
//    private var mShowTag: Boolean = false
//    private var mCurrentType = 0
//    private var mOrder = CommonConstant.EFFECTCENTER.ORDER_HOT
//
//    var attachToWindowCallback: ((Runnable) -> Unit)? = null
//
//
//    companion object {
//        fun getTagByType(type: Int): String {
//            when (type) {
//                CommonConstant.SERVER.TYPE_FILTER -> {
//                    return "滤镜"
//                }
//                CommonConstant.SERVER.TYPE_EFFECT -> {
//                    return "特效"
//                }
//                CommonConstant.SERVER.TYPE_BACKGROUND -> {
//                    return "背景"
//                }
//                CommonConstant.SERVER.TYPE_VSTICKER -> {
//                    return "贴纸"
//                }
//                CommonConstant.SERVER.TYPE_FONT -> {
//                    return "字体"
//                }
//                CommonConstant.SERVER.TYPE_SUBTITLE -> {
//                    return "字幕"
//                }
//                CommonConstant.SERVER.TYPE_TRANSITION -> {
//                    return "转场"
//                }
//                CommonConstant.SERVER.TYPE_VIDEO -> {
//                    return "视频"
//                }
//                CommonConstant.SERVER.TYPE_EFFECT_PACKAGE -> {
//                    return "素材包"
//                }
//            }
//            return ""
//        }
//    }
//
//    fun setData(data: List<EffectDataEntity>?) {
//        mData = data
//    }
//
//    fun setCurrentType(type: Int) {
//        mCurrentType = type
//    }
//
//    fun setOrder(order:Int){
//        mOrder = order
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        if (mSize == 0) {
//            mSize = (ScreenUtil.getScreenWidth(parent.context) - ScreenUtil.dip2px(parent.context, 16f) * 2 - ScreenUtil.dip2px(parent.context, 11f)*2) / 3
//        }
//        val viewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.search_item_effect_details, parent, false))
//        val layoutParams = viewHolder.mSivCover.layoutParams
//        layoutParams.width = mSize
//        layoutParams.height = mSize
//        viewHolder.itemView.setOnClickListener {
//            if (viewHolder.adapterPosition != -1) {
//                val item = mData?.get(viewHolder.adapterPosition)
//                mOnItemClickListener?.onItemClick(item,viewHolder.bindingAdapterPosition)
//                item?.let { StudioReport.reportMaterialCenterAlbumClick(it.type.toString(), it.id.toString(), "tab_detail", "") }
//            }
//        }
//        return viewHolder
//    }
//
//    override fun getItemCount() = mData?.size ?: 4
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val effectDetailsEntity = mData?.get(position)
//        holder.mTvName.text = effectDetailsEntity?.name
//
//        BCutImageLoader.with<SimpleDraweeView>(holder.mSivCover.context).url(effectDetailsEntity?.cover)
//            .load(holder.mSivCover)
//        effectDetailsEntity?.let {
//            StudioReport.reportMaterialCenterSingleIconShow(it.type, it.id.toLong(),it.cat_id.toLong(),"search_detail", it.badge, position = position+1)
//        }
//    }
//
//
//    override fun getItemId(position: Int) = position.toLong()
//
//
//    override fun onViewAttachedToWindow(holder: ViewHolder) {
//        super.onViewAttachedToWindow(holder)
//        mData?.getOrNull(holder.adapterPosition)?.takeIf {
//            !it.exposed
//        }?.let { item ->
//            item.exposed = true
//            attachToWindowCallback?.invoke(Runnable {
//                StudioReport.reportMaterialAlbumShow(effectFromType =  item.type, album_id = item.id, albumRank = if (mOrder==CommonConstant.EFFECTCENTER.ORDER_HOT) 0 else 1,
//                    pageFrom = "all",
//                    fromTab = mCurrentType)
//            })
//        }
//    }
//
//    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val mTvName: TextView = itemView.findViewById(R.id.tv_name)
//        val mSivCover: SimpleDraweeView = itemView.findViewById(R.id.siv_cover)
//    }
//
//    fun addOnItemClickListener(l: OnItemClickListener) {
//        mOnItemClickListener = l
//    }
//
//    fun setShowTag(showTag: Boolean) {
//        mShowTag = showTag
//    }
//
//    interface OnItemClickListener {
//        fun onItemClick(entity: EffectDataEntity?, adapterPosition: Int)
//    }
//}