package test.taylor.com.taylorcode.proxy.system;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * hook IActivityManager
 * 1.拦截startActivity(特洛伊木马):将假activity(士兵)塞进真Activity(木马),待通过检查后,将假Activity放出来
 */
public class ActivityHook {
    public static final String EXTRA_SOLDIER = "extra-soldier";

    private ActivityHook() {
    }

    private static class ActivityHookHolder {
        public static final ActivityHook INSTANCE = new ActivityHook();
    }

    public static ActivityHook getInstance() {
        return ActivityHookHolder.INSTANCE;
    }

    /**
     * 初始化木马Activity
     *
     * @param trojansActivity 木马Activity
     */
    public void init(Class<?> trojansActivity) {
        try {
            getInTrojans(trojansActivity);
            getOutTrojans();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 士兵塞进木马
     *
     * @param trojansActivity 木马activity(被注册过的)
     * @throws ClassNotFoundException
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private void getInTrojans(Class<?> trojansActivity) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        /**1.获得ActivityManagerService实例**/
        Class<?> activityManagerNative = Class.forName("android.app.ActivityManagerNative");
        Field gDefaultField = activityManagerNative.getDeclaredField("gDefault");
        gDefaultField.setAccessible(true);
        Object gDefault = gDefaultField.get(null);
        Class<?> singleton = Class.forName("android.util.Singleton");
        Field mInstanceField = singleton.getDeclaredField("mInstance");
        mInstanceField.setAccessible(true);
        Object ams = mInstanceField.get(gDefault);

        /**2.伪造ActivityManagerService实例**/
        ClassLoader classLoader = trojansActivity.getClassLoader();
        Class<?> iActivityManager = Class.forName("android.app.IActivityManager");
        Class[] interfaces = new Class[]{iActivityManager};
        InvocationHandler invocationHandler = new IActivityManagerInvocationHandler(ams, trojansActivity);
        Object myAms = Proxy.newProxyInstance(classLoader, interfaces, invocationHandler);

        /**3.将伪造的ActivityManagerService存回去 这样系统就能用伪造的来办事了**/
        mInstanceField.set(gDefault, myAms);
        // [下面这个套路不行] 因为gDefault是一个静态变量并通过单例方式获得 在系统启动第一个Activity的时候Singleton.create()方法已经被调用了 以后就再也不会调用了 所以hook create()中的内容没有用
//            InvocationHandler invocationHandler1 = new IBinderInvocationHandler(trojansActivity, iActivityManagerInstance);
//            IBinder iBinder = (IBinder) Proxy.newProxyInstance(classLoader, new Class[]{IBinder.class}, invocationHandler1);
//            Class<?> serviceMangerClass = Class.forName("android.os.ServiceManager");
//            Field field = serviceMangerClass.getDeclaredField("sCache");
//            field.setAccessible(true);
//            Map<String, IBinder> map = (Map) field.get(null);
//            map.put(Context.ACTIVITY_SERVICE, iBinder);
    }

    /**
     * 士兵从木马中出来
     *
     * @throws Exception
     */
    public void getOutTrojans() throws Exception {
        //拦截系统启动activity的消息并修改处理消息的方式
        Class<?> activityThread = Class.forName("android.app.ActivityThread");
        Field activityThreadSingleton = activityThread.getDeclaredField("sCurrentActivityThread");
        activityThreadSingleton.setAccessible(true);
        Object activityThreadInstance = activityThreadSingleton.get(null);
        Field mHField = activityThread.getDeclaredField("mH");
        mHField.setAccessible(true);
        Handler mH = (Handler) mHField.get(activityThreadInstance);
        Field callBackField = Handler.class.getDeclaredField("mCallback");
        callBackField.setAccessible(true);
        callBackField.set(mH, new SoldierOutingCallBack());
    }

    private class SoldierOutingCallBack implements Handler.Callback {

        @Override
        public boolean handleMessage(Message msg) {
            //读和startActivity有关的msg.what值
            int LAUNCH_ACTIVITY = 0;
            try {
                Class<?> clazz = Class.forName("android.app.ActivityThread$H");
                Field field = clazz.getField("LAUNCH_ACTIVITY");
                LAUNCH_ACTIVITY = field.getInt(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //这里只是拦截了启动Activity的message
            if (msg.what == LAUNCH_ACTIVITY) {
                soldierOuting(msg);
            }
            return false;
        }
    }

    /**
     * 士兵从木马中出来
     *
     * @param msg 系统发送的启动activity的消息
     */
    public void soldierOuting(Message msg) {
        try {
            Object obj = msg.obj;
            Field intentField = obj.getClass().getDeclaredField("intent");
            intentField.setAccessible(true);
            Intent trojansIntent = (Intent) intentField.get(obj);
            Intent soldierIntent = trojansIntent.getParcelableExtra(EXTRA_SOLDIER);
            trojansIntent.setComponent(soldierIntent.getComponent());
            Log.v("taylor ttinvoke" , "ActivityHook.soldierOuting() "+ " trojansIntent="+trojansIntent) ;

            //for AppCompatActivity
//            Class<?> forName = Class.forName("android.app.ActivityThread");
//            Field field = forName.getDeclaredField("sCurrentActivityThread");
//            field.setAccessible(true);
//            Object activityThread = field.get(null);
//            Method getPackageManager = activityThread.getClass().getDeclaredMethod("getPackageManager");
//            Object iPackageManager = getPackageManager.invoke(activityThread);
//            PackageManagerHandler handler = new PackageManagerHandler(iPackageManager);
//            Class<?> iPackageManagerIntercept = Class.forName("android.content.pm.IPackageManager");
//            Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
//                    new Class<?>[]{iPackageManagerIntercept}, handler);
//            // 获取 sPackageManager 属性
//            Field iPackageManagerField = activityThread.getClass().getDeclaredField("sPackageManager");
//            iPackageManagerField.setAccessible(true);
//            iPackageManagerField.set(activityThread, proxy);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    private static class PackageManagerHandler implements InvocationHandler {
//        private Object mActivityManagerObject;
//
//        public PackageManagerHandler(Object mActivityManagerObject) {
//            this.mActivityManagerObject = mActivityManagerObject;
//        }
//
//        @Override
//        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//            if (method.getName().equals("getActivityInfo")) {
//                ComponentName componentName = new ComponentName(FakeActivity.class.getPackage().getName(), RealActivity.class.getName());
//                args[0] = componentName;
//            }
//            return method.invoke(mActivityManagerObject, args);
//        }
//    }

    /**
     * 拦截IActivityManager.startActivity(),将假Intent塞进木马
     */
    private class IActivityManagerInvocationHandler implements InvocationHandler {
        public static final String START_ACTIVITY = "startActivity";
        private Object iActivityManager;
        private Class<?> trojansActivity;

        public IActivityManagerInvocationHandler(Object amsObj, Class<?> trojansActivity) {
            this.iActivityManager = amsObj;
            this.trojansActivity = trojansActivity;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (START_ACTIVITY.equals(method.getName())) {
                //查找参数Intent
                int index = 0;
                for (int i = 0; i < args.length; i++) {
                    if (args[i] instanceof Intent) {
                        index = i;
                        break;
                    }
                }
                //取出传入startActivity的士兵intent
                Intent soldierIntent = (Intent) args[index];
                Log.v("taylor ttinvoke" , "IActivityManagerInvocationHandler.invoke() "+ " soldierIntent="+soldierIntent) ;
                //创造木马intent
                Intent trojansIntent = new Intent();
                String PackageName = trojansActivity.getPackage().getName();
                Log.v("taylor ttinvoke" , "IActivityManagerInvocationHandler.invoke() "+ " packageName="+PackageName) ;
                ComponentName componentName = new ComponentName(PackageName, trojansActivity.getName());
                Log.v("taylor ttinvoke" , "IActivityManagerInvocationHandler.invoke() "+ " componentName="+componentName) ;
                trojansIntent.setComponent(componentName);
                Log.v("taylor ttinvoke" , "IActivityManagerInvocationHandler.invoke() "+ " trojansIntent="+trojansIntent) ;
                //将士兵Intent塞入木马Intent以逃避检查 Instrumentation.checkStartActivityResult()
                trojansIntent.putExtra(EXTRA_SOLDIER, soldierIntent);
                //利用动态代理修改了方法参数
                args[index] = trojansIntent;
            }
            return method.invoke(iActivityManager, args);
        }
    }

// [下面这个套路不行] 因为gDefault是一个静态变量并通过单例方式获得 在系统启动第一个Activity的时候Singleton.create()方法已经被调用了 以后就再也不会调用了 所以hook create()中的内容没有用
//    public class IBinderInvocationHandler implements InvocationHandler {
//        public static final String QUERY_LOCAL_INTERFACE = "queryLocalInterface";
//        private Activity trojansActivity ;
//        private Object activityManagerService ;
//
//        public IBinderInvocationHandler(Activity trojansActivity, Object activityManagerService) {
//            this.trojansActivity = trojansActivity ;
//            this.activityManagerService = activityManagerService ;
//        }
//
//        @Override
//        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//            if (QUERY_LOCAL_INTERFACE.equals(method.getName())) {
//                //we cant access the source code of IActivityManager,but we know the package name.so forName() comes in handy
//                Class<?> iClipboard = Class.forName("android.app.IActivityManager");
//                return Proxy.newProxyInstance(MainActivity.class.getClassLoader(),
//                        new Class[]{iClipboard},
//                        new HookInvocationHandler(activityManagerService,null, trojansActivity.getClass()));
//            }
//            return method.invoke(proxy, args);
//        }
//    }
}
