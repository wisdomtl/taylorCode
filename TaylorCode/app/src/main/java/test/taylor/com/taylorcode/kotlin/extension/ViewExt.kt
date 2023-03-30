package test.taylor.com.taylorcode.kotlin.extension

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.*
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.util.Size
import android.view.*
import android.view.View.OnAttachStateChangeListener
import android.view.ViewGroup.OnHierarchyChangeListener
import android.view.ViewTreeObserver.*
import android.widget.EditText
import android.widget.ImageView
import androidx.coordinatorlayout.widget.ViewGroupUtils
import androidx.core.graphics.applyCanvas
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.*
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import test.taylor.com.taylorcode.kotlin.dp
import test.taylor.com.taylorcode.kotlin.relativeTo
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun View.extraAnimClickListener(animator: ValueAnimator, action: (View) -> Unit) {
    setOnTouchListener { v, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> animator.start()
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> animator.reverse()
        }
        false
    }

    setOnClickListener { action(this) }
}

/**
 * A simple wrapper for [OnPageChangeCallback] to tell which page is showing or gone
 * @param block a lambda to tell the page visibility change
 */
fun ViewPager2.onPageVisibilityChange(block: (index: Int, isVisible: Boolean) -> Unit) {
    var lastPage: Int = currentItem
    val listener = object : OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            if (lastPage != position) {
                block(lastPage, false)
            }
            block(position, true)
            lastPage = position
        }
    }
    registerOnPageChangeCallback(listener)
    addOnAttachStateChangeListener(object : OnAttachStateChangeListener {
        override fun onViewAttachedToWindow(v: View) {
        }

        override fun onViewDetachedFromWindow(v: View) {
            if (v == null || v !is ViewPager2) return
            if (ViewCompat.isAttachedToWindow(v)) {
                v.unregisterOnPageChangeCallback(listener)
            }
            removeOnAttachStateChangeListener(this)
        }

    })
}

/**
 * A wrapper for [OnScrollListener] to tell which ItemView is showing or gone according to [percent]
 * @param viewGroups the parent ViewGroups which new view to be added in (like Fragment's Container View)
 * @param percent the percentage of ItemView's visibility, range from 0 to 1, for example, 0.5 means the half of ItemView is visible to user.
 * @param block a lambda to tell which ItemView is visible to user with the Adapter Index
 */
fun RecyclerView.onItemVisibilityChange(
    percent: Float = 0.5f,
    viewGroups: List<ViewGroup>? = null,
    block: (itemView: View, adapterIndex: Int, isVisible: Boolean) -> Unit
) {
    val visibleRect = Rect() // reuse rect object rather than recreate it everytime for a better performance
    val visibleAdapterIndexs = mutableSetOf<Int>()
    val checkVisibility = {
        // iterate all children of RecyclerView to check whether it is visible
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val adapterIndex = getChildAdapterPosition(child)
            if (adapterIndex == RecyclerView.NO_POSITION) continue
            val isChildVisible = child.getLocalVisibleRect(visibleRect)
            val visibleArea = visibleRect.let { it.height() * it.width() }
            val realArea = child.width * child.height
            if (visiblePercent(visibleRect) > percent && isChildVisible && visibleArea >= realArea * percent) {
                if (visibleAdapterIndexs.add(adapterIndex)) {
                    block(child, adapterIndex, true)
                }
            } else {
                if (adapterIndex in visibleAdapterIndexs) {
                    block(child, adapterIndex, false)
                    visibleAdapterIndexs.remove(adapterIndex)
                }
            }
        }
    }
    val scrollListener = object : OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            checkVisibility()
        }
    }
    addOnScrollListener(scrollListener)
    onVisibilityChange(viewGroups, false) { view, isVisible ->
        if (isVisible) {
            checkVisibility()
        } else {
            for (i in 0 until childCount) {
                val child = getChildAt(i)
                val adapterIndex = getChildAdapterPosition(child)
                if (adapterIndex in visibleAdapterIndexs) {
                    block(child, adapterIndex, false)
                    visibleAdapterIndexs.remove(adapterIndex)
                }
            }
        }
    }
    addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
        override fun onViewAttachedToWindow(v: View) {
        }

        override fun onViewDetachedFromWindow(v: View) {
            if (v == null || v !is RecyclerView) return
            v.removeOnScrollListener(scrollListener)
            removeOnAttachStateChangeListener(this)
        }
    })
}

