package test.taylor.com.taylorcode.proxy.remote

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Process
import android.util.Log
import test.taylor.com.taylorcode.IRemoteSingleton
import test.taylor.com.taylorcode.proxy.remote.RemoteServiceSingleton.add
import java.io.File

/**
 * represents Service in another process,which is different from the default process of application
 * Created on 17/5/2.
 */
class RemoteService : Service() {
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
    private val binder: IRemoteSingleton.Stub = RemoteServiceSingleton

    /**
     * [story IPC]3.server wrap implemented aidl interface with IBinder and return it to client
     *
     * @param intent
     * @return
     */
    override fun onBind(intent: Intent): IBinder? {
        return binder
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.i("bound-service-in-service", "onStartCommand.()")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreate() {
        super.onCreate()
        Log.v("bound-service-in-service", "RemoteService.onCreate() " + " pid=" + Process.myPid())
        changeSingletonValue()
        map[KEY] = "origin value"
    }

    private fun changeSingletonValue() {
        add("d")
        RemoteServiceSingleton.count = 10
        RemoteServiceSingleton.text2 = "changed by Remote service"
    }

    override fun onUnbind(intent: Intent): Boolean {
        Log.i("bound-service-in-service", "uuuuuuuuuuubind.()")
        File(cacheDir,"abcd.txt").writeText("abcd")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        File(cacheDir,"abcd.txt").writeText("abcd")
        super.onDestroy()
        Log.i("bound-service-in-service", "dddddestroy.()")
    }

    companion object {
        const val KEY = "map"
        private val map: MutableMap<String, String> = HashMap()
    }
}