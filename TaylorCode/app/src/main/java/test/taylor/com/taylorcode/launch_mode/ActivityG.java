package test.taylor.com.taylorcode.launch_mode;

import android.os.Bundle;
import android.util.Log;

/**
 * Created on 2018/3/5.
 */

public class ActivityG extends ActivityBase {
    @Override
    String getClassName() {
        return "ActivityG";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btn1.setText("STANDARD");
        Log.v("ttaylor", "ActivityG.onCreate(): intent="+getIntent());
    }

    /**
     * launch mode case8:SINGLE_INSTANCE
     * start a new activityG with singleInstance mode,and start another activityH with standard mode
     * a new task will be created only for activityG,
     * no other activities is allowed to be in this task,
     * even if the activity has the same task affinity with activityG,in this case,a new task whose task affinity is the same as activityG will be create
     *
     * launch mode9:SINGLE_INSTANCE
     * restart an singleInstance activity with value kept in intent
     * the lifecycle is like this:
     * onNewIntent()
     * onRestart()
     * onStart()
     * onResume()
     */
    @Override
    public void onButton1Click() {
        super.onButton1Click();
        startActivity(ActivityH.class, null, null);
    }
}
