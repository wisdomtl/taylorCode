package test.taylor.com.taylorcode.ui;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import test.taylor.com.taylorcode.R;

public class DialogActivity extends Activity implements View.OnClickListener {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dailog_activity);
        findViewById(R.id.btn_show_dialog).setOnClickListener(this);
    }


    private void show2() {
        Dialog bottomDialog = new Dialog(this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_content, null);
        bottomDialog.setContentView(contentView);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
        params.width = ViewGroup.MarginLayoutParams.MATCH_PARENT;
        contentView.setLayoutParams(params);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.ActionSheetDialogAnimation);
        bottomDialog.show();
    }

    @Override
    public void onClick(View v) {
       int id = v.getId();
       switch (id){
           case R.id.btn_show_dialog :
               show2();
               break;
       }
    }
}
