package test.taylor.com.taylorcode.ui.recyclerview.select

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import test.taylor.com.taylorcode.R
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.ui.custom_view.selector.kt.SelectorGroup
import test.taylor.com.taylorcode.ui.recyclerview.variety.VarietyAdapter
import test.taylor.com.taylorcode.util.print
import java.util.*

class SelectRecycleViewActivity: AppCompatActivity() {

    private val singleSelectorGroup = SelectorGroup().apply {
        choiceMode = SelectorGroup.MODE_SINGLE
        selectChangeListener = {selectors->
            Log.v("ttaylor","tag=asdf, SelectRecycleViewActivity.()  selectors=${selectors.print { it.tag }}")
        }
    }

    private val multiSelectorGroup = SelectorGroup().apply {
        choiceMode = SelectorGroup.MODE_MULTIPLE
        selectChangeListener = {selectors->
            Log.v("ttaylor","tag=asdf, SelectRecycleViewActivity.()  selectors=${selectors.print { it.tag }}")
        }
    }

    private val selectorAdapter = VarietyAdapter().apply {
        addProxy(SelectAdapterProxy())
//
//        datas = listOf(
//            SelectionBeanWrapper(SelectionBean(UUID.randomUUID().toString()),singleSelectorGroup,false),
//            SelectionBeanWrapper(SelectionBean(UUID.randomUUID().toString()),singleSelectorGroup,false),
//            SelectionBeanWrapper(SelectionBean(UUID.randomUUID().toString()),singleSelectorGroup,false),
//            SelectionBeanWrapper(SelectionBean(UUID.randomUUID().toString()),singleSelectorGroup,false),
//            SelectionBeanWrapper(SelectionBean(UUID.randomUUID().toString()),singleSelectorGroup,false),
//            SelectionBeanWrapper(SelectionBean(UUID.randomUUID().toString()),singleSelectorGroup,false),
//            SelectionBeanWrapper(SelectionBean(UUID.randomUUID().toString()),singleSelectorGroup,false),
//            SelectionBeanWrapper(SelectionBean(UUID.randomUUID().toString()),singleSelectorGroup,false)
//        )

         dataList = mutableListOf(
            SelectionBeanWrapper(SelectionBean(UUID.randomUUID().toString()),multiSelectorGroup,false),
            SelectionBeanWrapper(SelectionBean(UUID.randomUUID().toString()),multiSelectorGroup,false),
            SelectionBeanWrapper(SelectionBean(UUID.randomUUID().toString()),multiSelectorGroup,false),
            SelectionBeanWrapper(SelectionBean(UUID.randomUUID().toString()),multiSelectorGroup,false),
            SelectionBeanWrapper(SelectionBean(UUID.randomUUID().toString()),multiSelectorGroup,false),
            SelectionBeanWrapper(SelectionBean(UUID.randomUUID().toString()),multiSelectorGroup,false),
            SelectionBeanWrapper(SelectionBean(UUID.randomUUID().toString()),multiSelectorGroup,false),
            SelectionBeanWrapper(SelectionBean(UUID.randomUUID().toString()),multiSelectorGroup,false)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent

            RecyclerView {
                layout_width = match_parent
                layout_height = wrap_content
                adapter = selectorAdapter
                top_toTopOf = parent_id
                bottom_toTopOf = "tvDelete"
                layoutManager = LinearLayoutManager(this@SelectRecycleViewActivity)
            }

            TextView {
                layout_id = "tvDelete"
                layout_width = wrap_content
                layout_height = wrap_content
                background_color = "#ff00ff"
                text = "delete"
                textSize = 15f
                center_horizontal = true
                bottom_toBottomOf = parent_id
                onClick = {
                    selectorAdapter.dataList.forEach { selectBean ->
                        if (selectBean is SelectionBeanWrapper){
                            selectBean.showSelector = !selectBean.showSelector
                        }
                    }
                    selectorAdapter.notifyDataSetChanged()
                }
            }

            ImageView {
                layout_id = "ivCircle"
                layout_width = 40
                layout_height = 40
                src = R.drawable.diamond_tag
                scaleType = scale_fit_xy
                toCircleOf = "tvDelete"
                circle_angle = 45f
                circle_radius = 30
            }
        })
        selectorAdapter.notifyDataSetChanged()
    }
}