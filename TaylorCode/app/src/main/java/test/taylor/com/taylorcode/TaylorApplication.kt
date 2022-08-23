package test.taylor.com.taylorcode

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.os.Debug
import android.os.Looper
import android.util.Log
import com.facebook.stetho.Stetho
import test.taylor.com.taylorcode.util.DateUtil.formatDate
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.ExperimentalTime

/**
 * Created on 17/7/26.
 */
class TaylorApplication : Application() {
    @OptIn(ExperimentalTime::class)
    override fun onCreate() {
        super.onCreate()
        System.currentTimeMillis().milliseconds
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            }

            override fun onActivityStarted(activity: Activity) {
            }

            override fun onActivityResumed(activity: Activity) {
            }

            override fun onActivityPaused(activity: Activity) {
                val now = System.currentTimeMillis()
                Log.v("test", "onActivityPaused(${activity.componentName}) time=${now.milliseconds.inWholeSeconds}")
            }

            override fun onActivityStopped(activity: Activity) {
                val now = System.currentTimeMillis()
                Log.v("test", "onActivityStopped(${activity.componentName}) time=${now.milliseconds.inWholeSeconds}")
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

            override fun onActivityDestroyed(activity: Activity) {
            }
        })
        //        ClipboardHook.getInstance().init(this);
//        ActivityHook.getInstance().init(HookSystemServiceActivity.class);

//        BlockCanary.install(this, new AppBlockCanaryContext()).start();
        Debug.startMethodTracing("ttaylor")
        var time: Long = 0
        try {
            time = utcToTimestamp("2019-01-16T15:13:56Z")
        } catch (e: ParseException) {
            Log.v("ttaylor", "TaylorApplication.onCreate()  e=$e")
            e.printStackTrace()
        }
        Log.v("ttaylor", "TaylorApplication.onCreate()  time=$time")
//        Looper.getMainLooper().setMessageLogging { x -> Log.v("ttaylorLooper", " msg=$x") }

        //java quote case1:
        val origin = mutableListOf<String>()
        origin.add("a")
        origin.add("b")
        origin.add("c")
        makeChange(origin)
        for (str in origin) {
            Log.v("ttaylor", "TaylorApplication.onCreate()  str=$str")
        }
        Stetho.initializeWithDefaults(this) //then type "chrome://inspect/#devices" at chrome ,fun start
        registerActivityLifecycleCallbacks(TaylorActivityLifeCycle(object : AppStatusListener {
            override fun onAppBackground() {}
        }))
        //        registerActivityLifecycleCallbacks(FloatWindow.getInstance().getAppStatusListener());
//        List<Class> whiteList = new ArrayList<Class>();
//        whiteList.add(ActivityB.class);
//        whiteList.add(WindowActivity.class);
//        FloatWindow.getInstance().setWhiteList(whiteList);

        //java string index case1
        val d = "asdfgh"
        val index = d.indexOf("f")
        val subs = d.substring(index, d.length)
        Log.v("ttaylor", "TaylorApplication.onCreate() subString= $subs")

        //java round case1:
        val i = Math.round(4.5).toInt()
        val j = Math.round(4.1).toInt()
        val k = (10 / 2.3).toInt()
        Log.v("ttaylor", "TaylorApplication.onCreate()  i=$i ,j=$j ,k=$k")

        //case: format milliseconds to string date
        val date = formatDate(System.currentTimeMillis(), "yyyy-MM-dd")
        Log.v("ttaylor", "TaylorApplication.onCreate()  date=$date")
        /**
         * case: handle exception in global scope
         */
//        ExceptionActivity.TaylorHandler handler = new ExceptionActivity.TaylorHandler();
//        handler.init(this);
//
//        String str = null;
//        str.toCharArray();
        Debug.stopMethodTracing()
    }

    /**
     * time case: convert utc to timestamp
     *
     * @param time
     * @return
     * @throws ParseException
     */
    @Throws(ParseException::class)
    fun utcToTimestamp(time: String?): Long {
        val df2 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        df2.timeZone = TimeZone.getTimeZone("UTC")
        val date = df2.parse(time)
        return date.time
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
    }

    private fun makeChange(origin: MutableList<String>) {
        origin.removeAt(0)
    }

    private inner class TaylorActivityLifeCycle(private val appStatusListener: AppStatusListener?) : ActivityLifecycleCallbacks {
        private var forgroundActivityCount = 0
        private var isConfigurationChange = false
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
        override fun onActivityStarted(activity: Activity) {
            if (isConfigurationChange) {
                isConfigurationChange = false
                return
            }
            forgroundActivityCount++
        }

        override fun onActivityResumed(activity: Activity) {}
        override fun onActivityPaused(activity: Activity) {}
        override fun onActivityStopped(activity: Activity) {
            if (activity.isChangingConfigurations) {
                isConfigurationChange = true
                return
            }
            forgroundActivityCount--
            if (forgroundActivityCount == 0) {
                appStatusListener?.onAppBackground()
            }
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
        override fun onActivityDestroyed(activity: Activity) {}
    }

    private interface AppStatusListener {
        fun onAppBackground()
    }
}