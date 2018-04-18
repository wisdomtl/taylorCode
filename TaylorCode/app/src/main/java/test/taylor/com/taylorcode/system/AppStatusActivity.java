package test.taylor.com.taylorcode.system;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;

import com.example.app_monitor.AppMonitor;

import java.util.Arrays;

import test.taylor.com.taylorcode.R;
import test.taylor.com.taylorcode.broadcast.BroadcastActivity;

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
        setContentView(R.layout.app_status_activity);
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

        //broadcast case2:register BroadcastReceiver in an anonymous inner class,it will last even if onClick() finished
        findViewById(R.id.tv_within).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppStatusActivity.this, BroadcastActivity.class);
                startActivity(intent);
                LocalBroadcastManager.getInstance(AppStatusActivity.this).registerReceiver(broadcastReceiver, new IntentFilter("action_interactive_ad_show"));
            }
        });

        //broadcast case2:control group,the receiver defined above will also receive broadcast
        findViewById(R.id.tv_without).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppStatusActivity.this, BroadcastActivity.class);
                startActivity(intent);
            }
        });
    }

    //broadcast case3:unregister receiver the time one broadcast arrive
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            LocalBroadcastManager.getInstance(AppStatusActivity.this).unregisterReceiver(broadcastReceiver);
        }
    };

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.v("taylor ", "AppStatusActivity.onReceive() " + " ");
            //we have to post the unregister action,or it cant be done
            findViewById(R.id.tv_without).postDelayed(runnable, 100);
        }
    };
}
