package test.taylor.com.taylorcode.retrofit.repository_livedata.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [News::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao

    companion object {
        @Volatile
        private var INSTANCE: NewsDatabase? = null

        fun getInstance(context: Context): NewsDatabase {
            if (INSTANCE == null) {
                synchronized(NewsDatabase::class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            NewsDatabase::class.java,
                            "news.db"
                        ).build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}