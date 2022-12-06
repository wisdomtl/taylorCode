package com.bilibili.studio.search.base


/**
 * Base type of search repository
 * Different search biz should have it's own repository
 */
interface BaseSearchRepository<out BEAN> {
    /**
     * fetch search result, which we called [BEAN]
     */
    suspend fun search(keyword: String, extraParam: Map<String, Any>? = null): BEAN?

    /**
     * fetch more search result, which we called [BEAN]
     */
    suspend fun searchMore(keyword: String, extraParam: Map<String, Any>? = null): BEAN?

    /**
     * fetch the hints for keyword
     */
    suspend fun fetchSearchHint(keyword: String):List<String>

    /**
     * clear the paging param in repository
     */
    fun clear()


    fun hasMore(materialType: Int): Boolean {
        return true
    }

    fun isLoading(materialType: Int): Boolean {
        return false
    }
}