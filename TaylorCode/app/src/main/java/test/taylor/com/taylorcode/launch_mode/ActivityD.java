package test.taylor.com.taylorcode.launch_mode;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Created on 2018/3/2.
 */

public class ActivityD extends ActivityBase {
    @Override
    String getClassName() {
        return "ActivityD";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btn1.setText("STANDARD");
        btn2.setText("STANDARD BUT THE ACTIVITY EXIST IN OTHER TASK");
    }

    /**
     * launch mode case4:STANDARD
     * a new instance of activity with be created in the current task,by default,the activity will had the same task affinity with it's starter activity
     *
     * launch mode case6:FLAG_ACTIVITY_NEW_TASK
     * start an existed activity in the mid of another task
     * new instance wont be created,and the target task will be brought to the front,the focused activity is the one above the target activity.(different with SINGLE_TASK)
     */
    @Override
    public void onButton1Click() {
        super.onButton1Click();
        startActivity(ActivityA.class, null, null);
    }

    /**
     * launch mode case5:FLAG_ACTIVITY_NEW_TASK
     * start an activity which already exists in another task with no task affinity set
     * a new instance of activity will be created in the first task of app
     */
    @Override
    public void onButton2Click() {
        super.onButton2Click();
        startActivity(LaunchModeActivity.class, Intent.FLAG_ACTIVITY_NEW_TASK, null);//2
    }
}
