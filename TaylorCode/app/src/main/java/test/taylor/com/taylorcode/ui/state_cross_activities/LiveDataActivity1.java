package test.taylor.com.taylorcode.ui.state_cross_activities;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import test.taylor.com.taylorcode.R;

public class LiveDataActivity1 extends AppCompatActivity implements View.OnClickListener {
    private TextView tv1;
    private TextView tvMediator;
    private MutableLiveData<Integer> liveData1 = new MutableLiveData<>();
    private MutableLiveData<Integer> liveData2 = new MutableLiveData<>();
    private MediatorLiveData<List<Integer>> mediatorLiveData = new MediatorLiveData<>();
    private boolean isLiveData1Update;
    private boolean isLiveData2Update;
    private List<Integer> finalIntegers = new ArrayList<>();

    /**
     * LiveData case1:observer wont be notified until it's lifecycle component is at STARTED or RESUMED status
     */
    private MutableLiveData<String> mutableLiveData = new MutableLiveData<>();

    private MutableLiveData<String> lateLiveData = new MutableLiveData<>();

    private MutableLiveData<String> threadLiveData = new MutableLiveData<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activit1);
        tv1 = findViewById(R.id.tv1);
        tv1.setOnClickListener(this);
        tvMediator = findViewById(R.id.tv_mediator12);
        findViewById(R.id.btn_start_activity2).setOnClickListener(this);
        findViewById(R.id.btn_change_livedata1).setOnClickListener(this);
        findViewById(R.id.btn_change_livedata2).setOnClickListener(this);

        /**
         * LiveData case1:observer wont be notified until it's lifecycle component is at STARTED or RESUMED status
         */
        mutableLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Log.v("ttaylor", "Activity1.onChanged()" + "  string=" + s);
            }
        });
        mutableLiveData.setValue("oncreate");


        /**
         * LiveData case2:a app lifecycle LiveData
         */
        StringLiveData.getInstance().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.v("ttaylor", "StringLiveData.onChanged()" + "  s="+s);
                tv1.setText(s);
            }
        });
        StringLiveData.getInstance().setValue(String.valueOf(0));

        /**
         * LiveData case3:combine 2 LiveData into one
         */
        mediatorLiveData.addSource(liveData1, value -> {
            isLiveData1Update = true;
            finalIntegers.add(value);
            if (isLiveData1Update && isLiveData2Update) {
                mediatorLiveData.setValue(finalIntegers);
            }
        });
        mediatorLiveData.addSource(liveData2, value -> {
            isLiveData2Update = true;
            finalIntegers.add(value);
            if (isLiveData2Update && isLiveData1Update) {
                mediatorLiveData.setValue(finalIntegers);
            }
        });
        mediatorLiveData.observe(this, integers -> {
            StringBuilder sb = new StringBuilder();
            for (int integer : integers) {
                sb.append(String.valueOf(integer));
                sb.append(",");
            }
            tvMediator.setText(sb.toString());
        });

        /**
         *  LiveData case: LiveData is sticky
         */
        lateLiveData.postValue("fast post later observer");
        findViewById(R.id.btn_start_activity2).postDelayed(new Runnable() {
            @Override
            public void run() {
                lateLiveData.observe(LiveDataActivity1.this, new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        Log.v("ttaylor", "LiveDataActivity1.onChanged()" + "  later observer");
                    }
                });
            }
        },5000);

        /**
         *  LiveData case: LiveData's observe will invoked in main thread
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.v("ttaylor", "LiveDataActivity1.run()" + "  thread id="+Thread.currentThread().getId());
                threadLiveData.postValue("done");
            }
        },"livedata").start();

        Log.v("ttaylor", "LiveDataActivity1.onCreate()" + "  thread id="+Thread.currentThread().getId());
        threadLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.v("ttaylor", "LiveDataActivity1.onChanged()" + "  thread id="+Thread.currentThread().getId());
            }
        });


        /**
         * concurrentmodification:
         */
//        ArrayList<String> list = new ArrayList<>();
//        list.add("1");
//        list.add("2");
//        for (String element :
//                list) {
//            if(element.equals("1")){
//                list.add("3");
//            }
//        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mutableLiveData.setValue("onstart");
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv1:
                String s = tv1.getText().toString();
                int integer = Integer.valueOf(s) + 1;
                StringLiveData.getInstance().setValue(String.valueOf(integer));
                break;
            case R.id.btn_start_activity2:
                Intent intent = new Intent(this, Activity2.class);
                startActivity(intent);
                break;
            case R.id.btn_change_livedata1:
                liveData1.setValue(1);
                break;
            case R.id.btn_change_livedata2:
                liveData2.setValue(2);
                break;
        }
    }
}
