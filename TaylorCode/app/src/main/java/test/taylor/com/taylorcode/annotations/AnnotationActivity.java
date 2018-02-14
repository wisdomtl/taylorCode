package test.taylor.com.taylorcode.annotations;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created on 2018/2/14.
 */

public class AnnotationActivity extends Activity {

    //1.define annotation server as enum,this use less memory than enum
    @IntDef({Job.SAMAN, Job.HUNTER, Job.MAGIC})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Job {
        int SAMAN = 1;
        int HUNTER = 2;
        int MAGIC = 3;
    }

    //2.describe variable by annotation
    @Job
    int job ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //work like an enum
        job = Job.HUNTER ;
        switch (job) {
            case Job.HUNTER:
                break;
            case Job.MAGIC:
                break;
            case Job.SAMAN:
                break;
        }
    }
}
