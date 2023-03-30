package test.taylor.com.taylorcode.kotlin

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Rect
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Build
import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.InputType
import android.text.TextUtils
import android.util.TypedValue
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.*
import androidx.constraintlayout.helper.widget.Flow
import androidx.constraintlayout.helper.widget.Layer
import androidx.constraintlayout.widget.*
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.MarginLayoutParamsCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import androidx.viewpager2.widget.ViewPager2
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import test.taylor.com.taylorcode.ui.custom_view.recyclerview_indicator.Indicator
import test.taylor.com.taylorcode.ui.one.OneViewGroup
import test.taylor.com.taylorcode.ui.performance.widget.PercentLayout
import kotlin.math.abs


//</editor-fold>

//<editor-fold desc="View extend field">
inline var View.layout_id: String
    get() {
        return ""
    }
    set(value) {
        id = value.toLayoutId()
    }
inline var View.padding_top: Number
    get() {
        return 0
    }
    set(value) {
        setPadding(paddingLeft, value.dp, paddingRight, paddingBottom)
    }

inline var View.padding_bottom: Number
    get() {
        return 0
    }
    set(value) {
        setPadding(paddingLeft, paddingTop, paddingRight, value.dp)
    }

inline var View.padding_start: Number
    get() {
        return 0
    }
    set(value) {
        setPadding(value.dp, paddingTop, paddingRight, paddingBottom)
    }

inline var View.padding_end: Number
    get() {
        return 0
    }
    set(value) {
        setPadding(paddingLeft, paddingTop, value.dp, paddingBottom)
    }

inline var View.padding: Number
    get() {
        return 0
    }
    set(value) {
        setPadding(value.dp, value.dp, value.dp, value.dp)
    }

inline var View.padding_horizontal: Number
    get() {
        return 0
    }
    set(value) {
        padding_start = value.dp
        padding_end = value.dp
    }

inline var View.padding_vertical: Number
    get() {
        return 0
    }
    set(value) {
        padding_top = value.dp
        padding_bottom = value.dp
    }

inline var View.layout_width: Number
    get() {
        return 0
    }
    set(value) {
        val w = if (value.dp > 0) value.dp else value.toInt()
        val h = layoutParams?.height ?: 0
        updateLayoutParams<ViewGroup.LayoutParams> {
            width = w
            height = h
        }
    }

inline var View.layout_height: Number
    get() {
        return 0
    }
    set(value) {
        val w = layoutParams?.width ?: 0
        val h = if (value.dp > 0) value.dp else value.toInt()
        updateLayoutParams<ViewGroup.LayoutParams> {
            width = w
            height = h
        }
    }

inline var View.alignParentStart: Boolean
    get() {
        return false
    }
    set(value) {
        if (!value) return
        layoutParams = RelativeLayout.LayoutParams(layoutParams.width, layoutParams.height).apply {
            (layoutParams as? RelativeLayout.LayoutParams)?.rules?.forEachIndexed { index, i ->
                addRule(index, i)
            }
            addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE)
        }
    }

inline var View.alignParentEnd: Boolean
    get() {
        return false
    }
    set(value) {
        if (!value) return
        layoutParams = RelativeLayout.LayoutParams(layoutParams.width, layoutParams.height).apply {
            (layoutParams as? RelativeLayout.LayoutParams)?.rules?.forEachIndexed { index, i ->
                addRule(index, i)
            }
            addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE)
        }
    }

inline var View.centerVertical: Boolean
    get() {
        return false
    }
    set(value) {
        if (!value) return
        layoutParams = RelativeLayout.LayoutParams(layoutParams.width, layoutParams.height).apply {
            (layoutParams as? RelativeLayout.LayoutParams)?.rules?.forEachIndexed { index, i ->
                addRule(index, i)
            }
            addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE)
        }
    }

inline var View.centerInParent: Boolean
    get() {
        return false
    }
    set(value) {
        if (!value) return
        layoutParams = RelativeLayout.LayoutParams(layoutParams.width, layoutParams.height).apply {
            (layoutParams as? RelativeLayout.LayoutParams)?.rules?.forEachIndexed { index, i ->
                addRule(index, i)
            }
            addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE)
        }
    }

inline var View.weight: Float
    get() {
        return 0f
    }
    set(value) {
        updateLayoutParams<LinearLayout.LayoutParams> {
            gravity = (layoutParams as? LinearLayout.LayoutParams)?.gravity ?: -1
            weight = value
        }
    }
inline var View.layout_gravity: Int
    get() {
        return -1
    }
    set(value) {
        updateLayoutParams<LinearLayout.LayoutParams> {
            weight = (layoutParams as? LinearLayout.LayoutParams)?.weight ?: 0f
            gravity = value
        }
    }

inline var View.layout_gravity2: Int
    get() {
        return -1
    }
    set(value) {
        updateLayoutParams<FrameLayout.LayoutParams> {
            gravity = value
        }
    }

inline var View.toCircleOf: String
    get() {
        return ""
    }
    set(value) {
        updateLayoutParams<ConstraintLayout.LayoutParams> {
            circleConstraint = value.toLayoutId()
        }
    }

inline var View.circle_radius: Int
    get() {
        return -1
    }
    set(value) {
        updateLayoutParams<ConstraintLayout.LayoutParams> {
            circleRadius = value.dp
        }
    }

inline var View.circle_angle: Float
    get() {
        return -1f
    }
    set(value) {
        updateLayoutParams<ConstraintLayout.LayoutParams> {
            circleAngle = value
        }
    }

inline var View.start_toStartOf: String
    get() {
        return ""
    }
    set(value) {
        updateLayoutParams<ConstraintLayout.LayoutParams> {
            startToStart = value.toLayoutId()
            startToEnd = -1
        }
    }

