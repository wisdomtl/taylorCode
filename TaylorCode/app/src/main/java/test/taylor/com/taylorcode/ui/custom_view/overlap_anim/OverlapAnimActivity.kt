package test.taylor.com.taylorcode.ui.custom_view.overlap_anim

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import test.taylor.com.taylorcode.kotlin.ConstraintLayout
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.ui.StrokeImageView
import test.taylor.com.taylorcode.ui.performance.load

class OverlapAnimActivity : AppCompatActivity() {

    private val ITEM_DIMENSION = 50

    private val OVERLAP_MARGIN_START = 35

    val contentView by lazy {
        ConstraintLayout {
            layout_id = "root"
            layout_width = 250//todo:dynamically
            layout_height = ITEM_DIMENSION
            background_color = "#e8e8e8"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)

        lifecycleScope.launchWhenCreated {
            showOverlapAnimView(
                listOf(
                    "https://xiaored.oss-cn-hangzhou.aliyuncs.com/backend/party/hall/01111111111_1628221952063_0_w76h76.png",
                    "https://xiaoredpics.neoclub.cn/backend/appearance/defaultAvatar2.png",
                    "http://xiaoredpic.neoclub.cn/miaohong/user/custom/avatar/image/test/15209233/e845424c-4778-48a3-b699-6bc5bdc30f52.jpg",
                    "http://xiaoredpic.neoclub.cn/miaohong/user/custom/avatar/image/test/15233023/120f44fb-849c-4112-b8db-259331f2fc17.jpg",
                    "http://xiaoredpic.neoclub.cn/backend/appearance/default/avatar/male001.png"
                )
            )
        }
    }

    private fun showOverlapAnimView(urls: List<String>) {
        var lastView: View = contentView
        urls.forEachIndexed { index, url ->
            contentView.apply {
                layout_width = urls.size * ITEM_DIMENSION - (urls.size - 1) * (ITEM_DIMENSION - OVERLAP_MARGIN_START)
                StrokeImageView(context).apply {
                    layout_id = "ivOverlap$index"
                    layout_width = ITEM_DIMENSION
                    layout_height = ITEM_DIMENSION
                    scaleType = scale_center_crop
                    start_toStartViewOf = lastView
                    top_toTopViewOf = lastView
                    bottom_toBottomViewOf = lastView
                    roundedAsCircle = true
                    if (index != 0) margin_start = OVERLAP_MARGIN_START
                    load(url)
                }.also {
                    addView(it)
                    lastView = it
                }
            }
        }
    }


}