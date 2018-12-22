package test.taylor.com.taylorcode.photo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import test.taylor.com.taylorcode.R;

public class TakePhotoActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int REQUEST_CODE_TAKE_PICTURE = 1;
    public static final int REQUEST_CODE_PICK_PICTURE = 2;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private Uri imageUri;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.take_photo_activity);
        initView();
    }

    private void initView() {
        findViewById(R.id.btn_take_photo).setOnClickListener(this);
        findViewById(R.id.btn_pick_photo).setOnClickListener(this);
        findViewById(R.id.btn_take_photo_store).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pick_photo:
                pickPhoto();
                break;
            case R.id.btn_take_photo:
                takePicture();
                break;
            case R.id.btn_take_photo_store:
                verifyStoragePermission(this);
                break;
        }
    }

    private void pickPhoto() {
        Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
        openAlbumIntent.setType("image/*");
        startActivityForResult(openAlbumIntent, REQUEST_CODE_PICK_PICTURE);//打开相册
    }

    private void takePictureAndStore() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            File imageFile = createImageFile();
            imageUri = getImageUri(imageFile);
            if (imageUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
            }
        }
    }

    private void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_TAKE_PICTURE) {
            if (resultCode != RESULT_OK) {
                return;
            }
            showPictureTaken(data);

        } else if (requestCode == REQUEST_CODE_PICK_PICTURE) {
            if (resultCode != RESULT_OK) {
                return;
            }
            showPicturePicked(data);
        }
    }

    private void showPicturePicked(Intent data) {
        Uri uri = data.getData();
        try {
            Bitmap bitmap1  = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
            ((ImageView) findViewById(R.id.iv_pic_origin)).setImageBitmap(bitmap1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void showPictureTaken(Intent data) {
        //show thumbnail
        if (data != null) {
            Bundle extras = data.getExtras();
            Bitmap bitmap = ((Bitmap) extras.get("data"));
            ((ImageView) findViewById(R.id.iv_pic)).setImageBitmap(bitmap);
        }
        //show origin image
        else if (imageUri != null) {
            try {
                Bitmap bitmap1 = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                ((ImageView) findViewById(R.id.iv_pic_origin)).setImageBitmap(bitmap1);
                showImageInGallery(imageUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    private void showImageInGallery(Uri imageUri) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(imageUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private File createImageFile() {
        String imageFileName = String.valueOf(System.currentTimeMillis());
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File imageFile = null;
        try {
            imageFile = File.createTempFile(imageFileName, ".jpg", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageFile;
    }

    private Uri getImageUri(File imageFile) {
        if (imageFile == null) {
            return null;
        }
        Uri imageUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            imageUri = FileProvider.getUriForFile(this, "com.taylor.fileProvider", imageFile);
        } else {
            imageUri = Uri.fromFile(imageFile);
        }
        return imageUri;
    }

    public void verifyStoragePermission(AppCompatActivity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        } else {
            takePictureAndStore();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_TAKE_PICTURE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePictureAndStore();
                } else {
                }
            }
        }
    }
}
