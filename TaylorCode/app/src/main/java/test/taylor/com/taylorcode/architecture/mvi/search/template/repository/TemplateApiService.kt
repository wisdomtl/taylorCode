//package com.bilibili.studio.search.template.repository
//
//import com.bilibili.api.BiliApiSites
//import com.bilibili.okretro.GeneralResponse
//import com.bilibili.okretro.call.BiliCall
//import com.bilibili.studio.search.template.data.SearchHintsBean
//import com.tab.bean.TemplateTabListBean
//import retrofit2.http.BaseUrl
//import retrofit2.http.GET
//import retrofit2.http.Header
//import retrofit2.http.Query
//
//@BaseUrl(BiliApiSites.HTTPS_MEMBER_BILIBILI_COM)
//interface TemplateApiService {
//
//    @GET("x/bcut/app/template/reclist")
//    fun fetchRecommendTemplate(
//        @Header("buvid") buvid: String,
//        @Query("access_key") accessKey: String,
//        @Query("vertical") vertical: Int = 0,
//        @Query("apply_for") apply_for: Int,
//        @Query("material_type") material_type: Int,
//        @Query("ps") ps: Int,
//        @Query("max_rank") max_rank: Int = 0,
//        @Query("version") version: Int = 0,
//        @Query("trace_info") trace_info: String,
//        @Query("filter_material_ids") filter_material_ids: String,
//        @Query("cat_id") cat_id: Int,
//    ): BiliCall<GeneralResponse<TemplateTabListBean>>
//
//    @GET("/x/material/bcut/template/search")
//    fun searchTemplate(
//        @Query("access_key") accessKey: String,
//        @Query("kw") keyword: String,
//        @Query("pn") pn: Int,
//        @Query("ps") ps: Int,
//        @Query("supported_ability") ability: Int,
//    ): BiliCall<GeneralResponse<TemplateTabListBean>>
//
//
//    @GET("/x/material/bcut/template/prefix/search")
//    fun fetchHints(
//        @Query("access_key") accessKey: String,
//        @Query("kw") keyword: String,
//    ): BiliCall<GeneralResponse<SearchHintsBean>>
//}