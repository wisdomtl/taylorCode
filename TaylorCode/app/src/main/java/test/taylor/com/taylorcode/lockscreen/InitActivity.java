package test.taylor.com.taylorcode.lockscreen;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.widget.Toast;

/**
 * Created on 2017/12/26.
 */

public class InitActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent();
        intent.setClass(InitActivity.this, LockScreenService.class);
        startService(intent);
        Toast.makeText(InitActivity.this, "锁屏服务已启动，请先关闭屏幕然后再打开屏幕进行测试", Toast.LENGTH_SHORT).show();
    }

    public void test(){
        class Receivers extends BroadcastReceiver {

            @Override
            public void onReceive(Context context, Intent intent) {

            }
        }
    }
}
