package test.taylor.com.taylorcode.data_persistence;

import android.content.Context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ActivityRepository {

    private ExecutorService executorService;

    private ActivityDao activityDao;

    public ActivityRepository(Context context) {
        AppDataBase appDataBase = AppDataBase.getInstance(context);
        activityDao = appDataBase.activityDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(Activity activity) {
        if (executorService == null) {
            return;
        }
        executorService.submit(() -> activityDao.insertActivity(activity));
    }

    public void stop() {
        if (executorService != null) {
            executorService.shutdownNow();
        }
    }

}
