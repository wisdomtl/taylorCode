package test.taylor.com.taylorcode.system;

import android.app.IActivityController;
import android.app.IUidObserver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.RemoteException;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created on 2017/12/5.
 */

public class AppStatusService {

    private Context context;
    private int uid;

    public AppStatusService(Context context) {
        this.context = context;
    }

    private IActivityController.Stub binder = new IActivityController.Stub() {
        @Override
        public boolean activityStarting(Intent intent, String pkg) throws RemoteException {
            Log.v("ttaylor", "AppStatusService.activityStarting(): intent=" + intent + " ,pkg=" + pkg);
            return false;
        }

        @Override
        public boolean activityResuming(String pkg) throws RemoteException {
            Log.v("ttaylor", "AppStatusService.activityResuming(): pkg=" + pkg);
            return false;
        }

        @Override
        public boolean appCrashed(String processName, int pid, String shortMsg, String longMsg, long timeMillis, String stackTrace) throws RemoteException {
            Log.v("ttaylor", "AppStatusService.appCrashed(): ");
            return false;
        }

        @Override
        public int appEarlyNotResponding(String processName, int pid, String annotation) throws RemoteException {
            Log.v("ttaylor", "AppStatusService.appEarlyNotResponding(): ");
            return 0;
        }

        @Override
        public int appNotResponding(String processName, int pid, String processStats) throws RemoteException {
            Log.v("ttaylor", "AppStatusService.appNotResponding(): ");
            return 0;
        }
    };


    private IUidObserver.Stub uidObserver = new IUidObserver.Stub() {
        @Override
        public void onUidStateChanged(int uid, int procState) throws RemoteException {
            Log.v("ttaylor", "AppStatusService.onUidStateChanged(): uid=" + uid + ",procState=" + procState);
        }

        @Override
        public void onUidGone(int uid) throws RemoteException {
            Log.v("ttaylor", "AppStatusService.onUidGone(): uid=" + uid);
        }
    };


    /**
     * this wont destroy the whole system ,app wont not be able to run
     */
    public void setActivityController() {
        Log.v("ttaylor", "AppStatusService.setActivityController(): ");
        try {
            /**1.获得ActivityManagerService实例**/
            Class<?> activityManagerNative = Class.forName("android.app.ActivityManagerNative");
            Field gDefaultField = activityManagerNative.getDeclaredField("gDefault");
            gDefaultField.setAccessible(true);
            Object gDefault = gDefaultField.get(null);
            Class<?> singleton = Class.forName("android.util.Singleton");
            Field mInstanceField = singleton.getDeclaredField("mInstance");
            mInstanceField.setAccessible(true);
            Object ams = mInstanceField.get(gDefault);

            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP_MR1 || Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
                Log.v("ttaylor", "AppStatusService.setActivityController(): reflect setActivityController()");
                Method mSetActivityController = activityManagerNative.getMethod("setActivityController", Class.forName("android.app.IActivityController"));
                mSetActivityController.invoke(ams, binder);
            } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1) {
                Log.v("ttaylor", "AppStatusService.setActivityController(): reflect setActivityController()");
                Method mSetActivityController = activityManagerNative.getMethod("setActivityController", Class.forName("android.app.IActivityController"), boolean.class);
                mSetActivityController.invoke(ams, binder, false);
            }
        } catch (ClassNotFoundException e) {
            Log.v("ttaylor", "AppStatusService.setActivityController(): e=" + e);
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            Log.v("ttaylor", "AppStatusService.setActivityController(): e=" + e);
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            Log.v("ttaylor", "AppStatusService.setActivityController(): e=" + e);
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            Log.v("ttaylor", "AppStatusService.setActivityController(): e=" + e);
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            Log.v("ttaylor", "AppStatusService.setActivityController(): e=" + e);
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            Log.v("ttaylor", "AppStatusService.setActivityController(): e=" + e);
            e.printStackTrace();
        }
    }




    public void registerUidObserver() {
        try {
            /**1.获得ActivityManagerService实例**/
            Class<?> activityManagerNative = Class.forName("android.app.ActivityManagerNative");
            Field gDefaultField = activityManagerNative.getDeclaredField("gDefault");
            gDefaultField.setAccessible(true);
            Object gDefault = gDefaultField.get(null);
            Class<?> singleton = Class.forName("android.util.Singleton");
            Field mInstanceField = singleton.getDeclaredField("mInstance");
            mInstanceField.setAccessible(true);
            Object ams = mInstanceField.get(gDefault);

            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP_MR1 || Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
                Method registerUidObserver = activityManagerNative.getMethod("registerUidObserver", Class.forName("android.app.IUidObserver"));
                registerUidObserver.invoke(ams, uidObserver);
            } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1) {
            }
        } catch (ClassNotFoundException e) {
            Log.v("ttaylor", "AppStatusService.setActivityController(): e=" + e);
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            Log.v("ttaylor", "AppStatusService.setActivityController(): e=" + e);
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            Log.v("ttaylor", "AppStatusService.setActivityController(): e=" + e);
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            Log.v("ttaylor", "AppStatusService.setActivityController(): e=" + e);
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            Log.v("ttaylor", "AppStatusService.setActivityController(): e=" + e);
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            Log.v("ttaylor", "AppStatusService.registerProcessObserver(): e=" + e);
            e.printStackTrace();
        }
    }
}
