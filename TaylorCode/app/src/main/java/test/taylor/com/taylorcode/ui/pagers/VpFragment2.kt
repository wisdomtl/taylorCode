package test.taylor.com.taylorcode.ui.pagers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import test.taylor.com.taylorcode.R

class VpFragment2:Fragment() {

    companion object{
        fun newInstance() = VpFragment2()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.vp2_f2, container)
    }
}