package test.taylor.com.taylorcode.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by taylor on 2017/11/5.
 */

public class Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v("ttaylor", "Receiver.onReceive(): intent="+intent+" ,thread-id="+Thread.currentThread().getId());
        //case1:do time-consuming task in BroadcastReceiver by IntentService
        Intent intent1 = new Intent(context,MyIntentService.class) ;
        context.startService(intent1) ;
    }
}