inline var View.start_toStartViewOf: View?
    get() {
        return null
    }
    set(value) {
        updateLayoutParams<ConstraintLayout.LayoutParams> {
            startToStart = value?.id ?: -1
            startToEnd = -1
        }
    }

inline var View.start_toEndOf: String
    get() {
        return ""
    }
    set(value) {
        updateLayoutParams<ConstraintLayout.LayoutParams> {
            startToEnd = value.toLayoutId()
            startToStart = -1
        }
    }

inline var View.start_toEndViewOf: View?
    get() {
        return null
    }
    set(value) {
        updateLayoutParams<ConstraintLayout.LayoutParams> {
            startToEnd = value?.id ?: -1
            startToStart = -1
        }
    }

inline var View.top_toBottomOf: String
    get() {
        return ""
    }
    set(value) {
        updateLayoutParams<ConstraintLayout.LayoutParams> {
            topToBottom = value.toLayoutId()
            topToTop = -1
        }
    }

inline var View.top_toBottomViewOf: View?
    get() {
        return null
    }
    set(value) {
        updateLayoutParams<ConstraintLayout.LayoutParams> {
            topToBottom = value?.id ?: -1
            topToTop = -1
        }
    }

inline var View.top_toTopOf: String
    get() {
        return ""
    }
    set(value) {
        updateLayoutParams<ConstraintLayout.LayoutParams> {
            topToTop = value.toLayoutId()
            topToBottom = -1
        }
    }

inline var View.top_toTopViewOf: View?
    get() {
        return null
    }
    set(value) {

        updateLayoutParams<ConstraintLayout.LayoutParams> {
            topToTop = value?.id ?: -1
            topToBottom = -1
        }
    }

inline var View.end_toEndOf: String
    get() {
        return ""
    }
    set(value) {
        updateLayoutParams<ConstraintLayout.LayoutParams> {
            endToEnd = value.toLayoutId()
            endToStart = -1
        }
    }

inline var View.end_toEndViewOf: View?
    get() {
        return null
    }
    set(value) {
        updateLayoutParams<ConstraintLayout.LayoutParams> {
            endToEnd = value?.id ?: -1
            endToStart = -1
        }
    }

inline var View.end_toStartOf: String
    get() {
        return ""
    }
    set(value) {
        updateLayoutParams<ConstraintLayout.LayoutParams> {
            endToStart = value.toLayoutId()
            endToEnd = -1
        }
    }

inline var View.end_toStartViewOf: View?
    get() {
        return null
    }
    set(value) {
        updateLayoutParams<ConstraintLayout.LayoutParams> {
            endToStart = value?.id ?: -1
            endToEnd = -1
        }
    }

inline var View.bottom_toBottomOf: String
    get() {
        return ""
    }
    set(value) {
        updateLayoutParams<ConstraintLayout.LayoutParams> {
            bottomToBottom = value.toLayoutId()
            bottomToTop = -1
        }
    }

inline var View.bottom_toBottomViewOf: View?
    get() {
        return null
    }
    set(value) {
        updateLayoutParams<ConstraintLayout.LayoutParams> {
            bottomToBottom = value?.id ?: -1
            bottomToTop = -1
        }
    }

inline var View.bottom_toTopOf: String
    get() {
        return ""
    }
    set(value) {
        updateLayoutParams<ConstraintLayout.LayoutParams> {
            bottomToTop = value.toLayoutId()
            bottomToBottom = -1
        }
    }

inline var View.bottom_toTopViewOf: View?
    get() {
        return null
    }
    set(value) {
        updateLayoutParams<ConstraintLayout.LayoutParams> {
            bottomToTop = value?.id ?: -1
            bottomToBottom = -1
        }
    }

inline var View.horizontal_chain_style: Int
    get() {
        return -1
    }
    set(value) {
        updateLayoutParams<ConstraintLayout.LayoutParams> {
            horizontalChainStyle = value
        }
    }

inline var View.vertical_chain_style: Int
    get() {
        return -1
    }
    set(value) {
        updateLayoutParams<ConstraintLayout.LayoutParams> {
            verticalChainStyle = value
        }
    }

inline var View.horizontal_bias: Float
    get() {
        return -1f
    }
    set(value) {
        updateLayoutParams<ConstraintLayout.LayoutParams> {
            horizontalBias = value
        }
    }

inline var View.dimension_radio: String
    get() {
        return ""
    }
    set(value) {
        updateLayoutParams<ConstraintLayout.LayoutParams> {
            dimensionRatio = value
        }
    }

inline var View.vertical_bias: Float
    get() {
        return -1f
    }
    set(value) {
        updateLayoutParams<ConstraintLayout.LayoutParams> {
            verticalBias = value
        }
    }

inline var View.center_horizontal: Boolean
    get() {
        return false
    }
    set(value) {
        if (!value) return
        start_toStartOf = parent_id
        end_toEndOf = parent_id
    }

inline var View.center_vertical: Boolean
    get() {
        return false
    }
    set(value) {
        if (!value) return
        top_toTopOf = parent_id
        bottom_toBottomOf = parent_id
    }

inline var View.align_vertical_to: String
    get() {
        return ""
    }
    set(value) {
        top_toTopOf = value
        bottom_toBottomOf = value
    }

inline var View.align_horizontal_to: String
    get() {
        return ""
    }
    set(value) {
        start_toStartOf = value
        end_toEndOf = value
    }

inline var View.width_percentage: Float
    get() {
        return -1f
    }
    set(value) {
        updateLayoutParams<ConstraintLayout.LayoutParams> {
            width = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT
            matchConstraintPercentWidth = value
        }
    }

inline var View.height_percentage: Float
    get() {
        return -1f
    }
    set(value) {
        updateLayoutParams<ConstraintLayout.LayoutParams> {
            height = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT
            matchConstraintPercentHeight = value
        }
    }

