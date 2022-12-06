package com.bilibili.studio.search.material.listener

interface ISearchMaterialClickListener {

    fun onItemClickListener(data: Any, section: Int, sectionIndex: Int, play: Boolean = true,position:Int)

}