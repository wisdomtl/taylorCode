package test.taylor.com.taylorcode.retrofit.repository_livedata.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(news: List<News>)

    @Query("select * from news")
    fun queryNews(): LiveData<List<News>?>

    @Query("select * from news")
    fun queryNewsFlow(): Flow<List<News>?>

    @Query("select * from news")
    suspend fun queryNewsSuspend(): List<News>

    @Query("delete from news")
    fun deleteAllNews(): Int
}