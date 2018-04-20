package test.taylor.com.taylorcode.util;

import android.content.Context;

public class ResUtil {
    public static final String LAYOUT = "layout";

    public static final String ID = "id";

    public static final String DRAWABLE = "drawable";

    public static final String STRING = "string";

    public static final String COLOR = "color";

    public static final String STYLE = "style";

    public static final String ARRAY = "array";

    public static final String DIMEN = "dimen";

    public static final String RAW = "raw";

    public static final String ANIM = "anim";

    public static int getResourceId(Context context, String name, String type) {
        return context.getResources().getIdentifier(name,
                type,
                context.getPackageName());
    }

    public static int getStringId(Context context, String name) {
        return getResourceId(context,
                name,
                ResUtil.STRING);
    }

    public static int getTypeId(Context context, String name) {
        return getResourceId(context,
                name,
                ResUtil.ID);
    }

    public static int getColorId(Context context, String name) {
        return getResourceId(context,
                name,
                ResUtil.COLOR);
    }

    public static int getDrawableId(Context context, String name) {
        return getResourceId(context,
                name,
                ResUtil.DRAWABLE);
    }

    public static int getLayoutId(Context context, String name) {
        return getResourceId(context,
                name,
                ResUtil.LAYOUT);
    }

    public static int getDimenId(Context context, String name) {
        return getResourceId(context,
                name,
                ResUtil.DIMEN);
    }

    public static int getStyleId(Context context, String name) {
        return getResourceId(context,
                name,
                ResUtil.STYLE);
    }

    public static int getRawId(Context context, String name) {
        return getResourceId(context,
                name,
                ResUtil.RAW);
    }

    public static int getAnimId(Context context, String name) {
        return getResourceId(context,
                name,
                ResUtil.ANIM);
    }

    public static String getString(Context context, String name) {
        int id = getResourceId(context,
                name,
                ResUtil.STRING);
        return context.getString(id);
    }
}