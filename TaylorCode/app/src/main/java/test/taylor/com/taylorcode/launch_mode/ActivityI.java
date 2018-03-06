package test.taylor.com.taylorcode.launch_mode;

import android.os.Bundle;

/**
 * Created on 2018/3/5.
 */

public class ActivityI extends ActivityBase {
    @Override
    String getClassName() {
        return "ActivityI";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btn1.setText("SINGLE_TASK");
        btn2.setText("STANDARD");
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
    public void onButton1Click() {
        super.onButton1Click();
        startActivity(ActivityI.class,null,null);
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
    public void onButton2Click() {
        super.onButton2Click();
        startActivity(ActivityA.class,null,null);
    }


}