inline var View.background_color: String
    get() {
        return ""
    }
    set(value) {
        setBackgroundColor(Color.parseColor(value))
    }

inline var View.background_res: Int
    get() {
        return -1
    }
    set(value) {
        setBackgroundResource(value)
    }

inline var View.background_vector: Int
    get() {
        return -1
    }
    set(value) {
        val drawable = VectorDrawableCompat.create(context.getResources(), value, null)
        background = drawable
    }

inline var View.background_drawable: Drawable?
    get() {
        return null
    }
    set(value) {
        value?.let { background = it }
    }

inline var View.background_drawable_state_list: List<Pair<IntArray, Drawable>>
    get() {
        return listOf(intArrayOf() to GradientDrawable())
    }
    set(value) {
        background = StateListDrawable().apply {
            value.forEach { pair ->
                addState(pair.first, pair.second)
            }
        }
    }

inline var View.margin_top: Number
    get() {
        return -1
    }
    set(value) {
        updateLayoutParams<ViewGroup.MarginLayoutParams> {
            topMargin = value.dp
        }
    }

inline var View.margin_bottom: Number
    get() {
        return -1
    }
    set(value) {
        updateLayoutParams<ViewGroup.MarginLayoutParams> {
            bottomMargin = value.dp
        }
    }

inline var View.margin_start: Number
    get() {
        return -1
    }
    set(value) {
        updateLayoutParams<ViewGroup.MarginLayoutParams> {
            MarginLayoutParamsCompat.setMarginStart(this, value.dp)
        }
    }

inline var View.margin_end: Number
    get() {
        return -1
    }
    set(value) {
        updateLayoutParams<ViewGroup.MarginLayoutParams> {
            MarginLayoutParamsCompat.setMarginEnd(this, value.dp)
        }
    }

inline var View.margin_horizontal: Number
    get() {
        return -1
    }
    set(value) {
        updateLayoutParams<ViewGroup.MarginLayoutParams> {
            MarginLayoutParamsCompat.setMarginEnd(this, value.dp)
            MarginLayoutParamsCompat.setMarginStart(this, value.dp)
        }
    }

inline var View.margin_vertical: Number
    get() {
        return -1
    }
    set(value) {
        updateLayoutParams<ViewGroup.MarginLayoutParams> {
            topMargin = value.dp
            bottomMargin = value.dp
        }
    }

inline var View.left_percent: Float
    get() {
        return -1f
    }
    set(value) {
        updateLayoutParams<PercentLayout.LayoutParam> {
            leftPercent = value
        }
    }

inline var View.top_percent: Float
    get() {
        return -1f
    }
    set(value) {
        updateLayoutParams<PercentLayout.LayoutParam> {
            topPercent = value
        }
    }

inline var View.start_to_start_of_percent: String
    get() {
        return ""
    }
    set(value) {
        updateLayoutParams<PercentLayout.LayoutParam> {
            startToStartOf = value.toLayoutId()
        }
    }

inline var View.start_to_end_of_percent: String
    get() {
        return ""
    }
    set(value) {
        updateLayoutParams<PercentLayout.LayoutParam> {
            startToEndOf = value.toLayoutId()
        }
    }

inline var View.end_to_end_of_percent: String
    get() {
        return ""
    }
    set(value) {
        updateLayoutParams<PercentLayout.LayoutParam> {
            endToEndOf = value.toLayoutId()
        }
    }

inline var View.end_to_start_of_percent: String
    get() {
        return ""
    }
    set(value) {
        updateLayoutParams<PercentLayout.LayoutParam> {
            endToStartOf = value.toLayoutId()
        }
    }

inline var View.top_to_top_of_percent: String
    get() {
        return ""
    }
    set(value) {
        updateLayoutParams<PercentLayout.LayoutParam> {
            topToTopOf = value.toLayoutId()
        }
    }

inline var View.top_to_bottom_of_percent: String
    get() {
        return ""
    }
    set(value) {
        updateLayoutParams<PercentLayout.LayoutParam> {
            topToBottomOf = value.toLayoutId()
        }
    }

inline var View.bottom_to_bottom_of_percent: String
    get() {
        return ""
    }
    set(value) {
        updateLayoutParams<PercentLayout.LayoutParam> {
            bottomToBottomOf = value.toLayoutId()
        }
    }

inline var View.bottom_to_top_of_percent: String
    get() {
        return ""
    }
    set(value) {
        updateLayoutParams<PercentLayout.LayoutParam> {
            bottomToTopOf = value.toLayoutId()
        }
    }

inline var View.center_vertical_of_percent: String
    get() {
        return ""
    }
    set(value) {
        updateLayoutParams<PercentLayout.LayoutParam> {
            centerVerticalOf = value.toLayoutId()
        }
    }

inline var View.center_horizontal_of_percent: String
    get() {
        return ""
    }
    set(value) {
        updateLayoutParams<PercentLayout.LayoutParam> {
            centerHorizontalOf = value.toLayoutId()
        }
    }

inline var View.gone_margin_end: Number
    get() {
        return -1
    }
    set(value) {
        updateLayoutParams<ConstraintLayout.LayoutParams> {
            goneEndMargin = value.dp
        }
    }

inline var View.gone_margin_start: Number
    get() {
        return -1
    }
    set(value) {
        updateLayoutParams<ConstraintLayout.LayoutParams> {
            goneStartMargin = value.dp
        }
    }

inline var View.gone_margin_top: Number
    get() {
        return -1
    }
    set(value) {
        updateLayoutParams<ConstraintLayout.LayoutParams> {
            goneTopMargin = value.dp
        }
    }

inline var View.gone_margin_bottom: Number
    get() {
        return -1
    }
    set(value) {
        updateLayoutParams<ConstraintLayout.LayoutParams> {
            goneBottomMargin = value.dp
        }
    }

