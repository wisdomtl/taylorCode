package test.taylor.com.taylorcode.aidl;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;
import java.util.Map;

import test.taylor.com.taylorcode.IMessage;
import test.taylor.com.taylorcode.R;
import test.taylor.com.taylorcode.proxy.remote.RemoteActivity;

/**
 * Created on 17/4/30.
 */

public class ClientActivity extends Activity {
    public static final String KEY = "map";
    private IMessage iMessage;

    /**
     * case1
     */
    private IMessageResponse iMessageResponse = new IMessageResponse() {
        @Override
        public void onMessageTypeResponse(int messageType) {
            Log.e("taylor", "ClientActivity.onMessageTypeResponse() " + " messageType=" + messageType);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_activity);
        findViewById(R.id.btn_start_remote_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClientActivity.this, RemoteActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_print_local).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("ttaylor", "ClientActivity.onClick()" +" pid="+ Process.myPid() + "  string="+LocalSingleton.INSTANCE.getString());
            }
        });

        findViewById(R.id.btn_set_local).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               LocalSingleton.INSTANCE.setString("set by main thread");
            }
        });
        //case1
        getMessageType(2, iMessageResponse);
//        getMessageType(3, iMessageResponse);
    }


    /**
     * case1:getMessageType from another process in a async way
     *
     * @param index
     * @param iMessageResponse
     */
    private void getMessageType(final int index, final IMessageResponse iMessageResponse) {
        Intent intent = new Intent(this, LocalServer.class);
        ServiceConnection serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                iMessage = IMessage.Stub.asInterface(iBinder);
                int messageType = LocalServer.MESSAGE_TYPE_INVALID;
                try {
                    Log.v("taylor", "ClientActivity.onServiceConnected() " + " Thread=" + Thread.currentThread().getId());
                    messageType = iMessage.getMessageType(index);
                    //case3:tamper value in local service by reflection
                    Log.v("taylor ttreflection", "ClientActivity.onServiceConnected() " + "origin map value=" + iMessage.getMapValue());
                    reflectLocalServiceValue();
                    Log.v("taylor ttreflection", "ClientActivity.onServiceConnected() " + "tampered map value=" + iMessage.getMapValue());
                } catch (RemoteException e) {
                    e.printStackTrace();
                    iMessageResponse.onMessageTypeResponse(LocalServer.MESSAGE_TYPE_INVALID);
                }
                iMessageResponse.onMessageTypeResponse(messageType);
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                iMessage = null;
            }
        };
        //[bug]:bind twice
        this.bindService(intent, serviceConnection, BIND_AUTO_CREATE);

    }

    /**
     * case1
     */
    private interface IMessageResponse {
        void onMessageTypeResponse(int messageType);
    }


    /**
     * case3:tamper member value in the same process by reflection
     */
    private void reflectLocalServiceValue() {
        try {
            Class localService = Class.forName("test.taylor.com.taylorcode.aidl.LocalServer");
            Field mapField = localService.getDeclaredField("map");
            mapField.setAccessible(true);
            Map map = ((Map) mapField.get(null));
            map.put(LocalServer.KEY, "tampered by the same process");
            Log.v("taylor ttReflection", "ClientActivity.reflectLocalServiceValue() " + " map=" + map.get(LocalServer.KEY));
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("taylor  ttReflection", "ClientActivity.reflectLocalServiceValue() " + " reflect failed");
        }
    }
}
