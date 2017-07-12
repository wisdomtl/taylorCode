package test.taylor.com.taylorcode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import test.taylor.com.taylorcode.proxy.ClipboardHook;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EditText et = new EditText(this) ;
        setContentView(et);
        new ClipboardHook().binder(this);
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
