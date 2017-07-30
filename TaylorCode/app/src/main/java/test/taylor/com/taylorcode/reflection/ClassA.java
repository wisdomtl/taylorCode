package test.taylor.com.taylorcode.reflection;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 17/7/30.
 */

public class ClassA {
    public static final String KEY = "key";
    private static Map<String, String> map = new HashMap();

    public void onCreate() {
        map.put(KEY, "origin value");
        Log.v("taylor ttreflection2" , "ClassA.onCreate() "+ " map value="+map.get(KEY)) ;
    }

    public static String getMapValue() {
        if (map != null) {
            return map.get(KEY);
        }
        return "map is empty" ;
    }
}
