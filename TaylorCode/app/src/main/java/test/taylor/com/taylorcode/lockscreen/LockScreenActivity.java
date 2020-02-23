package test.taylor.com.taylorcode.lockscreen;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.Nullable;

/**
 * Created on 2017/12/25.
 */

public class LockScreenActivity extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams params = new
                WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
                WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD,
                PixelFormat.TRANSPARENT);
        getWindow().setAttributes(params);
        Button btn = new Button(this);
        btn.setText("Lock Screen");
        setContentView(btn);

//        popupWindow();

    }


    private void popupWindow() {
        Button bb = new Button(getApplicationContext());
        bb.setText("ddccccccccccaaaaaaaaaa");
        bb.setTextSize(50);

        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);

        WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();

        wmParams.type = 2002;  //这里是关键，你也可以试试2003

        wmParams.flags = 40;

        wmParams.width = WindowManager.LayoutParams.MATCH_PARENT;

        wmParams.height = 300;

        wm.addView(bb, wmParams);  //创建View
    }

}
