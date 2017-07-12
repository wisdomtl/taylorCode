package test.taylor.com.taylorcode.aidl;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import test.taylor.com.taylorcode.IMessage;

/**
 * Created on 17/4/30.
 */

public class ClientActivity extends Activity {
    private IMessage iMessage;

    private IMessageResponse iMessageResponse = new IMessageResponse() {
        @Override
        public void onMessageTypeResponse(int messageType) {
            Log.e("taylor", "ClientActivity.onMessageTypeResponse() " + " messageType=" + messageType);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMessageType(2, iMessageResponse);
        getMessageType(3, iMessageResponse);
    }

    /**
     * getMessageType from another process in a async way
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
        this.bindService(intent, serviceConnection, BIND_AUTO_CREATE);

    }

    private interface IMessageResponse {
        void onMessageTypeResponse(int messageType);
    }
}
