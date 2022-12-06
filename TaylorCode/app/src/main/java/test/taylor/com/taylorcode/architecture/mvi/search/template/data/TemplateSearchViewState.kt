package com.bilibili.studio.search.template.data

import android.os.Bundle
import com.bilibili.studio.search.SearchViewModel
import com.bilibili.studio.search.base.SearchEffect
import com.bilibili.studio.search.base.SearchViewState

/**
 * Define how template search UI looks like,
 * this class is the code version of UI design
 */
data class TemplateSearchViewState(
    /**
     * whether highlight the search button
     */
    val highlightSearch: Boolean = false,
    /**
     * the search hint showed when search word is inputted
     */
    val hints: List<SearchHint> = emptyList(),
    /**
     * whether to show the "X" in the end of search bar
     */
    val showClear: Boolean = false,
    /**
     * the keywords searched
     */
    val historys: MutableList<String> = mutableListOf(),
    /**
     * the templates returned from server by searching
     */
    val templates: List<String> = emptyList(),
    /**
     * whether to hide the search button
     */
    val hideSearch: Boolean = false,
    /**
     * the search outcome respond to user
     */
    val searchResultString: String = "",
    /**
     * whether show all history or only two lines
     */
    val showAllHistory: Boolean = false
) : SearchViewState {
    override fun <T : SearchViewState> reduce(effect: SearchEffect<T>): T {
        val viewState = when (effect) {
            is SearchEffect.Init.ShowKeyboard -> TemplateSearchViewState()
            is SearchEffect.Init.LoadHistory -> copy(historys = effect.historys.toMutableList())
            is SearchEffect.ClearHistory.NoHistory -> copy(historys = mutableListOf())
            is SearchEffect.Search.Success -> copy(templates = effect.templates, searchResultString = effect.matchTip)
            is SearchEffect.Search.Fail -> copy(searchResultString = effect.matchTip)
            is SearchEffect.GotoDetailPage.Navigate -> this
            is SearchEffect.GoToHistoryPage.Back -> copy(
                highlightSearch = false,
                showClear = false,
                hints = emptyList(),
                templates = emptyList(),
                hideSearch = false
            )
            is SearchEffect.HideKeyboard.Hide -> this
            is SearchEffect.SearchMore.Fail -> this
            is SearchEffect.SearchMore.Success -> {
                val oldTemplates = templates.toMutableList()
                oldTemplates.addAll(effect.templates)
                copy(templates = oldTemplates)
            }
            is SearchEffect.InputKeyword.Hints -> copy(
                hints = effect.hints.map { SearchHint(effect.keyword, it) },
                showClear = true,
            )
            is SearchEffect.InputKeyword.Highlight -> copy(
                highlightSearch = effect.isHighlight,
                showClear = effect.isHighlight,
            )
            is SearchEffect.ClearKeyword.NoKeyword -> copy(
                highlightSearch = false,
                hints = emptyList(),
                showClear = false,
            )
            is SearchEffect.GotoSearchPage.Navigate -> copy(
                hints = emptyList(),
                hideSearch = true,
            ).addHistory(effect.keyword)
            is SearchEffect.HistorySwitch.Switch -> copy(showAllHistory = effect.on)
            is SearchEffect.PopBack.Pop -> copy(
                highlightSearch = false,
                showClear = false,
                hints = emptyList(),
                templates = emptyList(),
                hideSearch = false
            )
            else -> {}
        }
        return viewState as T
    }

    companion object {
        val Init = TemplateSearchViewState()
        const val MAX_HISTORY_COUNT = 11
    }

    private fun addHistory(history: String) = apply {
        if (historys.contains(history)) {
            historys.remove(history)
            historys.add(0, history)
        } else {
            historys.add(0, history)
            if (historys.size > MAX_HISTORY_COUNT) historys.removeLast()
        }
    }.also {
        val bundle = Bundle().apply { putStringArray(TemplateSearchViewState::class.java.simpleName, it.historys.toTypedArray()) }
//        MMKV.mmkvWithID(SearchViewModel.TEMPLATE_MMAPID)?.encode("search-history", bundle)
    }
}

