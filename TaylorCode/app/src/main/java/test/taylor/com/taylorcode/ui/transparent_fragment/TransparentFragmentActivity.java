package test.taylor.com.taylorcode.ui.transparent_fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import test.taylor.com.taylorcode.R;

public class TransparentFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transparent_fragment_activity);
        findViewById(R.id.btn_center).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("ttaylor", "TransparentFragmentActivity.onClick()" + "  ");
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.add(R.id.transparent_root,new TransparentFragment());
                ft.commit() ;
            }
        });
    }
}
