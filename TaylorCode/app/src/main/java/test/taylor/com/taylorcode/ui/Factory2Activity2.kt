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
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.LayoutInflaterCompat
import androidx.core.widget.NestedScrollView
import test.taylor.com.taylorcode.R
import test.taylor.com.taylorcode.kotlin.collection.*
import test.taylor.com.taylorcode.util.dp
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

class Factory2Activity2 : AppCompatActivity() {
    private var sum: Double = 0.0

    @ExperimentalTime
    override fun onCreate(savedInstanceState: Bundle?) {
        LayoutInflaterCompat.setFactory2(LayoutInflater.from(this@Factory2Activity2), object : LayoutInflater.Factory2 {
            override fun onCreateView(parent: View?, name: String?, context: Context?, attrs: AttributeSet?): View? {
                val (view, duration) = measureTimedValue { delegate.createView(parent, name, context!!, attrs!!) }
                sum += duration.inMilliseconds
                Log.v("ttaylor", "view=${view?.let { it::class.simpleName }} duration=${duration}  sum=${sum}")
                return view
            }

            override fun onCreateView(name: String?, context: Context?, attrs: AttributeSet?): View? {
                return null
            }
        })
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.factory2_activity2)
//        setContentView(buildView())
        setContentView(buildViewByDsl())
    }

    private fun buildViewByDsl(): View =
        LinearLayout {
            layout_width = match_parent
            layout_height = match_parent
            orientation = vertical

            RelativeLayout {
                layout_width = match_parent
                layout_height = 80
                padding_left = 20
                padding_top = 10
                padding_right = 20
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
                    centerVertical =true
                    src = R.drawable.ic_member_more
                }
            }

            View {
                layout_width = match_parent
                layout_height = 1
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


                LinearLayout(this@Factory2Activity2).apply {
                    layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
                        marginStart = 10f.dp()
                        marginEnd = 10f.dp()
                        topMargin = 20f.dp()
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