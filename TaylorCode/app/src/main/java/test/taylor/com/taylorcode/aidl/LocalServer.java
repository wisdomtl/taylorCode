package test.taylor.com.taylorcode.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import test.taylor.com.taylorcode.IMessage;

/**
 * the Service works in the same process as the application
 * Created on 17/4/30.
 */

public class LocalServer extends Service {
    public static final String KEY = "map";

    public static final int MESSAGE_TYPE_TEXT = 1;
    public static final int MESSAGE_TYPE_SOUND = 2;
    public static final int MESSAGE_TYPE_INVALID = -1;

    private static Map<String, String> map = new HashMap<>();

    private IMessage.Stub binder = new IMessage.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
            //do nothing
        }

        @Override
        public int getMessageType(int index) throws RemoteException {
            //server logic
            Log.v("taylor", "LocalServer.getMessageType() " + " Thread=" + Thread.currentThread().getId());
            return index % 2 == 0 ? MESSAGE_TYPE_SOUND : MESSAGE_TYPE_TEXT;
        }

        /**
         * case3:tamper value in local service by reflection
         * @return
         * @throws RemoteException
         */
        @Override
        public String getMapValue() throws RemoteException {
            if (map != null) {
                return map.get(KEY);
            }
            return "map is empty";
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        map.put(KEY, "origin value");
    }
}
