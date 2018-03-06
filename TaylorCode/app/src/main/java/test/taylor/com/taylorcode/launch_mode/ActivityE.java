package test.taylor.com.taylorcode.launch_mode;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Created on 2018/3/2.
 */

public class ActivityE extends ActivityBase {

    @Override
    String getClassName() {
        return "ActivityE";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btn1.setText("FLAG_ACTIVITY_NEW_TASK WITH EXIST ACTIVITY");
        btn2.setText("SINGLE_TASK_WITH_EXIST_ACTIVITY");
    }

    /**
     * launch mode case6:FLAG_ACTIVITY_NEW_TASK
     * if start an existed activity in the mid of another task
     * new instance wont be created,and the target task will be brought to the front,the focused activity is the one above the target activity.(different with SINGLE_TASK)
     *
     * launch mode case13:FLAG_ACTIVITY_CLEAR_TOP
     * based on case6,if FLAG_ACTIVITY_CLEAR_TOP is not set,then the activity above target activity wont be destroyed,if set,the activity above will be destroyed
     */
    @Override
    public void onButton1Click() {
        super.onButton1Click();
        startActivity(ActivityD.class, Intent.FLAG_ACTIVITY_NEW_TASK, null);//case6
//        startActivity(ActivityD.class, Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP, null);//case13
    }

    /**
     * launch mode case12:SINGLE_TASK
     * start an existing singleTask activity not in the current task
     * the target task will be brought to the front,and the activity above the target activity will be destroyed(different with NEW_TASK)
     * the target lifecycle is like this:
     * onNewIntent()
     * onRestart()
     * onResume()
     */
    @Override
    public void onButton2Click() {
        super.onButton2Click();
        startActivity(ActivityI.class, null, null);
    }
}
