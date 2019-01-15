package test.taylor.com.taylorcode.data_persistence;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.util.Log;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ActivityRepository {

    private ExecutorService executorService;
    private AppDataBase appDataBase;
    private ActivityDao activityDao;

    public ActivityRepository(Context context) {
        appDataBase = AppDataBase.getInstance(context);
        activityDao = appDataBase.activityDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(Activity activity) {
        if (executorService == null) {
            return;
        }
        executorService.submit(() -> activityDao.insert(activity));
    }

    public void insertAll(List<Activity> activities) {
        if (executorService == null) {
            Log.v("ttaylor", "ActivityRepository.insertAll()" + "  ExecutorService is null");
            return;
        }
        if (appDataBase != null) {
            Log.v("ttaylor", "ActivityRepository.insertAll()" + "  AppDatabase is null");
            return;
        }
        executorService.submit(() -> {
            appDataBase.runInTransaction(() -> activityDao.insertAll(activities));
        });
    }

    public LiveData<List<Activity>> queryActivities(int start, int count) {
//        if (executorService == null) {
//            Log.v("ttaylor", "ActivityRepository.queryActivities()" + "  ExecutorService is null");
//            return;
//        }
//        if (appDataBase != null) {
//            Log.v("ttaylor", "ActivityRepository.queryActivities()" + "  AppDatabase is null");
//            return;
        return activityDao.queryActivities(start,count) ;
    }

    public void stop() {
        if (executorService != null) {
            executorService.shutdownNow();
        }
    }

}
