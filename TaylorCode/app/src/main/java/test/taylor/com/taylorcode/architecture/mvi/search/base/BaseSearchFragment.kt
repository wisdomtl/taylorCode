package com.bilibili.studio.search.base

import com.bilibili.studio.search.SearchViewModel
import test.taylor.com.taylorcode.architecture.flow.lifecycle.BaseFragment

/**
 * The father of search fragment,
 * There is a [SearchViewModel] here, search fragment should use it to send [SearchIntent]
 */
abstract class BaseSearchFragment : BaseFragment() {
    abstract val searchViewModel:SearchViewModel<*,*>
}