/**
 * Check whether the view is visible to user
 */
fun View.visiblePercent(rect: Rect): Float {
    val isVisible = getLocalVisibleRect(rect)
    return if (ViewCompat.isAttachedToWindow(this) && visibility == View.VISIBLE && isVisible) {
        rect.let { it.width() * it.height() }.toFloat() / (width * height)
    } else 0f
}


/**
 * Check whether the view is visible to the user.
 * This function works for the following scenario:
 * 1.The switch of power button,
 * 2.Home clicked
 * 3.Invoke [View.setVisibility] manually,
 * 4.Covered by another [View] in the same view tree,
 * 5.[ViewPager]'s scrolling,
 * 6.[ScrollView]'s scrolling,
 * 7.[NestedScrollView]'s scrolling,
 * 8.[Dialog]'s showing,
 * 9.[DialogFragment]'s showing,
 * 10.[Activity]'s switching
 * 11.[Fragment]'s switching
 *
 * But it is not recommend to use it in the child view of [ScrollView] or [NestedScrollView] due to the performance issue.
 * Too many child lead to too many scroll listeners.
 *
 * @param viewGroups the parent ViewGroups which new view to be added in (like Fragment's Container View)
 * @param needScrollListener whether detects scroll or not
 * @param block a lambda to tell whether the current View is visible to user
 */
fun View.onVisibilityChange(
    viewGroups: List<ViewGroup>? = null,
    needScrollListener: Boolean = true,
    block: (view: View, isVisible: Boolean) -> Unit
) {
    val KEY_VISIBILITY = "KEY_VISIBILITY".hashCode()
    val KEY_HAS_LISTENER = "KEY_HAS_LISTENER".hashCode()
    if (getTag(KEY_HAS_LISTENER) == true) return

    val visibleRect = Rect()

    val checkVisibility = {
        val lastVisibility = getTag(KEY_VISIBILITY) as? Boolean
        val isInScreen = visiblePercent(visibleRect) > 0f
        if (lastVisibility == null) {
            if (isInScreen) {
                block(this, true)
                setTag(KEY_VISIBILITY, true)
            }
        } else if (lastVisibility != isInScreen) {
            block(this, isInScreen)
            setTag(KEY_VISIBILITY, isInScreen)
        }
    }

    class LayoutListener : ViewTreeObserver.OnGlobalLayoutListener {
        var addedView: View? = null
        override fun onGlobalLayout() {
            if (addedView != null) {
                val addedRect = Rect().also { addedView?.getGlobalVisibleRect(it) }
                val rect = Rect().also { this@onVisibilityChange.getGlobalVisibleRect(it) }
                if (addedRect.contains(rect)) {
                    block(this@onVisibilityChange, false)
                    setTag(KEY_VISIBILITY, false)
                } else {
                    block(this@onVisibilityChange, true)
                    setTag(KEY_VISIBILITY, true)
                }
            } else {
                checkVisibility()
            }
        }
    }

    val layoutListener = LayoutListener()
    viewGroups?.forEachIndexed { index, viewGroup ->
        viewGroup.setOnHierarchyChangeListener(object : ViewGroup.OnHierarchyChangeListener {
            override fun onChildViewAdded(parent: View?, child: View?) {
                layoutListener.addedView = child
            }

            override fun onChildViewRemoved(parent: View?, child: View?) {
                layoutListener.addedView = null
            }
        })
    }
    viewTreeObserver.addOnGlobalLayoutListener(layoutListener)

    var scrollListener: ViewTreeObserver.OnScrollChangedListener? = null
    if (needScrollListener) {
        scrollListener = ViewTreeObserver.OnScrollChangedListener { checkVisibility() }
        viewTreeObserver.addOnScrollChangedListener(scrollListener)
    }

    val focusChangeListener = ViewTreeObserver.OnWindowFocusChangeListener { hasFocus ->
        val lastVisibility = getTag(KEY_VISIBILITY) as? Boolean
        val isInScreen = visiblePercent(visibleRect) > 0
        if (hasFocus) {
            if (lastVisibility != isInScreen) {
                block(this, isInScreen)
                setTag(KEY_VISIBILITY, isInScreen)
            }
        } else {
            if (lastVisibility == true) {
                block(this, false)
                setTag(KEY_VISIBILITY, false)
            }
        }
    }
    viewTreeObserver.addOnWindowFocusChangeListener(focusChangeListener)

    addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
        override fun onViewAttachedToWindow(v: View) {
        }

        override fun onViewDetachedFromWindow(v: View) {
            v ?: return
            // delay by post for giving the last execution chance to OnGlobalLayoutListener
            post {
                try {
                    v.viewTreeObserver.removeOnGlobalLayoutListener(layoutListener)
                } catch (_: java.lang.Exception) {
                    v.viewTreeObserver.removeGlobalOnLayoutListener(layoutListener)
                }
                v.viewTreeObserver.removeOnWindowFocusChangeListener(focusChangeListener)
                if (scrollListener != null) v.viewTreeObserver.removeOnScrollChangedListener(scrollListener)
                viewGroups?.forEach { it.setOnHierarchyChangeListener(null) }
            }
            removeOnAttachStateChangeListener(this)
        }
    })
    setTag(KEY_HAS_LISTENER, true)
}


