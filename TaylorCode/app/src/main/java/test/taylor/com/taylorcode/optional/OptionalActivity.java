package test.taylor.com.taylorcode.optional;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Optional;

public class OptionalActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bean bean1 = null;
        Bean bean2 = new Bean();
        Count count = new Count() ;
        count.setVal(2);
        getCount(bean2);
        Log.v("ttaylor", "OptionalActivity.onCreate()" + "  count=" + getCount(bean1));
    }


    /**
     * Optional case1:use Optional as method return value
     * @param bean
     * @return
     */
    public int getCount(Bean bean) {
        //if bean is null it will return 100
        //if Bean.count is null it will also return 100
        return Optional.ofNullable(bean)
                .map(bean1 -> bean1.getCount())
                .map(count -> count.getVal())
                .orElse(100);
    }
}