inline var View.guide_percentage: Float
    get() {
        return -1f
    }
    set(value) {
        updateLayoutParams<ConstraintLayout.LayoutParams> {
            guidePercent = value
        }
    }

inline var View.guide_orientation: Int
    get() {
        return 1
    }
    set(value) {
        updateLayoutParams<ConstraintLayout.LayoutParams> {
            orientation = value
        }
    }

inline var View.layout_visibility: Int
    get() {
        return -1
    }
    set(value) {
        visibility = value
    }

/**
 * bind async data
 */
inline var View.bindLiveData: LiveDataBinder?
    get() {
        return null
    }
    set(value) {
        observe(value?.liveData) {
            value?.action?.invoke(it)
        }
    }

/**
 * old fashion for binding data
 */
inline var View.bind: Binder?
    get() {
        return null
    }
    set(value) {
        value?.action?.invoke(this, value.data)
    }

/**
 * bind sync data
 */
inline var View.bindData: () -> Unit
    get() {
        return {}
    }
    set(value) {
        value()
    }

inline var View.fitsSystemWindows: Boolean
    get() {
        return false
    }
    set(value) {
        fitsSystemWindows = value
    }

/**
 * use this attribute to build shape dynamically, getting rid of "shape.xml"
 */
inline var View.shape: GradientDrawable
    get() {
        return GradientDrawable()
    }
    set(value) {
        background = value
    }

inline var ImageView.src: Int
    get() {
        return -1
    }
    set(value) {
        setImageResource(value)
    }

inline var ImageView.imageDrawable: Drawable?
    get() {
        return null
    }
    set(value) {
        setImageDrawable(value)
    }

inline var ImageView.bitmap: Bitmap?
    get() {
        return null
    }
    set(value) {
        setImageBitmap(value)
    }

inline var ImageView.vector_src: Int
    get() {
        return -1
    }
    set(value) {
        val src = VectorDrawableCompat.create(context.getResources(), value, null)
        setImageDrawable(src)
    }

inline var TextView.maxLength: Int
    get() {
        return 1
    }
    set(value) {
        filters = arrayOf<InputFilter>(LengthFilter(value))
    }

inline var TextView.textRes: Int
    get() {
        return -1
    }
    set(value) {
        setText(value)
    }

inline var TextView.hint_color: String
    get() {
        return ""
    }
    set(value) {
        setHintTextColor(Color.parseColor(value))
    }

inline var TextView.hint_text_res: Int
    get() {
        return -1
    }
    set(value) {
        setHint(value)
    }

inline var TextView.hint_text: String
    get() {
        return ""
    }
    set(value) {
        setHint(value)
    }

inline var TextView.line_space_multiplier: Float
    get() {
        return -1f
    }
    set(value) {
        setLineSpacing(lineSpacingExtra, value)
    }

inline var TextView.line_space_extra: Float
    get() {
        return -1f
    }
    set(value) {
        setLineSpacing(value, lineSpacingMultiplier)
    }

inline var TextView.textStyle: Int
    get() {
        return -1
    }
    set(value) = setTypeface(typeface, value)

inline var TextView.textColor: String
    get() {
        return ""
    }
    set(value) {
        setTextColor(Color.parseColor(value))
    }

inline var TextView.fontFamily: Int
    get() {
        return 1
    }
    set(value) {
        try {
            typeface = ResourcesCompat.getFont(context, value)
        } catch (e: Resources.NotFoundException) {
        }
    }

inline var TextView.drawable_start: Int
    get() {
        return -1
    }
    set(value) {
        setCompoundDrawablesRelativeWithIntrinsicBounds(value, 0, 0, 0)
    }

inline var TextView.drawable_end: Int
    get() {
        return -1
    }
    set(value) {
        setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, value, 0)
    }

inline var TextView.drawable_top: Int
    get() {
        return -1
    }
    set(value) {
        setCompoundDrawablesRelativeWithIntrinsicBounds(0, value, 0, 0)
    }

inline var TextView.drawable_bottom: Int
    get() {
        return -1
    }
    set(value) {
        setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, value)
    }

inline var TextView.drawable_padding: Int
    get() {
        return 0
    }
    set(value) {
        compoundDrawablePadding = value.dp
    }

inline var TextView.onTextChange: TextWatcher
    get() {
        return TextWatcher()
    }
    set(value) {
        val textWatcher = object : android.text.TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                value.afterTextChanged.invoke(s)
            }

            override fun beforeTextChanged(
                text: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                value.beforeTextChanged.invoke(text, start, count, after)
            }

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                value.onTextChanged.invoke(text, start, before, count)
            }
        }
        addTextChangedListener(textWatcher)
    }

inline var TextView.onEditorAction: EditorActionListener
    get() {
        return EditorActionListener()
    }
    set(value) {
        val editorActionListener = object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                return value.onEditorAction(v, actionId, event)
            }
        }
        setOnEditorActionListener(editorActionListener)
    }

inline var Button.textAllCaps: Boolean
    get() {
        return false
    }
    set(value) {
        isAllCaps = value
    }


inline var NestedScrollView.fadeScrollBar: Boolean
    get() {
        return false
    }
    set(value) {
        isScrollbarFadingEnabled = true
    }

inline var ConstraintHelper.referenceIds: String
    get() {
        return ""
    }
    set(value) {
        referencedIds = value.split(",").map { it.toLayoutId() }.toIntArray()
    }

inline var Flow.flow_horizontalGap: Int
    get() {
        return 0
    }
    set(value) {
        setHorizontalGap(value.dp)
    }

inline var Flow.flow_verticalGap: Int
    get() {
        return 0
    }
    set(value) {
        setVerticalGap(value.dp)
    }
