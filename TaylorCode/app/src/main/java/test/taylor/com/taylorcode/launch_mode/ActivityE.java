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
        btn1.setText("FLAG_ACTIVITY_NEW_TASK");
        btn2.setText("FLAG_ACTIVITY_NEW_TASK WITH EXIST ACTIVITY");
        btn3.setText("SINGLE_TASK");
    }

    /**
     * launch mode case3:FLAG_ACTIVITY_NEW_TASK
     * the initial stack is D--(start E by standard)--D,E--(start D by FLAG_ACTIVITY_NEW_TASK)--D,E
     */
    @Override
    public void onButton1Click() {
        super.onButton1Click();
        startActivity(ActivityD.class, Intent.FLAG_ACTIVITY_NEW_TASK, null);
    }

    /**
     * launch mode case5:FLAG_ACTIVITY_NEW_TASK
     * start activity which already exists in another task
     * new instance of this activity wont be created,instead,the task which this activity is in will be brought to the front
     */
    @Override
    public void onButton2Click() {
        super.onButton2Click();
        startActivity(ActivityD.class,Intent.FLAG_ACTIVITY_NEW_TASK, null);
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
    public void onButton3Click() {
        super.onButton3Click();
        startActivity(ActivityI.class,null,null);
    }
}
