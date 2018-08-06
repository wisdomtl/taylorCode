package test.taylor.com.taylorcode.ui.custom_view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import test.taylor.com.taylorcode.R;
import test.taylor.com.taylorcode.util.Timer;

public class CustomViewActivity extends Activity {

    private ProgressRing progressRing;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_view_activity);

        //custom view case1:update custom view periodically
        progressRing = ((ProgressRing) findViewById(R.id.progress_ring));
        final float FULL_TIME_MILLISECOND = 10 * 1000;
        new Timer(new Timer.TimerListener() {
            @Override
            public void onTick(long pastMillisecond) {
                float mod = pastMillisecond % FULL_TIME_MILLISECOND;
                float i = mod == 0 ? 1 : mod;
                float progress = i / FULL_TIME_MILLISECOND;
                Log.v("ttaylor", "CustomViewActivity.onTick()" + " ,past time=" + pastMillisecond + "  mod=" + mod + " ,progress=" + progress);
                progressRing.setProgress((pastMillisecond % FULL_TIME_MILLISECOND) / FULL_TIME_MILLISECOND);
            }
        }).start(2000, 50);
    }
}
