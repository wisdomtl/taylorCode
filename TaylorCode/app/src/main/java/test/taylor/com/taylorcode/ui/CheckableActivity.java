package test.taylor.com.taylorcode.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import test.taylor.com.taylorcode.R;

/**
 * Created on 17/11/3.
 */

public class CheckableActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkable_activity);
        String[] numbers = new String[]{"2132132" ,"312312222221" ,"2312423423"} ;
        ((ListView) findViewById(R.id.lv_checkable)).setAdapter(new NumberAdapter(this, Arrays.asList(numbers)));
    }



    private class NumberAdapter extends BaseAdapter {

        private List<String> numbers;
        private Context context;

        public NumberAdapter(Context context, List<String> numbers) {
            this.numbers = numbers;
            this.context = context;
        }

        @Override
        public int getCount() {
            return numbers.size();
        }

        @Override
        public Object getItem(int position) {
            return numbers.get(position);
        }

        @Override
        public long getItemId(int position) {
            return numbers.get(position).hashCode();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View numberView = LayoutInflater.from(context).inflate(R.layout.checkable_item, null);
            ((TextView) numberView.findViewById(R.id.cootek_pre_call_number)).setText(numbers.get(position));
            return numberView;
        }
    }
}
