package test.taylor.com.taylorcode;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.facebook.stetho.Stetho;
import com.github.moduth.blockcanary.BlockCanary;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import test.taylor.com.taylorcode.block_canary.AppBlockCanaryContext;
import test.taylor.com.taylorcode.util.DateUtil;


/**
 * Created on 17/7/26.
 */

public class TaylorApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        ClipboardHook.getInstance().init(this);
//        ActivityHook.getInstance().init(HookSystemServiceActivity.class);

//        BlockCanary.install(this, new AppBlockCanaryContext()).start();
        long time = 0;
        try {
            time = utcToTimestamp("2019-01-16T15:13:56Z");
        } catch (ParseException e) {
            Log.v("ttaylor", "TaylorApplication.onCreate()" + "  e="+e);
            e.printStackTrace();
        }
        Log.v("ttaylor", "TaylorApplication.onCreate()" + "  time="+time);


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
        String d = "asdfgh";
        int index = d.indexOf("f");
        String subs = d.substring(index, d.length());
        Log.v("ttaylor", "TaylorApplication.onCreate()" + " subString= " + subs);

        //java round case1:
        int i = ((int) Math.round(4.5d));
        int j = ((int) Math.round(4.1d));
        int k = (int) (10 / 2.3);
        Log.v("ttaylor", "TaylorApplication.onCreate()" + "  i=" + i + " ,j=" + j + " ,k=" + k);

        //case: format milliseconds to string date
        String date = DateUtil.INSTANCE.formatDate(System.currentTimeMillis(), "yyyy-MM-dd");
        Log.v("ttaylor", "TaylorApplication.onCreate()" + "  date=" + date);

        /**
         * case: handle exception in global scope
         */
//        ExceptionActivity.TaylorHandler handler = new ExceptionActivity.TaylorHandler();
//        handler.init(this);
//
//        String str = null;
//        str.toCharArray();
    }

    /**
     * time case: convert utc to timestamp
     * @param time
     * @return
     * @throws ParseException
     */
    public  long utcToTimestamp(String time) throws ParseException {
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        df2.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = df2.parse(time);
        return date.getTime();
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
                    appStatusListener.onAppBackground();
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
