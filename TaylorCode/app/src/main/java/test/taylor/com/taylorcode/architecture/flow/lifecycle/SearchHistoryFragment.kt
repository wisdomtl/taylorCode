package test.taylor.com.taylorcode.architecture.flow.lifecycle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import test.taylor.com.taylorcode.R
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.startActivity
import test.taylor.com.taylorcode.ui.flow.FlowActivity
import test.taylor.com.taylorcode.ui.fragment.visibility.IPvTracker

class SearchHistoryFragment : BaseFragment(), IPvTracker {

    private val fragment1 by lazy {
        SubFragment1()
    }

    private val fragment2 by lazy {
        SubFragment2()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return context?.run {
            ConstraintLayout {
                layout_width = match_parent
                layout_height = match_parent
                background_color = "#ff0000"

                LinearLayout {
                    layout_width = match_parent
                    layout_height = 80
                    orientation = horizontal


                    TextView {
                        layout_id = "tvchange2"
                        layout_width = wrap_content
                        layout_height = wrap_content
                        textSize = 15f
                        textColor = "#ffffff"
                        text = "activity"
                        fontFamily = R.font.pingfang
                        gravity = gravity_center
                        shape = shape {
                            solid_color = "#00ff00"
                            corner_radius = 20
                        }
                        onClick = {
                            startActivity<FlowActivity>()
                        }
                    }

                    TextView {
                        layout_id = "tvChange"
                        layout_width = wrap_content
                        layout_height = wrap_content
                        textSize = 15f
                        textColor = "#ffffff"
                        text = "add"
                        fontFamily = R.font.pingfang
                        gravity = gravity_center
                        shape = shape {
                            solid_color = "#00ff00"
                            corner_radius = 20
                        }
                        onClick = {
                            childFragmentManager.beginTransaction().add("container".toLayoutId(), fragment1).commit()
                        }
                    }

                    TextView {
                        layout_id = "tvChange"
                        layout_width = wrap_content
                        layout_height = wrap_content
                        textSize = 15f
                        textColor = "#ffffff"
                        text = "replace"
                        fontFamily = R.font.pingfang
                        gravity = gravity_center
                        shape = shape {
                            solid_color = "#00ff00"
                            corner_radius = 20
                        }
                        onClick = {
                            childFragmentManager.beginTransaction().replace("container".toLayoutId(), fragment2).addToBackStack(null).commit()
                        }
                    }

                    TextView {
                        layout_id = "tvChange"
                        layout_width = wrap_content
                        layout_height = wrap_content
                        textSize = 15f
                        textColor = "#ffffff"
                        text = "hide"
                        fontFamily = R.font.pingfang
                        gravity = gravity_center
                        shape = shape {
                            solid_color = "#00ff00"
                            corner_radius = 20
                        }
                        onClick = {
                            childFragmentManager.beginTransaction().hide(fragment2).commit()
                        }
                    }

                    TextView {
                        layout_id = "tvChange"
                        layout_width = wrap_content
                        layout_height = wrap_content
                        textSize = 15f
                        textColor = "#ffffff"
                        text = "show"
                        fontFamily = R.font.pingfang
                        gravity = gravity_center
                        shape = shape {
                            solid_color = "#00ff00"
                            corner_radius = 20
                        }
                        onClick = {
                            childFragmentManager.beginTransaction().show(fragment2).commit()
                        }
                    }

                    TextView {
                        layout_id = "tvChange"
                        layout_width = wrap_content
                        layout_height = wrap_content
                        textSize = 15f
                        textColor = "#ffffff"
                        text = "remove"
                        fontFamily = R.font.pingfang
                        gravity = gravity_center
                        shape = shape {
                            solid_color = "#00ff00"
                            corner_radius = 20
                        }
                        onClick = {
                            childFragmentManager.beginTransaction().remove(fragment2).commit()
                        }
                    }

                    TextView {
                        layout_id = "tvChange"
                        layout_width = wrap_content
                        layout_height = wrap_content
                        textSize = 15f
                        textColor = "#ffffff"
                        text = "detach"
                        fontFamily = R.font.pingfang
                        gravity = gravity_center
                        shape = shape {
                            solid_color = "#00ff00"
                            corner_radius = 20
                        }
                        onClick = {
                            childFragmentManager.beginTransaction().detach(fragment2).commit()
                        }
                    }

                    TextView {
                        layout_id = "tvChange"
                        layout_width = wrap_content
                        layout_height = wrap_content
                        textSize = 15f
                        textColor = "#ffffff"
                        text = "attach"
                        fontFamily = R.font.pingfang
                        gravity = gravity_center
                        shape = shape {
                            solid_color = "#00ff00"
                            corner_radius = 20
                        }
                        onClick = {
                            childFragmentManager.beginTransaction().attach(fragment2).commit()
                        }
                    }
                }

                FrameLayout {
                    layout_id = "container"
                    layout_width = match_parent
                    layout_height = 500
                    center_vertical = true
                    background_color = "#cc33cc"
                }
            }
        }
    }

    override fun getPvEventId(): String {
        return "SearchHistoryFragment"
    }

    override fun getPvExtra(): Bundle {
        return bundleOf()
    }
}