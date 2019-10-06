package test.taylor.com.taylorcode.list;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;

import java.util.LinkedList;

public class ListActivity extends Activity {

    private LinkedList<Integer> linkedLists = new LinkedList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //LinkedList case1:remove list header and list tail
        initLinkedList();
        removeLinkedListHeader();
        removeLinkedListTail();

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
}
