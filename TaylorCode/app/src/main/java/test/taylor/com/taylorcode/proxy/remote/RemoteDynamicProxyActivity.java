package test.taylor.com.taylorcode.proxy.remote;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;

import test.taylor.com.taylorcode.IRemoteService;
import test.taylor.com.taylorcode.IRemoteSingleton;
import test.taylor.com.taylorcode.R;

/**
 * Created on 17/5/2.
 */

public class RemoteDynamicProxyActivity extends Activity {

    private IRemoteService iRemoteService;
    private IRemoteSingleton iRemoteSingleton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("taylor servicePid", "RemoteDynamicProxyActivity.onCreate() " + " pid=" + android.os.Process.myPid());
        //case1
        startRemoteService();
        setContentView(R.layout.remote_dynamic_proxy_activity);
        findViewById(R.id.btn_start_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RemoteDynamicProxyActivity.this, RemoteActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * case3:tamper value in remote service(another process) by reflection---failed,we could not reflect value in another process
     */
    private void reflectRemoteServiceValue() {
        try {
            Class remoteService = Class.forName("test.taylor.com.taylorcode.proxy.remote.RemoteService");
            Field mapField = remoteService.getDeclaredField("map");
            mapField.setAccessible(true);
            Map map = ((Map) mapField.get(null));
            map.put(RemoteService.KEY, "tampered by another process");
            Log.v("taylor ttReflection", "RemoteDynamicProxyActivity.reflectRemoteServiceValue() " + " map=" + map.get(RemoteService.KEY));
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("taylor  ttReflection", "RemoteDynamicProxyActivity.reflectRemoteServiceValue() " + " reflect failed");
        }
    }

    /**
     * case1:start remote service which is in another process
     */
    private void startRemoteService() {
        Intent intent = new Intent(this, RemoteService.class);
        this.bindService(intent, serviceConnection2, BIND_AUTO_CREATE);
    }


    /**
     * case2:make proxy for remote service
     *
     * @param iBinder remote service
     */
    private void makeProxy(IBinder iBinder) {
        IRemoteService iRemoteService = IRemoteService.Stub.asInterface(iBinder);
        //hook IBinder,make an proxy of IBinder instance which created by RemoteService.the proxy IBinder will return a local interface
        ClassLoader classLoader = RemoteDynamicProxyActivity.this.getClassLoader();
        Class[] interfaces = new Class[]{IBinder.class};
        InvocationHandler invocationHandler = new IBinderInvocationHandler(iBinder);
        IBinder iBinderProxy = (IBinder) Proxy.newProxyInstance(classLoader, interfaces, invocationHandler);
        IRemoteService iRemoteServiceProxy = IRemoteService.Stub.asInterface(iBinderProxy);
        try {
            Log.v("taylor tamperService", "origin remote service " + " ,isEngineOk = " + iRemoteService.isEngineOk());
            Log.v("taylor tamperService", "remote service proxy " + " ,isEngineOk = " + iRemoteServiceProxy.isEngineOk());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }

    /**
     * case1
     */
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            //case2
            makeProxy(iBinder);
            //[story IPC]4.client convert IBinder into aidl interface for using server function
            iRemoteService = IRemoteService.Stub.asInterface(iBinder);
            try {
                Log.v("taylor servicePid", "RemoteDynamicProxyActivity.onServiceConnected() " + " Thread=" + Thread.currentThread().getId());
                iRemoteService.sail();
                //case3
                Log.v("taylor ttreflection", "RemoteDynamicProxyActivity.onServiceConnected() " + "origin value=" + iRemoteService.getMapValue());
                reflectRemoteServiceValue();
                Log.v("taylor ttreflection", "RemoteDynamicProxyActivity.onServiceConnected() " + " tampered value=" + iRemoteService.getMapValue());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            iRemoteService = null;
        }
    };

    /**
     * case: get value from remote service whose implementation is an singleton
     */
    private ServiceConnection serviceConnection2 = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            iRemoteSingleton = IRemoteSingleton.Stub.asInterface(iBinder);
            try {
                Log.v("ttaylor", "RemoteDynamicProxyActivity.onServiceConnected()"+ " pid="+ Process.myPid() + "  count=" + iRemoteSingleton.getCount());
                Log.v("ttaylor", "RemoteDynamicProxyActivity.onServiceConnected()"+ " pid="+ Process.myPid() + "  text=" + iRemoteSingleton.getText2());
                List<String> list = iRemoteSingleton.getList();
                if (list != null) {
                    for (String element : list) {
                        Log.v("ttaylor", "RemoteDynamicProxyActivity.onServiceConnected()"+ " pid="+ Process.myPid() + "  element=" + element);
                    }
                }
                //case: change value in remote service
                iRemoteSingleton.setCount(20);
                Log.d("ttaylor", "RemoteDynamicProxyActivity.onServiceConnected()"+ " pid="+ Process.myPid() + "  count=" + iRemoteSingleton.getCount());
                iRemoteSingleton.setText2("changed by main process");
                Log.d("ttaylor", "RemoteDynamicProxyActivity.onServiceConnected()"+ " pid="+ Process.myPid() + "  text=" + iRemoteSingleton.getText2());
                iRemoteSingleton.add("e");
                List<String> list1 = iRemoteSingleton.getList();
                if (list1 != null) {
                    for (String element : list1) {
                        Log.d("ttaylor", "RemoteDynamicProxyActivity.onServiceConnected()"+ " pid="+ Process.myPid() + "  element=" + element);
                    }
                }

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            iRemoteService = null;
        }
    };

    /**
     * case2:tamper queryLocalInterface() in Stub.asInterface()
     */
    private class IBinderInvocationHandler implements InvocationHandler {

        public static final String QUERY_LOCAL_INTERFACE = "queryLocalInterface";
        //we only want to hook queryLocalInterface(),this iBinder make the rest invocation of IBinder works as always
        private final IBinder iBinder;

        public IBinderInvocationHandler(IBinder iBinder) {
            this.iBinder = iBinder;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            //this is the only method which must be hooked when making proxy of IBinder
            //we have to invoke asInterface() before using aidl interface which implemented by server and queryLocalInterface() is invoked by asInterface()
            if (QUERY_LOCAL_INTERFACE.equals(method.getName())) {
                //hook IRemoteService which we defined
                return Proxy.newProxyInstance(proxy.getClass().getClassLoader(), new Class[]{IRemoteService.class}, new RemoteServiceInvocationHandler());
            }
            return method.invoke(iBinder, args);
        }
    }

    /**
     * case2:tamper isEngineOk() in remote service
     */
    private class RemoteServiceInvocationHandler implements InvocationHandler {
        public static final String METHOD_SAIL = "sail";
        public static final String METHOD_IS_ENGINE_OK = "isEngineOk";

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (METHOD_IS_ENGINE_OK.equals(method.getName())) {
                return true;
            } else if (METHOD_SAIL.equals(method.getName())) {
            }
            return null;
        }
    }
}
