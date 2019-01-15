package test.taylor.com.taylorcode.data_persistence;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ActivityDao {

    @Insert
    void insert(Activity activityBean);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<Activity> products);

    @Query("select * from activity order by time limit :start, :count")
    LiveData<List<Activity>> queryActivities(int start, int count);
}
