package test.taylor.com.taylorcode.data_persistence;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import test.taylor.com.taylorcode.R;

public class RoomActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityViewModel activityViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_activity);
        initView();
        initViewModel();
    }

    private void initViewModel() {
        activityViewModel = ViewModelProviders.of(this).get(ActivityViewModel.class);
    }

    private void initView() {
        findViewById(R.id.btn_insert).setOnClickListener(this);
        findViewById(R.id.btn_insert_all).setOnClickListener(this);
        findViewById(R.id.btn_query).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);
        findViewById(R.id.btn_query_earlier).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_insert:
                insert();
                break;
            case R.id.btn_insert_all:
                insertAll();
                break;
            case R.id.btn_query:
                query();
                break;
            case R.id.btn_delete:
                delete();
                break;
            case R.id.btn_query_earlier:
                queryEarlier() ;
                break;
        }
    }

    private void queryEarlier() {
        activityViewModel.queryActivityEarlier(System.currentTimeMillis()).observe(this, activities -> {
            Log.v("ttaylor", "RoomActivity.queryEarlier()" + "  size="+activities.size());
            for (Activity activity : activities) {
                Log.v("ttaylor", "RoomActivity.queryEarlier()" + "  name=" + activity.getUserName() + " ,time=" + activity.getTime() + " ,type=" + activity.getType() + " ,url=" + activity.getUserAvatarUrl() + " ,title=" + activity.getTitle());
            }
        });
    }

    private void delete() {
        activityViewModel.deleteActivity("5678");
    }

    private void query() {
        activityViewModel.queryActivities(0, 2).observe(this, activities -> {
            for (Activity activity : activities) {
                Log.v("ttaylor", "RoomActivity.query()" + "  name=" + activity.getUserName() + " ,time=" + activity.getTime() + " ,type=" + activity.getType() + " ,url=" + activity.getUserAvatarUrl() + " ,title=" + activity.getTitle());
            }
        });
    }

    private void insertAll() {
        Activity activity = new Activity();
        activity.setId("5678");
        activity.setTime("2017-07-29T08:28:47Z");
        activity.setTitle("init");
        activity.setType("a");
        activity.setUserAvatarUrl("fdfs3333333333");
        activity.setUserName("t2222");
        Activity activity1 = new Activity();
        activity1.setId("8901");
        activity1.setTime("2020-07-29T09:28:47.77Z");
        activity1.setTitle("init2");
        activity1.setType("a");
        activity1.setUserAvatarUrl("22222222");
        activity1.setUserName("t3333r");
        Activity activity2 = new Activity();
        activity2.setId("6666");
        activity2.setTime("2020-07-29T09:30:47.77Z");
        activity2.setTitle("init3");
        activity2.setType("a");
        activity2.setUserAvatarUrl("333333333");
        activity2.setUserName("t4444lor");
        List<Activity> activities = new ArrayList<>();
        activities.add(activity);
        activities.add(activity1);
        activities.add(activity2);
        activityViewModel.insertAll(activities);
    }

    private void insert() {
        Activity activity = new Activity();
        activity.setId("1234");
        activity.setTime("2017-07-20T08:28:47.776Z");
        activity.setTitle("init");
        activity.setType("a");
        activity.setUserAvatarUrl("fdfsdfdfdsf");
        activity.setUserName("taylor");
        activityViewModel.insert(activity);
    }
}
