//package com.bilibili.studio.search.material.adapter
//
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import androidx.viewpager2.adapter.FragmentStateAdapter
//import com.bilibili.studio.search.material.fragment.BaseSearchResultFragment
//
//class SimpleFragmentStatePagerAdapter2(fragment:Fragment): FragmentStateAdapter(fragment) {
//
//     lateinit var mTitles: Array<String>
//     lateinit var mMaterialTypes: List<Int>
//    lateinit var createFragment: (position: Int) -> BaseSearchResultFragment
//
//    override fun getItemCount() = mTitles.size
//    override fun createFragment(position: Int): BaseSearchResultFragment {
//        return createFragment.invoke(position)
//    }
//    fun getPositionByType(materialType: Int): Int {
//        return mMaterialTypes.indexOf(materialType)
//    }
//
//    fun getTitleByType(materialType: Int): String {
//        return mTitles[mMaterialTypes.indexOf(materialType)]
//    }
//}