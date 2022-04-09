package test.taylor.com.taylorcode.architecture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import test.taylor.com.taylorcode.kotlin.*

class TrolleyFragment : Fragment() {

    private val myViewModel by lazy { ViewModelProvider(requireActivity()).get(MyViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ConstraintLayout {
            layout_id = "dfsdfsdf"
            layout_width = match_parent
            layout_height = match_parent
            background_color = "#ff00ff"
            onClick = {
                myViewModel.setSelectsList(listOf("meet","water"))
            }

            TextView {
                layout_id = "balance"
                layout_width = wrap_content
                layout_height = wrap_content
                text = "balance"
                gravity = gravity_center
                onClick = {
                    parentFragmentManager.beginTransaction()
                        .replace("container".toLayoutId(), BalanceFragment())
                        .addToBackStack("trolley")
                        .commit()
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myViewModel.selectsListLiveData.asTransient().observe(viewLifecycleOwner) { goods ->
            goods.takeIf { it.size >= 2 }?.let {
                Toast.makeText(context,"full",Toast.LENGTH_LONG).show()
            }
        }
    }
}