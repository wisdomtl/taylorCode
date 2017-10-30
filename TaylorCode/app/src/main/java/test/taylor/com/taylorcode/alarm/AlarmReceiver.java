package test.taylor.com.taylorcode.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by taylor on 2017/10/30.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v("ttaylor", "AlarmReceiver.onReceive(): intent="+intent);
    }
}
