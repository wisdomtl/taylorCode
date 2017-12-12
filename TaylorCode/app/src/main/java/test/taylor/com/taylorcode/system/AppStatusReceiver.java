package test.taylor.com.taylorcode.system;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import test.taylor.com.taylorcode.broadcast.Receiver;

/**
 * Created on 2017/12/5.
 */

public class AppStatusReceiver extends Receiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            Log.v("ttaylor", "AppStatusReceiver.onReceive(): BOOT_COMPLETED");
        }
        //listen app installation
        else if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
            Log.v("ttaylor", "AppStatusReceiver.onReceive(): PACKAGE_ADDED");
        }
        //listen app removed
        else if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {
            Log.v("ttaylor", "AppStatusReceiver.onReceive(): PACKAGE_REMOVED");
        }
    }
}
