//package com.bilibili.studio.search.material.fragment
//
//import android.graphics.drawable.StateListDrawable
//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.constraintlayout.widget.ConstraintLayout
//import androidx.core.content.ContextCompat
//import androidx.fragment.app.activityViewModels
//import androidx.lifecycle.lifecycleScope
//import com.bcut.collectIn
//import com.bilibili.baseui.extension.*
//import com.bilibili.studio.search.R
//import com.bilibili.studio.search.SearchViewModel
//import com.bilibili.studio.search.base.BaseSearchFragment
//import com.bilibili.studio.search.base.SearchFrom
//import com.bilibili.studio.search.base.SearchIntent
//import com.bilibili.studio.search.material.data.MaterialSearchViewState
//import com.bilibili.studio.search.material.data.MaterialTabListBeanWrapper
//import test.taylor.com.taylorcode.ui.line_feed_layout.LineFeedLayout
//import com.bilibili.widgets.dialog.BDialogHelper
//import com.bilibili.widgets.dialog.BDialogType
//import kotlinx.coroutines.launch
//
//class MaterialSearchHistoryFragment : BaseSearchFragment() {
//
//    private lateinit var tvHistory: TextView
//    private lateinit var ivDelete: ImageView
//    private lateinit var flowSearchHistory: LineFeedLayout
//    private lateinit var ivSwitch: ImageView
//
//    companion object {
//        const val HISTORY_FOLDED_MAX_LINES = 2
//    }
//
//    private val contentView by lazy(LazyThreadSafetyMode.NONE) {
//        ConstraintLayout {
//            layout_width = match_parent
//            layout_height = match_parent
//
//            tvHistory = BTextView {
//                layout_id = "tvHistory"
//                layout_width = wrap_content
//                layout_height = wrap_content
//                textSize = 16f
//                textColor = "#24252C"
//                text = "搜索历史"
//                gravity = gravity_center
//                start_toStartOf = parent_id
//                top_toTopOf = parent_id
//                margin_start = 16
//                margin_top = 18
//                visibility = gone
//            }
//
//            ivDelete = ImageView {
//                layout_id = "ivDelete"
//                layout_width = 20
//                layout_height = 20
//                scaleType = scale_fit_xy
//                end_toEndOf = parent_id
//                align_vertical_to = "tvHistory"
//                margin_end = 16
//                visibility = gone
//                src = R.drawable.ic_gray_delete
//                onClick = {
//                    showDeleteConfirmDialog()
//                    searchViewModel.send(SearchIntent.HideKeyboard)
//                }
//            }
//
//            flowSearchHistory = LineFeedLayout {
//                layout_id = "fSearchHistory"
//                layout_width = match_parent
//                layout_height = 70
//                top_toBottomOf = "tvHistory"
//                margin_horizontal = 16
//                margin_top = 14
//                verticalGap = 10.dp
//                horizontalGap = 8.dp
//                setOnItemClickListener { view, i ->
//                    (view as? TextView)?.text?.takeIf { it.isNotEmpty() }?.let {
//                        searchViewModel.send(SearchIntent.GotoSearchPage(it.toString(), SearchFrom.HISTORY))
//                    }
//                }
//            }
//
//            ivSwitch = ImageView {
//                layout_id = "ivSwitch"
//                layout_width = 30
//                layout_height = 30
//                scaleType = scale_fit_xy
//                imageDrawable = StateListDrawable().apply {
//                    addState(intArrayOf(state_selected), ContextCompat.getDrawable(context, R.drawable.search_material_history_off))
//                    addState(intArrayOf(state_unselected), ContextCompat.getDrawable(context, R.drawable.search_material_history_on))
//                }
//                top_toBottomOf = "fSearchHistory"
//                center_horizontal = true
//                margin_top = 20
//                isSelected = false
//                visibility = gone
//                onClick = {
//                    isSelected = isSelected.not()
//                    searchViewModel.send(SearchIntent.HistorySwitch(isSelected))
//                }
//            }
//        }
//    }
//
//    private fun getHistoryTagView(tag: String) =
//        BTextView {
//            layout_id = tag
//            layout_width = wrap_content
//            layout_height = wrap_content
//            textSize = 12f
//            textColor = "#222331"
//            text = tag
//            gravity = gravity_center
//            padding_horizontal = 12
//            padding_vertical = 7
//            maxLines = 1
//            ellipsize = ellipsize_end
//            shape = shape {
//                corner_radius = 25
//                solid_color = "#F5F6F9"
//            }
//        }
//
//    private fun showDeleteConfirmDialog() {
//        BDialogHelper.createBDialog(requireContext(), BDialogType.B_ALERT_DIALOG)
//            .setMessage(R.string.template_search_delete_history)
//            .setCancelable(false)
//            .setMessageFontSize(16f)
//            .toWhiteStyle()
//            .setPositiveButton(R.string.editor_record_delete_confirm) { bDialogHelper: BDialogHelper ->
//                searchViewModel.send(SearchIntent.ClearHistory(SearchViewModel.MATERIAL_MMAPID))
//                true
//            }
//            .setNegativeButton(R.string.editor_record_delete_cancel) { bDialogHelper: BDialogHelper -> true }
//            .show()
//    }
//
//    override val searchViewModel: SearchViewModel<*, *> by activityViewModels<SearchViewModel<MaterialTabListBeanWrapper, MaterialSearchViewState>>()
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        return contentView
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        searchViewModel
//            .searchState
//            .collectIn(viewLifecycleOwner) { show(it as MaterialSearchViewState) }
//    }
//
//    private fun show(state: MaterialSearchViewState) {
//        if (state.historys.isEmpty()) {
//            tvHistory.visibility = gone
//            ivDelete.visibility = gone
//            ivSwitch.visibility = gone
//            flowSearchHistory.removeAllViews()
//        } else {
//            tvHistory.visibility = visible
//            ivDelete.visibility = visible
//            flowSearchHistory.removeAllViews()
//            flowSearchHistory.apply { state.historys.forEach { addView(getHistoryTagView(it)) } }
//            lifecycleScope.launch {
//                val lines = flowSearchHistory.getLines()
//                Log.v("ttaylor", "show() lines=$lines")
//                if (lines > HISTORY_FOLDED_MAX_LINES) {
//                    flowSearchHistory.updateLayoutParams<ConstraintLayout.LayoutParams> {
//                        height = if (state.showAllHistory) wrap_content else 70.dp
//                    }
//                }
//                ivSwitch.apply {
//                    visibility = if (lines <= HISTORY_FOLDED_MAX_LINES || state.historys.isEmpty()) gone else visible
//                    isSelected = state.showAllHistory
//                }
//            }
//        }
//    }
//}