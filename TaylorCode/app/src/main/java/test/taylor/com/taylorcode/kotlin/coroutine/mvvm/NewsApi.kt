package test.taylor.com.taylorcode.kotlin.coroutine.mvvm

import kotlinx.coroutines.flow.Flow
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import test.taylor.com.taylorcode.retrofit.NewsBean

interface NewsApi {
    @FormUrlEncoded
    @POST("/getWangYiNews")
    fun fetchNews(@FieldMap map:Map<String,String>): Flow<NewsBean>
}