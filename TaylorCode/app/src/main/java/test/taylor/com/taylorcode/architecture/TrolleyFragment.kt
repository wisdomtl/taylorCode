package test.taylor.com.taylorcode.architecture

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.util.print

class TrolleyFragment : Fragment() {

    private val myViewModel by lazy { ViewModelProvider(requireActivity())[MyViewModel::class.java] }

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myViewModel.setSelectsList(listOf("food_meet", "food_water", "book_1"))
        myViewModel.setSelectsList2(listOf("food_meet", "food_water", "book_1"))
    }

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.v("ttaylor", "onViewCreated()---------")
        myViewModel.selectsListLiveData.observe(viewLifecycleOwner) {
            Log.v("ttaylor", "onChanged() invoked======")
        }

        myViewModel.foodListLiveData.observe(viewLifecycleOwner) {
            Log.v("ttaylor", "onViewCreated() food=${it.print { it }}")
        }

        myViewModel.asyncLiveData.observe(viewLifecycleOwner) {
            Log.v("ttaylor", "onViewCreated() async food=${it.print { it }}")
        }

        myViewModel.singleListLiveData.observe(viewLifecycleOwner) { goods ->
            goods.takeIf { it.size >= 2 }?.let {
                Log.v("ttaylor", "singleListLiveData")
            }
        }

        lifecycleScope.launch {
            myViewModel.selectsListFlow.collect { goods ->
                goods.takeIf { it.size >= 2 }?.let {
                    Log.v("ttaylor", "selectsListFlow")
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                myViewModel.hotFlow.collect{
                    Log.v("ttaylor","collect hot flow num=${it}")
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            myViewModel.oneShotStateFlow.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect{
                Log.v("ttaylor6","one shot collect =${it}")
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            myViewModel.oneShotSharedFlow.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect{
                Log.v("ttaylor7","one shot collect=$it")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}