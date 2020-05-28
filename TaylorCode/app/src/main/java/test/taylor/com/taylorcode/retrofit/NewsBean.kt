package test.taylor.com.taylorcode.retrofit

import com.google.gson.annotations.SerializedName

data class NewsBean(
    @SerializedName("code") var code: Int,
    @SerializedName("message") var message: String,
    @SerializedName("result") var result: List<News>?
)

data class News(
    @SerializedName("path") var path: String?,
    @SerializedName("image") var image: String?,
    @SerializedName("title") var title: String?,
    @SerializedName("passtime") var passtime: String?
)