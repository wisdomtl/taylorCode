//package com.bilibili.studio.search.material.mapper
//
//import com.bilibili.studio.search.base.SearchEffect
//import com.bilibili.studio.search.material.data.MaterialSearchViewState
//import com.bilibili.studio.search.material.data.MaterialTabListBeanWrapper
//
//const val NETWORK_ERROR_STRING = "似乎已断开于互联网的连接"
//const val SEARCHMORE_NETWORK_ERROR_RETRY = "网络错误，请重试"
//const val HAS_NO_DATA = "暂无搜索结果"
//const val HAS_NO_MORE = "没有更多了"
//
//
//val materialSearchMapper: (MaterialTabListBeanWrapper?) -> SearchEffect.SearchByMaterialType<MaterialSearchViewState> =
//    { value ->
//        if (value == null) SearchEffect.SearchByMaterialType.Fail(NETWORK_ERROR_STRING)
//        else if (value.isSuccess) {
//            SearchEffect.SearchByMaterialType.Success(value, "")
//        } else {
//            SearchEffect.SearchByMaterialType.Fail(HAS_NO_DATA)
//        }
//    }
//
//val materialSearchMoreMapper: (MaterialTabListBeanWrapper?) -> SearchEffect.SearchByMaterialType<MaterialSearchViewState> =
//    { value ->
//        if (value == null) SearchEffect.SearchByMaterialType.Fail(SEARCHMORE_NETWORK_ERROR_RETRY)
//        else if (value.isSuccess) {
//            SearchEffect.SearchByMaterialType.Success(value, "")
//        } else {
//            SearchEffect.SearchByMaterialType.Fail(HAS_NO_MORE)
//        }
//    }