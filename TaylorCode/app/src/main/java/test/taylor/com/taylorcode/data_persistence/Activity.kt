package test.taylor.com.taylorcode.data_persistence

import android.provider.Telephony
import androidx.room.TypeConverters
import test.taylor.com.taylorcode.data_persistence.DataConverter
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "taylor")
@TypeConverters(DataConverter::class)
class Activity {
    @ColumnInfo(name = "user_name")
    var userName: String? = null

    @ColumnInfo(name = "user_avatar_url")
    var userAvatarUrl: String? = null
    var title: String? = null

    @TypeConverters(DataConverter::class)
    var time: String? = null
    var type: String? = null

    @PrimaryKey
    var id: String = ""

    @TypeConverters(TestBeanConverter::class)
    var textBean: TestBean? = null

//    @TypeConverters(TestBean2Converter::class)
    @TypeConverters(TestBean3Converter::class)
    var testBean2: List<TestBean2>? = null
}