package test.taylor.com.taylorcode.retrofit.repository_livedata.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class News(
    var path:String,
    var image:String,
    @PrimaryKey
    var title:String,
    var passtime:String
)