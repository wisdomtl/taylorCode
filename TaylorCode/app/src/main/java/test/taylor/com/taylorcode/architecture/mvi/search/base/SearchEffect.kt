package com.bilibili.studio.search.base

import androidx.annotation.Keep

/**
 * In MVI, Effect is the outcome of an Intent
 * One [SearchIntent] could produce several [SearchEffect]
 */
@Keep
sealed interface SearchEffect<T : SearchViewState> {
    fun reduce(oldState: T): T = oldState.reduce(this)

    @Keep
    sealed class Init<T : SearchViewState> : SearchEffect<T> {
        @Keep
        class ShowKeyboard<T : SearchViewState> : Init<T>()

        @Keep
        data class LoadHistory<T : SearchViewState>(val historys: List<String>) : Init<T>()
    }

    @Keep
    sealed class InputKeyword<T : SearchViewState> : SearchEffect<T> {
        @Keep
        data class Hints<T : SearchViewState>(val keyword: String, val hints: List<String>, val isFromDebounce :Boolean = false) : InputKeyword<T>()

        @Keep
        data class Highlight<T : SearchViewState>(val keyword: String, val isHighlight: Boolean) : InputKeyword<T>()
    }

    @Keep
    sealed class CheckWhole<T : SearchViewState> : SearchEffect<T>{
        @Keep
        data class Goto<T : SearchViewState>(val materialType: Int) : CheckWhole<T>()
    }

    @Keep
    sealed class GotoSearchPage<T : SearchViewState> : SearchEffect<T> {
        @Keep
        data class Navigate<T : SearchViewState>(val keyword: String, val searchFrom: SearchFrom) : GotoSearchPage<T>()
    }

    @Keep
    sealed class ClearHistory<T : SearchViewState> : SearchEffect<T> {
        @Keep
        class NoHistory<T : SearchViewState> : ClearHistory<T>()
    }

    @Keep
    sealed class Search<T : SearchViewState> : SearchEffect<T> {
        @Keep
        data class Success<T : SearchViewState>(val templates: List<String>, val matchTip: String) : Search<T>()

        @Keep
        data class Fail<T : SearchViewState>(val matchTip: String) : Search<T>()
    }

    @Keep
    sealed class SearchMore<T : SearchViewState> : SearchEffect<T> {
        @Keep
        data class Success<T : SearchViewState>(val templates: List<String>) : SearchMore<T>()

        @Keep
        data class Fail<T : SearchViewState>(val toast: String) : SearchMore<T>()
    }

    @Keep
    sealed class SearchByMaterialType<T : SearchViewState> : SearchEffect<T> {
        @Keep
        data class Success<T : SearchViewState>(val materialWrapper: String, val matchTip: String) : SearchByMaterialType<T>()

        @Keep
        data class Fail<T : SearchViewState>(val matchTip: String) : SearchByMaterialType<T>()
    }

    @Keep
    sealed class ClearKeyword<T : SearchViewState> : SearchEffect<T> {
        @Keep
        class NoKeyword<T : SearchViewState> : ClearKeyword<T>()
    }

    @Keep
    sealed class GoToHistoryPage<T : SearchViewState> : SearchEffect<T> {
        @Keep
        class Back<T : SearchViewState> : GoToHistoryPage<T>()
    }

    @Keep
    sealed class HideKeyboard<T : SearchViewState> : SearchEffect<T> {
        @Keep
        class Hide<T : SearchViewState> : HideKeyboard<T>()
    }

    @Keep
    sealed class GotoDetailPage<T : SearchViewState> : SearchEffect<T> {
        @Keep
        class Navigate<T : SearchViewState>(val map: Map<String, Any>) : GotoDetailPage<T>()
    }

    @Keep
    sealed class HistorySwitch<T : SearchViewState> : SearchEffect<T> {
        @Keep
        class Switch<T : SearchViewState>(val on: Boolean) : HistorySwitch<T>()
    }

    @Keep
    sealed class PopBack<T : SearchViewState> : SearchEffect<T> {
        @Keep
        class Pop<T : SearchViewState> : PopBack<T>()
    }
}