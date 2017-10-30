package test.taylor.com.taylorcode.alarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by taylor on 2017/10/30.
 */

public class AlarmActivity extends Activity {

    public static final int ALARM_HOUR_OF_DAY = 12;
    public static final int ALARM_MINUTE = 40;
    //    public static final int ALARM_INTERVAL = 1000*60*60*24 ;
    public static final int ALARM_INTERVAL = 10 ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setAlarm(this, ALARM_HOUR_OF_DAY, ALARM_MINUTE, ALARM_INTERVAL);
    }

    private void setAlarm(Context context, int hourOfDay, int minute, int interval) {
        Log.v("ttaylor", String.format("AlarmActivity.setAlarm(): hourOfDay=%s ,minute=%s ,internal=%s",hourOfDay , minute ,interval));
        AlarmManager alarmManager = ((AlarmManager) this.getSystemService(ALARM_SERVICE));

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);

        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), interval, alarmIntent);
    }
}
