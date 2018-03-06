package test.taylor.com.taylorcode.launch_mode;


import android.content.Intent;
import android.os.Bundle;

/**
 * Created on 2018/2/27.
 */

public class ActivityA extends ActivityBase {

    @Override
    String getClassName() {
        return "ActivityA";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btn1.setText("FLAG_ACTIVITY_NEW_TASK");
        btn2.setText("STANDARD");
        btn3.setText("SINGLE_TASK");
        btn4.setText("SINGLE_TASK_IN_OTHER_TASK");
    }

    /**
     * launch mode case6:FLAG_ACTIVITY_NEW_TASK
     * start an existed activity in the mid of another task
     * new instance wont be created,and the target task will be brought to the front,the focused activity is the one above the target activity.(different with SINGLE_TASK)
     */
    @Override
    public void onButton1Click() {
        super.onButton1Click();
        startActivity(ActivityE.class, Intent.FLAG_ACTIVITY_NEW_TASK, null);
    }


    /**
     * launch mode case7:FLAG_ACTIVITY_NEW_TASK
     * start an existed activity in the mid of another task with no task affinity set
     */
    @Override
    public void onButton2Click() {
        super.onButton2Click();
        startActivity(ActivityF.class, null, null);
    }

    /**
     * launch mode case11:SINGLE_TASK
     * start an existing singleTask activity
     * if the existing singleTask activity is on the top of task then the following lifecycle will be invoked:
     * ActivityI.onPause()
     * ActivityI.onNewIntent()
     * ActivityI.onResume()
     *
     * if it is not on the top of task,the activities above it will be destroyed
     */
    @Override
    public void onButton3Click() {
        super.onButton3Click();
        startActivity(ActivityI.class, null, null);
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
    public void onButton4Click() {
        super.onButton3Click();
        startActivity(ActivityE.class, Intent.FLAG_ACTIVITY_NEW_TASK,null);
    }
}
