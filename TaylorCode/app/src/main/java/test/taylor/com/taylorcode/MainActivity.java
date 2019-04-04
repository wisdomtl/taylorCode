package test.taylor.com.taylorcode;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import test.taylor.com.taylorcode.data_persistence.RoomActivity;
import test.taylor.com.taylorcode.ui.SelectorActivity;
import test.taylor.com.taylorcode.ui.custom_view.SelectorDemoActivity;
import test.taylor.com.taylorcode.ui.navigation.NavigationActivity;
import test.taylor.com.taylorcode.ui.state_cross_activities.Activity1;
import test.taylor.com.taylorcode.ui.touch_event.TouchEventActivity;
import test.taylor.com.taylorcode.ui.window.WindowActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        initView();
        Log.v("ttaylor", "MainActivity.onCreate()" + "  ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v("ttaylor", "MainActivity.onStart()" + "  ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("ttaylor", "MainActivity.onResume()" + "  ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v("ttaylor", "MainActivity.onPause()" + "  ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v("ttaylor", "MainActivity.onStop()" + "  ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("ttaylor", "MainActivity.onDestroy()" + "  ");
    }

    private void initView() {
        findViewById(R.id.btn_room).setOnClickListener(this);
        findViewById(R.id.btn_livedata).setOnClickListener(this);
        findViewById(R.id.btn_window).setOnClickListener(this);
        findViewById(R.id.btn_touch_event).setOnClickListener(this);
        findViewById(R.id.btn_selector).setOnClickListener(this);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.v("ttaylor", "MainActivity.onSaveInstanceState()" + "  ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.v("ttaylor", "MainActivity.onRestart()" + "  ");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.v("ttaylor", "MainActivity.onRestoreInstanceState()" + "  ");
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
            case R.id.btn_window:
                startActivity(WindowActivity.class);
                break;
            case R.id.btn_selector:
                startActivity(SelectorDemoActivity.class);
                break;

            case R.id.btn_touch_event:
                startActivity(TouchEventActivity.class);
                break;
        }

    }

    private void startActivity(Class cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
}
