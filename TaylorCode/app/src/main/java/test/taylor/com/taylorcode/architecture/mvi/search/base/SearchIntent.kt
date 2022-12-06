package com.bilibili.studio.search.base

import androidx.annotation.Keep

/**
 * In MVI, Intent represents the action performed by the UI.
 * [SearchIntent] is all action the search biz could performed
 */
@Keep
sealed class SearchIntent {
    /**
     * the very beginning of search biz
     */
    @Keep
    data class Init(val mmapID: String) : SearchIntent()

    /**
     * input keywords in EditText
     */
    @Keep
    data class InputKeyword(val keyword: String, val isUserInput: Boolean) : SearchIntent()

    /**
     * clear keywords in EditText
     */
    @Keep
    object ClearKeyword : SearchIntent()

    /**
     * trigger search by [keyword]
     */
    @Keep
    data class GotoSearchPage(val keyword: String, val type: SearchFrom) : SearchIntent()

    /**
     * clear history
     */
    @Keep
    data class ClearHistory(val mmapID: String) : SearchIntent()

    /**
     * search template by [keyword],
     * if no search result returned, fetch recommend templates
     * @param keyword the word to search
     * @param mapper define how to map [BEAN] to [SearchEffect.Search], it differs from every search biz
     */
    @Keep
    data class Search<BEAN, T : SearchViewState>(val keyword: String, val mapper: (BEAN?) -> SearchEffect.Search<T>) : SearchIntent()

    /**
     * search more templates by [keyword],
     * if no search result returned, fetch recommend templates
     * @param keyword the word to search
     * @param mapper define how to map [BEAN] to [SearchEffect.SearchMore], it differs from every search biz
     */
    @Keep
    data class SearchMore<BEAN, T : SearchViewState>(val keyword: String, val mapper: (BEAN?) -> SearchEffect.SearchMore<T>) : SearchIntent()

    /**
     * search more templates by [keyword] and [materialType],
     * if no search result returned, fetch recommend templates
     * @param keyword the word to search
     * @param mapper define how to map [BEAN] to [SearchEffect.SearchMore], it differs from every search biz
     */
    @Keep
    data class SearchByMaterialType<BEAN, T : SearchViewState>(
        val keyword: String,
        val materialType: Int,
        val mapper: (BEAN?) -> SearchEffect.SearchByMaterialType<T>,
    ) : SearchIntent()

    /**
     * navigate to history page
     */
    @Keep
    object GoToHistoryPage : SearchIntent()

    /**
     * Check Whole click
     */
    @Keep
    data class CheckWhole(val materialType: Int) : SearchIntent()

    /**
     * hide keyboard
     */
    @Keep
    object HideKeyboard : SearchIntent()

    /**
     * go to detail page with additional param
     */
    @Keep
    class GoToDetailPage(val map: Map<String, Any>, val block: ((Any) -> Map<String, Any>)? = null) : SearchIntent()

    /**
     * turn on or off the switch of history
     */
    @Keep
    data class HistorySwitch(val on:Boolean):SearchIntent()

    /**
     * navigate back from the preview page
     */
    @Keep
    object PopBack:SearchIntent()
}

/**
 * the enter pointer of search action,
 * it is used in report
 */
@Keep
enum class SearchFrom(val typeInt: Int) {
    BUTTON(1),//点击搜索按钮触发搜索
    HINT(2),//点击联想词触发搜索
    HISTORY(3),// 点击搜索历史关键词触发搜索
}