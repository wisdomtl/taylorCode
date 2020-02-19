package test.taylor.com.taylorcode.file;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

import test.taylor.com.taylorcode.R;

public class FileActivity extends AppCompatActivity {

    private Bean bean;
    private File file;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_activity);

        findViewById(R.id.btnReadFile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object object = FileUtil.readObject(file);
                Bean bean1 = ((Bean) object);
                ((TextView) findViewById(R.id.tvFile)).setText(bean1.toString());
            }
        });

        findViewById(R.id.btnWriteFile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {
                    int REQUEST_CODE_CONTACT = 101;
                    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    //验证是否许可权限
                    for (String str : permissions) {
                        if (FileActivity.this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                            //申请权限
                            FileActivity.this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                        }else{
                           writeFile();
                        }
                    }
                }
            }
        });

        bean = new Bean();
        bean.setCount(1);
        bean.setName("taylor");
        bean.setMsgType("video");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        writeFile();
    }

    private void writeFile() {
        File root = Environment.getExternalStorageDirectory();
        Log.v("ttaylor", "FileActivity.onCreate()" + " path= " + root);
        file = new File(root, "taylor-cache");
        FileUtil.writeObject(bean, file);
    }
}
