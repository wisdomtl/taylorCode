package test.taylor.com.taylorcode.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;
import java.util.Set;

import test.taylor.com.taylorcode.R;

/**
 * Created by taylor on 2017/11/16.
 */

public class TagViewActivity extends Activity {
    public static final String[] tags = new String[]{"dfdsfsda", "dffsdfsdfs", "fdfsdfdserweresfsdfsdfdsfsdf", "fdfewrewfwefwefwf"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tag_view_activity);
        //case1:multiple-select TagView
        final TagFlowLayout tfl = (TagFlowLayout)findViewById(R.id.id_flowlayout);
        MyTagAdapter adapter = new MyTagAdapter(tags , tfl) ;
        tfl.setAdapter(adapter);
        tfl.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Toast.makeText(TagViewActivity.this,tags[position],Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        //case2:set select-listener
        tfl.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                Log.v("ttaylor", "TagViewActivity.onSelected(): selectedItems="+selectPosSet);
            }
        });
    }

    private class MyTagAdapter extends TagAdapter<String>{

        private ViewGroup tagContainer ;

        public MyTagAdapter(List<String> datas,ViewGroup tagContainer) {
            super(datas);
            this.tagContainer = tagContainer ;
        }

        public MyTagAdapter(String[] datas,ViewGroup tagContainer) {
            super(datas);
            this.tagContainer = tagContainer ;
        }

        @Override
        public View getView(FlowLayout parent, int position, String s) {
            TextView tv = (TextView) LayoutInflater.from(TagViewActivity.this).inflate(R.layout.tv, tagContainer, false);
            tv.setText(s);
            return tv;
        }
    }
}
