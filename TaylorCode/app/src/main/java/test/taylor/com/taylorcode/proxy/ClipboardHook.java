package test.taylor.com.taylorcode.proxy;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.os.IBinder;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

import test.taylor.com.taylorcode.MainActivity;

public class ClipboardHook {
    public void binder(Activity activity) {
        try {
            /**
             * 1.生成假包
             * 创建一个能从queryLocalInterface中拿到自定义剪贴板接口的代理IBinder ,让这个IBinder发挥作用的是asInterface()接口
             */
            IBinder myClipboardService = (IBinder) Proxy.newProxyInstance(activity.getClassLoader(), new Class[]{IBinder.class}, new IBinderInvocationHandler());
            /**
             * 2.调包
             * 将代理IBinder植入到系统中,为了让传入asInterface()的是代理IBinder,所以必须将其写入ServiceManager.sCache
             */
            Class<?> serviceMangerClass = Class.forName("android.os.ServiceManager");
            Field field = serviceMangerClass.getDeclaredField("sCache");
            field.setAccessible(true);
            Map<String, IBinder> map = (Map) field.get(null);
            map.put(Context.CLIPBOARD_SERVICE, myClipboardService);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class IBinderInvocationHandler implements InvocationHandler {
        public static final String QUERY_LOCAL_INTERFACE = "queryLocalInterface";

        public IBinderInvocationHandler() {
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (QUERY_LOCAL_INTERFACE.equals(method.getName())) {
                //we cant access the source code of IClipboard,but we know the package name.so forName comes in handy
                Class<?> iClipboard = Class.forName("android.content.IClipboard");
                return Proxy.newProxyInstance(MainActivity.class.getClassLoader(),
                        new Class[]{iClipboard},
                        new IClipboardInvocationHandler());
            }
            return method.invoke(proxy, args);
        }
    }

    private class IClipboardInvocationHandler implements InvocationHandler {

        public IClipboardInvocationHandler() {
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if ("getPrimaryClip".equals(method.getName())) {
                String clipText = null;
//                ClipData originClipData = ((ClipData) method.invoke(iClipboard, args));
//                if(originClipData != null && originClipData.getItemCount() > 0){
//                    clipText = originClipData.getItemAt(0).getText().toString() ;
//                }
                return ClipData.newPlainText(clipText, "Taylor !");
            }
            //hooking this method is a must,or crash will happen
            if ("hasPrimaryClip".equals(method.getName())) {
                return true;
            }
            return method.invoke(proxy, args);
        }
    }
}

