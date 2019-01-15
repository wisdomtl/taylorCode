package test.taylor.com.taylorcode.data_persistence;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import test.taylor.com.taylorcode.R;

public class RoomActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityViewModel activityViewModel ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_activity);
        initView();
        initViewModel();
    }

    private void initViewModel() {
        activityViewModel = ViewModelProviders.of(this).get(ActivityViewModel.class) ;
    }

    private void initView() {
        findViewById(R.id.btn_create_table).setOnClickListener(this);
        findViewById(R.id.btn_insert).setOnClickListener(this);
        findViewById(R.id.btn_insert_all).setOnClickListener(this);
        findViewById(R.id.btn_query).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_create_table:
                createTable();
                break;
            case R.id.btn_insert:
                insert();
                break;
            case R.id.btn_insert_all:
                insertAll();
                break;
            case R.id.btn_query:
                query();
                break;
        }
    }

    private void query() {
    }

    private void insertAll() {
    }

    private void insert() {
        Activity activity = new Activity() ;
        activity.setId("1234");
        activity.setTime(String.valueOf(System.currentTimeMillis()));
        activity.setTitle("init");
        activity.setType("a");
        activity.setUserAvatarUrl("fdfsdfdfdsf");
        activity.setUserName("taylor");
        activityViewModel.insert(activity);
    }

    private void createTable() {
    }
}
