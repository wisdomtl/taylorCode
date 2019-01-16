package test.taylor.com.taylorcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import test.taylor.com.taylorcode.data_persistence.RoomActivity;
import test.taylor.com.taylorcode.ui.state_cross_activities.Activity1;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        initView();
    }

    private void initView() {
        findViewById(R.id.btn_room).setOnClickListener(this);
        findViewById(R.id.btn_livedata).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_room:
                startActivity(RoomActivity.class);
                break;
            case R.id.btn_livedata:
                startActivity(Activity1.class);
                break;
        }

    }

    private void startActivity(Class cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
}
