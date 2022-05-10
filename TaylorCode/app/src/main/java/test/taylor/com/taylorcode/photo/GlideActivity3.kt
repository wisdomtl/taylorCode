package test.taylor.com.taylorcode.photo

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.photo.okhttp.model_loader.okHttpClient
import test.taylor.com.taylorcode.photo.okhttp.model_loader.track.NetworkTrackCallback
import test.taylor.com.taylorcode.photo.okhttp.model_loader.track.TrackEventListener
import test.taylor.com.taylorcode.photo.okhttp.model_loader.track.TrackEventListener.Companion.KEY_REQUEST_TOTAL_TIME
import test.taylor.com.taylorcode.ui.line_feed_layout.LineFeedLayout
import java.lang.Integer.min
import java.util.concurrent.CountDownLatch

class GlideActivity3 : AppCompatActivity() {

    private lateinit var lf: LineFeedLayout

    private val images = listOf(
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fppt.chnlib.com%2FFileUpload%2F2018-11%2F7-Cai_Se_Re_1i_1iu_Gao-110740_129.png&refer=http%3A%2F%2Fppt.chnlib.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1654343732&t=06d9d1091d3bf3ff9211e0cb27e0afe0",
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.51miz.com%2FElement%2F00%2F84%2F05%2F33%2Fb07322b0_E840533_bf6a953e.png%21%2Fquality%2F90%2Funsharp%2Ftrue%2Fcompress%2Ftrue%2Fformat%2Fpng&refer=http%3A%2F%2Fimg.51miz.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1654343749&t=cacd1263d9232c376e3b59f5eb4b3254",
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimage101.360doc.com%2FDownloadImg%2F2016%2F11%2F0111%2F83510768_1.png&refer=http%3A%2F%2Fimage101.360doc.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1654343763&t=a106fc9fea3c3b62cf271947a6ade37f",
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fbbsfiles.vivo.com.cn%2Fvivobbs%2Fattachment%2Fforum%2F201611%2F06%2F211252asasxdc78psgaflg.png&refer=http%3A%2F%2Fbbsfiles.vivo.com.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1654343803&t=eb1a25e7ed7cdefc4e387cf2ec2f4afe",
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fitem%2F201803%2F25%2F20180325210912_vcfXH.jpeg&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1654343860&t=377ddbacd2710b3c4505d5adb481ec36",
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farticle%2F853126203de1cf032deadd4d783b8ba881a1ce8a.jpg&refer=http%3A%2F%2Fi0.hdslb.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1654343873&t=94c3b2ec979fa69f481a2b675b91aad1",
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fitem%2F202005%2F04%2F20200504150124_t3iQA.jpeg&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1654343884&t=da1e0bad45c8bbe4da2789f65b7093d2",
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201811%2F17%2F20181117005612_AVJvw.jpeg&refer=http%3A%2F%2Fb-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1654343926&t=d8dce9688923ed4fc5b5a08bdc8302fe",
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F20181%2F30%2F2018130185920_HQ82m.jpeg&refer=http%3A%2F%2Fb-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1654343954&t=cae923ee08bbc79ec78eb19afa5d9f54",
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fn.sinaimg.cn%2Fsinakd20110%2F96%2Fw2048h2048%2F20210627%2Fc8ed-6d1cd3485e8b83f3e9e8fb9c5c85bede.jpg&refer=http%3A%2F%2Fn.sinaimg.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1654343964&t=48dc3ca5734d78bfe40bd92201f3b6fc",
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fn.sinaimg.cn%2Fsinakd20114%2F96%2Fw2048h2048%2F20210915%2Fc5c6-4c0a8d0d5ed782f4c61fcc6143cc8500.jpg&refer=http%3A%2F%2Fn.sinaimg.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1654343977&t=e4104e3e6225b5f871734b9003e216b4",
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fitem%2F202006%2F23%2F20200623184344_oscsd.jpg&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1654344000&t=696365df5abffba9170a7802ad013157",
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.nga.178.com%2Fattachments%2Fmon_202002%2F02%2FbiQ5-3ydxXuZ8eT3cS1yg-140.png&refer=http%3A%2F%2Fimg.nga.178.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1654344029&t=481858dd07437105b055629188ca4fd6",
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farticle%2Fbc2764753cc88f2f14abfcc448e386677394ea40.jpg&refer=http%3A%2F%2Fi0.hdslb.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1654359412&t=bae9d0a04c0d7fac18587c50c788a713",
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg31.51tietu.net%2Fpic%2F2016-120805%2F20161208051934ibognezuubc112650.jpg&refer=http%3A%2F%2Fimg31.51tietu.net&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1654359483&t=43e460285b89ed1feccd58b66d7f550f",
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farticle%2F9b6de70095b0a623992462e27b2cc435b1e39158.jpg&refer=http%3A%2F%2Fi0.hdslb.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1654359531&t=51b5e96b1a750f01c3207cab69871a2c",
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2F68c7e4ec9a8b3804d387012c8f2c2c69f9db95a42b695-Rxfr9z_fw658&refer=http%3A%2F%2Fhbimg.b0.upaiyun.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1653817842&t=9fa35989a42b23f39f692e03aebe4672",
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.ivsky.com%2Fimg%2Ftupian%2Fpic%2F202002%2F27%2Fkatong_yingtao_png_sucai.png%3Fdownload&refer=http%3A%2F%2Fimg.ivsky.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1654343700&t=cc961b89a23fa161c143ca2b93629a9b",
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic.90sjimg.com%2Fdesign%2F01%2F84%2F14%2F63%2F5a2e2b3b888ae.png&refer=http%3A%2F%2Fpic.90sjimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1654343721&t=36f54ccbfa53efc1fc91dfb74b80b89f",
//        "https://img0.baidu.com/it/u=697211124,3956615873&fm=253&fmt=auto&app=138&f=JPEG?w=889&h=500",
//        "https://img0.baidu.com/it/u=968428281,902750749&fm=253&fmt=auto&app=138&f=PNG?w=500&h=500",
//        "https://img0.baidu.com/it/u=157152016,3925643688&fm=253&fmt=auto&app=138&f=JPEG?w=889&h=500",
//        "https://img0.baidu.com/it/u=1764448065,2695969508&fm=253&fmt=auto&app=138&f=PNG?w=500&h=296",
//        "https://img1.baidu.com/it/u=3506002250,4076074537&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=281",
//        "https://img1.baidu.com/it/u=3365054696,3236689645&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500",
//        "https://img1.baidu.com/it/u=4264407738,2970616074&fm=253&fmt=auto&app=120&f=JPEG?w=800&h=1731",
//        "https://img1.baidu.com/it/u=1426315203,3936820487&fm=253&fmt=auto&app=138&f=JPEG?w=375&h=500",
//        "https://img1.baidu.com/it/u=2775250516,3786057343&fm=253&fmt=auto&app=138&f=JPEG?w=889&h=500",
//        "https://img1.baidu.com/it/u=3802458197,3531572720&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=750",
//        "https://img1.baidu.com/it/u=2022213094,1337366528&fm=253&fmt=auto&app=138&f=JPG?w=500&h=281",
//        "https://img2.baidu.com/it/u=2119769400,690855938&fm=253&fmt=auto&app=138&f=JPEG?w=499&h=353",
//        "https://img2.baidu.com/it/u=1703186005,31784364&fm=253&fmt=auto&app=138&f=JPEG?w=260&h=260",
//        "https://img2.baidu.com/it/u=604657093,836796767&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=825",
//        "https://img2.baidu.com/it/u=1539918067,2492473331&fm=253&fmt=auto&app=138&f=JPEG?w=889&h=500",
    )

//    private val images2 = listOf(
//        "http://xiaoredpic.neoclub.cn/miaohong/post/photo/test/15231903/576ad5fc-a950-4c34-bd02-73e068deb34d_w1200h2640.jpg?x-oss-process=image",
//        "http://xiaoredpic.neoclub.cn/miaohong/post/photo/test/15231903/a9ae8b49-c24e-4346-8df5-6fcbb07cf181_w2592h3840.jpg?x-oss-process=image",
//        "http://xiaoredpic.neoclub.cn/miaohong/post/photo/test/15231903/48cc94e9-4d45-460c-a776-0da2c9c096f3_w2592h3840.jpg?x-oss-process=image",
//        "http://xiaoredpic.neoclub.cn/miaohong/post/photo/test/15231903/97c0e3d7-e04b-469a-8c06-7c80d0a7db8d_w2592h3840.jpg?x-oss-process=image",
//        "http://xiaoredpic.neoclub.cn/miaohong/post/photo/test/15231903/9f97be96-1768-4c0a-bac3-0839db0eb13c_w2592h3840.jpg?x-oss-process=image",
//        "http://xiaoredpic.neoclub.cn/miaohong/post/photo/test/15231903/2c7a83ce-8332-45b3-b118-d311ffc55c04_w2592h3840.jpg?x-oss-process=image",
//        "http://xiaoredpic.neoclub.cn/miaohong/post/photo/test/15231903/835e30f4-bec8-4f2d-a845-7e9f028b3b2a_w1200h2640.jpg?x-oss-process=image",
//        "http://xiaoredpic.neoclub.cn/miaohong/post/photo/test/15231903/576ad5fc-a950-4c34-bd02-73e068deb34d_w1200h2640.jpg?x-oss-process=image",
//        "http://xiaoredpic.neoclub.cn/miaohong/post/photo/test/15253754/cdd9a6af-1129-487d-96fc-cc5780bf2255_w1080h2340.jpg?x-oss-process=image",
//        "http://xiaoredpic.neoclub.cn/miaohong/test/post/15252717/16496497200_w828h570.png?x-oss-process=image",
//        "http://xiaoredpic.neoclub.cn/miaohong/test/post/15252717/16496495280_w826h1102.png?x-oss-process=image",
//        "http://xiaoredpic.neoclub.cn/miaohong/post/photo/test/15224874/d8a87975-21bb-4ca4-bf2c-e721b55ed10c_w0h0.jpg",
//        "http://xiaoredpic.neoclub.cn/miaohong/user/custom/avatar/image/test/15224874/d14381f1-e6c9-40bf-bc4d-d1f1f08d3ec6.jpg",
//        "http://xiaoredpic.neoclub.cn/miaohong/post/photo/test/15231903/576ad5fc-a950-4c34-bd02-73e068deb34d_w1200h2640.jpg?x-oss-process=image",
//    )

