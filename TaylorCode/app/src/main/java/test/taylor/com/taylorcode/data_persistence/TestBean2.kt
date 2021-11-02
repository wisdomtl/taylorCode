package test.taylor.com.taylorcode.data_persistence

import com.google.gson.annotations.SerializedName

data class TestBean2(
    @SerializedName("dd")val id: String? = null,
    @SerializedName("oio")val aaa: Aaa? = null,
    @SerializedName("iukkk")val stttt:List<String>)


data class Aaa(
    val id: String? = null,
    val strs: List<String>? = null
)

