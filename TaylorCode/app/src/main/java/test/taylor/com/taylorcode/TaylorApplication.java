package test.taylor.com.taylorcode;

import android.app.Application;

import test.taylor.com.taylorcode.proxy.system.ActivityHook;
import test.taylor.com.taylorcode.proxy.system.ClipboardHook;

/**
 * Created on 17/7/26.
 */

public class TaylorApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ClipboardHook.getInstance().init(this);
        ActivityHook.getInstance().init(HookSystemServiceActivity.class);
    }
}
