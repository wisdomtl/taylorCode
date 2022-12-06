package com.bilibili.studio.search.template.mapper

import com.bilibili.studio.search.template.data.MatchType
import com.bilibili.studio.search.base.SearchEffect
import com.bilibili.studio.search.template.data.TemplateSearchViewState
import com.bilibili.studio.search.template.data.TemplateTabListBeanWrapper

const val NETWORK_ERROR_STRING = "网络异常，请检查网络"


val templateSearchMapper: (TemplateTabListBeanWrapper?) -> SearchEffect.Search<TemplateSearchViewState> = { value ->
    if (value == null) SearchEffect.Search.Fail(NETWORK_ERROR_STRING)
    else if (value.isSuccess) {
        when (value.matchType) {
            MatchType.SEARCH -> SearchEffect.Search.Success(emptyList(), "")
            MatchType.RECOMMEND -> SearchEffect.Search.Success(emptyList(), "暂无搜索结果，为你推荐以下模板")
        }
    } else {
        SearchEffect.Search.Fail(NETWORK_ERROR_STRING)
    }
}

val templateSearchMoreMapper: (TemplateTabListBeanWrapper?) -> SearchEffect.SearchMore<TemplateSearchViewState> = { value ->
    if (value == null) SearchEffect.SearchMore.Fail("网络错误，请重试")
    else if (value.isSuccess) {
        SearchEffect.SearchMore.Success(emptyList())
    } else {
        SearchEffect.SearchMore.Fail("没有更多了")
    }
}