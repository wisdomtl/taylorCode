package test.taylor.com.taylorcode.ui.pagers

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.ui.pagers.paging.PagingAdapter
import test.taylor.com.taylorcode.ui.pagers.paging.PagingViewModel
import test.taylor.com.taylorcode.ui.pagers.paging.TextRepository


class ViewPagerFragment : Fragment() {

    private val viewModel by lazy { ViewModelProvider(this,object :ViewModelProvider.Factory{
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PagingViewModel(TextRepository()) as T
        }
    })[PagingViewModel::class.java] }

    private lateinit var rv:RecyclerView

    private val pagingAdapter by lazy { PagingAdapter() }

    private val contentView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent


            rv= RecyclerView {
                layout_id = "rv"
                layout_width = match_parent
                layout_height = wrap_content //if it is 0, then recyclerView with paging3 will blink
                adapter = pagingAdapter
                top_toTopOf = parent_id
                bottom_toTopOf = "tvChange"
                layoutManager = LinearLayoutManager(context)
            }

            TextView {
                layout_id = "tvChange"
                layout_width = match_parent
                layout_height = wrap_content
                textSize = 15f
                textColor = "#00ff00"
                gravity = gravity_center
                top_toBottomOf = "rv"
                bottom_toBottomOf= parent_id
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return contentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("ttaylor", "ViewPagerFragment.onViewCreated(): ")
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.pagingData.collect {
                Log.d("ttaylor", "ViewPagerFragment.onViewCreated(): viewModel.pagingData.collect")
                pagingAdapter.submitData(it)
            }
        }
    }
}