inline var Flow.flow_wrapMode: Int
    get() {
        return 0
    }
    set(value) {
        setWrapMode(value)
    }

inline var Flow.flow_padding: Int
    get() {
        return 0
    }
    set(value) {
        setPadding(value.dp)
    }
inline var Flow.flow_paddingRight: Int
    get() {
        return 0
    }
    set(value) {
        setPaddingRight(value.dp)
    }

inline var Flow.flow_paddingTop: Int
    get() {
        return 0
    }
    set(value) {
        setPaddingTop(value.dp)
    }
inline var Flow.flow_paddingBottom: Int
    get() {
        return 0
    }
    set(value) {
        setPaddingBottom(value.dp)
    }
inline var Flow.flow_paddingLeft: Int
    get() {
        return 0
    }
    set(value) {
        setPaddingLeft(value.dp)
    }
inline var Flow.flow_orientation: Int
    get() {
        return 0
    }
    set(value) {
        setOrientation(value)
    }
inline var Flow.flow_horizontalStyle: Int
    get() {
        return 0
    }
    set(value) {
        setHorizontalStyle(value)
    }
inline var Flow.flow_verticalStyle: Int
    get() {
        return 0
    }
    set(value) {
        setVerticalStyle(value)
    }
inline var Flow.flow_horizontalBias: Float
    get() {
        return 0f
    }
    set(value) {
        setHorizontalBias(value)
    }
inline var Flow.flow_verticalBias: Float
    get() {
        return 0f
    }
    set(value) {
        setVerticalBias(value)
    }
inline var Flow.flow_horizontalAlign: Int
    get() {
        return 0
    }
    set(value) {
        setHorizontalAlign(value)
    }
inline var Flow.flow_verticalAlign: Int
    get() {
        return 0
    }
    set(value) {
        setVerticalAlign(value)
    }
inline var Flow.flow_maxElementWrap: Int
    get() {
        return 0
    }
    set(value) {
        setMaxElementsWrap(value)
    }

inline var ConstraintHelper.reference_ids: List<String>
    get() {
        return emptyList()
    }
    set(value) {
        referencedIds = value.map { it.toLayoutId() }.toIntArray()
    }

inline var Barrier.barrier_direction: Int
    get() {
        return -1
    }
    set(value) {
        type = value
    }

var View.onClick: (View) -> Unit
    get() {
        return {}
    }
    set(value) {
        setOnClickListener { v -> value(v) }
    }

var View.shakelessClick: (View) -> Unit
    get() {
        return {}
    }
    set(value) {
        setShakelessClickListener(1000) {
            value(it)
        }
    }

var RecyclerView.onItemClick: (View, Int, Float, Float) -> Boolean
    get() {
        return { _, _, _, _ -> false }
    }
    set(value) {
        setOnItemClickListener(value)
    }

var RecyclerView.onItemLongClick: (View, Int, Float, Float) -> Unit
    get() {
        return { _, _, _, _ -> }
    }
    set(value) {
        setOnItemLongClickListener(value)
    }

var RecyclerView.hasFixedSize: Boolean
    get() {
        return false
    }
    set(value) {
        setHasFixedSize(value)
    }
//</editor-fold>


//<editor-fold desc="View layout constant">
val match_parent = ViewGroup.LayoutParams.MATCH_PARENT
val wrap_content = ViewGroup.LayoutParams.WRAP_CONTENT

val visible = View.VISIBLE
val gone = View.GONE
val invisible = View.INVISIBLE

val horizontal = LinearLayout.HORIZONTAL
val vertical = LinearLayout.VERTICAL

val bold = Typeface.BOLD
val normal = Typeface.NORMAL
val italic = Typeface.ITALIC
val bold_italic = Typeface.BOLD_ITALIC

val gravity_center = Gravity.CENTER
val gravity_left = Gravity.LEFT
val gravity_right = Gravity.RIGHT
val gravity_bottom = Gravity.BOTTOM
val gravity_top = Gravity.TOP
val gravity_center_horizontal = Gravity.CENTER_HORIZONTAL
val gravity_center_vertical = Gravity.CENTER_VERTICAL

val scale_fit_xy = ImageView.ScaleType.FIT_XY
val scale_center_crop = ImageView.ScaleType.CENTER_CROP
val scale_center = ImageView.ScaleType.CENTER
val scale_center_inside = ImageView.ScaleType.CENTER_INSIDE
val scale_fit_center = ImageView.ScaleType.FIT_CENTER
val scale_fit_end = ImageView.ScaleType.FIT_END
val scale_matrix = ImageView.ScaleType.MATRIX
val scale_fit_start = ImageView.ScaleType.FIT_START

val constraint_start = ConstraintProperties.START
val constraint_end = ConstraintProperties.END
val constraint_top = ConstraintProperties.TOP
val constraint_bottom = ConstraintProperties.BOTTOM
val constraint_baseline = ConstraintProperties.BASELINE
val constraint_parent = ConstraintProperties.PARENT_ID

val spread = ConstraintLayout.LayoutParams.CHAIN_SPREAD
val packed = ConstraintLayout.LayoutParams.CHAIN_PACKED
val spread_inside = ConstraintLayout.LayoutParams.CHAIN_SPREAD_INSIDE

val barrier_left = Barrier.LEFT
val barrier_top = Barrier.TOP
val barrier_right = Barrier.RIGHT
val barrier_bottom = Barrier.BOTTOM
val barrier_start = Barrier.START
val barrier_end = Barrier.END

val wrap_none = Flow.WRAP_NONE
val wrap_chain = Flow.WRAP_CHAIN
val wrap_aligned = Flow.WRAP_ALIGNED

