package test.taylor.com.taylorcode.reflection;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 17/7/30.
 */
public class ReflectionActivity extends Activity {

    public static final String KEY = "map";
    private static Map map = new HashMap();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //case1
        map.put(KEY, "origin value");
        reflectSameClassValue();
        Log.v("taylor ttReflection1", "ReflectionActivity.onCreate() " + " map value=" + map.get(KEY));
        //case2
        reflectSamePackageStaticValue();
        Log.v("taylor ttReflection2", "ReflectionActivity.onCreate() " + " map value=" + ClassA.getStaticMapValue());
        //case3
        ClassA classA = reflectSamePackageValue();
        Log.v("taylor ttReflection3", "ReflectionActivity.onCreate() " + " map value=" + classA.getMapValue());
    }

    /**
     * case1:tamper value in this class by reflection
     */
    private void reflectSameClassValue() {
        try {
            Class reflectionActivity = Class.forName("test.taylor.com.taylorcode.reflection.ReflectionActivity");
            Field mapField = reflectionActivity.getDeclaredField("map");
            mapField.setAccessible(true);
            Map map = ((Map) mapField.get(null));
            map.put(KEY, "tampered by the same class");
            Log.v("taylor ttReflection1", "ReflectionActivity.reflectSameClassValue() " + " map=" + map.get(KEY));
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("taylor  ttReflection1", "ReflectionActivity.reflectSameClassValue() " + " reflect failed");
        }
    }

    /**
     * case2:tamper static value of class which is in the same package by reflection
     */
    private void reflectSamePackageStaticValue() {
        //init ClassA
        new ClassA().onCreate();
        //reflect ClassA
        try {
            Class classA = Class.forName("test.taylor.com.taylorcode.reflection.ClassA");
            Field mapField = classA.getDeclaredField("staticMap");
            mapField.setAccessible(true);
            Map map = ((Map) mapField.get(null));
            map.put(ClassA.KEY, "tampered static value by the same package");
            Log.v("taylor ttReflection2", "ReflectionActivity.reflectSamePackageStaticValue() " + " map=" + map.get(ClassA.KEY));
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("taylor  ttReflection2", "ReflectionActivity.reflectSamePackageStaticValue() " + " reflect failed");
        }
    }

    /**
     * case3:tamper non-static value of class which is in the same package by reflection
     */
    private ClassA reflectSamePackageValue() {
        //init ClassA
        ClassA classAInstance = new ClassA() ;
        classAInstance.onCreate();
        //reflect ClassA
        try {
            Class classA = Class.forName("test.taylor.com.taylorcode.reflection.ClassA");
            Field mapField = classA.getDeclaredField("map");
            mapField.setAccessible(true);
            Map map = ((Map) mapField.get(classAInstance));
            map.put(ClassA.KEY, "tampered non-static value by the same package");
            Log.v("taylor ttReflection3", "ReflectionActivity.reflectSamePackageValue() " + " map=" + map.get(ClassA.KEY));
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("taylor  ttReflection3", "ReflectionActivity.reflectSameClassValue() " + " reflect failed");
        }finally {
            return classAInstance ;
        }
    }
}
