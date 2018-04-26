package pl.droidsonroids.gif;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.RawRes;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class GifViewUtils {
    static final String ANDROID_NS = "http://schemas.android.com/apk/res/android";
    static final List<String> SUPPORTED_RESOURCE_TYPE_NAMES = Arrays.asList("raw", "drawable", "mipmap");

    private GifViewUtils() {
    }

    static GifViewUtils.InitResult initImageView(ImageView view, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        if (attrs != null && !view.isInEditMode()) {
            int sourceResId = getResourceId(view, attrs, true);
            int backgroundResId = getResourceId(view, attrs, false);
            boolean freezesAnimation = isFreezingAnimation(view, attrs, defStyleAttr, defStyleRes);
            return new GifViewUtils.InitResult(sourceResId, backgroundResId, freezesAnimation);
        } else {
            return new GifViewUtils.InitResult(0, 0, false);
        }
    }

    private static int getResourceId(ImageView view, AttributeSet attrs, boolean isSrc) {
        int resId = attrs.getAttributeResourceValue("http://schemas.android.com/apk/res/android", isSrc ? "src" : "background", 0);
        if (resId > 0) {
            String resourceTypeName = view.getResources().getResourceTypeName(resId);
            if (SUPPORTED_RESOURCE_TYPE_NAMES.contains(resourceTypeName) && !setResource(view, isSrc, resId)) {
                return resId;
            }
        }

        return 0;
    }

    static boolean setResource(ImageView view, boolean isSrc, int resId) {
        Resources res = view.getResources();
        if (res != null) {
            try {
                GifDrawable d = new GifDrawable(res, resId);
                if (isSrc) {
                    view.setImageDrawable(d);
                } else if (Build.VERSION.SDK_INT >= 16) {
                    view.setBackground(d);
                } else {
                    view.setBackgroundDrawable(d);
                }

                return true;
            } catch (Resources.NotFoundException | IOException var5) {
                ;
            }
        }

        return false;
    }

    static boolean isFreezingAnimation(View view, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        TypedArray gifViewAttributes = view.getContext().obtainStyledAttributes(attrs, style, defStyleAttr, defStyleRes);
//        boolean freezesAnimation = gifViewAttributes.getBoolean(R.styleable.GifView_freezesAnimation, false);
//        gifViewAttributes.recycle();
//        return freezesAnimation;
        /**
         * class-tamper case1:
         * 1.use "jar xf xxx.jar" to unzip .jar file into .class file
         * 2.drag A.class into AndroidStudio
         * 3.create a new module and a new java file(A.java)
         * 4.copy A.class into A.java
         * 5.modify source code,then build this module,the .class of this .java will be made in build/intermediates/classes
         * 6.cover the origin .class with the new class
         * 7.use "jar cvf xxxx.jar" to zip a new jar
         * 8.use the new jar
         */
        return false ;
    }

    static boolean setGifImageUri(ImageView imageView, Uri uri) {
        if (uri != null) {
            try {
                imageView.setImageDrawable(new GifDrawable(imageView.getContext().getContentResolver(), uri));
                return true;
            } catch (IOException var3) {
                ;
            }
        }

        return false;
    }

    static float getDensityScale(@NonNull Resources res, @DrawableRes @RawRes int id) {
        TypedValue value = new TypedValue();
        res.getValue(id, value, true);
        int resourceDensity = value.density;
        int density;
        if (resourceDensity == 0) {
            density = 160;
        } else if (resourceDensity != 65535) {
            density = resourceDensity;
        } else {
            density = 0;
        }

        int targetDensity = res.getDisplayMetrics().densityDpi;
        return density > 0 && targetDensity > 0 ? (float)targetDensity / (float)density : 1.0F;
    }

    static class InitResult {
        final int mSourceResId;
        final int mBackgroundResId;
        final boolean mFreezesAnimation;

        InitResult(int sourceResId, int backgroundResId, boolean freezesAnimation) {
            this.mSourceResId = sourceResId;
            this.mBackgroundResId = backgroundResId;
            this.mFreezesAnimation = freezesAnimation;
        }
    }
}
