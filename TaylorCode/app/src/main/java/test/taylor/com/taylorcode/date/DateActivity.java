package test.taylor.com.taylorcode.date;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

/**
 * Created by taylor on 2017/11/8.
 */

public class DateActivity extends Activity implements View.OnClickListener {
    private Button btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btn = new Button(this);
        btn.setOnClickListener(this);
        setContentView(btn);
    }

    @Override
    public void onClick(View v) {
        Log.v("ttaylor", "DateActivity.onClick(): ");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 21);
        calendar.set(Calendar.MINUTE, 0);

        long currentTime = System.currentTimeMillis();
        long setTime = calendar.getTimeInMillis();
        Log.v("ttaylor", "DateActivity.onClick(): currentTime="+currentTime);
        Log.v("ttaylor", "DateActivity.onClick(): setTime="+setTime);
        boolean boo = currentTime > setTime;
        btn.setText(String.valueOf(boo));
    }
}
