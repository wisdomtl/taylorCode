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
        btn3.setText("FLAG_ACTIVITY_NEW_TASK");
        Log.v("ttaylor", "ActivityD.onCreate(): taskId=" + getTaskId());
    }

    /**
     * launch mode case4
     * by default,the activity will had the same task affinity with it's starter activity
     * <p>
     * launch mode case3
     * the initial stack is D--(start E by standard)--D,E--(start D by FLAG_ACTIVITY_NEW_TASK)--D,E
     *
     * launch mode case6:FLAG_ACTIVITY_NEW_TASK
     * start an existed activity in the mid of another task
     * new instance wont be created,and the target task will be brought to the front,the focused activity is the one above the target activity.
     */
    @Override
    public void onButton1Click() {
        super.onButton1Click();
        startActivity(ActivityA.class, null, null);
    }

    /**
     * launch mode case5
     * start an activity with different flag ,which already exists in another task
     * 1.with standard flag:a new instance of activity with be created in the current task
     * 2.with FLAG_ACTIVITY_NEW_TASK:a new instance of activity will be created in the first task of app,there will be two instance of this activity
     */
    @Override
    public void onButton2Click() {
        super.onButton2Click();
//        startActivity(LaunchModeActivity.class, null);//1
        startActivity(LaunchModeActivity.class, Intent.FLAG_ACTIVITY_NEW_TASK, null);//2
    }

    /**
     * launch mode case6:FLAG_ACTIVITY_NEW_TASK
     * start an activity by FLAG_ACTIVITY_NEW_TASK with no task affinity set in a new task,the new activity will be created in the first task of app
     * <p>
     * launch mode case5:FLAG_ACTIVITY_NEW_TASK
     * start activity which already exists in another task
     * new instance of this activity wont be created,instead,the task which this activity is in will be brought to the front
     */
    @Override
    public void onButton3Click() {
        super.onButton3Click();
        startActivity(ActivityE.class, Intent.FLAG_ACTIVITY_NEW_TASK, null);
    }
}