/**
 * get relative position of this [View] relative to [otherView]
 */
fun View.getRelativeRectTo(otherView: View): Rect {
    val parentRect = Rect().also { otherView.getGlobalVisibleRect(it) }
    val childRect = Rect().also { getGlobalVisibleRect(it) }
    return childRect.relativeTo(parentRect)
}

/**
 * get the screen size including navigation bar
 */
val Context.screenSize: Size
    get() =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            this.getSystemService(WindowManager::class.java).currentWindowMetrics.let { Size(it.bounds.width(), it.bounds.height()) }
        } else {
            val display = getSystemService(WindowManager::class.java).defaultDisplay
            val metrics = if (display != null) DisplayMetrics().also { display.getRealMetrics(it) } else resources.displayMetrics
            Size(metrics.widthPixels, metrics.heightPixels)
        }

/**
 * get the relative rect of the [Rect] according to the [otherRect] ,considering the [otherRect]'s left and top is zero
 */
fun Rect.relativeTo(otherRect: Rect): Rect {
    val relativeLeft = left - otherRect.left
    val relativeTop = top - otherRect.top
    val relativeRight = relativeLeft + right - left
    val relativeBottom = relativeTop + bottom - top
    return Rect(relativeLeft, relativeTop, relativeRight, relativeBottom)
}

val View.isInScreen: Boolean
    get() = ViewCompat.isAttachedToWindow(this) && visibility == View.VISIBLE && getLocalVisibleRect(Rect())

/**
 * add listener to RecyclerView which listens it's items in and out event
 * @param inOrOut 1 means item enter RecyclerView, 0 means item leave RecyclerView, 2 means item is fully visible in RecyclerView
 */
