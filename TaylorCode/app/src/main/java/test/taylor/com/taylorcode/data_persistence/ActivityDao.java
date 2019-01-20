package test.taylor.com.taylorcode.data_persistence;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;

import java.util.List;

@Dao
@TypeConverters(DataConverter.class)
public interface ActivityDao {

    @Insert
    void insert(Activity activityBean);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    @TypeConverters(DataConverter.class)
    void insertAll(List<Activity> products);

    @Query("select * from taylor order by time limit :start, :count")
    LiveData<List<Activity>> queryActivities(int start, int count);

    @Query("select * from taylor where time < :now  order by time ")
    LiveData<List<Activity>> queryActivitiesEarlier(long now);

    @Query("delete from taylor where id like :id")
    void delete(String id);
}
