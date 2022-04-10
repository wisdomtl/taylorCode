package test.taylor.com.taylorcode.ui

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.LayoutInflaterCompat
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import test.taylor.com.taylorcode.R
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.kotlin.extension.addItemInOutListener
import test.taylor.com.taylorcode.kotlin.override_property.ColorBean
import test.taylor.com.taylorcode.kotlin.override_property.MyAdapter
import test.taylor.com.taylorcode.kotlin.override_property.MyBean
import test.taylor.com.taylorcode.kotlin.override_property.MyViewHolder
import test.taylor.com.taylorcode.util.dp
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

class Factory2Activity2 : AppCompatActivity() {
    private var sum: Double = 0.0
    private lateinit var rv: RecyclerView

    @ExperimentalTime
    override fun onCreate(savedInstanceState: Bundle?) {
        LayoutInflaterCompat.setFactory2(LayoutInflater.from(this@Factory2Activity2), object : LayoutInflater.Factory2 {

            override fun onCreateView(
                parent: View?,
                name: String,
                context: Context,
                attrs: AttributeSet
            ): View? {
                val (view, duration) = measureTimedValue { delegate.createView(parent, name, context!!, attrs!!) }
                sum += duration.inMilliseconds
                Log.v("ttaylor", "view=${view?.let { it::class.simpleName }} duration=${duration}  sum=${sum}")
                return view
            }

            override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
                val v: String = "dfd"
                return null
            }
        })
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.factory2_activity2)
//        setContentView(R.layout.factory2_activity2_cl)
//        setContentView(buildView())
//        setContentView(buildViewByDsl())
        setContentView(buildViewByClDsl())
        initView()
    }

    private fun initView() {
        rv.layoutManager = LinearLayoutManager(this)
        val beans =
            listOf(
                MyBean("a"),
                MyBean("b"),
                MyBean("c"),
                MyBean("d"),
                MyBean("e"),
                MyBean("f"),
                MyBean("h"),
                MyBean("i"),
                MyBean("j"),
                MyBean("k"),
                MyBean("l"),
                MyBean("m"),
                MyBean("o"),
                MyBean("p"),
                MyBean("q")
            )
        val adapter: RecyclerView.Adapter<MyViewHolder> = object : MyAdapter(beans) {
            override val color: ColorBean?
                get() = ColorBean("#ff00ff")
        }
        rv.adapter = adapter

        rv.addItemInOutListener { childView, adapterIndex, inOrOut ->
            Log.v("ttaylor", "tag=12345, Factory2Activity2.initView() item(${beans[adapterIndex]}) is ${if (inOrOut == 1) "in" else if(inOrOut == 0) "out" else "fully visible"} ")
        }
    }

    private fun buildViewByClDsl(): View =
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent
            background_color = "#ffff00"

            ImageView {
                layout_id = "ivBack"
                layout_width = 40
                layout_height = 40
                margin_start = 20
                margin_top = 20
                src = R.drawable.ic_back_black
                start_toStartOf = parent_id
                top_toTopOf = parent_id
                onClick = { onBackClick() }
            }

            TextView {
                layout_width = 0
                layout_height = 70
                text = "commit"
                textSize = 30f
                textStyle = bold
                gravity = gravity_center_horizontal + gravity_top
                align_vertical_to = "ivBack"
                center_horizontal = true
            }

            ImageView {
                layout_width = 40
                layout_height = 40
                src = R.drawable.ic_member_more
                align_vertical_to = "ivBack"
                end_toEndOf = parent_id
                margin_end = 20
            }

            View {
                layout_id = "vDivider"
                layout_width = match_parent
                layout_height = 1
                margin_top = 10
                background_color = "#eeeeee"
                top_toBottomOf = "ivBack"
            }

            Layer {
                layout_id = "layer"
                layout_width = wrap_content
                layout_height = wrap_content
                referenceIds = "ivDiamond,tvTitle,tvContent,ivAvatar,tvTime,tvSub"
                background_res = R.drawable.tag_checked_shape
                start_toStartOf = "ivDiamond"
                top_toTopOf = "ivDiamond"
                bottom_toBottomOf = "tvTime"
                end_toEndOf = "tvTime"
            }

            ImageView {
                layout_id = "ivDiamond"
                layout_width = 40
                layout_height = 40
                margin_start = 20
                margin_top = 40
                src = R.drawable.diamond_tag
                start_toStartOf = "ivBack"
                top_toBottomOf = "vDivider"
            }

            TextView {
                layout_id = "tvTitle"
                layout_width = wrap_content
                layout_height = wrap_content
                margin_start = 5
                gravity = gravity_center
                text = "gole"
                padding = 10
                textColor = "#389793"
                textSize = 20f
                textStyle = bold
                align_vertical_to = "ivDiamond"
                start_toEndOf = "ivDiamond"
            }

            TextView {
                layout_id = "tvContent"
                layout_width = 0
                layout_height = wrap_content
                margin_top = 5
                text = "The changes were merged into release with so many bugs"
                textSize = 23f
                start_toStartOf = "ivDiamond"
                top_toBottomOf = "ivDiamond"
                end_toStartOf = "ivAvatar"
            }

            ImageView {
                layout_id = "ivAvatar"
                layout_width = 100
                layout_height = 100
                margin_end = 20
                src = R.drawable.user_portrait_gender_female
                end_toEndOf = parent_id
                start_toEndOf = "tvContent"
                top_toTopOf = "tvContent"
            }

            TextView {
                layout_id = "tvSub"
                layout_width = wrap_content
                layout_height = wrap_content
                text = "merge it with mercy"
                textColor = "#c4747E8B"
                textSize = 18f
                start_toStartOf = "ivDiamond"
                top_toBottomOf = "tvContent"
            }

            TextView {
                layout_id = "tvTime"
                layout_width = wrap_content
                layout_height = wrap_content
                margin_top = 20
                text = "2020.04.30"
                end_toEndOf = "ivAvatar"
                top_toBottomOf = "ivAvatar"
            }

            TextView {
                layout_id = "tvCancel"
                layout_width = wrap_content
                layout_height = wrap_content
                margin_end = 30
                background_res = R.drawable.bg_orange_btn
                padding_start = 30
                padding_top = 10
                padding_end = 30
                padding_bottom = 10
                text = "cancel"
                margin_bottom = 20
                textSize = 20f
                textStyle = bold
                bottom_toBottomOf = parent_id
                end_toStartOf = "tvOk"
                start_toStartOf = parent_id
                horizontal_chain_style = packed
            }

            TextView {
                layout_id = "tvOk"
                layout_width = wrap_content
                layout_height = wrap_content
                background_res = R.drawable.bg_orange_btn
                padding_start = 30
                padding_top = 10
                margin_bottom = 20
                padding_end = 30
                padding_bottom = 10
                text = "Ok"
                textSize = 20f
                textStyle = bold
                bottom_toBottomOf = parent_id
                end_toEndOf = parent_id
                horizontal_chain_style = packed
                start_toEndOf = "tvCancel"
            }

            rv = RecyclerView {
                layout_id = "rvTest"
                layout_width = match_parent
                layout_height = 0
                top_toBottomOf = "tvTime"
                bottom_toBottomOf = parent_id
                onItemClick = { v, i, x, y -> onItemClickEvent(v, i) }
            }

        }

    private fun onItemClickEvent(v: View, i: Int):Boolean {
        Log.v("ttaylor", "tag=, Factory2Activity2.onItemClickEvent()  index=${i}")
        return false
    }

    private fun onBackClick() {
        finish()
    }

    private fun buildViewByDsl(): View =
        LinearLayout {
            layout_width = match_parent
            layout_height = match_parent
            orientation = vertical

            RelativeLayout {
                layout_width = match_parent
                layout_height = 80
                padding_start = 20
                padding_top = 10
                padding_end = 20
                padding_bottom = 10

                ImageView {
                    layout_width = 40
                    layout_height = 40
                    alignParentStart = true
                    centerVertical = true
                    src = R.drawable.ic_back_black
                }

                TextView {
                    layout_width = wrap_content
                    layout_height = wrap_content
                    centerInParent = true
                    text = "commit"
                    textSize = 30f
                    textStyle = bold
                }

                ImageView {
                    layout_width = 40
                    layout_height = 40
                    alignParentEnd = true
                    centerVertical = true
                    src = R.drawable.ic_member_more
                }
            }

            View {
                layout_width = match_parent
                layout_height = 1
                background_color = "#eeeeee"
            }



            LinearLayout {
                layout_width = match_parent
                layout_height = wrap_content
                orientation = vertical
                margin_start = 10
                margin_end = 10
                background_res = R.drawable.tag_checked_shape

                LinearLayout {
                    layout_width = wrap_content
                    layout_height = wrap_content
                    orientation = horizontal

                    ImageView {
                        layout_width = 40
                        layout_height = 40
                        src = R.drawable.diamond_tag
                    }

                    TextView {
                        layout_width = wrap_content
                        layout_height = wrap_content
                        margin_start = 10
                        gravity = gravity_center
                        text = "gole"
                        textColor = "#398793"
                        textSize = 20f
                        textStyle = bold
                    }
                }

                LinearLayout {
                    layout_width = match_parent
                    layout_height = wrap_content
                    orientation = horizontal
                    weightSum = 8f

                    LinearLayout {
                        layout_width = wrap_content
                        layout_height = wrap_content
                        weight = 5f
                        orientation = vertical

                        TextView {
                            layout_width = wrap_content
                            layout_height = wrap_content
                            text = "The changes were merged into release with so many bugs"
                            textSize = 23f
                        }

                        TextView {
                            layout_width = wrap_content
                            layout_height = wrap_content
                            textColor = "#c4747E8B"
                            text = "merge it with mercy"
                            textSize = 18f
                        }
                    }

                    ImageView {
                        layout_width = 100
                        layout_height = 100
                        weight = 3f
                        scaleType = scale_fit_xy
                        src = R.drawable.user_portrait_gender_female
                    }
                }

                RelativeLayout {
                    layout_width = match_parent
                    layout_height = wrap_content
                    margin_top = 10
                    padding_start = 0
                    padding_top = 0
                    padding_end = 10
                    padding_bottom = 10

                    TextView {
                        layout_width = wrap_content
                        layout_height = wrap_content
                        alignParentEnd = true
                        text = "2020.04.30"
                    }
                }
            }
            View {
                layout_width = match_parent
                layout_height = 1
                background_color = "#eeeeee"
            }

            RelativeLayout {
                layout_width = match_parent
                layout_height = wrap_content
                margin_top = 40

                LinearLayout {
                    layout_width = wrap_content
                    layout_height = wrap_content
                    centerInParent = true
                    orientation = horizontal

                    Button {
                        layout_width = wrap_content
                        layout_height = wrap_content
                        layout_gravity = gravity_left
                        margin_end = 20
                        background_res = R.drawable.bg_orange_btn
                        text = "cancel"
                    }

                    Button {
                        layout_width = wrap_content
                        layout_height = wrap_content
                        margin_start = 20
                        layout_gravity = gravity_right
                        background_res = R.drawable.bg_orange_btn
                        text = "OK"
                    }
                }
            }
        }

    private fun buildView(): View {
        return LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

            RelativeLayout(this@Factory2Activity2).apply {
                layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 80f.dp())
                setPadding(20f.dp(), 10f.dp(), 20.0f.dp(), 10f.dp())

                ImageView(this@Factory2Activity2).apply {
                    layoutParams = RelativeLayout.LayoutParams(40f.dp(), 40f.dp()).apply {
                        addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE)
                        addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE)
                    }
                    setImageResource(R.drawable.ic_back_black)
                }.also { addView(it) }

                TextView(this@Factory2Activity2).apply {
                    layoutParams =
                        RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT).apply {
                            addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE)
                        }
                    text = "commit"
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, 30f)
                    setTypeface(null, Typeface.BOLD)
                }.also { addView(it) }

                ImageView(this@Factory2Activity2).apply {
                    layoutParams =
                        RelativeLayout.LayoutParams(40f.dp(), 40f.dp()).apply {
                            addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE)
                            addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE)
                        }
                    setImageResource(R.drawable.ic_member_more)
                }.also { addView(it) }
            }.also { addView(it) }

            View(this@Factory2Activity2).apply {
                layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1f.dp())
                setBackgroundColor(Color.parseColor("#eeeeee"))
            }.also { addView(it) }


            NestedScrollView(this@Factory2Activity2).apply {
                layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 500f.dp()).apply {
                    topMargin = 20f.dp()
                }
                isScrollbarFadingEnabled = true

                LinearLayout(this@Factory2Activity2).apply {
                    layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    orientation = LinearLayout.VERTICAL
                    setPadding(5f.dp(), 5f.dp(), 30f.dp(), 30f.dp())

                    LinearLayout(this@Factory2Activity2).apply {
                        layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
                            marginStart = 10f.dp()
                            marginEnd = 10f.dp()
                        }
                        orientation = LinearLayout.VERTICAL
                        setBackgroundResource(R.drawable.tag_checked_shape)

                        LinearLayout(this@Factory2Activity2).apply {
                            layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                            orientation = LinearLayout.HORIZONTAL

                            ImageView(this@Factory2Activity2).apply {
                                layoutParams = LinearLayout.LayoutParams(40f.dp(), 40f.dp())
                                setImageResource(R.drawable.diamond_tag)
                            }.also { addView(it) }

                            TextView(this@Factory2Activity2).apply {
                                layoutParams =
                                    LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
                                        marginStart = 10f.dp()
                                    }
                                gravity = Gravity.CENTER
                                setPadding(10f.dp(), 10f.dp(), 10f.dp(), 10f.dp())
                                text = "gole"
                                setTextColor(Color.parseColor("#389793"))
                                setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
                                this.setTypeface(null, Typeface.BOLD)

                            }.also { addView(it) }
                        }.also { addView(it) }

                        LinearLayout(this@Factory2Activity2).apply {
                            layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                            orientation = LinearLayout.HORIZONTAL
                            weightSum = 8f

                            LinearLayout(this@Factory2Activity2).apply {
                                layoutParams =
                                    LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
                                        weight = 5f
                                    }
                                orientation = LinearLayout.VERTICAL

                                TextView(this@Factory2Activity2).apply {
                                    layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                    text = "The changes were merged into release with so many bugs"
                                    setTextSize(TypedValue.COMPLEX_UNIT_SP, 23f)
                                }.also { addView(it) }

                                TextView(this@Factory2Activity2).apply {
                                    layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                    text = "merge it with mercy"
                                    setTextColor(Color.parseColor("#c4747E8B"))
                                    setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
                                }.also { addView(it) }

                            }.also { addView(it) }
                            ImageView(this@Factory2Activity2).apply {
                                layoutParams = LinearLayout.LayoutParams(100f.dp(), 100f.dp()).apply {
                                    weight = 3f
                                }
                                scaleType = ImageView.ScaleType.FIT_XY
                                setImageResource(R.drawable.user_portrait_gender_female)
                            }.also { addView(it) }
                        }.also { addView(it) }

                        RelativeLayout(this@Factory2Activity2).apply {
                            layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
                                topMargin = 10f.dp()
                            }
                            setPadding(0, 0, 10f.dp(), 10f.dp())

                            TextView(this@Factory2Activity2).apply {
                                layoutParams =
                                    RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
                                        .apply {
                                            addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE)
                                        }
                                text = "2020.04.30"
                            }.also { addView(it) }
                        }.also { addView(it) }
                    }.also { addView(it) }
                }.also { addView(it) }
            }.also { addView(it) }

            View(this@Factory2Activity2).apply {
                layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1f.dp())
                setBackgroundColor(Color.parseColor("#eeeeee"))

            }.also { addView(it) }

            RelativeLayout(this@Factory2Activity2).apply {
                layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                    topMargin = 40f.dp()
                }

                LinearLayout(this@Factory2Activity2).apply {
                    layoutParams = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
                        addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE)
                    }
                    orientation = LinearLayout.HORIZONTAL

                    Button(this@Factory2Activity2).apply {
                        layoutParams =
                            LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                                rightMargin = 20f.dp()
                                gravity = Gravity.LEFT
                            }
                        setBackgroundResource(R.drawable.bg_orange_btn)
                        text = "cancel"
                    }.also {
                        addView(it)
                    }
                    Button(this@Factory2Activity2).apply {
                        layoutParams =
                            LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                                leftMargin = 20f.dp()
                                gravity = Gravity.RIGHT
                            }
                        setBackgroundResource(R.drawable.bg_orange_btn)
                        text = "OK"
                    }.also { addView(it) }

                }.also { addView(it) }

            }.also { addView(it) }
        }
    }
}