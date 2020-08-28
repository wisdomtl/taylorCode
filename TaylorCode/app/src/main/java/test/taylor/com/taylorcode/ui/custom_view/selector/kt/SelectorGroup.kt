package test.taylor.com.taylorcode.ui.custom_view.selector.kt


/**
 * the controller for [Selector]s
 */
class SelectorGroup {
    companion object {
        /**
         * single choice mode, previous [Selector] will be unselected if a new one is selected
         */
        var MODE_SINGLE = { selectorGroup: SelectorGroup, selector: Selector ->
            selectorGroup.run {
                findLast(selector.groupTag)?.let { setSelected(it, false) }
                setSelected(selector, true)
            }
        }

        /**
         * multiple choice mode, several [Selector] could be selected in one [SelectorGroup]
         */
        var MODE_MULTIPLE = { selectorGroup: SelectorGroup, selector: Selector ->
            selectorGroup.setSelected(selector, !selector.isSelecting)
        }
    }

    /**
     * key is group tag and value is selected [Selector] in this group
     * the reason why use [LinkedHashMap] is to keep the sequence of groups
     */
    private var selectorMap = LinkedHashMap<String, MutableSet<Selector>>()

    /**
     * the choice mode of this [SelectorGroup], there are two default choice mode, which is [MODE_SINGLE] and [MODE_MULTIPLE]
     */
    var choiceMode: ((SelectorGroup, Selector) -> Unit)? = null

    /**
     * if selection in this [SelectorGroup] is changed ,this lambda will be invoked,
     * override this to listen the change of selection
     */
    var selectChangeListener: ((List<Selector>/*selected set*/) -> Unit)? = null

    fun onSelectorClick(selector: Selector) {
        choiceMode?.invoke(this, selector)
    }

    /**
     * find [Selector]s with the same [groupTag]
     */
    fun find(groupTag: String) = selectorMap[groupTag]

    /**
     * find last selected [Selector] of [groupTag]
     */
    fun findLast(groupTag: String) = find(groupTag)?.takeUnless { it.isNullOrEmpty() }?.last()

    fun setSelected(selector: Selector, select: Boolean) {
        if (select) {
            selectorMap[selector.groupTag]?.also { it.add(selector) } ?: also { selectorMap[selector.groupTag] = mutableSetOf(selector) }
        } else {
            selectorMap[selector.groupTag]?.also { it.remove(selector) }
        }
        selector.showSelectEffect(select)
        selectChangeListener?.invoke(selectorMap.flatMap { it.value })
    }

    fun clear() {
        selectorMap.clear()
    }
}

