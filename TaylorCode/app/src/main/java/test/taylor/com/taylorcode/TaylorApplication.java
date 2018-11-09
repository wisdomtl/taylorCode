package test.taylor.com.taylorcode;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.facebook.stetho.Stetho;

import java.util.ArrayList;

import test.taylor.com.taylorcode.ui.window.FloatWindow;
import test.taylor.com.taylorcode.ui.window.WindowActivity;
import test.taylor.com.taylorcode.util.DimensionUtil;

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
        makeChange(origin);
        for (String str :
                origin) {
            Log.v("ttaylor", "TaylorApplication.onCreate()" + "  str=" + str);
        }

        Stetho.initializeWithDefaults(this);//then type "chrome://inspect/#devices" at chrome ,fun start

        registerActivityLifecycleCallbacks(new TaylorActivityLifeCycle(new AppStatusListener() {
            @Override
            public void onAppBackground() {
            }
        }));
//        registerActivityLifecycleCallbacks(FloatWindow.getInstance().getAppStatusListener());
//        List<Class> whiteList = new ArrayList<Class>();
//        whiteList.add(ActivityB.class);
//        whiteList.add(WindowActivity.class);
//        FloatWindow.getInstance().setWhiteList(whiteList);

        //java string index case1
        String d = "asdfgh" ;
        int index = d.indexOf("f") ;
        String subs = d.substring(index,d.length()) ;
        Log.v("ttaylor", "TaylorApplication.onCreate()" + " subString= "+subs);

        //java round case1:
        int i = ((int) Math.round(4.5d));
        int j = ((int) Math.round(4.1d));
        Log.v("ttaylor", "TaylorApplication.onCreate()" + "  i="+i+ " ,j="+j);
    }

    private void makeChange(ArrayList<String> origin) {
        origin.remove(0);
    }


    private class TaylorActivityLifeCycle implements ActivityLifecycleCallbacks {

        private int forgroundActivityCount = 0;
        private boolean isConfigurationChange = false;
        private AppStatusListener appStatusListener;

        public TaylorActivityLifeCycle(AppStatusListener appStatusListener) {
            this.appStatusListener = appStatusListener;
        }

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {
            if (isConfigurationChange) {
                isConfigurationChange = false;
                return;
            }

            forgroundActivityCount++;
        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {
            if (activity.isChangingConfigurations()) {
                isConfigurationChange = true;
                return;
            }
            forgroundActivityCount--;
            if (forgroundActivityCount == 0) {
                if (appStatusListener != null) {
//                    appStatusListener.onAppBackground();
                }
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    }

    private interface AppStatusListener {
        void onAppBackground();

    }
}
