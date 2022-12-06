package com.bilibili.studio.search.template.data


/**
 * [TemplateTabListBean]'s wrapper, in order to reuse [TemplateTabListBean] and add addition field for search biz
 */
data class TemplateTabListBeanWrapper(
    val templateTabListBean: String,
    val matchType: MatchType,
    val isSuccess:Boolean
){
    fun isEmpty() = false
}

enum class MatchType(val intValue:Int) {
    SEARCH(2),
    RECOMMEND(3)
}