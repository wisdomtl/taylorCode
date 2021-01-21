package test.taylor.com.taylorcode.ui.navigation;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//import androidx.navigation.Navigation;
import test.taylor.com.taylorcode.R;


public class NavFragment2 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nav_fragment2,null);
        initView(view);
        return view ;
    }

    private void initView(View view) {
        view.findViewById(R.id.btn2).setOnClickListener(v->{
//            Navigation.findNavController(view).navigateUp() ;
        });

    }
}
