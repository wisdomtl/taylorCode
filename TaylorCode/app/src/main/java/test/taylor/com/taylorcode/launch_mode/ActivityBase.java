package test.taylor.com.taylorcode.launch_mode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import test.taylor.com.taylorcode.Constant;
import test.taylor.com.taylorcode.R;

/**
 * Created on 2018/2/27.
 */

public abstract class ActivityBase extends Activity {
    protected Button btn1;
    protected Button btn2;
    protected Button btn3;
    protected Button btn4;
    protected Button btn5;
    protected Button btn6;
    protected TextView tvContent ;

    abstract String getClassName();

    public void onButton1Click() {
        Log.d("ttaylor", "ActivityBase.onButton1Click(): ");
    }

    public void onButton2Click() {
        Log.d("ttaylor", "ActivityBase.onButton2Click(): ");
    }

    public void onButton3Click() {
        Log.d("ttaylor", "ActivityBase.onButton3Click(): ");
    }

    public void onButton4Click() {
        Log.d("ttaylor", "ActivityBase.onButton4Click(): ");
    }

    public void onButton5Click() {
        Log.d("ttaylor", "ActivityBase.onButton5Click(): ");
    }

    public void onButton6Click() {
        Log.d("ttaylor", "ActivityBase.onButton6Click(): ");
    }

    public void startActivity(Class<?> targetClass, Integer flag, String value) {
        Intent intent = new Intent(this, targetClass);
        if (flag != null) {
            intent.addFlags(flag);
        }
        if (!TextUtils.isEmpty(value)) {
            intent.putExtra(Constant.EXTRA_STRING,value) ;
        }
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("ttaylor", getClassName() + ".onCreate(): intent="+getIntent()+" ,taskId="+getTaskId());
        setContentView(R.layout.launch_mode_activity);
        ((TextView) findViewById(R.id.tv_activity_name)).setText(getClassName());

        btn1 = ((Button) findViewById(R.id.btn_standard));
        btn2 = ((Button) findViewById(R.id.btn_single_top));
        btn3 = ((Button) findViewById(R.id.btn_new_task));
        btn4 = ((Button) findViewById(R.id.btn_multiple_task));
        btn5 = ((Button) findViewById(R.id.btn_clear_top));
        btn6 = ((Button) findViewById(R.id.btn_clear_task));
        tvContent = ((TextView) findViewById(R.id.tv_content)) ;

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButton1Click();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButton2Click();
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButton3Click();
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButton4Click();
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButton5Click();
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButton6Click();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v("ttaylor", getClassName() + ".onStart(): ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("ttaylor", getClassName() + ".onResume(): ");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.v("ttaylor", getClassName() + ".onSaveInstanceState(): ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v("ttaylor", getClassName() + ".onPause(): ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v("ttaylor", getClassName() + ".onStop(): ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("ttaylor", getClassName() + ".onDestroy(): ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.v("ttaylor", getClassName() + ".onRestart(): ");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.v("ttaylor", getClassName() + ".onRestoreInstanceState(): ");
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
        Log.v("ttaylor", getClassName() + ".onRestoreInstanceState(): ");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.v("ttaylor", getClassName() + ".onNewIntent(): intent="+getIntent());
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Log.v("ttaylor", getClassName() + ".onPostCreate(): ");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.v("ttaylor", getClassName() + ".onPostResume(): ");
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        Log.v("ttaylor", getClassName() + ".onPostCreate(): ");
    }

}
