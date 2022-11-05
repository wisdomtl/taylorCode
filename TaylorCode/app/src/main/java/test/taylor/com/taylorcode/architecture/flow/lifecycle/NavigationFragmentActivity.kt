package test.taylor.com.taylorcode.architecture.flow.lifecycle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import test.taylor.com.taylorcode.R
import test.taylor.com.taylorcode.kotlin.ConstraintLayout
import test.taylor.com.taylorcode.kotlin.*

class NavigationFragmentActivity:AppCompatActivity() {

    private val contentView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent

            TextView {
                layout_id = "tvChange"
                layout_width = wrap_content
                layout_height = wrap_content
                textSize =20f
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
                end_toEndOf = parent_id
                gravity = gravity_center
                onClick = {
                    runCatching {
                        findNavController("navHost".toLayoutId()).popBackStack()
                    }
                }
            }
            FragmentContainerView {
                layout_id = "navHost"
                layout_width = match_parent
                layout_height = 0
                top_toBottomOf = "tvChange"
                bottom_toBottomOf = parent_id
                margin_top =  20

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