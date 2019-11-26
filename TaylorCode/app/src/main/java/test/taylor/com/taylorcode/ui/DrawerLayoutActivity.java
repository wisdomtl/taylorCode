package test.taylor.com.taylorcode.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import test.taylor.com.taylorcode.R;
import test.taylor.com.taylorcode.SetResultActivity;

/**
 * Created on 2017/11/19.
 */

public class DrawerLayoutActivity extends Activity {
    public static final String[] DRAWER_CONTENT = new String[]{"caidan 1", "caidan 2", "caidan 3", "caidan 4"};
    private DrawerLayout dl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout_activity);
        ListView lv = ((ListView) findViewById(R.id.lv_drawer_content));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.tv, DRAWER_CONTENT);
        dl = ((DrawerLayout) findViewById(R.id.dl));
        lv.setAdapter(adapter);
        View drawerActivator = findViewById(R.id.tv_drawer_activator);
        dl.setScrimColor(Color.TRANSPARENT);
        drawerActivator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dl.openDrawer(Gravity.LEFT);
            }
        });


        findViewById(R.id.btnSetResult).setOnClickListener(v -> {
            startActivity(new Intent(DrawerLayoutActivity.this, SetResultActivity.class));
        });
        findViewById(R.id.btnForResult).setOnClickListener(v -> {
            startActivityForResult(new Intent(DrawerLayoutActivity.this, SetResultActivity.class),200);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.v("ttaylor", "DrawerLayoutActivity.onActivityResult()" + "  requestCode="+requestCode+" resultCode="+resultCode);
    }
}
