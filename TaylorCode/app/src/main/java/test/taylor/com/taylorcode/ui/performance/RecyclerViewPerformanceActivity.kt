package test.taylor.com.taylorcode.ui.performance

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.FrameMetrics
import android.view.Window
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.ui.performance.better_performance.BetterRank
import test.taylor.com.taylorcode.ui.performance.better_performance.BetterRankProxy
import test.taylor.com.taylorcode.ui.recyclerview.variety.VarietyAdapter2

class RecyclerViewPerformanceActivity : AppCompatActivity() {
    private val myAdapter = VarietyAdapter2().apply {
        addProxy(HeaderProxy())
        addProxy(RankProxy())
        addProxy(BetterRankProxy())
    }

    private val contentView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent
            background_color = "#FFEFF6"

            RecyclerView {
                layout_width = match_parent
                layout_height = match_parent
                margin_horizontal = 10
                adapter = myAdapter
                margin_top =  20
                layoutManager = LinearLayoutManager(this@RecyclerViewPerformanceActivity)
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)

        detectFrame()

//        bindData1()
        bindData2()
    }

    private fun bindData2() {
        myAdapter.dataList = listOf(
            BetterRank(
                "排名", "主播", "粉丝数", "本周新增",
                listOf(
                    Rank(
                        1,
                        "小红",
                        19203,
                        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic.90sjimg.com%2Fdesign%2F02%2F02%2F08%2F14%2F5a44ec4e1c829.png&refer=http%3A%2F%2Fpic.90sjimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1616931237&t=a265e1a71e69f2b2d57e7dcc5cb5b769",
                        "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2729107397,409883431&fm=26&gp=0.jpg"
                    ),
                    Rank(
                        2,
                        "小红",
                        309293,
                        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic.90sjimg.com%2Fdesign%2F00%2F58%2F41%2F78%2F5933cf3f09c71.png&refer=http%3A%2F%2Fpic.90sjimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1616930901&t=3b79951168c9d6bfc69233c2c8eb0c7e",
                        "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2729107397,409883431&fm=26&gp=0.jpg"
                    ),
                    Rank(
                        3,
                        "小里",
                        193930,
                        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic.90sjimg.com%2Fdesign%2F00%2F58%2F41%2F78%2F5933cf3f09c71.png&refer=http%3A%2F%2Fpic.90sjimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1616930901&t=3b79951168c9d6bfc69233c2c8eb0c7e",
                        "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2729107397,409883431&fm=26&gp=0.jpg"
                    ),
                    Rank(
                        4,
                        "小腾",
                        1999990,
                        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic.90sjimg.com%2Fdesign%2F02%2F02%2F08%2F14%2F5a44ec4e1c829.png&refer=http%3A%2F%2Fpic.90sjimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1616931237&t=a265e1a71e69f2b2d57e7dcc5cb5b769",
                        "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2729107397,409883431&fm=26&gp=0.jpg"
                    ),
                    Rank(
                        5,
                        "天青色",
                        19939330,
                        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic.90sjimg.com%2Fdesign%2F00%2F58%2F41%2F78%2F5933cf3f09c71.png&refer=http%3A%2F%2Fpic.90sjimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1616930901&t=3b79951168c9d6bfc69233c2c8eb0c7e",
                        "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2729107397,409883431&fm=26&gp=0.jpg"
                    ),
                    Rank(
                        6,
                        "等烟雨来,就是不来",
                        19090990,
                        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpngimg.com%2Fuploads%2Fxbox%2Fxbox_PNG17507.png&refer=http%3A%2F%2Fpngimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1616931278&t=3faf6ba95b0ea1fedd21bb7ccc965477",
                        "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2729107397,409883431&fm=26&gp=0.jpg"
                    ),
                    Rank(
                        7,
                        "急死你,我就不来",
                        190,
                        "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1405706906,3786206755&fm=26&gp=0.jpg",
                        "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2729107397,409883431&fm=26&gp=0.jpg"
                    ),
                    Rank(
                        8,
                        "你拿我怎么办呢,嘿嘿",
                        190,
                        "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3544022885,3616110694&fm=26&gp=0.jpg",
                        "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2729107397,409883431&fm=26&gp=0.jpg"
                    ),
                    Rank(
                        9,
                        "不来就不来,爱来不来",
                        138239,
                        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic.90sjimg.com%2Fdesign%2F00%2F58%2F41%2F78%2F5933cf3f09c71.png&refer=http%3A%2F%2Fpic.90sjimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1616930901&t=3b79951168c9d6bfc69233c2c8eb0c7e",
                        "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2729107397,409883431&fm=26&gp=0.jpg"
                    ),
                    Rank(
                        10,
                        "呵呵呵呵呵呵呵呵",
                        382989238,
                        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic.90sjimg.com%2Fdesign%2F02%2F03%2F48%2F08%2F5a44fc71b9e36.png&refer=http%3A%2F%2Fpic.90sjimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1616931214&t=9efa218eec84e6ac2e37a65587f97738",
                        "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2729107397,409883431&fm=26&gp=0.jpg"
                    ),
                    Rank(
                        11,
                        "不去吧,,d,d,d,nazme",
                        3223,
                        "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1587290859,1459340053&fm=26&gp=0.jpg",
                        "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2729107397,409883431&fm=26&gp=0.jpg"
                    ),
                    Rank(
                        12,
                        "呵呵呵呵呵呵呵呵",
                        382989238,
                        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic.90sjimg.com%2Fdesign%2F02%2F03%2F48%2F08%2F5a44fc71b9e36.png&refer=http%3A%2F%2Fpic.90sjimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1616931214&t=9efa218eec84e6ac2e37a65587f97738",
                        "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2729107397,409883431&fm=26&gp=0.jpg"
                    ),
                    Rank(
                        13,
                        "呵呵呵呵呵呵呵呵",
                        382989238,
                        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic.90sjimg.com%2Fdesign%2F02%2F03%2F48%2F08%2F5a44fc71b9e36.png&refer=http%3A%2F%2Fpic.90sjimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1616931214&t=9efa218eec84e6ac2e37a65587f97738",
                        "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2729107397,409883431&fm=26&gp=0.jpg"
                    ),
                    Rank(
                        14,
                        "呵呵呵呵呵呵呵呵",
                        382989238,
                        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic.90sjimg.com%2Fdesign%2F02%2F03%2F48%2F08%2F5a44fc71b9e36.png&refer=http%3A%2F%2Fpic.90sjimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1616931214&t=9efa218eec84e6ac2e37a65587f97738",
                        "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2729107397,409883431&fm=26&gp=0.jpg"
                    ),
                    Rank(
                        15,
                        "呵呵呵呵呵呵呵呵",
                        382989238,
                        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic.90sjimg.com%2Fdesign%2F02%2F03%2F48%2F08%2F5a44fc71b9e36.png&refer=http%3A%2F%2Fpic.90sjimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1616931214&t=9efa218eec84e6ac2e37a65587f97738",
                        "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2729107397,409883431&fm=26&gp=0.jpg"
                    ),
                    Rank(
                        16,
                        "呵呵呵呵呵呵呵呵",
                        382989238,
                        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic.90sjimg.com%2Fdesign%2F02%2F03%2F48%2F08%2F5a44fc71b9e36.png&refer=http%3A%2F%2Fpic.90sjimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1616931214&t=9efa218eec84e6ac2e37a65587f97738",
                        "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2729107397,409883431&fm=26&gp=0.jpg"
                    ),
                    Rank(
                        17,
                        "呵呵呵呵呵呵呵呵",
                        382989238,
                        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic.90sjimg.com%2Fdesign%2F02%2F03%2F48%2F08%2F5a44fc71b9e36.png&refer=http%3A%2F%2Fpic.90sjimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1616931214&t=9efa218eec84e6ac2e37a65587f97738",
                        "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2729107397,409883431&fm=26&gp=0.jpg"
                    ),
                    Rank(
                        18,
                        "呵呵呵呵呵呵呵呵",
                        382989238,
                        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic.90sjimg.com%2Fdesign%2F02%2F03%2F48%2F08%2F5a44fc71b9e36.png&refer=http%3A%2F%2Fpic.90sjimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1616931214&t=9efa218eec84e6ac2e37a65587f97738",
                        "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2729107397,409883431&fm=26&gp=0.jpg"
                    )
                )
            )
        )
    }

    private fun bindData1() {
        myAdapter.dataList = listOf(
            Header("排名", "主播", "粉丝数", "本周新增"),
            Rank(
                1,
                "小红",
                19203,
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic.90sjimg.com%2Fdesign%2F02%2F02%2F08%2F14%2F5a44ec4e1c829.png&refer=http%3A%2F%2Fpic.90sjimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1616931237&t=a265e1a71e69f2b2d57e7dcc5cb5b769",
                "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2729107397,409883431&fm=26&gp=0.jpg"
            ),
            Rank(
                2,
                "小红",
                309293,
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic.90sjimg.com%2Fdesign%2F00%2F58%2F41%2F78%2F5933cf3f09c71.png&refer=http%3A%2F%2Fpic.90sjimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1616930901&t=3b79951168c9d6bfc69233c2c8eb0c7e",
                "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2729107397,409883431&fm=26&gp=0.jpg"
            ),
            Rank(
                3,
                "小里",
                193930,
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic.90sjimg.com%2Fdesign%2F00%2F58%2F41%2F78%2F5933cf3f09c71.png&refer=http%3A%2F%2Fpic.90sjimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1616930901&t=3b79951168c9d6bfc69233c2c8eb0c7e",
                "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2729107397,409883431&fm=26&gp=0.jpg"
            ),
            Rank(
                4,
                "小腾",
                1999990,
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic.90sjimg.com%2Fdesign%2F02%2F02%2F08%2F14%2F5a44ec4e1c829.png&refer=http%3A%2F%2Fpic.90sjimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1616931237&t=a265e1a71e69f2b2d57e7dcc5cb5b769",
                "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2729107397,409883431&fm=26&gp=0.jpg"
            ),
            Rank(
                5,
                "天青色",
                19939330,
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic.90sjimg.com%2Fdesign%2F00%2F58%2F41%2F78%2F5933cf3f09c71.png&refer=http%3A%2F%2Fpic.90sjimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1616930901&t=3b79951168c9d6bfc69233c2c8eb0c7e",
                "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2729107397,409883431&fm=26&gp=0.jpg"
            ),
            Rank(
                6,
                "等烟雨来,就是不来",
                19090990,
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpngimg.com%2Fuploads%2Fxbox%2Fxbox_PNG17507.png&refer=http%3A%2F%2Fpngimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1616931278&t=3faf6ba95b0ea1fedd21bb7ccc965477",
                "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2729107397,409883431&fm=26&gp=0.jpg"
            ),
            Rank(
                7,
                "急死你,我就不来",
                190,
                "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1405706906,3786206755&fm=26&gp=0.jpg",
                "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2729107397,409883431&fm=26&gp=0.jpg"
            ),
            Rank(
                8,
                "你拿我怎么办呢,嘿嘿",
                190,
                "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3544022885,3616110694&fm=26&gp=0.jpg",
                "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2729107397,409883431&fm=26&gp=0.jpg"
            ),
            Rank(
                9,
                "不来就不来,爱来不来",
                138239,
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic.90sjimg.com%2Fdesign%2F00%2F58%2F41%2F78%2F5933cf3f09c71.png&refer=http%3A%2F%2Fpic.90sjimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1616930901&t=3b79951168c9d6bfc69233c2c8eb0c7e",
                "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2729107397,409883431&fm=26&gp=0.jpg"
            ),
            Rank(
                10,
                "呵呵呵呵呵呵呵呵",
                382989238,
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic.90sjimg.com%2Fdesign%2F02%2F03%2F48%2F08%2F5a44fc71b9e36.png&refer=http%3A%2F%2Fpic.90sjimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1616931214&t=9efa218eec84e6ac2e37a65587f97738",
                "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2729107397,409883431&fm=26&gp=0.jpg"
            ),
            Rank(
                11,
                "不去吧,,d,d,d,nazme",
                3223,
                "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1587290859,1459340053&fm=26&gp=0.jpg",
                "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2729107397,409883431&fm=26&gp=0.jpg"
            ),
            Rank(
                12,
                "呵呵呵呵呵呵呵呵",
                382989238,
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic.90sjimg.com%2Fdesign%2F02%2F03%2F48%2F08%2F5a44fc71b9e36.png&refer=http%3A%2F%2Fpic.90sjimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1616931214&t=9efa218eec84e6ac2e37a65587f97738",
                "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2729107397,409883431&fm=26&gp=0.jpg"
            ),
            Rank(
                13,
                "呵呵呵呵呵呵呵呵",
                382989238,
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic.90sjimg.com%2Fdesign%2F02%2F03%2F48%2F08%2F5a44fc71b9e36.png&refer=http%3A%2F%2Fpic.90sjimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1616931214&t=9efa218eec84e6ac2e37a65587f97738",
                "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2729107397,409883431&fm=26&gp=0.jpg"
            ),
            Rank(
                14,
                "呵呵呵呵呵呵呵呵",
                382989238,
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic.90sjimg.com%2Fdesign%2F02%2F03%2F48%2F08%2F5a44fc71b9e36.png&refer=http%3A%2F%2Fpic.90sjimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1616931214&t=9efa218eec84e6ac2e37a65587f97738",
                "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2729107397,409883431&fm=26&gp=0.jpg"
            ),
            Rank(
                15,
                "呵呵呵呵呵呵呵呵",
                382989238,
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic.90sjimg.com%2Fdesign%2F02%2F03%2F48%2F08%2F5a44fc71b9e36.png&refer=http%3A%2F%2Fpic.90sjimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1616931214&t=9efa218eec84e6ac2e37a65587f97738",
                "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2729107397,409883431&fm=26&gp=0.jpg"
            ),
            Rank(
                16,
                "呵呵呵呵呵呵呵呵",
                382989238,
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic.90sjimg.com%2Fdesign%2F02%2F03%2F48%2F08%2F5a44fc71b9e36.png&refer=http%3A%2F%2Fpic.90sjimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1616931214&t=9efa218eec84e6ac2e37a65587f97738",
                "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2729107397,409883431&fm=26&gp=0.jpg"
            ),
            Rank(
                17,
                "呵呵呵呵呵呵呵呵",
                382989238,
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic.90sjimg.com%2Fdesign%2F02%2F03%2F48%2F08%2F5a44fc71b9e36.png&refer=http%3A%2F%2Fpic.90sjimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1616931214&t=9efa218eec84e6ac2e37a65587f97738",
                "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2729107397,409883431&fm=26&gp=0.jpg"
            ),
            Rank(
                18,
                "呵呵呵呵呵呵呵呵",
                382989238,
                "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic.90sjimg.com%2Fdesign%2F02%2F03%2F48%2F08%2F5a44fc71b9e36.png&refer=http%3A%2F%2Fpic.90sjimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1616931214&t=9efa218eec84e6ac2e37a65587f97738",
                "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2729107397,409883431&fm=26&gp=0.jpg"
            )
        )
    }
}

@RequiresApi(Build.VERSION_CODES.N)
fun Activity.detectFrame(){
    window?.addOnFrameMetricsAvailableListener(Window.OnFrameMetricsAvailableListener { window, frameMetrics, dropCountSinceLastInvocation ->
        Log.v("ttaylor","drop count = ${dropCountSinceLastInvocation}, measure + layout=${frameMetrics.getMetric(FrameMetrics.LAYOUT_MEASURE_DURATION)/1000000}, " +
                "    delay=${frameMetrics.getMetric(FrameMetrics.UNKNOWN_DELAY_DURATION)/1000000}, " +
                "    anim=${frameMetrics.getMetric(FrameMetrics.ANIMATION_DURATION)/1000000}," +
                "    touch=${frameMetrics.getMetric(FrameMetrics.INPUT_HANDLING_DURATION)/1000000}, " +
                "    draw=${frameMetrics.getMetric(FrameMetrics.DRAW_DURATION)/1000000}, " +
                "    total=${frameMetrics.getMetric(FrameMetrics.TOTAL_DURATION)/1000000}")
    }, Handler())
}