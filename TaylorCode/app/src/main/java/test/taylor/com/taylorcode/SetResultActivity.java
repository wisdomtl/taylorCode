package test.taylor.com.taylorcode;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

public class SetResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activit1);

        findViewById(R.id.btnFinish).setOnClickListener(v -> finish());
    }

    @Override
    protected void onStop() {
        Log.v("ttaylor", "SetResultActivity.onStop()" + "  ");
        setResult(111);
        super.onStop();
    }

    @Override
    protected void onPause() {
        Log.v("ttaylor", "SetResultActivity.onPause()" + "  ");
        setResult(222);
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        Log.v("ttaylor", "SetResultActivity.onBackPressed()" + "  ");
        setResult(333);
        super.onBackPressed();
    }

    @Override
    public void finish() {
        Log.v("ttaylor", "SetResultActivity.finish()" + "  ");
        setResult(444);
        super.finish();
    }
}