val gradient_top_bottom = GradientDrawable.Orientation.TOP_BOTTOM
val gradient_tr_bl = GradientDrawable.Orientation.TR_BL
val gradient_right_left = GradientDrawable.Orientation.RIGHT_LEFT
val gradient_br_tl = GradientDrawable.Orientation.BR_TL
val gradient_bottom_top = GradientDrawable.Orientation.BOTTOM_TOP
val gradient_bl_tr = GradientDrawable.Orientation.BL_TR
val gradient_left_right = GradientDrawable.Orientation.LEFT_RIGHT
val gradient_tl_br = GradientDrawable.Orientation.TL_BR

val shape_rectangle = GradientDrawable.RECTANGLE
val shape_oval = GradientDrawable.OVAL
val shape_line = GradientDrawable.LINE
val shape_ring = GradientDrawable.RING

val gradient_type_linear = GradientDrawable.LINEAR_GRADIENT
val gradient_type_radial = GradientDrawable.RADIAL_GRADIENT
val gradient_type_sweep = GradientDrawable.SWEEP_GRADIENT

val state_enable = android.R.attr.state_enabled
val state_disable = -android.R.attr.state_enabled
val state_pressed = android.R.attr.state_pressed
val state_unpressed = -android.R.attr.state_pressed
val state_focused = android.R.attr.state_focused
val state_unfocused = -android.R.attr.state_focused
val state_selected = android.R.attr.state_selected
val state_unselected = -android.R.attr.state_selected

val input_type_number = InputType.TYPE_CLASS_NUMBER

val wrap_mode_chain = Flow.WRAP_CHAIN
val wrap_mode_none = Flow.WRAP_NONE
val wrap_mode_aligned = Flow.WRAP_ALIGNED

val ellipsize_end = TextUtils.TruncateAt.END
val ellipsize_marquee = TextUtils.TruncateAt.MARQUEE
val ellipsize_middle = TextUtils.TruncateAt.MIDDLE
val ellipsize_start = TextUtils.TruncateAt.START

val parent_id = "0"
//</editor-fold>

//<editor-fold desc="layout helper function">
val Int.dp: Int
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    ).toInt()


val Float.dp: Float
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )

val Float.sp: Float
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        this,
        Resources.getSystem().displayMetrics
    )

val Number.dp: Int
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    ).toInt()

fun ViewGroup.MarginLayoutParams.toConstraintLayoutParam() =
    ConstraintLayout.LayoutParams(width, height).also { it ->
        it.topMargin = this.topMargin
        it.bottomMargin = this.bottomMargin
        it.marginStart = this.marginStart
        it.marginEnd = this.marginEnd
    }

/**
 * update LayoutParams according to it's type
 */
inline fun <reified T : ViewGroup.LayoutParams> View.updateLayoutParams(block: T.() -> Unit) {
    layoutParams = (layoutParams as? T)?.apply(block) ?: kotlin.run {
        val width = layoutParams?.width ?: 0
        val height = layoutParams?.height ?: 0
        val lp = ViewGroup.LayoutParams(width, height)
        new<T>(lp).apply(block)
    }
}

/**
 * create a new instance of [T] with [params]
 */
inline fun <reified T> new(vararg params: Any): T =
    T::class.java.getDeclaredConstructor(*params.map { it::class.java }.toTypedArray()).also { it.isAccessible = true }.newInstance(*params)

fun String.toLayoutId(): Int {
    var id = hashCode()
    if (this == parent_id) id = 0
    return abs(id)
}

fun DialogFragment.fullScreenMode() {
    dialog?.window?.apply {
        attributes?.apply {
            width = WindowManager.LayoutParams.MATCH_PARENT
            height = WindowManager.LayoutParams.MATCH_PARENT
        }
        decorView.setPadding(0, 0, 0, 0)
        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}

fun <T : View> View.find(id: String): T? = findViewById(id.toLayoutId())

fun <T : View> AppCompatActivity.find(id: String): T? = findViewById(id.toLayoutId())

/**
 * build a horizontal or vertical chain in [ConstraintLayout]
 * [startView] is the starting point of the chain
 * [endView] is the endding point of the chain
 * [views] is the body of the chain
 * [orientation] could be [horizontal] or [vertical]
 */
fun ConstraintLayout.buildChain(
    startView: View,
    views: List<View>,
    endView: View?,
    orientation: Int,
    outMarginStart: Int,
    outMarinEnd: Int,
    innerMargin: Int
) {
    if (views.isNullOrEmpty()) return
    var preView = startView
    var startSide = if (orientation == horizontal) constraint_start else constraint_top
    var endSide = if (orientation == horizontal) constraint_end else constraint_bottom

    val firstView = views.first()
    val isStartViewParent = firstView.isChildOf(startView)
    val isEndViewParent = firstView.isChildOf(endView)

    // deal with the first view
    ConstraintProperties(firstView)
        .connect(
            startSide,
            if (isStartViewParent) ConstraintProperties.PARENT_ID else preView.id,
            if (isStartViewParent) startSide else endSide,
            outMarginStart
        )
        .apply()

    preView = firstView

    (1 until views.size).map { views[it] }.forEach { currentView ->
        ConstraintProperties(currentView)
            .connect(startSide, preView.id, endSide, innerMargin)
            .apply()
        ConstraintProperties(preView)
            .connect(endSide, currentView.id, startSide, innerMargin)
            .apply()
        preView = currentView
    }

    // deal with the last view
    ConstraintProperties(preView)
        .connect(
            endSide,
            if (isEndViewParent) ConstraintProperties.PARENT_ID else endView?.id ?: ConstraintSet.UNSET,
            if (isEndViewParent) endSide else startSide,
            outMarinEnd
        )
        .apply()
}

fun View.isChildOf(view: View?) = view?.findViewById<View>(this.id) != null

fun <T> View.observe(liveData: LiveData<T>?, action: (T) -> Unit) {
    (context as? LifecycleOwner)?.let { owner ->
        liveData?.observe(owner, Observer { action(it) })
    }
}

fun RecyclerView.setOnItemLongClickListener(listener: (View, Int, Float, Float) -> Unit) {
    addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
        val gestureDetector = GestureDetector(context, object : GestureDetector.OnGestureListener {
            override fun onShowPress(e: MotionEvent) {
            }

            override fun onSingleTapUp(e: MotionEvent): Boolean {
                return false
            }

            override fun onDown(e: MotionEvent): Boolean {
                return false
            }

            override fun onFling(
                e1: MotionEvent,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                return false
            }

            override fun onScroll(
                e1: MotionEvent,
                e2: MotionEvent,
                distanceX: Float,
                distanceY: Float
            ): Boolean {
                return false
            }

            override fun onLongPress(e: MotionEvent) {
                e?.let {
                    findChildViewUnder(it.x, it.y)?.let { child ->
                        listener(
                            child,
                            getChildAdapterPosition(child),
                            it.x - child.left,
                            it.y - child.top
                        )
                    }
                }
            }
        })

        override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {

        }

        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
            gestureDetector.onTouchEvent(e)
            return false
        }

        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        }
    })
}