fun RecyclerView.addItemInOutListener(listener: ((childView: View?, adapterIndex: Int, inOrOut: Int) -> Unit)?) {
    data class Item(var index: Int, var view: View?) {
        infix fun after(item: Item) = this.index > item.index
        infix fun before(item: Item) = this.index < item.index

        val isInvalid = index < 0
    }
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        private var preTopItem = Item(-1, null)
        private var preBottomItem = Item(-1, null)
        private var curTopItem = Item(0, null)
        private var curBottomItem = Item(Int.MAX_VALUE, null)

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            recyclerView.findChildViewUnder(0f, 0f)?.let { topChildView ->
                val index = recyclerView.getChildAdapterPosition(topChildView)
                curTopItem = curTopItem.copy(index, topChildView)
                if (preTopItem.isInvalid) preTopItem = curTopItem
                if (preTopItem before curTopItem) listener?.invoke(preTopItem.view, preTopItem.index, 0)
                else if (preTopItem after curTopItem) {
                    listener?.invoke(curTopItem.view, curTopItem.index, 1)
                    listener?.invoke(preTopItem.view, preTopItem.index, 2)
                }
                preTopItem = preTopItem.copy(curTopItem.index, curTopItem.view)
            }

            recyclerView.findChildViewUnder(recyclerView.width.toFloat(), recyclerView.height.toFloat())?.let { bottomChildView ->
                val index = recyclerView.getChildAdapterPosition(bottomChildView)
                curBottomItem = curBottomItem.copy(index, bottomChildView)
                if (preBottomItem.isInvalid) preBottomItem = curBottomItem
                if (preBottomItem before curBottomItem) {
                    listener?.invoke(curBottomItem.view, curBottomItem.index, 1)
                    listener?.invoke(preBottomItem.view, preBottomItem.index, 2)
                } else if (preBottomItem after curBottomItem) listener?.invoke(preBottomItem.view, preBottomItem.index, 0)
                preBottomItem = preBottomItem.copy(curBottomItem.index, curBottomItem.view)
            }
        }
    })
}


val SHADOW_LEFT: Int
    get() = 1.shl(1)
val SHADOW_RIGHT: Int
    get() = 1.shl(2)
val SHADOW_BOTTOM: Int
    get() = 1.shl(3)
val SHADOW_TOP: Int
    get() = 1.shl(4)

/**
 * add shadow under this [View]
 */
fun View.shadow(
    direction: Int = SHADOW_LEFT.or(SHADOW_BOTTOM).or(SHADOW_RIGHT),
    radius: Int = 5.dp,
    color: Int = Color.parseColor("#3c000000")
) {
    doOnAttach {
        post {
            // keep the origin background here
            val backgroundBitmap = background.let { it.toBitmap(max(width, 1), max(height, 1)) }
            var extraPaddingRight = 0
            var extraPaddingLeft = 0
            var extraPaddingBottom = 0
            var extraPaddingTop = 0

            // calculate extra space for shadow
            var extraWidth: Int = 0
            extraWidth += if (direction.and(SHADOW_LEFT) == SHADOW_LEFT) {
                extraPaddingLeft = radius
                radius
            } else 0
            extraWidth += if (direction.and(SHADOW_RIGHT) == SHADOW_RIGHT) {
                extraPaddingRight = radius
                radius
            } else 0
            var extraHeight: Int = 0
            extraHeight += if (direction.and(SHADOW_BOTTOM) == SHADOW_BOTTOM) {
                extraPaddingBottom = radius
                radius
            } else 0
            extraHeight += if (direction.and(SHADOW_TOP) == SHADOW_TOP) {
                extraPaddingTop = radius
                radius
            } else 0

            //shift view by set padding according to the shadow radius
            val paddingLeft = paddingLeft
            val paddingRight = paddingRight
            val paddingBottom = paddingBottom
            val paddingTop = paddingTop
            setPadding(
                paddingLeft + extraPaddingLeft,
                paddingTop + extraPaddingTop,
                paddingRight + extraPaddingRight,
                paddingBottom + extraPaddingBottom
            )
            val shadowWidth = width + extraWidth
            val shadowHeight = height + extraHeight

            /**
             * create a bitmap which is bigger than the origin background of this view, the extra space is used to draw shadow
             * 1. draw shadow firstly
             * 2. draw the origin background upon shadow
             */
            Bitmap.createBitmap(shadowWidth, shadowHeight, Bitmap.Config.ARGB_8888).applyCanvas {
                val shadowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    maskFilter = BlurMaskFilter(radius.toFloat(), BlurMaskFilter.Blur.NORMAL)
                    this.color = color
                }
                drawRect(Rect(radius, radius, width - radius, height - radius), shadowPaint)
                drawBitmap(
                    backgroundBitmap,
                    null,
                    Rect(extraPaddingLeft, extraPaddingTop, width - extraPaddingRight, height - extraPaddingBottom),
                    Paint()
                )
            }.let {
                // renew view's background
                background = it.toDrawable(resources)
            }
        }
    }
}

