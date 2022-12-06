package com.bilibili.studio.search.base

import androidx.annotation.Keep

/**
 * In MVI, the Event is the things could be consumed by the UI only once
 * [SearchEvent] is all Events the search biz could consumed
 */
@Keep
sealed interface SearchEvent {
    @Keep
    sealed interface Init : SearchEvent {
        object ShowKeyboard : Init
    }

    @Keep
    sealed interface ClearKeyword : SearchEvent {
        object NoKeyword : ClearKeyword
    }

    @Keep
    sealed interface HideKeyboard : SearchEvent {
        object Hide : ClearKeyword
    }

    @Keep
    sealed interface GotoSearchPage : SearchEvent {
        data class Navigate(val keyword: String, val searchFrom: SearchFrom) : GotoSearchPage
    }

    @Keep
    sealed interface GoToHistory : SearchEvent {
        object Navigate : GoToHistory
    }

    @Keep
    sealed interface SearchMore : SearchEvent {
        object Success : SearchMore
        data class Fail(val toast: String) : SearchMore
    }

    @Keep
    sealed interface GotoDetailPage : SearchEvent {
        data class Navigate(val map: Map<String, Any>) : SearchMore
    }

    @Keep
    sealed interface GotoHintPage : SearchEvent {
        object Navigate : GotoHintPage
    }

    @Keep
    sealed interface PopBack : SearchEvent {
        object Pop : GotoHintPage
    }

    @Keep
    data class CheckWhole(val materialType: Int) : SearchEvent
}