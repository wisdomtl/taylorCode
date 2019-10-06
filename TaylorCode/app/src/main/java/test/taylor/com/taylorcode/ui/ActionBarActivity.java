package test.taylor.com.taylorcode.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;

import test.taylor.com.taylorcode.R;

/**
 * Created on 17/8/21.
 */

public class ActionBarActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.action_bar_activity);
        //case1:show action bar in activity
        ActionBar actionBar = getActionBar() ;
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.custom_action_bar);
        //case2:set click listener for custom view in action bar
        actionBar.getCustomView().findViewById(R.id.btn_back).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.btn_back){
            Log.v("taylor ttactionbar" , "ActionBarActivity.onClick() "+ " back is click") ;
            this.finish();
        }
    }
}
