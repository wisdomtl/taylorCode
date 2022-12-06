//package com.bilibili.studio.search.material.adapter.all
//
//import androidx.fragment.app.FragmentActivity
//import androidx.lifecycle.ViewModelProvider
//import com.bilibili.studio.search.SearchViewModel
//import com.bilibili.studio.search.material.data.SearchResultListBean
//
//interface IItemView<T> {
//
//    val activity: FragmentActivity
//
//    val searchViewModel: SearchViewModel<*, *>
//        get() = ViewModelProvider(activity)[SearchViewModel::class.java]
//
//    fun setData(data: SearchResultListBean<T>, section: Int = 0, sectionIndex: Int)
//
//}