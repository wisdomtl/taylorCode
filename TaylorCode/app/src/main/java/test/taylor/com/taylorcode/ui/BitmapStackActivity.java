package test.taylor.com.taylorcode.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import test.taylor.com.taylorcode.R;
import test.taylor.com.taylorcode.util.BitmapUtil;

public class BitmapStackActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bitmap_stack_activity);
        Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.watch_reward_1).copy(Bitmap.Config.ARGB_8888, true);
        Bitmap foreground = BitmapFactory.decodeResource(getResources(), R.drawable.stop).copy(Bitmap.Config.ARGB_8888, true);
        Bitmap bitmapStack = BitmapUtil.combineBitmap(background, foreground);
        ((ImageView) findViewById(R.id.iv_bitmap_stack)).setImageBitmap(bitmapStack);
    }
}
