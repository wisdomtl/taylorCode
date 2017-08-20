package test.taylor.com.taylorcode.audio;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.IOException;

/**
 * Created on 17/8/20.
 */

public class SoundPoolActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button btnPlay = new Button(this);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(100, 50);
        btnPlay.setLayoutParams(layoutParams);
        btnPlay.setText("Click to play sound");
        setContentView(btnPlay);
        btnPlay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //case1:play short sound by SoundPool
        SoundPool soundPool = new SoundPool(1, AudioManager.STREAM_VOICE_CALL,0);
        AssetFileDescriptor soundPath = null;
        try {
            soundPath = getAssets().openFd("beep.wav");
        } catch (IOException e) {
            e.printStackTrace();
        }
        final int soundId = soundPool.load(soundPath, 1);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPool.play(soundId, 1, 1, 0, 0, 1);
            }
        });
    }
}
