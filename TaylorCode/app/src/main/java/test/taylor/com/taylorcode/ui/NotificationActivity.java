package test.taylor.com.taylorcode.ui;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

import test.taylor.com.taylorcode.R;

/**
 * Created by taylor on 2017/10/30.
 */

public class NotificationActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button btn = new Button(this);
        btn.setOnClickListener(this);
        setContentView(btn);
    }

    @Override
    public void onClick(View v) {
        Log.v("ttaylor", "NotificationActivity.onClick(): ");
        showNotification(this , 0 ,SelectorActivity.class, "notification title", "notification main_content");
    }

    /**
     * case1:show ui-customized notification
     * @param context
     * @param id
     * @param activityClass
     * @param title
     * @param content
     */
    private void showNotification(Context context, int id, Class activityClass, CharSequence title, CharSequence content) {
        Intent notifyIntent = new Intent(this, activityClass);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                context,
                0,
                notifyIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        RemoteViews remoteView = new RemoteViews(context.getPackageName() ,R.layout.custom_notification);
        remoteView.setTextViewText(R.id.tv_title,"djfklslkfjldsjlk  dlkfjlsdjflksjdl kjl");
        remoteView.setImageViewResource(R.id.iv_auther,R.drawable.watch_reward_1);
        remoteView.setTextViewText(R.id.tv_content,"dsfsdfsdfadfewfwefwgewrg rgre gregfd fd");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                //this 3 attribute is a must for notification
                .setSmallIcon(R.drawable.calling_mic_active)
                .setContentTitle(title)
                .setContentText(content)
                //cancel by clicking notification
                .setAutoCancel(true)
                //customize notification ui
                .setContent(remoteView) ;
        builder.setContentIntent(notifyPendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(id, builder.build());
    }
}
