package test.taylor.com.taylorcode.ui.navigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.navigation.Navigation;
import test.taylor.com.taylorcode.R;

public class NavFragment1 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nav_fragment1,null);
        initView(view);
        return view ;
    }

    private void initView(View view) {
        view.findViewById(R.id.btn1).setOnClickListener(v->{
            Navigation.findNavController(view).navigate(R.id.action_to_f2);
        });
    }
}
