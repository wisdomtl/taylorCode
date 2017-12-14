package test.taylor.com.taylorcode.system;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.app_monitor.AppMonitor;

/**
 * Created on 2017/12/5.
 */

public class AppStatusActivity extends Activity {

    public final String[] PACKAGES = new String[]{"com.android.settings", "com.android.messaging", "com.android.browser"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        new AppStatusService(this).setActivityController();
//        new AppStatusService(this).registerUidObserver();

        //case1:monitor the other apps running status
        AppMonitor appMonitor = new AppMonitor(this.getApplicationContext());
        appMonitor.startMonitor(PACKAGES, new AppMonitor.AppStateChangeListener() {
            @Override
            public void onActivityStateChange(String s, boolean b) {
                Log.v("ttaylor", "AppStatusActivity.onActivityStateChange(): activity=" + s + " ,foreground=" + b);
            }

            @Override
            public void onUninstall(String s) {
                Log.v("ttaylor", "AppStatusActivity.onUninstall(): activity=" + s);
            }
        });
    }
}
