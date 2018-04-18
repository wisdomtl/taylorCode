package test.taylor.com.taylorcode.broadcast;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
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
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, intentFilter);

        /**
         * broadcast case2:send local broadcast to a receiver in anonymous inner class
         * although the receiver is in the anonymous inner class, it will last longer than the lifecycle of the class
         */
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);
        Intent intent = new Intent("action_interactive_ad_show");
        manager.sendBroadcast(intent);
        Log.v("taylor ", "BroadcastActivity.onCreate() " + " broadcast action_interactive_ad_show sent");
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
        }
    };

    private void updateButtonName() {
        btn.setText("update name when IntentService finished");
    }

}
