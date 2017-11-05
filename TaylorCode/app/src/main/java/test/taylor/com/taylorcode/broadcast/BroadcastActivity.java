package test.taylor.com.taylorcode.broadcast;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by taylor on 2017/11/5.
 */

public class BroadcastActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button btn = new Button(this) ;
        btn.setOnClickListener(this);
        setContentView(btn);
    }

    @Override
    public void onClick(View v) {
        Log.v("ttaylor", "BroadcastActivity.onClick(): thread-id="+Thread.currentThread().getId());
        Intent intent = new Intent(this,Receiver.class) ;
        sendBroadcast(intent);
    }
}
