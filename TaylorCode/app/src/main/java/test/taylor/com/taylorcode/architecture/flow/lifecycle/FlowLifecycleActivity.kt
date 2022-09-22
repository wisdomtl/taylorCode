package test.taylor.com.taylorcode.architecture.flow.lifecycle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import test.taylor.com.taylorcode.R
import test.taylor.com.taylorcode.kotlin.ConstraintLayout
import test.taylor.com.taylorcode.kotlin.*

class FlowLifecycleActivity:AppCompatActivity() {

    private val contentView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent

            FragmentContainerView {
                layout_id = "navHost"
                layout_width = match_parent
                layout_height = match_parent

                NavHostFragment.create(R.navigation.search_navigation).also {
                    supportFragmentManager.beginTransaction()
                        .replace(id, it)
                        .setPrimaryNavigationFragment(it)
                        .commit()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)
    }
}