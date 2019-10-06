package test.taylor.com.taylorcode.service;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;

import test.taylor.com.taylorcode.R;

/**
 * Created on 2018/3/23.
 */

public class ServiceActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_activity);
        findViewById(R.id.btn_stop_service).setOnClickListener(this);

        Intent intent = new Intent(this, ServiceA.class);
        startService(intent);
    }

    /**
     * service case1:stop service by stopService()
     * this method could stop the service even if the it is in the other process
     * @param v
     */
    @Override
    public void onClick(View v) {
        Log.v("ttaylor", "ServiceActivity.onClick(): ");
        stopService(new Intent(this, ServiceA.class));
    }
}
