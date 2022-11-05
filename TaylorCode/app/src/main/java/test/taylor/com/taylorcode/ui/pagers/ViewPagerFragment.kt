package test.taylor.com.taylorcode.ui.pagers

import android.icu.text.CaseMap.Lower
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import test.taylor.com.taylorcode.architecture.flow.lifecycle.BaseFragment
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.kotlin.coroutine.flow.collectIn
import test.taylor.com.taylorcode.ui.pagers.paging.PagingAdapter
import test.taylor.com.taylorcode.ui.pagers.paging.PagingViewModel
import test.taylor.com.taylorcode.ui.pagers.paging.TextRepository


class ViewPagerFragment : BaseFragment() {

    /**
     * share ViewModel instance between fragments by [requireActivity]
     * if using with Paging3, ViewModel should use the ViewModelStoreOwner of Activity to avoid being cleared when fragment is destory
     */
    private val viewModel by lazy {
        ViewModelProvider(requireActivity(), object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return PagingViewModel(TextRepository()) as T
            }
        })[PagingViewModel::class.java]
    }

    private lateinit var rv: RecyclerView

    private val pagingAdapter by lazy { PagingAdapter() }

    private val index by lazy {
        arguments?.get("index") as Int
    }

    private val contentView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent


            rv = RecyclerView {
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
                bottom_toBottomOf = parent_id
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("ttaylor[flow.fragment.test]", "-------${this@ViewPagerFragment}.onCreate[savedInstanceState]: ")
        /**
         * case: the way observe fragment's lifecycle event
         */
        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                Log.d("ttaylor", "ViewPagerFragment.onStateChanged[source, event=$event]: ")
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return contentView.also { it?.tag = index }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("ttaylor[flow.fragment.test]", "-------${this@ViewPagerFragment}$index.onViewCreated(): ")
        pagingAdapter.addLoadStateListener {
            Log.e("ttaylor", "ViewPagerFragment$index.onViewCreated[].addLoadStateListener state=${it} ")
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.pagingData.collect {
                Log.w("ttaylor", "ViewPagerFragment$index.onViewCreated(): viewModel.pagingData.collect")
                pagingAdapter.submitData(it)
            }
            pagingAdapter.loadStateFlow.collect {
                Log.d("ttaylor", "ViewPagerFragment$index.onViewCreated[].loadStateFlow.collect state=$it ")
            }
        }

        /**
         * case: collect flow value in this way will cause fragment1 receive new value even if it is onPaused
         */
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.stateFlow.collect {
                Log.d("ttaylor[flow.fragment.test]", "${this@ViewPagerFragment}.stateFlow.collect[str=$it]: ")
            }
        }

        /**
         * case: collect flow in this way, this fragment wont receive other fragment's emitting value if it is invisible
         */
        viewModel.stateFlow.collectIn(viewLifecycleOwner, minActiveState = Lifecycle.State.RESUMED) {
            Log.d("ttaylor", "${this@ViewPagerFragment}.onViewCreated[].collectIn str=$it ")
        }

    }

    override fun onPause() {
        super.onPause()
        Log.i("ttaylor[flow.fragment.test]", "-------${this@ViewPagerFragment}$index.onPause[]: ")
    }

    override fun onStop() {
        super.onStop()
        Log.i("ttaylor[flow.fragment.test]", "-------${this@ViewPagerFragment}$index.onStop[]: ")
    }

    override fun onStart() {
        super.onStart()
        Log.i("ttaylor[flow.fragment.test]", "-------${this@ViewPagerFragment}$index.onStart[]: ")
        /**
         * emit value in onStart(), collectIn(viewLifecycleOwner, minActiveState = Lifecycle.State.STARTED) wont receive value
         * emitting should be onResume() at least
         */
//        viewModel.send("hello world$index")
    }

    override fun onResume() {
        super.onResume()
        Log.i("ttaylor[flow.fragment.test]", "-------${this@ViewPagerFragment}$index.onResume[]: ")
        // ensure emit value will happened after onResume() done
        view?.post {
            viewModel.send("hello world$index")
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.i("ttaylor[flow.fragment.test]", "-------${this@ViewPagerFragment}$index.onDestroy[]: ")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i("ttaylor[flow.fragment.test]", "-------${this@ViewPagerFragment}$index.onDestroyView[]: ")
    }
}
