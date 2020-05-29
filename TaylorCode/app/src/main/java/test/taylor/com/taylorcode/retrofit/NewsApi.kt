package test.taylor.com.taylorcode.retrofit

import androidx.lifecycle.LiveData
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import test.taylor.com.taylorcode.retrofit.repository_livedata.ApiResponse

interface NewsApi {
    @FormUrlEncoded
    @POST("/getWangYiNews")
    fun fetchNews(@FieldMap map:Map<String,String>):Call<NewsBean>

    @FormUrlEncoded
    @POST("/getWangYiNews")
    fun fetchNewsSingle(@FieldMap map:Map<String,String>):Single<NewsBean>

    @FormUrlEncoded
    @POST("/getWangYiNews")
    fun fetchNewsLiveData(@FieldMap map:Map<String,String>):LiveData<ApiResponse<NewsBean>>
}