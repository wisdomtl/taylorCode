package test.taylor.com.taylorcode.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import test.taylor.com.taylorcode.R;

public class WindowActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button button = new Button(this);
        button.setOnClickListener(this);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setContentView(button, layoutParams);
    }

    private View getWindowView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.window_content, null);
        return view;
    }

    private void showWindow(Context context) {
        if (context == null) {
            Log.v("ttaylor", "WindowActivity.showWindow()" + "  context is null");
            return;
        }
        WindowManager windowManager = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE));
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        //this flag allow touching outside window
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL ;
        //android.permission.SYSTEM_ALERT_WINDOW is needed,or permission denied for window type 2003
        layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.CENTER;
        windowManager.addView(getWindowView(context), layoutParams);
    }

    @Override
    public void onClick(View view) {
        showWindow(this);
    }
}