fun RecyclerView.setOnItemClickListener(listener: (View, Int, Float, Float) -> Boolean) {
    addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
        val gestureDetector = GestureDetector(context, object : GestureDetector.OnGestureListener {
            override fun onShowPress(e: MotionEvent) {
            }

            override fun onSingleTapUp(e: MotionEvent): Boolean {
                e?.let {
                    findChildViewUnder(it.x, it.y)?.let { child ->
                        return listener(child, getChildAdapterPosition(child), it.x - child.left, it.y - child.top)
                    }
                }
                return false
            }

            override fun onDown(e: MotionEvent): Boolean {
                return false
            }

            override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
                return false
            }

            override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
                return false
            }

            override fun onLongPress(e: MotionEvent) {
            }
        })

        override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {

        }

        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
            gestureDetector.onTouchEvent(e)
            return false
        }

        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        }
    })
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
 *  listen click action for the child view of [RecyclerView]'s item
 */
inline fun View.onChildViewClick(
    vararg layoutId: String, // the id of the child view of RecyclerView's item
    x: Float, // the x coordinate of click point
    y: Float,// the y coordinate of click point,
    clickAction: ((View?) -> Unit)
) {
    var clickedView: View? = null
    layoutId
        .map { id ->
            find<View>(id)?.takeIf { it.visibility == visible }?.let { view ->
                view.getRelativeRectTo(this).also { rect ->
                    if (rect.contains(x.toInt(), y.toInt())) {
                        clickedView = view
                    }
                }
            } ?: Rect()
        }
        .fold(Rect()) { init, rect -> init.apply { union(rect) } }
        .takeIf { it.contains(x.toInt(), y.toInt()) }
        ?.let { clickAction.invoke(clickedView) }
}

/**
 *  listen click action for the child view of [RecyclerView]'s item
 */
inline fun View.onChildViewClick(
    vararg layoutId: Int, // the id of the child view of RecyclerView's item
    x: Float, // the x coordinate of click point
    y: Float,// the y coordinate of click point,
    clickAction: ((View?) -> Unit)
) {
    var clickedView: View? = null
    layoutId
        .map { id ->
            findViewById<View>(id)?.takeIf { it.visibility == visible }?.let { view ->
                view.getRelativeRectTo(this).also { rect ->
                    if (rect.contains(x.toInt(), y.toInt())) {
                        clickedView = view
                    }
                }
            } ?: Rect()
        }
        .fold(Rect()) { init, rect -> init.apply { union(rect) } }
        .takeIf { it.contains(x.toInt(), y.toInt()) }
        ?.let { clickAction.invoke(clickedView) }
}

/**
 * a new View.OnClickListener which prevents click shaking
 */
fun View.setShakelessClickListener(threshold: Long, onClick: (View) -> Unit) {
    class Click(
        var view: View? = null,
        var clickTime: Long = -1,
        var onClick: ((View?) -> Unit)? = null
    ) {
        fun isShake(click: Click) = abs(clickTime - click.clickTime) < threshold
    }

    val mainScope = MainScope()
    val clickActor = mainScope.actor<Click>(capacity = Channel.UNLIMITED) {
        var preClick: Click = Click()
        for (click in channel) {
            if (!click.isShake(preClick)) {
                click.onClick?.invoke(click.view)
            }
            preClick = click
        }
    }.autoDispose(this)
    setOnClickListener { view ->
        mainScope.launch {
            clickActor.send(
                Click(view, System.currentTimeMillis()) { onClick(view) }
            )
        }.autoDispose(this)
    }
}

/**
 * a debounce listener for [EditText]'s text change event
 */
fun EditText.setDebounceListener(timeoutMillis: Long, action: (CharSequence) -> Unit) {
    val mainScope = MainScope()
    val textChangeActor = mainScope.actor<CharSequence>(capacity = Channel.UNLIMITED) {
        consumeAsFlow().debounce(timeoutMillis).collect { action.invoke(text) }
    }.autoDispose(this)

    onTextChange = textWatcher {
        onTextChanged = change@{ text: CharSequence?, _: Int, _: Int, _: Int ->
            mainScope.launch {
                text.toString().trim().let { textChangeActor.send(it) }
            }.autoDispose(this@setDebounceListener)
        }
    }
}


/**
 * avoid memory leak for View and activity when activity has finished while coroutine is still running
 */
