package test.taylor.com.taylorcode.proxy.remote;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import test.taylor.com.taylorcode.IRemoteSingleton;


/**
 * represents Service in another process,which is different from the default process of application
 * Created on 17/5/2.
 */

public class RemoteService extends Service {
    public static final String KEY = "map";

    private static Map<String, String> map = new HashMap<>();

    /**
     * [story IPC]2.server implement aidl interface
     */
//    private IRemoteService.Stub binder = new IRemoteService.Stub() {
//        @Override
//        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
//            //do nothing
//        }
//
//        @Override
//        public void sail() throws RemoteException {
//            Log.v("taylor servicePid", "RemoteService.sail() " + " pid=" + android.os.Process.myPid());
//        }
//
//        @Override
//        public boolean isEngineOk() throws RemoteException {
//            boolean isEngineOk = false;
//            Log.v("taylor tamperService", "RemoteService.isEngineOk() " + " isEngineOk = " + isEngineOk);
//            return isEngineOk;
//        }
//
//        /**
//         * case3:tamper value in remote service(another process) by reflection---failed,we could not reflect value in another process
//         * @return
//         * @throws RemoteException
//         */
//        @Override
//        public String getMapValue() throws RemoteException {
//            if (map != null) {
//                return map.get(KEY);
//            }
//            return "map is null";
//        }
//    };

    private IRemoteSingleton.Stub binder = RemoteServiceSingleton.INSTANCE ;

    /**
     * [story IPC]3.server wrap implemented aidl interface with IBinder and return it to client
     *
     * @param intent
     * @return
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("taylor servicePid", "RemoteService.onCreate() " + " pid=" + android.os.Process.myPid());

        changeSingletonValue();

        map.put(KEY, "origin value");
    }

    private void changeSingletonValue() {
        RemoteServiceSingleton.INSTANCE.add("d");
        RemoteServiceSingleton.INSTANCE.setCount(10);
        RemoteServiceSingleton.INSTANCE.setText2("changed by Remote service");
    }
}
