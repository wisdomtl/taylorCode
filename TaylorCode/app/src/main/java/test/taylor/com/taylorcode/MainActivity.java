package test.taylor.com.taylorcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import test.taylor.com.taylorcode.aysnc.workmanager.WorkManagerActivity;
import test.taylor.com.taylorcode.data_persistence.RoomActivity;
import test.taylor.com.taylorcode.rxjava.LoginActivity;
import test.taylor.com.taylorcode.ui.ConstraintLayoutActivity;
import test.taylor.com.taylorcode.ui.DialogActivity;
import test.taylor.com.taylorcode.ui.DrawerLayoutActivity;
import test.taylor.com.taylorcode.ui.KotlinActivity;
import test.taylor.com.taylorcode.ui.TableLayoutActivity;
import test.taylor.com.taylorcode.ui.anim.AnimActivity;
import test.taylor.com.taylorcode.ui.custom_view.SelectorDemoActivity;
import test.taylor.com.taylorcode.ui.material_design.CoordinateActivity;
import test.taylor.com.taylorcode.ui.navigation.NavigationActivity;
import test.taylor.com.taylorcode.ui.pagers.ViewPagerActivity;
import test.taylor.com.taylorcode.ui.state_cross_activities.Activity1;
import test.taylor.com.taylorcode.ui.surface_view.SurfaceViewActivity;
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
        findViewById(R.id.btn_workmanager).setOnClickListener(this);
        findViewById(R.id.btn_anim).setOnClickListener(this);
        findViewById(R.id.btn_view_pager).setOnClickListener(this);
        findViewById(R.id.btn_navigation).setOnClickListener(this);
        findViewById(R.id.btn_navigation).setOnClickListener(this);
        findViewById(R.id.btn_drawer_layout).setOnClickListener(this);
        findViewById(R.id.btn_constraint_layout).setOnClickListener(this);
        findViewById(R.id.btn_tab_layout).setOnClickListener(this);
        findViewById(R.id.btn_dialog).setOnClickListener(this);
        findViewById(R.id.btn_coordinate_layout).setOnClickListener(this);
        findViewById(R.id.btn_rx_binding).setOnClickListener(this);
        findViewById(R.id.btn_surface_view).setOnClickListener(this);
        findViewById(R.id.btn_kotlin_activity).setOnClickListener(this);
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
            case R.id.btn_coordinate_layout:
                startActivity(CoordinateActivity.class);
                break;
            case R.id.btn_livedata:
                startActivity(Activity1.class);
                break;
            case R.id.btn_dialog:
                startActivity(DialogActivity.class);
                break;
            case R.id.btn_window:
                startActivity(WindowActivity.class);
                break;
            case R.id.btn_tab_layout:
                startActivity(TableLayoutActivity.class);
                break;
            case R.id.btn_drawer_layout:
                startActivity(DrawerLayoutActivity.class);
                break;
            case R.id.btn_selector:
                startActivity(SelectorDemoActivity.class);
                break;
            case R.id.btn_navigation:
                startActivity(NavigationActivity.class);
                break;
            case R.id.btn_workmanager:
                startActivity(WorkManagerActivity.class);
                break;
            case R.id.btn_constraint_layout:
                startActivity(ConstraintLayoutActivity.class);
                break;
            case R.id.btn_view_pager:
                startActivity(ViewPagerActivity.class);
                break;
            case R.id.btn_anim:
                startActivity(AnimActivity.class);
                break;
            case R.id.btn_touch_event:
                startActivity(TouchEventActivity.class);
                break;
            case R.id.btn_rx_binding:
                startActivity(LoginActivity.class);
                break;
            case R.id.btn_surface_view:
                startActivity(SurfaceViewActivity.class);
                break;
            case R.id.btn_kotlin_activity:
                startActivity(KotlinActivity.class);
                break;
        }

    }

    private void startActivity(Class cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
}
