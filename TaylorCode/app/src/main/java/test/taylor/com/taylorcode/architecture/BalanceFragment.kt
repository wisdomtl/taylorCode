package test.taylor.com.taylorcode.architecture

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import test.taylor.com.taylorcode.kotlin.*

class BalanceFragment:Fragment() {

    private val myViewModel by lazy { ViewModelProvider(requireActivity()).get(MyViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent
            background_color = "#ffff00"
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myViewModel.selectsListLiveData.observe(viewLifecycleOwner) {
            Log.v("ttaylor","onViewCreated() size=${it.size}")
        }
    }
}