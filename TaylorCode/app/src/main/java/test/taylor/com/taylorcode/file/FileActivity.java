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
import java.util.ArrayList;
import java.util.List;

import test.taylor.com.taylorcode.R;

public class FileActivity extends AppCompatActivity {

    private Bean bean;
    private File file;
    private File file2;

    private List<Bean> beans = new ArrayList<>();

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


        findViewById(R.id.btnReadObjects).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object object = FileUtil.readObject(file2);
                List<Bean> bean1 = ((List<Bean>) object);
                for (Bean bean :
                        bean1) {
                    Log.v("ttaylor", "FileActivity.onClick()" + "  bean=" + bean);
                }
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
                        } else {
                            writeObject();
                        }
                    }
                }
            }
        });

        findViewById(R.id.btnWriteObjects).setOnClickListener(new View.OnClickListener() {
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
                        } else {
                            writeObjects();
                        }
                    }
                }
            }
        });

        bean = new Bean("taylor", 1, "video");

        beans.add(new Bean("lili", 2, "text"));
        beans.add(new Bean("fangfang", 3, "video"));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        writeObject();
    }

    private void writeObject() {
        File root = Environment.getExternalStorageDirectory();
        Log.v("ttaylor", "FileActivity.writeObject()" + " path= " + root);
        file = new File(root, "taylor-cache");
        FileUtil.writeObject(bean, file);
    }

    private void writeObjects() {
        File root = Environment.getExternalStorageDirectory();
        Log.v("ttaylor", "FileActivity.writeObjects()" + " path= " + root);
        file2 = new File(root, "taylor-cache2");
        FileUtil.writeObject(beans, file2);
    }
}
