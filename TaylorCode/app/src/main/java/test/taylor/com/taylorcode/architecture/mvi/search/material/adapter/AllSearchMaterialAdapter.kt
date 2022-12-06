//package com.bilibili.studio.search.material.adapter
//
//import android.annotation.SuppressLint
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.bilibili.studio.model.Bgm
//import com.bilibili.studio.model.EffectDataEntity
//import com.bilibili.studio.module.album.bean.RemoteMaterialCategoryBean
//import com.bilibili.studio.module.audio.model.AudioEffectChildrenBean
//import com.bilibili.studio.search.material.adapter.all.*
//import com.bilibili.studio.search.material.data.MaterialSearchResultListBean
//import com.bilibili.studio.search.material.data.SearchResultListBean
//import com.bilibili.studio.search.material.listener.ISearchMaterialClickListener
//import com.bilibili.studio.search.material.repository.MaterialSearchRepository
//
///**
// * 搜索全部
// */
//class AllSearchMaterialAdapter : RecyclerView.Adapter<AllSearchMaterialAdapter.ViewHolder>() {
//
//    private var material = mutableListOf<SearchResultListBean<*>>()
//    var clickListener: ISearchMaterialClickListener? = null
//
//    @SuppressLint("NotifyDataSetChanged")
//    fun setData(data: MaterialSearchResultListBean?) {
//        material.clear()
//        data?.video?.apply {
//            if (!checkisEmpty()) {
//                materialType = MaterialSearchRepository.MATERIAL_TYPE_VIDEO
//                material.add(this)
//            }
//        }
//
//        data?.bgm?.apply {
//            if (!checkisEmpty()) {
//                materialType = MaterialSearchRepository.MATERIAL_TYPE_BGM
//                material.add(this)
//            }
//        }
//
//        data?.tag?.apply {
//            if (!checkisEmpty()) {
//                materialType = MaterialSearchRepository.MATERIAL_TYPE_TAG
//                material.add(this)
//            }
//        }
//
//        data?.picture?.apply {
//            if (!checkisEmpty()) {
//                materialType = MaterialSearchRepository.MATERIAL_TYPE_PICTURE
//                material.add(this)
//            }
//        }
//
//        notifyDataSetChanged()
//    }
//
////    fun getData(): MaterialSearchResultListBean? {
////        return material
////    }
//
////    fun getMaterialSection(sectionIndex: Int): MaterialMarketResponse.MaterialSection? {
////        return material?.getItemValue(sectionIndex) as? MaterialMarketResponse.MaterialSection
////    }
//
//    override fun getItemCount(): Int {
//        return material.size
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        return material[position].materialType
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//
//        when (viewType) {
//
//            MaterialSearchRepository.MATERIAL_TYPE_VIDEO -> {
//                val view = VideoItem(parent.context)
//                view.clickListener = clickListener
//                return ViewHolder(view)
//            }
//
//            MaterialSearchRepository.MATERIAL_TYPE_BGM -> {
//                val view = MusicItem(parent.context)
//                view.clickListener = clickListener
//                return ViewHolder(view)
//            }
//
//            MaterialSearchRepository.MATERIAL_TYPE_TAG -> {
//                val view = TagItem(parent.context)
//                view.clickListener = clickListener
//                return ViewHolder(view)
//            }
//
//            MaterialSearchRepository.MATERIAL_TYPE_PICTURE -> {
//                val view = StickyItem(parent.context)
//                view.clickListener = clickListener
//                return ViewHolder(view)
//            }
//
//            else -> {
//                return ViewHolder(View(parent.context))
//            }
//        }
//    }
//
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        if (holder.itemView is VideoItem) {
//            holder.itemView.setData(material[position] as SearchResultListBean<RemoteMaterialCategoryBean.RemoteMaterialItemBean>, getItemViewType(position), position)
//        }else if(holder.itemView is MusicItem){
//            holder.itemView.setData(material[position] as SearchResultListBean<Bgm>, getItemViewType(position), position)
//        }else if(holder.itemView is TagItem){
//            holder.itemView.setData(material[position] as SearchResultListBean<AudioEffectChildrenBean>, getItemViewType(position), position)
//        }else if(holder.itemView is StickyItem){
//            holder.itemView.setData(material[position] as SearchResultListBean<EffectDataEntity>, getItemViewType(position), position)
//        }
//    }
//
//
//    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
//
//
//}