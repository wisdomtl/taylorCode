//package com.bilibili.studio.search.material.data
//
//import android.os.Bundle
//import com.bilibili.studio.search.SearchViewModel
//import com.bilibili.studio.search.base.SearchEffect
//import com.bilibili.studio.search.base.SearchViewState
//import com.bilibili.studio.search.material.repository.MaterialSearchRepository
//import com.bilibili.studio.search.template.data.SearchHint
//import com.tencent.mmkv.MMKV
//
///**
// * Define how template search UI looks like,
// * this class is the code version of UI design
// */
//data class MaterialSearchViewState (
//    /**
//     * whether highlight the search button
//     */
//    val highlightSearch: Boolean = false,
//    /**
//     * the search hint showed when search word is inputted
//     */
//    val hints: List<SearchHint> = emptyList(),
//    /**
//     * whether to show the "X" in the end of search bar
//     */
//    val showClear: Boolean = false,
//    /**
//     * the keywords searched
//     */
//    val historys: MutableList<String> = mutableListOf(),
//
//    /**
//     * the materials returned from server by searching
//     */
//    var materials: MaterialTabListBeanWrapper? = null,
//
//    /**
//     * whether to hide the search button
//     */
//    val hideSearch: Boolean = false,
//    /**
//     * the search outcome respond to user
//     */
//    val searchResultString: String = "",
//    /**
//     * whether show all history or only two lines
//     */
//    val showAllHistory: Boolean = false,
//) : SearchViewState {
//    override fun <T : SearchViewState> reduce(effect: SearchEffect<T>): T {
//        val viewState = when (effect) {
//            is SearchEffect.CheckWhole -> MaterialSearchViewState()
//            is SearchEffect.Init.ShowKeyboard -> MaterialSearchViewState()
//            is SearchEffect.Init.LoadHistory -> copy(historys = effect.historys.toMutableList())
//            is SearchEffect.ClearHistory.NoHistory -> copy(historys = mutableListOf())
//            is SearchEffect.SearchByMaterialType.Success ->{
//                materials?.materialType = effect.materialWrapper.materialType
//                when (effect.materialWrapper.materialType) {
//                    MaterialSearchRepository.MATERIAL_TYPE_ALL -> copy(materials = effect.materialWrapper, searchResultString = effect.matchTip)
//                    MaterialSearchRepository.MATERIAL_TYPE_VIDEO -> {
//                        materials?.materialTabListBean?.materials?.video?.let { old ->
//                            effect?.materialWrapper?.materialTabListBean?.materials?.video?.let { new ->
//                                new?.material_list?.let {
//                                    old.material_list?.addAll(it)
//                                }
//                                old.page = new.page
//                            }
//
//                        }
//                        copy(materials = materials, searchResultString = effect.matchTip)
//                    }
//
//                    MaterialSearchRepository.MATERIAL_TYPE_BGM -> {
//                        materials?.materialTabListBean?.materials?.bgm?.let { old ->
//                            effect?.materialWrapper?.materialTabListBean?.materials?.bgm?.let { new ->
//                                new?.material_list?.let {
//                                    old.material_list?.addAll(it)
//                                }
//                                old.page = new.page
//                            }
//
//                        }
//                        copy(materials = materials, searchResultString = effect.matchTip)
//                    }
//
//                    MaterialSearchRepository.MATERIAL_TYPE_TAG-> {
//                        materials?.materialTabListBean?.materials?.tag?.let { old ->
//                            effect?.materialWrapper?.materialTabListBean?.materials?.tag?.let { new ->
//                                new?.material_list?.let {
//                                    old.material_list?.addAll(it)
//                                }
//                                old.page = new.page
//                            }
//
//                        }
//                        copy(materials = materials, searchResultString = effect.matchTip)
//                    }
//
//                    MaterialSearchRepository.MATERIAL_TYPE_PICTURE -> {
//                        materials?.materialTabListBean?.materials?.picture?.let { old ->
//                            effect?.materialWrapper?.materialTabListBean?.materials?.picture?.let { new ->
//                                new?.material_list?.let {
//                                    old.material_list?.addAll(it)
//                                }
//                                old.page = new.page
//                            }
//
//                        }
//                        copy(materials = materials, searchResultString = effect.matchTip)
//                    }
//                    else -> {copy(materials = effect.materialWrapper, searchResultString = effect.matchTip)}
//                }
//            }
//            is SearchEffect.SearchByMaterialType.Fail -> copy(searchResultString = effect.matchTip)
//            is SearchEffect.GotoDetailPage.Navigate -> this
//            is SearchEffect.GoToHistoryPage.Back -> copy(
//                highlightSearch = false,
//                showClear = false,
//                hints = emptyList(),
//                hideSearch = false
//            )
//            is SearchEffect.HideKeyboard.Hide -> this
//            is SearchEffect.InputKeyword.Hints -> copy(
//                hints = effect.hints.map { SearchHint(effect.keyword, it) },
//                showClear = true,
//            )
//            is SearchEffect.InputKeyword.Highlight -> copy(
//                highlightSearch = effect.isHighlight,
//                showClear = effect.isHighlight,
//            )
//            is SearchEffect.ClearKeyword.NoKeyword -> copy(
//                highlightSearch = false,
//                hints = emptyList(),
//                showClear = false,
//            )
//            is SearchEffect.GotoSearchPage.Navigate -> copy(
//                hints = emptyList(),
//                hideSearch = true,
//                materials = null
//            ).addHistory(effect.keyword)
//            is SearchEffect.HistorySwitch.Switch -> copy(showAllHistory = effect.on)
//            is SearchEffect.PopBack.Pop -> copy(
//                highlightSearch = false,
//                showClear = false,
//                hints = emptyList(),
//                hideSearch = false
//            )
//            else -> {}
//        }
//        return viewState as T
//    }
//
//    companion object {
//        val Init = MaterialSearchViewState()
//        const val MAX_HISTORY_COUNT = 11
//    }
//
//    private fun addHistory(history: String) = apply {
//        if (historys.contains(history)) {
//            historys.remove(history)
//            historys.add(0, history)
//        } else {
//            historys.add(0, history)
//            if (historys.size > MAX_HISTORY_COUNT) historys.removeLast()
//        }
//    }.also {
//        val bundle = Bundle().apply { putStringArray(MaterialSearchViewState::class.java.simpleName, it.historys.toTypedArray()) }
//        MMKV.mmkvWithID(SearchViewModel.MATERIAL_MMAPID)?.encode("search-history", bundle)
//    }
//}
//
