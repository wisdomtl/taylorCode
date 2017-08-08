package test.taylor.com.taylorcode.reflection;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 17/7/30.
 */

public class ClassA {
    /**
     * static map value to be tampered
     */
    public static final String KEY = "key";
    private static Map<String, String> staticMap = new HashMap();

    private Map<String, String> map = new HashMap<>();

    public void onCreate() {
        //case2: class static value
        staticMap.put(KEY, "origin static map value");
        Log.v("taylor ttReflection2", "ClassA.onCreate() " + " staticMap value=" + staticMap.get(KEY));
        //case3: class non-static value
        map.put(KEY, "origin non-static map value");
        Log.v("taylor ttReflection3", "ClassA.onCreate() " + " non-static map value=" + map.get(KEY));
    }

    public static String getStaticMapValue() {
        if (staticMap != null) {
            return staticMap.get(KEY);
        }
        return "staticMap is empty";
    }

    public String getMapValue() {
        if (map != null) {
            return map.get(KEY);
        }
        return "map is empty";
    }
}
