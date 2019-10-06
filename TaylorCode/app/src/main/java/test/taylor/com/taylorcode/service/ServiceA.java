package test.taylor.com.taylorcode.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;
import android.util.Log;

/**
 * Created on 2018/3/23.
 */

public class ServiceA extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("ttaylor", "ServiceA.onCreate(): ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v("ttaylor", "ServiceA.onDestroy(): ");
    }


}
