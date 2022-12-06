//package com.bilibili.studio.search.material.repository
//
//import com.bilibili.api.BiliApiSites
//import com.bilibili.okretro.GeneralResponse
//import com.bilibili.okretro.call.BiliCall
//import com.bilibili.studio.search.material.data.MaterialSearchResultBean
//import com.bilibili.studio.search.template.data.SearchHintsBean
//import com.tab.bean.TemplateTabListBean
//import retrofit2.http.BaseUrl
//import retrofit2.http.GET
//import retrofit2.http.Header
//import retrofit2.http.Query
//
//@BaseUrl(BiliApiSites.HTTPS_MEMBER_BILIBILI_COM)
//interface MaterialApiService {
//
//    /**
//     * pn	true
//    int64
//    页数
//    ps	true
//    int64
//    页大小
//    kw	true	string 	用户输入的关键词
//    weshine	false
//    int32
//    闪萌是否还有结果,将上次返回值传回,  0：有，1：无
//    es	false
//    int32
//    es是否还有结果,将上次返回值传回，0：有，1：无
//    material_type
//    true	int	bgms音乐:1，pictures贴纸:2，videos视频:3，tags音效:4, 获取首页展示全部类型:5
//    type	false	int	素材类型：如video 为19 如不需要可不加
//    platform
//    false	string	app请求接口常规参数
//    build
//    false
//    int64
//    app请求接口常规参数
//    device
//    false	string	app请求接口常规参数
//    mobi_app
//    false	string	app请求接口常规参数
//     */
//    @GET("x/bcut/search/all")
//    fun searchMaterial(
//        @Query("access_key") accessKey: String,
//        @Query("material_type") materialType: Int,
//        @Query("kw") keyword: String,
//        @Query("pn") pn: Int,
//        @Query("ps") ps: Int,
//    ): BiliCall<GeneralResponse<MaterialSearchResultBean>>
//
//
//    @GET("/x/material/bcut/template/prefix/search")
//    fun fetchHints(
//        @Query("access_key") accessKey: String,
//        @Query("kw") keyword: String,
//    ): BiliCall<GeneralResponse<SearchHintsBean>>
//}