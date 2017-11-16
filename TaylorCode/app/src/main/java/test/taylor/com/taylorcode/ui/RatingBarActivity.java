package test.taylor.com.taylorcode.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RatingBar;

import test.taylor.com.taylorcode.R;

/**
 * Created by taylor on 2017/11/13.
 */

public class RatingBarActivity extends Activity {
    @Override
    protected void onCreate(@org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating_activity);
        com.hedgehog.ratingbar.RatingBar ratingBar = ((com.hedgehog.ratingbar.RatingBar) findViewById(R.id.rating_bar)) ;
        ratingBar.setOnRatingChangeListener(new com.hedgehog.ratingbar.RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float RatingCount) {
                Log.v("ttaylor", "RatingBarActivity.onRatingChange(): rating="+RatingCount);
            }
        });
    }
}
