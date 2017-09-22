package test.taylor.com.taylorcode.big_data;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * Created on 17/9/22.
 */

public class BigStringActivity extends Activity implements View.OnClickListener {

    private static final String PREFIX = "1869";
    private static final String NUMBER = "8613501826147";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button btn = new Button(this);
        btn.setOnClickListener(this);
        setContentView(btn);
        Log.v("ttangliang ttbigString", "BigStringActivity.onCreate() " + " ");
    }

    @Override
    public void onClick(View v) {
        long startTime = System.currentTimeMillis() ;
        try {
            //case1:read large data from assets
            InputStream is = getAssets().open("big-data.txt");
            int length = is.available();
            byte[] buffer = new byte[length];
            is.read(buffer);
            String result = new String(buffer, "utf8");
            JSONObject bigJSON = new JSONObject(result);
            String prefix = containsPrefix(bigJSON, NUMBER);
            Log.v("ttangliang ttbigString", "BigStringActivity.onClick() " + " contain-prefix=" + prefix);
            if (prefix != null) {
                boolean containLongPrefix = containsLongPrefix(bigJSON.getJSONArray(prefix), NUMBER);
                Log.v("ttangliang ttbigString", "BigStringActivity.onClick() " + " contain-long-prexif=" + containLongPrefix);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis() ;
        Log.e("ttangliang  ttbigString" , "BigStringActivity.onClick() "+ " duration="+(endTime-startTime)) ;
    }

    private String containsPrefix(JSONObject bigJSON, String number) {
        Iterator iterator = bigJSON.keys();
        while (iterator.hasNext()) {
            String key = ((String) iterator.next());
            Log.v("ttangliang ttbigString", "BigStringActivity.containsPrefix() " + " key=" + key);
            if (number.startsWith(key)) {
                return key;
            }
        }
        return null;
    }

    private boolean containsLongPrefix(JSONArray array, String number) {
        for (int i = 0; i < array.length(); i++) {
            try {
                String longPrefix = array.get(i).toString();
                Log.v("ttangliang ttbigString", "BigStringActivity.containsLongPrefix() " + " long-prefix=" + longPrefix);
                if (number.startsWith(longPrefix)) {
                    return true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