fun Job.autoDispose(view: View?): Job {
    view ?: return this

    val listener = object : View.OnAttachStateChangeListener {
        override fun onViewDetachedFromWindow(v: View) {
            cancel()
            v?.removeOnAttachStateChangeListener(this)
        }

        override fun onViewAttachedToWindow(v: View) = Unit
    }

    view.addOnAttachStateChangeListener(listener)
    invokeOnCompletion {
        view.removeOnAttachStateChangeListener(listener)
    }
    return this
}

/**
 * avoid memory leak
 */
fun <T> SendChannel<T>.autoDispose(view: View?): SendChannel<T> {
    view ?: return this

    val isAttached = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && view.isAttachedToWindow || view.windowToken != null
    val listener = object : View.OnAttachStateChangeListener {
        override fun onViewDetachedFromWindow(v: View) {
            close()
            v?.removeOnAttachStateChangeListener(this)
        }

        override fun onViewAttachedToWindow(v: View) = Unit
    }

    view.addOnAttachStateChangeListener(listener)
    invokeOnClose {
        view.removeOnAttachStateChangeListener(listener)
    }
    return this
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
//</editor-fold>


//<editor-fold desc="listener helper class">
class TextWatcher(
    var beforeTextChanged: (
        text: CharSequence?,
        start: Int,
        count: Int,
        after: Int
    ) -> Unit = { _, _, _, _ -> },
    var onTextChanged: (
        text: CharSequence?,
        start: Int,
        count: Int,
        after: Int
    ) -> Unit = { _, _, _, _ -> },
    var afterTextChanged: (text: Editable?) -> Unit = {}
)

fun textWatcher(init: TextWatcher.() -> Unit): TextWatcher = TextWatcher().apply(init)

class EditorActionListener(
    var onEditorAction: (
        textView: TextView?,
        actionId: Int,
        keyEvent: KeyEvent?
    ) -> Boolean = { _, _, _ -> false }
)

fun editorAction(init: EditorActionListener.() -> Unit): EditorActionListener =
    EditorActionListener().apply(init)

/**
 * helper class for data binding
 */
class LiveDataBinder(var liveData: LiveData<*>? = null, var action: ((Any?) -> Unit)? = null)

fun liveDataBinder(liveData: LiveData<*>?, init: LiveDataBinder.() -> Unit): LiveDataBinder =
    LiveDataBinder(liveData).apply(init)

class Binder(var data: Any?, var action: ((View, Any?) -> Unit)? = null)
//</editor-fold>

//<editor-fold desc="building helper class">
/**
 * helper attribute for building [GradientDrawable]
 */
inline var GradientDrawable.solid_color: String
    get() {
        return ""
    }
    set(value) {
        setColor(Color.parseColor(value))
    }

inline var GradientDrawable.corner_radius: Int
    get() {
        return -1
    }
    set(value) {
        cornerRadius = value.dp.toFloat()
    }

inline var GradientDrawable.corner_radii: IntArray
    get() {
        return intArrayOf()
    }
    set(value) {
        cornerRadii = value.map { it.dp.toFloat() }.toFloatArray()
    }

inline var GradientDrawable.gradient_colors: List<String>
    get() {
        return emptyList()
    }
    set(value) {
        colors = value.map { Color.parseColor(it) }.toIntArray()
    }

inline var GradientDrawable.padding_start: Int
    get() {
        return -1
    }
    @RequiresApi(Build.VERSION_CODES.Q)
    set(value) {
        val paddingRect = Rect().also { getPadding(it) }
        setPadding(value.dp, paddingRect.top, paddingRect.right, paddingRect.bottom)
    }

inline var GradientDrawable.padding_end: Int
    get() {
        return -1
    }
    @RequiresApi(Build.VERSION_CODES.Q)
    set(value) {
        val paddingRect = Rect().also { getPadding(it) }
        setPadding(paddingRect.left, paddingRect.top, value.dp, paddingRect.bottom)
    }

inline var GradientDrawable.padding_top: Int
    get() {
        return -1
    }
    @RequiresApi(Build.VERSION_CODES.Q)
    set(value) {
        val paddingRect = Rect().also { getPadding(it) }
        setPadding(paddingRect.left, value.dp, paddingRect.right, paddingRect.bottom)
    }

inline var GradientDrawable.padding_bottom: Int
    get() {
        return -1
    }
    @RequiresApi(Build.VERSION_CODES.Q)
    set(value) {
        val paddingRect = Rect().also { getPadding(it) }
        setPadding(paddingRect.left, paddingRect.top, paddingRect.right, value.dp)
    }

inline var GradientDrawable.strokeAttr: Stroke?
    get() {
        return null
    }
    set(value) {
        value?.apply { setStroke(width.dp, Color.parseColor(color), dashWidth.dp, dashGap.dp) }
    }

inline var GradientDrawable.color_state_list: List<Pair<IntArray, String>>
    get() {
        return listOf(intArrayOf() to "#000000")
    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    set(value) {
        val states = mutableListOf<IntArray>()
        val colors = mutableListOf<Int>()
        value.forEach { pair ->
            states.add(pair.first)
            colors.add(Color.parseColor(pair.second))
        }
        color = ColorStateList(states.toTypedArray(), colors.toIntArray())
    }

/**
 * helper function for building [GradientDrawable]
 */
inline fun shape(init: GradientDrawable.() -> Unit) = GradientDrawable().apply(init)

/**
 * helper class for set stroke for [GradientDrawable]
 */
data class Stroke(
    var width: Int = 0,
    var color: String = "#000000",
    var dashWidth: Float = 0f,
    var dashGap: Float = 0f
)


/**
 * helper function for building [StateListDrawable]
 */
inline fun selector(init: StateListDrawable.() -> Unit) = StateListDrawable().apply(init)

inline var StateListDrawable.items: Map<IntArray, Drawable?>
    get() {
        return emptyMap()
    }
    set(value) {
        value.forEach { entry -> addState(entry.key, entry.value) }
    }

//</editor-fold>