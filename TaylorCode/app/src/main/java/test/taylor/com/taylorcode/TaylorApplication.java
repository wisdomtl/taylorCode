package test.taylor.com.taylorcode;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.facebook.stetho.Stetho;

import java.util.ArrayList;

import test.taylor.com.taylorcode.proxy.system.ActivityHook;
import test.taylor.com.taylorcode.proxy.system.ClipboardHook;

/**
 * Created on 17/7/26.
 */

public class TaylorApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        ClipboardHook.getInstance().init(this);
//        ActivityHook.getInstance().init(HookSystemServiceActivity.class);


        //java quote case1:
        ArrayList<String> origin = new ArrayList();
        origin.add("a");
        origin.add("b");
        origin.add("c");
        makeChange(origin) ;
        for (String str :
                origin) {
            Log.v("ttaylor", "TaylorApplication.onCreate()" + "  str="+str);
        }

        Stetho.initializeWithDefaults(this);//then type "chrome://inspect/#devices" at chrome ,fun start
    }

    private void makeChange(ArrayList<String> origin) {
        origin.remove(0) ;
    }


    private class TaylorActivityLifeCycle implements ActivityLifecycleCallbacks{

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    }
}
