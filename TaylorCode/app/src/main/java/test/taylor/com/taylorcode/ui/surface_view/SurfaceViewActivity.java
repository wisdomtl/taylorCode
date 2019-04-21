package test.taylor.com.taylorcode.ui.surface_view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Arrays;
import java.util.List;

import test.taylor.com.taylorcode.R;

public class SurfaceViewActivity extends AppCompatActivity implements View.OnClickListener {

    private FrameSurfaceView frameSurfaceView;

    private List<Integer> bitmaps = Arrays.asList(R.drawable.watch_reward_1,
            R.drawable.watch_reward_2,
            R.drawable.watch_reward_3,
            R.drawable.watch_reward_4,
            R.drawable.watch_reward_5,
            R.drawable.watch_reward_6,
            R.drawable.watch_reward_7,
            R.drawable.watch_reward_8,
            R.drawable.watch_reward_9,
            R.drawable.watch_reward_10,
            R.drawable.watch_reward_11,
            R.drawable.watch_reward_12,
            R.drawable.watch_reward_13,
            R.drawable.watch_reward_14,
            R.drawable.watch_reward_15,
            R.drawable.watch_reward_16,
            R.drawable.watch_reward_17,
            R.drawable.watch_reward_18,
            R.drawable.watch_reward_19,
            R.drawable.watch_reward_20,
            R.drawable.watch_reward_21,
            R.drawable.watch_reward_22);

//    private List<Integer> bitmaps = Arrays.asList(R.drawable.stop);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.surfaceview_activity);

        findViewById(R.id.btn_start).setOnClickListener(this);
        frameSurfaceView = findViewById(R.id.sv_frame);
        frameSurfaceView.setBitmaps(bitmaps);
        frameSurfaceView.setDuration(600);
    }

    @Override
    public void onClick(View v) {
        frameSurfaceView.start();
    }
}
