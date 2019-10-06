package test.taylor.com.taylorcode.broadcast;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import test.taylor.com.taylorcode.Constant;

/**
 * Created by taylor on 2017/11/5.
 */

public class BroadcastActivity extends Activity implements View.OnClickListener {

    private Button btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btn = new Button(this);
        btn.setOnClickListener(this);
        setContentView(btn);
        IntentFilter intentFilter = new IntentFilter(Constant.ACTION_INTENT_SERVICE_END);
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
//        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, intentFilter);
        /**
           broadcast case1:listener to network change
          LocalBroadcastManager wont receive NETWORK_CHANGE broadcast
         *
         */
        registerReceiver(receiver,intentFilter);


        /**
         * broadcast case2:send local broadcast to a receiver in anonymous inner class
         * although the receiver is in the anonymous inner class, it will last longer than the lifecycle of the class
         */
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);
        Intent intent = new Intent("action_interactive_ad_show");
        manager.sendBroadcast(intent);
        Log.v("taylor ", "BroadcastActivity.onCreate() " + " broadcast action_interactive_ad_show sent");
    }

    private class NetWorkCallBack extends ConnectivityManager.NetworkCallback{
        @Override
        public void onAvailable(Network network) {
            super.onAvailable(network);
            Log.v("ttaylor", "NetWorkCallBack.onAvailable()" );
        }

        @Override
        public void onLosing(Network network, int maxMsToLive) {
            super.onLosing(network, maxMsToLive);
            Log.v("ttaylor", "NetWorkCallBack.onLosing()" + "  ");
        }

        @Override
        public void onLost(Network network) {
            super.onLost(network);
            Log.v("ttaylor", "NetWorkCallBack.onLost()" + "  ");
        }

        @Override
        public void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
            super.onLinkPropertiesChanged(network, linkProperties);
            Log.v("ttaylor", "NetWorkCallBack.onLinkPropertiesChanged()" + "  ");
        }
    }

    @Override
    public void onClick(View v) {
        Log.v("ttaylor", "BroadcastActivity.onClick(): thread-id=" + Thread.currentThread().getId());
        Intent intent = new Intent(this, Receiver.class);
        sendBroadcast(intent);
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.v("ttaylor", "BroadcastActivity.onReceive(): intent=" + intent);
            updateButtonName();
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isAvailable()) {
                Log.v("ttaylor", "BroadcastActivity.onReceive()" + "  network available");
//                requestReward();
            }else {
                Log.v("ttaylor", "BroadcastActivity.onReceive()" + "  network unavailable");
            }
        }
    };

    private void updateButtonName() {
        btn.setText("update name when IntentService finished");
    }

}
