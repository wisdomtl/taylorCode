package com.example.app_monitor;

import android.app.ActivityManager;
import android.app.IProcessObserver;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ComponentInfo;
import android.os.Build;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

/**
 * Created on 2017/12/12.
 */

public class AppMonitor {
    public static final String TAG = AppMonitor.class.getSimpleName();

    private Context context;
    private AppStateChangeListener stateChangeListener;
    private List<ComponentName> componentNames;
    private HashMap<Integer, String> activityMap;

    /**
     * @param context should be ApplicationContext
     */
    public AppMonitor(Context context) {
        this.context = context;
        //case2:monitor app uninstall
        IntentFilter filter = new IntentFilter(Intent.ACTION_PACKAGE_REMOVED);
        filter.addDataScheme("package");
        context.registerReceiver(new AppStatusReceiver(), filter);
    }

    //case1:reflect IActivityManager.registerProcessObserver() for using our own monitor ,thus we could know the other apps' status
    public void startMonitor(List<ComponentName> componentNames, AppStateChangeListener stateChangeListener) {
        Log.v(TAG, "AppMonitor.startMonitor(): componentNames=" + componentNames + " ,listener=" + stateChangeListener);
        this.componentNames = componentNames;
        this.stateChangeListener = stateChangeListener;
        activityMap = new HashMap<>();
        try {
            //get instance of ActivityManager
            Class<?> activityManagerNative = Class.forName("android.app.ActivityManagerNative");
            Field gDefaultField = activityManagerNative.getDeclaredField("gDefault");
            gDefaultField.setAccessible(true);
            Object gDefault = gDefaultField.get(null);
            Class<?> singleton = Class.forName("android.util.Singleton");
            Field mInstanceField = singleton.getDeclaredField("mInstance");
            mInstanceField.setAccessible(true);
            Object ams = mInstanceField.get(gDefault);

            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP ||
                    Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP_MR1 ||
                    Build.VERSION.SDK_INT == Build.VERSION_CODES.M ||
                    Build.VERSION.SDK_INT == Build.VERSION_CODES.N ||
                    Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1) {
                Method registerProcessObserver = activityManagerNative.getMethod("registerProcessObserver", Class.forName("android.app.IProcessObserver"));
                registerProcessObserver.invoke(ams, monitor);//using our own monitor
            }
        } catch (ClassNotFoundException e) {
            Log.v(TAG, "AppStatusService.setActivityController(): e=" + e);
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            Log.v(TAG, "AppStatusService.setActivityController(): e=" + e);
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            Log.v(TAG, "AppStatusService.setActivityController(): e=" + e);
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            Log.v(TAG, "AppStatusService.setActivityController(): e=" + e);
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            Log.v(TAG, "AppStatusService.setActivityController(): e=" + e);
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            Log.v(TAG, "AppStatusService.registerProcessObserver(): e=" + e);
            e.printStackTrace();
        }
    }

    //case1:monitor other app's status
    private IProcessObserver.Stub monitor = new IProcessObserver.Stub() {
        @Override
        public void onForegroundActivitiesChanged(int pid, int uid, boolean foregroundActivities) throws RemoteException {
            String activity1 = getForegroundActivity();
            Log.e(TAG, "AppStatusService.onForegroundActivitiesChanged(): pid=" + pid + ",uid=" + uid + ",foregroundActivities=" + foregroundActivities+" ,activity="+activity1);
            if (foregroundActivities) {
                String activity = getForegroundActivity();
                if (isMoniteeActivity(componentNames, activity)) {
                    int id = pid + uid;
                    Log.v(TAG, "AppMonitor.onForegroundActivitiesChanged(): key=" + id + " ,value=" + activity);
                    activityMap.put(id, activity);
                    if (stateChangeListener != null) {
                        stateChangeListener.onActivityStateChange(activity, true);
                    }
                }
            } else {
                int id = pid + uid;
                String activity = activityMap.get(id);
                Log.v(TAG, "AppMonitor.onForegroundActivitiesChanged(): key=" + id + " ,value=" + activity);
                if (!TextUtils.isEmpty(activity) && stateChangeListener != null) {
                    stateChangeListener.onActivityStateChange(activity, false);
                }
            }
        }

        @Override
        public void onProcessStateChanged(int pid, int uid, int procState) throws RemoteException {
            Log.v(TAG, "AppStatusService.onProcessStateChanged(): pid=" + pid + " ,uid=" + uid + " ,procState=" + procState);
        }

        @Override
        public void onProcessDied(int pid, int uid) throws RemoteException {
            Log.e(TAG, "AppStatusService.onProcessDied(): pid=" + pid + ",uid=" + uid);
        }
    };

    /**
     * whether the input activity is in the componentInfos list which is monitored
     *
     * @param activities
     * @param activity
     * @return
     */
    private boolean isMoniteeActivity(List<ComponentName> activities, String activity) {
        for (ComponentName ac : activities) {
            String activityClass = ac.getClassName();
            if (!TextUtils.isEmpty(activityClass) && activityClass.equals(activity)) {
                return true;
            }
        }
        return false;
    }

    private String getForegroundActivity() {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RecentTaskInfo> taskInfo = am.getRecentTasks(1, ActivityManager.RECENT_IGNORE_UNAVAILABLE);
        return taskInfo.isEmpty() ? null : taskInfo.get(0).baseIntent.getComponent().getClassName();
    }

    public interface AppStateChangeListener {

        /**
         * invoked when the foreground activity change for the whole android system
         *
         * @param pkgName    the package name of an app
         * @param foreground true: the Activity named activityName is in the foreground ,otherwise false
         */
        void onActivityStateChange(String pkgName, boolean foreground);

        /**
         * invoked when app uninstall
         *
         * @param pkgName package name of main Activity
         */
        void onUninstall(String pkgName);
    }

    //case2:monitor app uninstall
    public class AppStatusReceiver extends BroadcastReceiver {

        public AppStatusReceiver() {
            super();
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {
                String pkgName = intent.getDataString();
                int index = pkgName.indexOf(":") ;
                if (index != -1){
                    pkgName = pkgName.substring(index+1) ;
                }
                Log.v(TAG, "AppStatusReceiver.onReceive(): PACKAGE_REMOVED , pkgName=" + pkgName);
                if (stateChangeListener != null && isMoniteePackage(componentNames, pkgName)) {
                    stateChangeListener.onUninstall(pkgName);
                }
            }
        }
    }

    private boolean isMoniteePackage(List<ComponentName> componentNames, String pkgName) {
        for (ComponentName ac : componentNames) {
            String pkg = ac.getPackageName();
            if (!TextUtils.isEmpty(pkg) && pkg.equals(pkgName)) {
                return true;
            }
        }
        return false;
    }
}
