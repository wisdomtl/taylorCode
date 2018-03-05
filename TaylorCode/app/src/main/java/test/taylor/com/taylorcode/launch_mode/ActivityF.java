package test.taylor.com.taylorcode.launch_mode;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created on 2018/3/3.
 */

public class ActivityF extends ActivityBase {
    @Override
    String getClassName() {
        return "ActivityF";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btn1.setText("FLAG_ACTIVITY_NEW_TASK");
    }

    /**
     * launch mode case7:FLAG_ACTIVITY_NEW_TASK
     * start an existed activity in the mid of another task with no task affinity set
     */
    @Override
    public void onButton1Click() {
        super.onButton1Click();
        startActivity(ActivityE.class, Intent.FLAG_ACTIVITY_NEW_TASK, null);
    }
}
