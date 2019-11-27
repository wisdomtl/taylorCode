package test.taylor.com.taylorcode.launch_mode;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import test.taylor.com.taylorcode.R;
import test.taylor.com.taylorcode.ui.window.FloatWindow;

/**
 * Created on 2018/2/27.
 */

public class ActivityB extends ActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btn1.setText("STANDARD");
        btn2.setText("FLAG_ACTIVITY_SINGLE_TOP");
        btn3.setText("STANDARD");
        btn4.setText("FLAG_ACTIVITY_NEW_TASK");

        /**
         * window case4:update window content
         */
        updateWindowView();
    }

    @Override
    String getClassName() {
        return "ActivityB";
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
     * <p>
     * launch mode case14:FLAG_ACTIVITY_REORDER_TO_FRONT
     * start an exist activity with this flag,if the activity is not on the top of task,it will be brought to the front like a bubble
     */
    @Override
    public void onButton1Click() {
        super.onButton1Click();
//        startActivity(ActivityB.class, null, null);
        startActivity(LaunchModeActivity.class, Intent.FLAG_ACTIVITY_REORDER_TO_FRONT, null);//case14:no new instance created
//        startActivity(LaunchModeActivity.class, null, null);//case14:a new instance will be created
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
    public void onButton2Click() {
        super.onButton2Click();
        startActivity(ActivityB.class, Intent.FLAG_ACTIVITY_SINGLE_TOP, null);
    }

    /**
     * launch mode case2
     * the initial stack is A--(start B by standard)--A,B--(start C by standard)--A,B,C--(start B by SINGLE_TOP)--A,B,C,B
     */
    @Override
    public void onButton3Click() {
        super.onButton3Click();
        startActivity(ActivityC.class, null, null);
    }

    @Override
    public void onButton4Click() {
        super.onButton4Click();
        startActivity(ActivityD.class, Intent.FLAG_ACTIVITY_NEW_TASK, null);
    }

    /**
     * window case4:update window content
     */
    private void updateWindowView(){
        FloatWindow.INSTANCE.updateWindowView(new FloatWindow.IWindowUpdater() {
            @Override
            public void updateWindowView(View windowView) {
                Log.v("ttaylor", "ActivityB.updateWindowView()" + "  ");
                if (windowView != null) {
//                    TextView tv = ((TextView) windowView.findViewById(R.id.tv_float_window));
//                    tv.setText("changed by ActivityB");


                }
            }
        });
    }
}
