package test.taylor.com.taylorcode.ui.navigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import test.taylor.com.taylorcode.R;

public class NavigationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_activity);
    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.nav_host);
//        return NavHostFragment.findNavController(fragment).navigateUp();
//    }


}
