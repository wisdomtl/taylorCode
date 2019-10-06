//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package pl.droidsonroids.gif;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;
import android.widget.MediaController.MediaPlayerControl;

import java.io.IOException;


public class GifDrawable extends Drawable implements Animatable, MediaPlayerControl {

    public GifDrawable(@NonNull Resources res, @RawRes @DrawableRes int id) throws NotFoundException, IOException {
//        this(res.openRawResourceFd(id));
//        float densityScale = GifViewUtils.getDensityScale(res, id);
//        this.mScaledHeight = (int)((float)this.mNativeInfoHandle.getHeight() * densityScale);
//        this.mScaledWidth = (int)((float)this.mNativeInfoHandle.getWidth() * densityScale);
    }

    public GifDrawable(@Nullable ContentResolver resolver, @NonNull Uri uri) throws IOException {
//        this(GifInfoHandle.openUri(resolver, uri), (GifDrawable)null, (ScheduledThreadPoolExecutor)null, true);
    }


    @Override
    public void start() {

    }

    @Override
    public void pause() {

    }

    @Override
    public int getDuration() {
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        return 0;
    }

    @Override
    public void seekTo(int pos) {

    }

    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return false;
    }

    @Override
    public boolean canSeekBackward() {
        return false;
    }

    @Override
    public boolean canSeekForward() {
        return false;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {

    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }
}
