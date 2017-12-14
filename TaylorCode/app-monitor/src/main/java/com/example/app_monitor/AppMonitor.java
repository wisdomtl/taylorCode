package com.example.app_monitor;

import android.app.ActivityManager;
import android.app.IProcessObserver;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
    private String[] packages;
    private HashMap<Integer, String> pkgMap;

    /**
     * @param context should be ApplicationContext
     */
    public AppMonitor(Context context) {
        this.context = context;
        IntentFilter filter = new IntentFilter(Intent.ACTION_PACKAGE_REMOVED);
        filter.addDataScheme("package");
        context.registerReceiver(new AppStatusReceiver(), filter);
    }

    //case1:reflect IActivityManager.registerProcessObserver() for using our own monitor ,thus we could know the other apps' status
    public void startMonitor(String[] packages, AppStateChangeListener stateChangeListener) {
        Log.v(TAG, "AppMonitor.startMonitor(): packages=" + packages + " ,listener=" + stateChangeListener);
        this.packages = packages;
        this.stateChangeListener = stateChangeListener;
        pkgMap = new HashMap<>();
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
            Log.v(TAG, "AppStatusService.onForegroundActivitiesChanged(): pid=" + pid + ",uid=" + uid + ",foregroundActivities=" + foregroundActivities);
            if (foregroundActivities) {
                String pkg = getForegroundPakage();
                if (isMonitee(packages, pkg)) {
                    Log.v(TAG, "AppMonitor.onForegroundActivitiesChanged(): key=" + uid + " ,value=" + pkg);
                    int id = pid + uid;
                    pkgMap.put(id, pkg);
                    if (stateChangeListener != null) {
                        stateChangeListener.onActivityStateChange(pkg, true);
                    }
                }
            } else {
                int id = pid + uid;
                String activity = pkgMap.get(id);
                Log.v(TAG, "AppMonitor.onForegroundActivitiesChanged(): key=" + uid + " ,value=" + activity);
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
     * whether the input activity is in the packages list which is monitored
     *
     * @param activities
     * @param activity
     * @return
     */
    private boolean isMonitee(String[] activities, String activity) {
        for (String ac : activities) {
            if (!TextUtils.isEmpty(ac) && ac.equals(activity)) {
                return true;
            }
        }
        return false;
    }

    private String getForegroundPakage() {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RecentTaskInfo> taskInfo = am.getRecentTasks(1, ActivityManager.RECENT_IGNORE_UNAVAILABLE);
        return taskInfo.isEmpty() ? null : taskInfo.get(0).baseIntent.getComponent().getPackageName();
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

    public class AppStatusReceiver extends BroadcastReceiver {

        public AppStatusReceiver() {
            super();
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {
                String pkgName = intent.getDataString();
                Log.v(TAG, "AppStatusReceiver.onReceive(): PACKAGE_REMOVED , pkgName=" + pkgName);
                if (stateChangeListener != null) {
                    stateChangeListener.onUninstall(pkgName);
                }
            }
        }
    }
}
