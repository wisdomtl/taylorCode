//package com.bilibili.studio.search.material.adapter
//
//import android.text.TextUtils
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.LinearLayout
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.bilibili.studio.constants.CommonConstant
//import com.bilibili.studio.search.R
//import com.bilibili.studio.model.Bgm
//import com.bilibili.studio.module.bgm.utils.UpperTimeFormat
//import com.bilibili.studio.module.imageloader.BCutImageLoader
//import com.bilibili.studio.module.material.view.MaterialTagContainer
//import com.bilibili.studio.report.StudioReport
//import com.bilibili.utils.DensityUtil
//import com.bilibili.utils.ScreenUtil
//import com.facebook.drawee.view.SimpleDraweeView
//import test.taylor.com.taylorcode.R
//
//
//class SearchMaterialMusicAdapter : RecyclerView.Adapter<SearchMaterialMusicAdapter.ViewHolder>() {
//
//
//    private var mData: MutableList<Bgm>? = null
//    var clickListener: ClickListener? = null
//    var catId: Long = 0
//
//
//    fun setData(list: MutableList<Bgm>?) {
//        mData = list
//        notifyDataSetChanged()
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.material_music_item, parent, false))
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        mData?.get(position)?.let { bgm ->
//            if (!TextUtils.isEmpty(bgm.cover)) {
//                BCutImageLoader.with<SimpleDraweeView>(holder.imageCover.context).url(bgm.cover)
//                    .load(holder.imageCover)
//            }
//            holder.nameView.text = bgm.name
//            holder.creatorView.text = bgm.musicians
//            println(bgm.id)
//            holder.timeView.text = UpperTimeFormat.formatTimeWithHour(bgm.duration * 1000)
//            holder.bottomView.measure( View.MeasureSpec.makeMeasureSpec(
//                ScreenUtil.getScreenWidth(holder.itemView.context) - DensityUtil.dp2px(90f),
//                View.MeasureSpec.EXACTLY
//            ), View.MeasureSpec.UNSPECIFIED)
//            holder.tagView.removeAllViews()
//            bgm.tags?.let { holder.tagView.updateTags(it, ScreenUtil.dip2px(6f)) }
//            StudioReport.reportMaterialCenterSingleIconShow(bgm.type, bgm.id, bgm.cat_id.toLong(), "search_detail", bgm.badge,position = position+1)
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return mData?.size ?: 0
//    }
//
//    fun setInitItemClick(position: Int){
//        if(position <= itemCount){
//            clickListener?.onItemClick(position, mData?.get(position)!!)
//        }
//    }
//
//
//
//    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
//        val imageCover: SimpleDraweeView = itemView.findViewById(R.id.music_item_iv)
//        val nameView: TextView = itemView.findViewById(R.id.music_name)
//        val creatorView: TextView = itemView.findViewById(R.id.music_creator)
//        val tagView: MaterialTagContainer = itemView.findViewById(R.id.tag_contain)
//        val timeView: TextView = itemView.findViewById(R.id.music_time)
//        val bottomView: LinearLayout = itemView.findViewById(R.id.ll_bottom)
//        init{
//            itemView.setOnClickListener {
//                clickListener?.onItemClick(adapterPosition, mData?.get(adapterPosition)!!)
//            }
//        }
//    }
//
//    interface ClickListener{
//        fun onItemClick(index: Int, tab: Bgm)
//    }
//}