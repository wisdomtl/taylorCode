package com.bilibili.studio.search.template.data

/**
 * a wrapper for search hint returned from server, used by UI
 */
data class SearchHint(
    val keyword: String,
    val hint: String
)