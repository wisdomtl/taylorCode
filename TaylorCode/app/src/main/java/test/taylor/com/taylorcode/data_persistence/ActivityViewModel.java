package test.taylor.com.taylorcode.data_persistence;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import java.util.List;

public class ActivityViewModel extends AndroidViewModel {
    private ActivityRepository activityRepository;

    public ActivityViewModel(@NonNull Application application) {
        super(application);
        activityRepository = new ActivityRepository(application) ;

    }

    public void insert(Activity activity){
        if(activityRepository!=null){
            activityRepository.insert(activity);
        }
    }

    public void insertAll(List<Activity> activities){
        if(activityRepository!=null){
            activityRepository.insertAll(activities);
        }
    }

}
