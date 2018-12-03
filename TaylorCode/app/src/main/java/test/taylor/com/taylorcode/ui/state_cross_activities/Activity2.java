package test.taylor.com.taylorcode.ui.state_cross_activities;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import test.taylor.com.taylorcode.R;

public class Activity2 extends AppCompatActivity implements View.OnClickListener {
    private TextView tv2 ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);
        tv2 = findViewById(R.id.tv2) ;
        tv2.setOnClickListener(this);

        StringLiveData.getInstance().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                tv2.setText(s);
            }
        });
    }


    @Override
    public void onClick(View v) {
        int id = v.getId() ;
        switch (id){
            case R.id.tv2 :
                String s = tv2.getText().toString();
                int integer = Integer.valueOf(s) + 1;
                StringLiveData.getInstance().setValue(String.valueOf(integer));
                break;
        }
    }
}
