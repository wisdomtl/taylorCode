package test.taylor.com.taylorcode.launch_mode;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created on 2018/2/27.
 */

public class LaunchModeActivity extends ActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btn1.setText("STANDARD");
        btn2.setText("FLAG_ACTIVITY_NEW_TASK");
    }

    @Override
    String getClassName() {
        return "LaunchModeActivity";
    }

    /**
     * launch mode case1:FLAG_ACTIVITY_SINGLE_TOP
     * the initial stack is A--(start B by standard)--A,B--(start B by standard)--A,B,B
     * vs
     * the initial stack is A--(start B by standard)--A,B--(start B by SINGLE_TOP)--A,B
     * and the lifecycle of B is like the following:
     * ActivityB.onPause()
     * ActivityB.onNewIntent()
     * ActivityB.onResume()
     * ActivityB.onPostResume()
     */
    @Override
    public void onButton1Click() {
        startActivity(ActivityB.class, null);
    }

    /**
     * launch mode case3
     * start an new activity with an different task affinity and FLAG_ACTIVITY_NEW_TASK,then the new activity will in new task
     * the new activity will be in current task if task affinity not set
     */
    @Override
    public void onButton2Click() {
        super.onButton2Click();
        startActivity(ActivityD.class, Intent.FLAG_ACTIVITY_NEW_TASK);
    }
}
