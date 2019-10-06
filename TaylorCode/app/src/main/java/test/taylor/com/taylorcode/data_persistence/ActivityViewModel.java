package test.taylor.com.taylorcode.data_persistence;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;
import android.util.Log;

import java.util.List;

public class ActivityViewModel extends AndroidViewModel {
    private ActivityRepository activityRepository;

    public ActivityViewModel(@NonNull Application application) {
        super(application);
        activityRepository = new ActivityRepository(application);

    }

    public void insert(Activity activity) {
        if (activityRepository != null) {
            activityRepository.insert(activity);
        }
    }

    public void insertAll(List<Activity> activities) {
        if (activityRepository != null) {
            activityRepository.insertAll(activities);
        }
    }

    public LiveData<List<Activity>> queryActivityEarlier(long now){
        if (activityRepository != null) {
            return activityRepository.queryActivityEarlier(now);
        }
        return null ;
    }

    public LiveData<List<Activity>> queryActivities(int start, int count) {
        if (activityRepository != null) {
            return activityRepository.queryActivities(start, count);
        }
        Log.v("ttaylor", "ActivityViewModel.queryActivities()" + "  activityRepository is null");
        return null ;
    }

    public void deleteActivity(String id){
        if(activityRepository!=null){
            activityRepository.deleteActivity(id);
        }
    }

}
