package test.taylor.com.taylorcode.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import test.taylor.com.taylorcode.R;
import test.taylor.com.taylorcode.util.ResUtil;

/**
 * Created by taylor on 2017/9/7.
 */

public class RadioButtonActivity extends Activity implements RadioGroup.OnCheckedChangeListener {
    private RadioGroup rg ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.radiobutton_activity);
        //case3:add radio button to radio group dynamically
        rg = (RadioGroup) findViewById(R.id.rg);
        rg.setOnCheckedChangeListener(this);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        for (int i = 0; i <= 2; i++) {
            RadioButton rb = ((RadioButton) layoutInflater.inflate(R.layout.radiobutton, null));
            RadioGroup.LayoutParams params1 = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 60);
            params1.setMargins(16, 20, 16, 20);
            rb.setId(i);
            rb.setText(i+"选项");
            rg.addView(rb, params1);
        }
        //case2:default checked for radio button
        rg.check(0);

        //resource case1:get resource by getResources().getIdentifier()
        int resId = ResUtil.getTypeId(this,"tv_name") ;
        ((TextView) findViewById(resId)).setText("get id by getIdentifier");
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        int id = radioGroup.getCheckedRadioButtonId() ;
        RadioButton rb= (RadioButton) rg.findViewById(id);
        String text = rb.getText().toString() ;
        Toast.makeText(this,text,Toast.LENGTH_LONG).show();
    }
}