    private val countdownLatch = CountDownLatch(images.size)
    private val contentView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent

            lf = LineFeedLayout {
                layout_width = match_parent
                layout_height = match_parent
                verticalGap = 10
                horizontalGap = 15
            }

        }
    }

    private var start = System.currentTimeMillis()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)

//        var minTotalTime = Int.MAX_VALUE
//        var sumTime = -1L
//        TrackEventListener.networkTrackCallback = object : NetworkTrackCallback {
//            override fun onCallEnd(map: Map<String, Any>) {
//                Log.v("ttaylor", "[glide-performance] $map")
//                val totalTime = map[KEY_REQUEST_TOTAL_TIME] as Long
//                minTotalTime = Math.min(minTotalTime, totalTime.toInt())
//                sumTime += totalTime
//            }
//        }
        MainScope().launch(Dispatchers.IO) {
            countdownLatch.await()
            Log.d(
                "test",
                "[glide-performance] glide-time-consume=${System.currentTimeMillis() - start}"
            )
        }

        lf.apply {
            images.forEach { img ->
                ImageView(autoAdd = false) {
                    layout_id = "tvChange"
                    layout_width = 100
                    layout_height = 100
                    scaleType = scale_fit_xy
                    Glide
                        .with(this@GlideActivity3)
                        .load(img)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .listener(object : RequestListener<Drawable> {
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                countdownLatch.countDown()
                                return false
                            }

                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: Target<Drawable>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {
                                countdownLatch.countDown()
                                return false
                            }
                        })
                        .into(this)
                }.also { addView(it) }
            }
        }
    }
}