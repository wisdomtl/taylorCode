package test.taylor.com.taylorcode.proxy.system;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import test.taylor.com.taylorcode.R;
import test.taylor.com.taylorcode.Taylor;

public class HookSystemServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hook_system_service_activity);
        ClipboardHook.getInstance().binder(this);
    }

    private Taylor taylor = new Taylor() {
        @Override
        public void a(int pa) {

        }

        @Override
        public int b(String pa) {
            return 0;
        }

        @Override
        public void c() {

        }
    } ;
}
