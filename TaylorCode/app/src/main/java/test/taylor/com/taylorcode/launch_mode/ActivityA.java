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
    }

    /**
     * launch mode case6:FLAG_ACTIVITY_NEW_TASK
     * start an existed activity in the mid of another task
     * new instance wont be created,and the target task will be brought to the front,the focused activity is the one above the target activity.
     */
    @Override
    public void onButton1Click() {
        super.onButton1Click();
        startActivity(ActivityE.class, Intent.FLAG_ACTIVITY_NEW_TASK);
    }


    /**
     * launch mode case7:FLAG_ACTIVITY_NEW_TASK
     * start an existed activity in the mid of another task with no task affinity set
     */
    @Override
    public void onButton2Click() {
        super.onButton2Click();
        startActivity(ActivityF.class,null);
    }
}
