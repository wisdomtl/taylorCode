package test.taylor.com.taylorcode.ui.custom_view.selector.kt

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import java.io.Closeable
import java.util.*
import kotlin.collections.HashMap

/**
 * a ViewGroup that has customized action when selected or unselected, it could be an substitution for [android.widget.CheckBox] and [android.widget.RadioButton]
 * [contentView] describe how do [Selector] looks like,
 * [onSelectChange] describe what effect will be shown after selection state change,
 * [tags] keeps data bean for this [Selector],
 * [group] describe selection mode for [Selector], and the mode could be extended easily.
 */
class Selector @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    FrameLayout(context, attrs, defStyleAttr) {

    /**
     * the values this [Selector] carry
     */
    private var tags = HashMap<Any?, Closeable?>()

    /**
     * the unique identifier for a [Selector]
     */
    var tag: String = "default tag-${UUID.randomUUID()}"

    /**
     * the identifier for the [SelectorGroup] this [Selector] belongs to
     */
    var groupTag: String = "default group tag"

    /**
     * the [SelectorGroup] this [Selector] belongs to
     */
    var group: SelectorGroup? = null

    /**
     * the layout view for this [Selector]
     */
     var contentView: View? = null
        set(value) {
            field = value
            value?.let {
                addView(it, LayoutParams(MATCH_PARENT, MATCH_PARENT))
                setOnClickListener {
                    group?.onSelectorClick(this)
                }
            }
        }

    /**
     * it will be invoked when the selection state of this [Selector] has changed,
     * override it if you want customized effect of selected or unselected
     */
    var onSelectChange: ((Selector, Boolean) -> Unit)? = null

    /**
     * whether this [Selector] is selected
     */
    var isSelecting: Boolean = false

    init {
        contentView?.let {
            addView(it, LayoutParams(MATCH_PARENT, MATCH_PARENT))
            setOnClickListener {
                group?.onSelectorClick(this)
            }
        }
    }

    operator fun <T : Closeable> set(key: Key<T>, closeable: Closeable) {
        tags[key] = closeable
    }

    operator fun <T : Closeable> get(key: Key<T>): T? = (tags.getOrElse(key, { null })) as T

    /**
     * change the selection state of this [Selector]
     */
    fun setSelect(select: Boolean) {
        group?.onSelectorClick(this)
        showSelectEffect(select)
    }

    fun showSelectEffect(select: Boolean) {
        if (isSelecting != select) {
            onSelectChange?.invoke(this, select)
        }
        isSelecting = select
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        clear()
    }

    /**
     * clear the values attached to this [Selector]
     */
    private fun clear() {
        group?.clear()
        tags.forEach { entry ->
            closeWithException(entry.value)
        }
    }

    private fun closeWithException(closable: Closeable?) {
        try {
            closable?.close()
        } catch (e: Exception) {
        }
    }

    override fun hashCode(): Int {
        return tag.hashCode()
    }

    /**
     * the key for data bean of this [Selector]
     */
    interface Key<E : Closeable>
}

