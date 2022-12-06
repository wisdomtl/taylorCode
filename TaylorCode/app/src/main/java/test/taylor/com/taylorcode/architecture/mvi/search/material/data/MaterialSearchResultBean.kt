//package com.bilibili.studio.search.material.data
//
//import androidx.annotation.Keep
//import com.bilibili.studio.model.Bgm
//import com.bilibili.studio.model.EffectDataEntity
//import com.bilibili.studio.module.album.bean.RemoteMaterialCategoryBean
//import com.bilibili.studio.module.audio.model.AudioEffectChildrenBean
//import com.bilibili.studio.search.material.repository.MaterialSearchRepository
//
//@Keep
//class MaterialSearchResultBean(
//    var materials: MaterialSearchResultListBean?,
//)
//
//
//@Keep
//class MaterialSearchResultListBean(
//    var bgm: SearchResultListBean<Bgm>?,
//    var picture: SearchResultListBean<EffectDataEntity>?,
//    var tag: SearchResultListBean<AudioEffectChildrenBean>?,
//    var video: SearchResultListBean<RemoteMaterialCategoryBean.RemoteMaterialItemBean>?,
//)
//
//@Keep
//class SearchResultListBean<T>(
//    var material_list: MutableList<T>? = mutableListOf(),
//    var page: Page?,
//) {
//    var materialType: Int = 0
//    fun checkisEmpty(): Boolean {
//        return material_list.isNullOrEmpty()
//    }
//}
//
//@Keep
//class Page(
//    var num: Int,
//    var size: Int,
//    var total: Int,
//    var has_more: Boolean? = false,
//    var weshine: Int? = 0,
//    var es: Int? = 0,
//)
//
