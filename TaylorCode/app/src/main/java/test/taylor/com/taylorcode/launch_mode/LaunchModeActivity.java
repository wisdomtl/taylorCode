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
        btn3.setText("SINGLE_INSTANCE");
        btn4.setText("SINGLE_TASK");
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
        startActivity(ActivityB.class, null, null);
    }

    /**
     * launch mode case3:FLAG_ACTIVITY_NEW_TASK
     * start an new activity with an different task affinity and FLAG_ACTIVITY_NEW_TASK,then the new activity will in new task
     * the new activity will be in current task if task affinity not set
     *
     * launch mode case15:FLAG_ACTIVITY_TASK_ON_HOME
     * start an activity and let it related to launcher task,it means when navigate back from the activity launcher will show up(not the task which starts the )
     */
    @Override
    public void onButton2Click() {
        super.onButton2Click();
        startActivity(ActivityD.class, Intent.FLAG_ACTIVITY_NEW_TASK, null);
//        startActivity(ActivityD.class, Intent.FLAG_ACTIVITY_TASK_ON_HOME | Intent.FLAG_ACTIVITY_NEW_TASK, null);//case15
    }

    /**
     * launch mode case8:SINGLE_INSTANCE
     * start a new activity with singleInstance mode,and start another activity with standard mode
     * a new task will be created only for activityG,
     * no other activities is allowed to be in this task,
     * even if the activity has the same task affinity with activityG,in this case,a new task whose task affinity is the same as activityG will be create
     */
    @Override
    public void onButton3Click() {
        super.onButton3Click();
        startActivity(ActivityG.class, null, null);
    }

    /**
     * launch mode case10:SINGLE_TASK
     * if task affinity is not set,a new activity will be created in the current task,or a new task will be created
     * if task affinity is set,a new task will be created,and singleTask activity will be root activity of new task
     */
    @Override
    public void onButton4Click() {
        super.onButton4Click();
        startActivity(ActivityI.class, null, null);
    }

}
