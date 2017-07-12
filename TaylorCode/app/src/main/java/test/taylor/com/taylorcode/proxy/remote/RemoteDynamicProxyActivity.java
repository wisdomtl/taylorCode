package test.taylor.com.taylorcode.proxy.remote;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import test.taylor.com.taylorcode.IRemoteService;

/**
 * Created on 17/5/2.
 */

public class RemoteDynamicProxyActivity extends Activity {

    private IRemoteService iRemoteService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("taylor servicePid", "RemoteDynamicProxyActivity.onCreate() " + " pid=" + android.os.Process.myPid());
        startRemoteService();
    }

    private void startRemoteService() {
        Intent intent = new Intent(this, RemoteService.class);
        this.bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            makeProxy(iBinder);
            //[story IPC]4.client convert IBinder into aidl interface for using server function
            iRemoteService = IRemoteService.Stub.asInterface(iBinder);
            try {
                Log.v("taylor servicePid", "RemoteDynamicProxyActivity.onServiceConnected() " + " Thread=" + Thread.currentThread().getId());
                iRemoteService.sail();
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
     * make proxy for remote service
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
     * tamper queryLocalInterface() in Stub.asInterface()
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
     * tamper isEngineOk() in remote service
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
