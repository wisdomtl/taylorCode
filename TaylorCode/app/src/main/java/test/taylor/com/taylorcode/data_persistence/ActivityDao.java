package test.taylor.com.taylorcode.data_persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

@Dao
public interface ActivityDao {

    @Insert
    void insertActivity(Activity activityBean);
}
