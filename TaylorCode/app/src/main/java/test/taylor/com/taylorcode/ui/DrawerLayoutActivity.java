package test.taylor.com.taylorcode.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import test.taylor.com.taylorcode.R;

/**
 * Created on 2017/11/19.
 */

public class DrawerLayoutActivity extends Activity {
    public static final String [] DRAWER_CONTENT = new String[]{"caidan 1" ,"caidan 2","caidan 3","caidan 4"} ;
    private DrawerLayout dl ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout_activity);
        ListView lv = ((ListView) findViewById(R.id.lv_drawer_content));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.tv,DRAWER_CONTENT) ;
         dl = ((DrawerLayout) findViewById(R.id.dl));
        lv.setAdapter(adapter);
        View drawerActivator = findViewById(R.id.tv_drawer_activator) ;
        drawerActivator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   dl.openDrawer(Gravity.LEFT);
            }
        });
    }
}
