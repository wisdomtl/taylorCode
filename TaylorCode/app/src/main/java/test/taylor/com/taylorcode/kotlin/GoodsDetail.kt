package test.taylor.com.taylorcode.kotlin

import android.util.Log
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class GoodsDetail(@SerializedName("commodityId") var commodityId: Int = 0,
                       @SerializedName("materialId") var materialId: String? = "",
                       @SerializedName("buyStatus") var buyStatus: Int = 1,
                       @SerializedName("unlockLevel") var unlockLevel: Int = 0,
                       @SerializedName("description") var description: String? = "",
                       @SerializedName("name") var name: String? = "",
                       @SerializedName("material") var material: Material? = null,
                       @SerializedName("imageUrl") var imageUrl: String? = "",
                       @SerializedName("ucoinPrice") var ucoinPrice: Int = 0,
                       @SerializedName("diamondPrice") var diamondPrice: Int = 0,
                       @SerializedName("sex") var sex: Int = 0,
                       @SerializedName("status") var status: Int = 0,
                       @SerializedName("buyChannel") var buyChannel: Int = 0,
                       var selected: Boolean = false//it is an additional field for showing selected goods frame
) : Comparable<GoodsDetail> {

    //sort by sex
    override fun compareTo(other: GoodsDetail): Int {
        //asexuality is always at tail
        Log.v("ttaylor","tag=sorted, GoodsDetail.compareTo()  ")
        if (this.sex == 2) {
            return 1
        }
        //male is in the front of female
        //sort wont work if a method is invoked
        if (UserManager.getSex() == 1) {
            return other.sex - this.sex
        }
        //female is in the front of male
        else {
            return this.sex - other.sex
        }
    }

    @Keep
    data class Material(@SerializedName("tintList") var list: List<Student>?)

    fun isValid(): Boolean = !materialId.isNullOrEmpty()

    fun firstTint() = material?.list?.get(0)
}