/**
 * add listener to [RecyclerView] which listens whether it's scrolling is touch the top(return 1) or bottom(return 2)
 */
fun RecyclerView.addTopBottomListener(listener: ((direction: Int) -> Unit)?) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (dy == 0) return
            if (!recyclerView.canScrollVertically(1)) {
                listener?.invoke(2)
            } else if (!recyclerView.canScrollVertically(-1)) {
                listener?.invoke(1)
            }
        }
    })
}

@SuppressLint("RestrictedApi")
fun View.expand(dx: Int, dy: Int) {
    class MultiTouchDelegate(bound: Rect? = null, delegateView: View) : TouchDelegate(bound, delegateView) {
        val delegateViewMap = mutableMapOf<View, Rect>()
        private var delegateView: View? = null

        override fun onTouchEvent(event: MotionEvent): Boolean {
            val x = event.x.toInt()
            val y = event.y.toInt()
            var handled = false
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    delegateView = findDelegateViewUnder(x, y)
                }
                MotionEvent.ACTION_CANCEL -> {
                    delegateView = null
                }
            }
            delegateView?.let {
                event.setLocation(it.width / 2f, it.height / 2f)
                handled = it.dispatchTouchEvent(event)
            }
            return handled
        }

        private fun findDelegateViewUnder(x: Int, y: Int): View? {
            delegateViewMap.forEach { entry -> if (entry.value.contains(x, y)) return entry.key }
            return null
        }
    }

    val parentView = parent as? ViewGroup
    parentView ?: return

    if (parentView.touchDelegate == null) parentView.touchDelegate = MultiTouchDelegate(delegateView = this)
    post {
        val rect = Rect()
        ViewGroupUtils.getDescendantRect(parentView, this, rect)
        rect.inset(-dx, -dy)
        (parentView.touchDelegate as? MultiTouchDelegate)?.delegateViewMap?.put(this, rect)
    }
}


@ExperimentalCoroutinesApi
fun EditText.textChangeFlow(): Flow<CharSequence> = callbackFlow {
    val watcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(
            s: CharSequence?, start: Int, count: Int, after: Int
        ) {
        }

        override fun onTextChanged(
            s: CharSequence?, start: Int, before: Int, count: Int
        ) {
            s?.let { trySend(it) }
        }
    }
    addTextChangedListener(watcher)
    awaitClose { removeTextChangedListener(watcher) }
}


@ExperimentalCoroutinesApi
fun View.clickFlow() = callbackFlow {
    setOnClickListener { trySend(Unit) }
    awaitClose { setOnClickListener(null) }
}

/**
 * fit image into a [rect]
 * @param rect a position relative to [ImageView] in percentage
 */
fun ImageView.fitIntoRect(rect: RectF? = null) {
    post {
        val r = rect ?: RectF(0f, 0f, 1f, 1f)
        scaleType = ImageView.ScaleType.MATRIX
        imageMatrix = imageMatrix.apply {
            val (imgWidth, imgHeight) = drawable?.let { it.minimumWidth to it.intrinsicHeight } ?: return@post
            val scaleX = r.width() * measuredWidth / imgWidth.toFloat()
            val scaleY = r.height() * measuredHeight / imgHeight.toFloat()
            val scale = min(scaleX, scaleY)
            postScale(scale, scale)
            val dx = r.centerX() * measuredWidth - scale * imgWidth / 2
            val dy = r.centerY() * measuredHeight - scale * imgHeight / 2
            postTranslate(dx, dy)
        }
    }
}