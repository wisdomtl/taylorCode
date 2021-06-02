package test.taylor.com.taylorcode.ui.notification

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.LOLLIPOP
import android.os.Build.VERSION_CODES.O
import android.os.Bundle
import android.provider.Settings.*
import android.view.View
import android.widget.Button
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import test.taylor.com.taylorcode.Constant
import test.taylor.com.taylorcode.R
import test.taylor.com.taylorcode.ui.SelectorActivity
import test.taylor.com.taylorcode.ui.performance.RecyclerViewPerformanceActivity

/**
 * Created by taylor on 2017/10/30.
 */
class NotificationActivity : Activity(), View.OnClickListener {


    private val CHANNEL_ID = "3"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notification_activity)
        findViewById<View>(R.id.btn_activity_notification).setOnClickListener(this)
        findViewById<View>(R.id.btn_broadcast_notification).setOnClickListener(this)
        findViewById<Button>(R.id.btn_message_notification).setOnClickListener(this)
        createMessageNotificationChannel(this@NotificationActivity, CHANNEL_ID, "Uki 通知", "消息通知")
    }

    override fun onClick(v: View) {
        val id = v.id
        when (id) {
            R.id.btn_activity_notification -> showActivityNotification(this, 0, SelectorActivity::class.java, "notification title", "notification main_content")
            R.id.btn_broadcast_notification -> showBroadcastNotification(this, 1, NotificationReceiver::class.java, "notification title2", "notification content2")
            R.id.btn_message_notification -> checkNotificationPermission(this@NotificationActivity, CHANNEL_ID) { showMessageNotification(this@NotificationActivity, CHANNEL_ID) }
            else -> {
            }
        }
    }

    /**
     * show notification
     */
    private fun showMessageNotification(context: Context, channelId: String) {
        val notificationManagerCompat = NotificationManagerCompat.from(context.applicationContext)
        val contentIntent = Intent(this, RecyclerViewPerformanceActivity::class.java)
        val contentPendingIntent = PendingIntent.getActivity(this, 1, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val notificationBuilder =
            NotificationCompat.Builder(this, channelId)
                .setChannelId(channelId)
                .setSmallIcon(R.drawable.watch_reward_1)
                .setContentTitle("Incoming call")
                .setContentText("(919) 555-1234")
                .setContentIntent(contentPendingIntent)
                .setDefaults(Notification.DEFAULT_SOUND.or(Notification.DEFAULT_VIBRATE))
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setAutoCancel(true)

        val incomingCallNotification = notificationBuilder.build()
        notificationManagerCompat.notify(0, incomingCallNotification)
    }

    /**
     * create a new channel for message notification
     */
    private fun createMessageNotificationChannel(context: Context, channelId: String, channelName: String, channelDesc: String) {
        if (SDK_INT >= O) {
            val notificationManagerCompat = NotificationManagerCompat.from(context.applicationContext)
            val notificationChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.description = channelDesc
            notificationChannel.enableVibration(true)
            notificationChannel.setShowBadge(true)
            notificationChannel.lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC //show notification in lock screen
            notificationManagerCompat.createNotificationChannel(notificationChannel)
        }
    }

    /**
     * check whether the specific [channelId] has notification or heads up notification permission
     * @param action the action will be invoked when permission is ready
     */
    private fun checkNotificationPermission(context: Context, channelId: String, action: () -> Unit) {
        val notificationManager = NotificationManagerCompat.from(context.applicationContext)
        if (! notificationManager.areNotificationsEnabled()) { // has no notification permission
            gotoNotificationSettingPage(context)
        } else if (SDK_INT >= O) {
            notificationManager.areNotificationsEnabled()
            val importance = notificationManager.getNotificationChannel(channelId)?.importance ?: NotificationManagerCompat.IMPORTANCE_UNSPECIFIED
            if (importance == NotificationManagerCompat.IMPORTANCE_DEFAULT) { // has no heads up notification permission
                gotoNotificationSettingPage(context, channelId)
            } else {
                action.invoke()
            }
        }
    }

    private fun gotoNotificationSettingPage(context: Context, channelId: String? = null) {
        Intent().apply {
            when {
                SDK_INT >= O -> {
                    action = if (channelId.isNullOrEmpty()) ACTION_APP_NOTIFICATION_SETTINGS else ACTION_CHANNEL_NOTIFICATION_SETTINGS
                    putExtra(EXTRA_APP_PACKAGE, context.packageName)
                    putExtra(EXTRA_CHANNEL_ID, channelId)
                }
                SDK_INT >= LOLLIPOP -> {
                    action = ACTION_APP_NOTIFICATION_SETTINGS
                    putExtra("app_package", context.packageName)
                    putExtra("app_uid", context.applicationInfo.uid)
                }
                else -> {
                    action = "android.settings.APPLICATION_DETAILS_SETTINGS"
                    data = Uri.fromParts("package", context.packageName, null)
                }
            }
        }.also { context.startActivity(it) }
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
    private fun showActivityNotification(context: Context, id: Int, activityClass: Class<*>, title: CharSequence, content: CharSequence) {
        val notifyIntent = Intent(this, activityClass)
        notifyIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val notifyPendingIntent = PendingIntent.getActivity(
            context,
            0,
            notifyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val remoteView = RemoteViews(context.packageName, R.layout.custom_notification)
        remoteView.setTextViewText(R.id.tv_title, "djfklslkfjldsjlk  dlkfjlsdjflksjdl kjl")
        remoteView.setImageViewResource(R.id.iv_auther, R.drawable.watch_reward_1)
        remoteView.setTextViewText(R.id.tv_content, "dsfsdfsdfadfewfwefwgewrg rgre gregfd fd")
        val builder = NotificationCompat.Builder(context) //this 3 attribute is a must for notification
            .setSmallIcon(R.drawable.calling_mic_active)
            .setContentTitle(title)
            .setContentText(content) //cancel by clicking notification
            .setAutoCancel(true) //customize notification ui
            .setContent(remoteView)
        builder.setContentIntent(notifyPendingIntent)
        val mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        mNotificationManager.notify(id, builder.build())
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
    private fun showBroadcastNotification(context: Context, id: Int, broadcastClass: Class<*>, title: CharSequence, content: CharSequence) {
        val notifyIntent = Intent(this, broadcastClass)
        notifyIntent.action = Constant.ACTION_NOTIFICATION_BROADCAST
        //        notifyIntent.putExtra(Constant.EXTRA_NOTIFICATION_ID,id) ;
        val pendingIntent = PendingIntent.getBroadcast(context, id, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val remoteView = RemoteViews(context.packageName, R.layout.video_big_notificaiton)
        remoteView.setTextViewText(R.id.tv_title, title)
        remoteView.setImageViewResource(R.id.iv_auther, R.drawable.watch_reward_1)
        remoteView.setTextViewText(R.id.tv_content, content)
        remoteView.setImageViewResource(R.id.iv_video, R.drawable.watch_reward_1)
        val background = BitmapFactory.decodeResource(resources, R.drawable.watch_reward_1).copy(Bitmap.Config.ARGB_8888, true)
        val foreground = BitmapFactory.decodeResource(resources, R.drawable.stop).copy(Bitmap.Config.ARGB_8888, true)
        val builder = NotificationCompat.Builder(context) //this 3 attribute is a must for notification
            .setSmallIcon(R.drawable.calling_mic_active)
            .setContentTitle(title)
            .setContentText(content) //cancel by clicking notification
            .setAutoCancel(true) //customize notification ui
            //                .setContent(remoteView)
            .setCustomBigContentView(remoteView)
        //                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapUtil.combineBitmap(background, foreground)));
        builder.setContentIntent(pendingIntent)
        val mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        mNotificationManager.notify(id, builder.build())
    }
}