//package com.bilibili.studio.search.template.repository
//
//import android.util.Log
//import com.bilibili.base.BiliContext
//import com.bilibili.lib.accounts.BiliAccounts
//import com.bilibili.lib.biliid.api.BuvidHelper
//import com.bilibili.okretro.BiliApiCallback
//import com.bilibili.okretro.GeneralResponse
//import com.bilibili.okretro.ServiceGenerator
//import com.bilibili.studio.module.ugc.producer.internal.ability.TemplateAbilityImpl
//import com.bilibili.studio.search.base.BaseSearchRepository
//import com.bilibili.studio.search.template.data.MatchType
//import com.bilibili.studio.search.template.data.SearchHintsBean
//import com.bilibili.studio.search.template.data.TemplateTabListBeanWrapper
//import com.tab.bean.TemplateTabListBean
//import com.tab.bean.TemplateTabManager
//import kotlinx.coroutines.suspendCancellableCoroutine
//
//class SearchRepository : BaseSearchRepository<TemplateTabListBeanWrapper?> {
//
//    companion object {
//        const val RECOMMEND_CAT_ID = 690116
//        const val ONE_PAGE_CAPACITY = 20
//        const val CATEGORY_ID_RECOMMEND = 1987 //magic number, no actual meaning
//        const val CATEGORY_ID_SEARCH = 1736 //magic number, no actual meaning
//    }
//
//    private var maxRank = 0
//    private var version = 0
//    private var traceInfo = ""
//
//    private var pn: Int = 1
//
//    override suspend fun search(keyword: String, extraParam: Map<String, Any>?): TemplateTabListBeanWrapper? {
//        val isFirstTime = extraParam?.getOrElse("isFirstTime") { null } as? Boolean ?: false
//        val wrapper = searchTemplate(keyword, isFirstTime)
//        return if (wrapper == null) { // search failed
//            null
//        } else if (!wrapper.isSuccess && pn == 1) { // search empty
//            fetchRecommendTemplates(true)
//        } else { // search success
//            wrapper
//        }
//    }
//
//    override suspend fun searchMore(keyword: String, extraParam: Map<String, Any>?): TemplateTabListBeanWrapper? =
//        if (pn > 1) { // search success, continue with search
//            search(keyword, mapOf("isFirstTime" to false))
//        } else { // recommend success, continue with recommend
//            fetchRecommendTemplates(false)
//        }
//
//    override suspend fun fetchSearchHint(keyword: String): List<String> = suspendCancellableCoroutine { continuation ->
//        ServiceGenerator.createService(TemplateApiService::class.java)
//            .fetchHints(BiliAccounts.get(BiliContext.application()).accessKey ?: "", keyword)
//            .enqueue(object : BiliApiCallback<GeneralResponse<SearchHintsBean>>() {
//                override fun onSuccess(result: GeneralResponse<SearchHintsBean>?) {
//                    if (result?.data?.hints?.isNotEmpty() == true) {
//                        val hints = if (result.data.hints.contains(keyword)) result.data.hints else listOf(keyword, *result.data.hints.toTypedArray())
//                        continuation.resume(hints, null)
//                    } else {
//                        continuation.resume(listOf(keyword), null)
//                    }
//                }
//
//                override fun onError(t: Throwable?) {
//                    continuation.resume(listOf(keyword), null)
//                }
//            })
//    }
//
//    override fun clear() {
//        pn = 1
//        version = 0
//        maxRank = 0
//        traceInfo = ""
//    }
//
//    private suspend fun fetchRecommendTemplates(isFirstTime: Boolean) = suspendCancellableCoroutine<TemplateTabListBeanWrapper?> { continuation ->
//        val maxRank = if (isFirstTime) 0 else this.maxRank
//        val version = if (isFirstTime) 0 else this.version
//        val traceInfo = if (isFirstTime) "" else this.traceInfo
//        if (isFirstTime) TemplateTabManager.getInstance().clearTabData(CATEGORY_ID_RECOMMEND)
//        ServiceGenerator.createService(TemplateApiService::class.java).fetchRecommendTemplate(
//            BuvidHelper.getBuvid(),
//            BiliAccounts.get(BiliContext.application()).accessKey ?: "",
//            0,
//            0,
//            49,
//            20, maxRank, version, traceInfo, "", RECOMMEND_CAT_ID
//        ).enqueue(object : BiliApiCallback<GeneralResponse<TemplateTabListBean>>() {
//            override fun onSuccess(result: GeneralResponse<TemplateTabListBean>?) {
//                if (!result?.data?.materials.isNullOrEmpty() && result?.data?.cursor != null) {
//                    TemplateTabManager.getInstance().addTabDataList(CATEGORY_ID_RECOMMEND, result.data.materials)
//                    continuation.resume(TemplateTabListBeanWrapper(result.data, MatchType.RECOMMEND, true), null)
//                    this@SearchRepository.version = result.data.cursor.version
//                    this@SearchRepository.traceInfo = result.data.cursor.trace_info
//                    this@SearchRepository.maxRank = result.data.cursor.maxRank
//                } else {
//                    continuation.resume(TemplateTabListBeanWrapper(EmptyTemplateTabListBean, MatchType.RECOMMEND, false), null)
//                }
//            }
//
//            override fun onError(t: Throwable?) {
//                continuation.resume(null, null)
//            }
//        })
//    }
//
//    private suspend fun searchTemplate(keyword: String, isFirstTime: Boolean) =
//        suspendCancellableCoroutine<TemplateTabListBeanWrapper?> { continuation ->
//            val pn = if (isFirstTime) 1 else this.pn
//            if (isFirstTime) TemplateTabManager.getInstance().clearTabData(CATEGORY_ID_SEARCH)
//            ServiceGenerator.createService(TemplateApiService::class.java)
//                .searchTemplate(
//                    BiliAccounts.get(BiliContext.application()).accessKey ?: "",
//                    keyword,
//                    pn,
//                    ONE_PAGE_CAPACITY,
//                    TemplateAbilityImpl.newInstance().getUGCTemplateAbility()
//                )
//                .enqueue(object : BiliApiCallback<GeneralResponse<TemplateTabListBean>>() {
//                    override fun onSuccess(result: GeneralResponse<TemplateTabListBean>?) {
//                        if (result?.data?.materials?.isNullOrEmpty() == false) {
//                            val ret = TemplateTabListBean().apply {
//                                cursor = null
//                                materials = result.data.materials
//                            }
//                            TemplateTabManager.getInstance().addTabDataList(CATEGORY_ID_SEARCH, result.data.materials)
//                            continuation.resume(TemplateTabListBeanWrapper(ret, MatchType.SEARCH, true), null)
//                            this@SearchRepository.pn++
//                        } else {
//                            continuation.resume(TemplateTabListBeanWrapper(EmptyTemplateTabListBean, MatchType.SEARCH, false), null)
//                        }
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
//val EmptyTemplateTabListBean = TemplateTabListBean().apply {
//    materials = arrayListOf()
//    cursor = null
//}
