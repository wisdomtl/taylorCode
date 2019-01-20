package test.taylor.com.taylorcode.data_persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

@Database(entities = {Activity.class}, version = 1,exportSchema = false)
@TypeConverters(DataConverter.class)
public abstract class AppDataBase extends RoomDatabase {
    public static final String DATABASE_NAME = "veeu_activities";

    private static volatile AppDataBase INSTANCE;

    public abstract ActivityDao activityDao();

    public static AppDataBase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, DATABASE_NAME).build();
                }
            }
        }
        return INSTANCE;
    }

    public void onDestroy() {
        INSTANCE = null;
    }
}
