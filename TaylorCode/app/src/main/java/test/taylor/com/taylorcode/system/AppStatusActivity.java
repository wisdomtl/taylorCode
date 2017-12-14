package test.taylor.com.taylorcode.system;

import android.app.Activity;
import android.content.ComponentName;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.app_monitor.AppMonitor;

import java.util.Arrays;

/**
 * Created on 2017/12/5.
 */

public class AppStatusActivity extends Activity {

    public static final String TOU_TIAO_PKG_NAME = "com.ss.android.article.news";
    public static final String TOU_TIAO_CLASS_NAME = "com.ss.android.article.news.activity.MainActivity";
    public static final String TOU_TIAO_SPLASH_CLASS_NAME = "com.ss.android.article.news.activity.SplashBadgeActivity";

    public static final String BAIDU_PKG_NAME = "com.baidu.news";
    public static final String BAIDU_CLASS_NAME = "com.baidu.news.ui.IndexActivity";

    public static final String TECENT_PKG_NAME = "com.tencent.news";
    public static final String TECENT_CLASS_NAME = "com.tencent.news.activity.SplashActivity";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        new AppStatusService(this).setActivityController();
//        new AppStatusService(this).registerUidObserver();

        //case1:monitor the other apps running status
        ComponentName touTiao = new ComponentName(TOU_TIAO_PKG_NAME, TOU_TIAO_SPLASH_CLASS_NAME);
        ComponentName baidu = new ComponentName(BAIDU_PKG_NAME, BAIDU_CLASS_NAME);
        ComponentName tecent = new ComponentName(TECENT_PKG_NAME, TECENT_CLASS_NAME);
        AppMonitor appMonitor = new AppMonitor(this.getApplicationContext());
        appMonitor.startMonitor(Arrays.asList(touTiao, baidu, tecent), new AppMonitor.AppStateChangeListener() {
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
