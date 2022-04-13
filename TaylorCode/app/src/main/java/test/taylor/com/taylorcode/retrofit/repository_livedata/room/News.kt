package test.taylor.com.taylorcode.retrofit.repository_livedata.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class News(
    var path:String?,
    var image:String?,
    var title:String,
    var passtime:String?,
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0
)