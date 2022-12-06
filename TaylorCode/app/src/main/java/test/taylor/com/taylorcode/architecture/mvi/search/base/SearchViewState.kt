package com.bilibili.studio.search.base

/**
 *  In MVI, ViewState defines what UI looks like.
 *  Different search biz should have it's own ViewState
 */
interface SearchViewState {
    /**
     * defines how to map [SearchEffect] into [SearchViewState], one [SearchEffect] corresponds to one [SearchViewState]
     */
    fun <T : SearchViewState> reduce(effect: SearchEffect<T>): T
}