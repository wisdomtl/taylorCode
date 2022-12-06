//package com.bilibili.studio.search.material.repository
//
//import android.util.SparseArray
//import com.bilibili.base.BiliContext
//import com.bilibili.lib.accounts.BiliAccounts
//import com.bilibili.okretro.BiliApiCallback
//import com.bilibili.okretro.GeneralResponse
//import com.bilibili.okretro.ServiceGenerator
//import com.bilibili.studio.search.base.BaseSearchRepository
//import com.bilibili.studio.search.material.data.MaterialSearchResultBean
//import com.bilibili.studio.search.template.data.SearchHintsBean
//import com.bilibili.studio.search.material.data.MaterialTabListBeanWrapper
//import com.bilibili.studio.search.material.data.Page
//import kotlinx.coroutines.suspendCancellableCoroutine
//
//class MaterialSearchRepository : BaseSearchRepository<MaterialTabListBeanWrapper?> {
//
//    companion object {
//        const val RECOMMEND_CAT_ID = 690116
//        const val ONE_PAGE_CAPACITY = 20
//        const val CATEGORY_ID_RECOMMEND = 1987 //magic number, no actual meaning
//        const val CATEGORY_ID_SEARCH = 1736 //magic number, no actual meaning
//
//        const val MATERIAL_TYPE_BGM = 1
//        const val MATERIAL_TYPE_PICTURE = 2
//        const val MATERIAL_TYPE_VIDEO = 3
//        const val MATERIAL_TYPE_TAG = 4
//        const val MATERIAL_TYPE_ALL = 5
//    }
//
//    private var loadState = SparseArray<Boolean>()
//
//    private var pages = SparseArray<Page>()
//
//    override suspend fun search(
//        keyword: String,
//        extraParam: Map<String, Any>?,
//    ): MaterialTabListBeanWrapper? {
//        return searchMaterial(keyword, MATERIAL_TYPE_ALL)
//    }
//
//    override suspend fun searchMore(
//        keyword: String,
//        extraParam: Map<String, Any>?,
//    ): MaterialTabListBeanWrapper? { // search success, continue with search
//        val materialType = extraParam!!["materialType"] as? Int ?: MATERIAL_TYPE_ALL
//        loadState[materialType] = true
//        val result = searchMaterial(keyword, materialType)
//        loadState[materialType] = false
//        return result
//    }
//
//    override suspend fun fetchSearchHint(keyword: String): List<String> =
//        suspendCancellableCoroutine { continuation ->
//            ServiceGenerator.createService(MaterialApiService::class.java)
//                .fetchHints(BiliAccounts.get(BiliContext.application()).accessKey ?: "", keyword)
//                .enqueue(object : BiliApiCallback<GeneralResponse<SearchHintsBean>>() {
//                    override fun onSuccess(result: GeneralResponse<SearchHintsBean>?) {
//                        if (result?.data?.hints?.isNotEmpty() == true) {
//                            val hints =
//                                if (result.data.hints.contains(keyword)) result.data.hints else listOf(
//                                    keyword,
//                                    *result.data.hints.toTypedArray())
//                            continuation.resume(hints, null)
//                        } else {
//                            continuation.resume(listOf(keyword), null)
//                        }
//                    }
//
//                    override fun onError(t: Throwable?) {
//                        continuation.resume(listOf(keyword), null)
//                    }
//                })
//        }
//
//    override fun clear() {
//        pages.clear()
//        loadState.clear()
//    }
//
//    override fun hasMore(materialType: Int): Boolean {
//        pages[materialType]?.apply {
//            return size * num < total
//        }
//        return true
//    }
//
//    override fun isLoading(materialType: Int): Boolean {
//        return loadState[materialType, false]
//    }
//
//    private suspend fun searchMaterial(
//        keyword: String,
//        materialType: Int,
//    ) =
//        suspendCancellableCoroutine<MaterialTabListBeanWrapper?> { continuation ->
//            var pn = pages[materialType]?.num ?: 0
//
//            if (!hasMore(materialType)) {
//                continuation.resume(MaterialTabListBeanWrapper(EmptyMaterialTabListBean, materialType, false), null)
//                return@suspendCancellableCoroutine
//            }
//
//            ServiceGenerator.createService(MaterialApiService::class.java).searchMaterial(
//                    BiliAccounts.get(BiliContext.application()).accessKey ?: "",
//                materialType,
//                    keyword,
//                    ++pn,
//                    ONE_PAGE_CAPACITY)
//                .enqueue(object : BiliApiCallback<GeneralResponse<MaterialSearchResultBean>>() {
//                    override fun onSuccess(result: GeneralResponse<MaterialSearchResultBean>?) {
//
//                        when (materialType) {
//                            MATERIAL_TYPE_ALL -> {
//                                result?.data?.materials?.let { materials ->
//                                    continuation.resume(MaterialTabListBeanWrapper(result?.data, materialType, true), null)
//                                    this@MaterialSearchRepository.pages[MATERIAL_TYPE_BGM] = materials.bgm?.page?:Page(0,0,0)
//                                    this@MaterialSearchRepository.pages[MATERIAL_TYPE_PICTURE] = materials.picture?.page?:Page(0,0,0)
//                                    this@MaterialSearchRepository.pages[MATERIAL_TYPE_TAG] = materials.tag?.page?:Page(0,0,0)
//                                    this@MaterialSearchRepository.pages[MATERIAL_TYPE_VIDEO] = materials.video?.page?:Page(0,0,0)
//                                } ?: continuation.resume(MaterialTabListBeanWrapper(EmptyMaterialTabListBean, materialType, false), null)
//                            }
//                            MATERIAL_TYPE_BGM -> {
//                                result?.data?.materials?.bgm?.let { bgm ->
//                                    continuation.resume(MaterialTabListBeanWrapper(result?.data, materialType, true), null)
//                                    this@MaterialSearchRepository.pages[materialType] = bgm.page
//                                } ?: continuation.resume(MaterialTabListBeanWrapper(EmptyMaterialTabListBean, materialType, false), null)
//                            }
//
//                            MATERIAL_TYPE_PICTURE -> {
//                                result?.data?.materials?.picture?.let { pic ->
//                                    continuation.resume(MaterialTabListBeanWrapper(result?.data, materialType, true), null)
//                                    this@MaterialSearchRepository.pages[materialType] = pic.page
//                                } ?: continuation.resume(MaterialTabListBeanWrapper(EmptyMaterialTabListBean, materialType, false), null)
//                            }
//
//                            MATERIAL_TYPE_VIDEO -> {
//                                result?.data?.materials?.video?.let { video ->
//                                    continuation.resume(MaterialTabListBeanWrapper(result?.data, materialType, true), null)
//                                    this@MaterialSearchRepository.pages[materialType] = video.page
//                                } ?: continuation.resume(MaterialTabListBeanWrapper(EmptyMaterialTabListBean, materialType, false), null)
//                            }
//
//                            MATERIAL_TYPE_TAG -> {
//                                result?.data?.materials?.tag?.let { tag ->
//                                    continuation.resume(MaterialTabListBeanWrapper(result?.data, materialType, true), null)
//                                    this@MaterialSearchRepository.pages[materialType] = tag.page
//                                } ?: continuation.resume(MaterialTabListBeanWrapper(EmptyMaterialTabListBean, materialType, false), null)
//                            }
//                        }
//
//                    }
//
//                    override fun onError(t: Throwable?) {
//                        continuation.resume(null, null)
//                    }
//                })
//        }
//
//}
//
//val EmptyMaterialTabListBean = MaterialSearchResultBean(null)
