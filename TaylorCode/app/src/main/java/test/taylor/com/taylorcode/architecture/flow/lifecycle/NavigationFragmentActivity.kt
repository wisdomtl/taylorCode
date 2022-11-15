package test.taylor.com.taylorcode.architecture.flow.lifecycle

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import test.taylor.com.taylorcode.R
import test.taylor.com.taylorcode.activitystack.Param
import test.taylor.com.taylorcode.activitystack.getParam
import test.taylor.com.taylorcode.kotlin.ConstraintLayout
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.startActivity
import test.taylor.com.taylorcode.ui.material_design.nested.NestedScrollViewActivity

class NavigationFragmentActivity : AppCompatActivity(), Param {

    private val contentView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent

            TextView {
                layout_id = "tvChange"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 20f
                textColor = "#ff00ff"
                text = "go hint"
                fontFamily = R.font.pingfang
                start_toStartOf = parent_id
                end_toStartOf = "tvChange2"
                top_toTopOf = parent_id
                gravity = gravity_center
                onClick = {
                    runCatching {
                        findNavController("navHost".toLayoutId()).navigate(R.id.action_to_hint)
                    }
                }
            }

            TextView {
                layout_id = "tvChange2"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 20f
                textColor = "#ff00ff"
                text = "go result"
                fontFamily = R.font.pingfang
                start_toEndOf = "tvChange"
                top_toTopOf = parent_id
                end_toStartOf = "tvChange3"
                gravity = gravity_center
                onClick = {
                    runCatching {
                        findNavController("navHost".toLayoutId()).navigate(R.id.action_to_result)
                    }
                }
            }

            TextView {
                layout_id = "tvChange3"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 20f
                textColor = "#ff00ff"
                text = "go result"
                fontFamily = R.font.pingfang
                start_toEndOf = "tvChange2"
                top_toTopOf = parent_id
                end_toStartOf = "tvChange4"
                gravity = gravity_center
                onClick = {
                    runCatching {
                        findNavController("navHost".toLayoutId()).popBackStack()
                    }
                }
            }

            TextView {
                layout_id = "tvChange4"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 20f
                textColor = "#ff00ff"
                text = "new activity"
                fontFamily = R.font.pingfang
                start_toEndOf = "tvChange3"
                top_toTopOf = parent_id
                end_toEndOf = parent_id
                gravity = gravity_center
                onClick = {
                    startActivity<NestedScrollViewActivity>()
                }
            }

            FragmentContainerView {
                layout_id = "navHost"
                layout_width = match_parent
                layout_height = 0
                top_toBottomOf = "tvChange"
                bottom_toBottomOf = parent_id
                margin_top = 20

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
        Log.d("ttaylor", "NavigationFragmentActivity.onCreate[savedInstanceState]: type=${getParam<Int>("type")}, tabName=${getParam<String>("tabName")}")

    }

    override val paramMap: Map<String, Any>
        get() = mapOf(
            "type" to 1,
            "tabName" to "ddd",
            "map" to mapOf<String, Int>(),
            "type2" to 2,
        )
}