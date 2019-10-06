package test.taylor.com.taylorcode.data_persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.TypeConverters;

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
