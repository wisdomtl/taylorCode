package test.taylor.com.taylorcode.list;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.LinkedList;

import test.taylor.com.taylorcode.R;
import test.taylor.com.taylorcode.util.CollectionUtil;

public class ListActivity extends Activity {

    private LinkedList<Integer> linkedLists = new LinkedList<>();
    private LinkedList<Integer> list = new LinkedList<>();
    private int count = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.list_activity);

        findViewById(R.id.btn_add_tail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int evict = addToTail();
                Log.v("ttaylor", "ListActivity.onClick()" + "  evict="+evict);
            }
        });

        //LinkedList case1:remove list header and list tail
        initLinkedList();
        removeLinkedListHeader();
        removeLinkedListTail();
        finalList();
    }

    private int addToTail() {
        list.add(count++);
        if(list.size() > 5){
            return  list.removeFirst();
        }
        return -100;
    }

    private void removeLinkedListTail() {
        linkedLists.removeFirst();
        for (int i :
                linkedLists) {

            Log.v("ttaylor", "ListActivity.removeLinkedListTail() after remove first" + "  i = " + i);
        }
        linkedLists.removeLast();
        for (int i :
                linkedLists) {
            Log.v("ttaylor", "ListActivity.removeLinkedListTail() after remove last" + "  i = " + i);
        }
    }

    private void removeLinkedListHeader() {
    }

    private void initLinkedList() {
        linkedLists.add(1);
        linkedLists.add(2);
        linkedLists.add(3);
        linkedLists.add(4);
        linkedLists.add(5);
        linkedLists.add(6);
    }

    private void finalList() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("" + i);
        }
        exec(new Runnable() {
            @Override
            public void run() {
                printList(list);
            }
        });
    }

    private void exec(final Runnable run) {
        new Thread(run).start();
    }

    private void printList(ArrayList<String> arrayList) {
        for (String str : arrayList) {
            Log.v("ttaylor", "ListActivity.printList()" + "  str=" + str);
        }
    }
}
