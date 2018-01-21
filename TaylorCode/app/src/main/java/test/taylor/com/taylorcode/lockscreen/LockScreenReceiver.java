package test.taylor.com.taylorcode.lockscreen;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import test.taylor.com.taylorcode.broadcast.Receiver;

/**
 * Created on 2017/12/26.
 */

public class LockScreenReceiver extends Receiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.v("ttaylor", "LockScreenReceiver.onReceive(): ");
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            Log.v("ttaylor", "LockScreenReceiver.onReceive(): ACTION_SCREEN_OFF");
            Intent lockIntent = new Intent(context, LockScreenActivity.class);
            lockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            context.startActivity(lockIntent);
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            // do other things if you need
        } else if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            // do other things if you need
        }
    }
}
