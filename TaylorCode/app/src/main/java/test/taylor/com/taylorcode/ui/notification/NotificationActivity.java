package test.taylor.com.taylorcode.ui.notification;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import test.taylor.com.taylorcode.Constant;
import test.taylor.com.taylorcode.R;
import test.taylor.com.taylorcode.ui.SelectorActivity;
import test.taylor.com.taylorcode.util.BitmapUtil;

/**
 * Created by taylor on 2017/10/30.
 */

public class NotificationActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_activity);
        findViewById(R.id.btn_activity_notification).setOnClickListener(this);
        findViewById(R.id.btn_broadcast_notification).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.v("ttaylor", "NotificationActivity.onClick(): ");
        int id = v.getId();
        switch (id) {
            case R.id.btn_activity_notification:
                showActivityNotification(this, 0, SelectorActivity.class, "notification title", "notification main_content");
                break;
            case R.id.btn_broadcast_notification:
                showBroadcastNotification(this, 1, NotificationReceiver.class, "notification title2", "notification content2");
                break;
            default:
                break;
        }
    }

    /**
     * notification case1:show ui-customized notification
     *
     * @param context
     * @param id
     * @param activityClass
     * @param title
     * @param content
     */
    private void showActivityNotification(Context context, int id, Class activityClass, CharSequence title, CharSequence content) {
        Intent notifyIntent = new Intent(this, activityClass);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                context,
                0,
                notifyIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.custom_notification);
        remoteView.setTextViewText(R.id.tv_title, "djfklslkfjldsjlk  dlkfjlsdjflksjdl kjl");
        remoteView.setImageViewResource(R.id.iv_auther, R.drawable.watch_reward_1);
        remoteView.setTextViewText(R.id.tv_content, "dsfsdfsdfadfewfwefwgewrg rgre gregfd fd");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                //this 3 attribute is a must for notification
                .setSmallIcon(R.drawable.calling_mic_active)
                .setContentTitle(title)
                .setContentText(content)
                //cancel by clicking notification
                .setAutoCancel(true)
                //customize notification ui
                .setContent(remoteView);
        builder.setContentIntent(notifyPendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(id, builder.build());
    }


    /**
     * notification case2:customize notification click event
     *
     * @param context
     * @param id
     * @param broadcastClass
     * @param title
     * @param content
     */
    private void showBroadcastNotification(Context context, int id, Class broadcastClass, CharSequence title, CharSequence content) {
        Intent notifyIntent = new Intent(this, broadcastClass);
        notifyIntent.setAction(Constant.ACTION_NOTIFICATION_BROADCAST);
//        notifyIntent.putExtra(Constant.EXTRA_NOTIFICATION_ID,id) ;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.video_big_notificaiton);
        remoteView.setTextViewText(R.id.tv_title, title);
        remoteView.setImageViewResource(R.id.iv_auther, R.drawable.watch_reward_1);
        remoteView.setTextViewText(R.id.tv_content, content);
        remoteView.setImageViewResource(R.id.iv_video,R.drawable.watch_reward_1);

        Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.watch_reward_1).copy(Bitmap.Config.ARGB_8888, true);
        Bitmap foreground = BitmapFactory.decodeResource(getResources(), R.drawable.stop).copy(Bitmap.Config.ARGB_8888, true);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                //this 3 attribute is a must for notification
                .setSmallIcon(R.drawable.calling_mic_active)
                .setContentTitle(title)
                .setContentText(content)
                //cancel by clicking notification
                .setAutoCancel(true)
                //customize notification ui
//                .setContent(remoteView)
        .setCustomBigContentView(remoteView);
//                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapUtil.combineBitmap(background, foreground)));
        builder.setContentIntent(pendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(id, builder.build());
    }


}
