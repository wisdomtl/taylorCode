package test.taylor.com.taylorcode.retrofit

import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface NewsApi {
    @FormUrlEncoded
    @POST("/getWangYiNews")
    fun fetchNews(@FieldMap map:Map<String,String>):Call<NewsBean>

    @FormUrlEncoded
    @POST("/getWangYiNews")
    fun fetchNewsSingle(@FieldMap map:Map<String,String>):Single<NewsBean>
}