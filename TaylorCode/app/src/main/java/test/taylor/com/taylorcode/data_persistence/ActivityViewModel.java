package test.taylor.com.taylorcode.data_persistence;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

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

}
