package test.taylor.com.taylorcode.ui.notification;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import test.taylor.com.taylorcode.Constant;

/**
 * notification case2:customize notification click event
 */
public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (Constant.ACTION_NOTIFICATION_BROADCAST.equals(action)) {
            Log.v("ttaylor", "NotificationReceiver.onReceive()" + " ACTION_NOTIFICATION_BROADCAST ");
            Toast.makeText(context, "receive message after notification has clickec", Toast.LENGTH_LONG).show();
//            NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
//            int notificationId = intent.getIntExtra(Constant.EXTRA_NOTIFICATION_ID, -1) ;
//            notificationManager.cancel(notificationId);
        }
    }
}
