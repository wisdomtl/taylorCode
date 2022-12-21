package test.taylor.com.taylorcode.gif

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import test.taylor.com.taylorcode.R
import test.taylor.com.taylorcode.kotlin.*
import test.taylor.com.taylorcode.ui.recyclerview.variety.VarietyAdapter2

class FrescoActivity : AppCompatActivity() {

    private val gifUrls = listOf(
        listOf(
            "http://i0.hdslb.com/bfs/creative/296d79807e4a854206bfc947877bf81312ca2674.webp",
            "http://i0.hdslb.com/bfs/creative/4cf201243ebac1bbcf66e6b5a27caa07ecc2d2d1.webp",
            "http://i0.hdslb.com/bfs/creative/3ae039ad6d761c7f85e1654df0640aecfe8c92ce.webp",
            "http://i0.hdslb.com/bfs/creative/5b43859acca9d118ddb8f1a4ff222a82756909b7.webp",
            "http://i0.hdslb.com/bfs/creative/1f4a1062e8f1f81d85012b60b6bfef9a43244055.webp",
            "http://i0.hdslb.com/bfs/creative/4bd298ed7fd23c9c7909c169976a4d0a7f82bbdd.webp",
            "http://i0.hdslb.com/bfs/creative/f3fd7393d8d8acd4d2e4bf3902b18f019755d416.webp",
            "http://i0.hdslb.com/bfs/creative/fe7110b7bcb537039eee75bba8d68a9dbd181c88.webp",
            "http://i0.hdslb.com/bfs/creative/2aebb56e343b7fac383ee585cc54dab6690323b3.webp",
            "http://i0.hdslb.com/bfs/creative/c2e44a541024813b4aedddc0d642e6fc1d97ec30.webp",
            "http://i0.hdslb.com/bfs/creative/7d5ed987384babe542d7646351ffd6b679166b85.webp",
            "http://i0.hdslb.com/bfs/creative/4da9060a9e42c93c259434dfaae6b30905ea1c70.webp",
            "http://i0.hdslb.com/bfs/creative/599e270234c335bc78b1f1b98a742263171961e1.webp",
            "http://i0.hdslb.com/bfs/creative/5240da40f5adddbcbfaa577369fba9ebb5f4095f.png",
            "http://i0.hdslb.com/bfs/creative/6073697e8efbaea26fea007e7168b894f33a0353.webp",
            "http://i0.hdslb.com/bfs/creative/92705d0af182f14f641de394b75b3cbaf4ec539d.webp",
            "http://i0.hdslb.com/bfs/creative/df40498a10e753c043c02b44d0740b75ef8d323a.webp",
            "http://i0.hdslb.com/bfs/creative/d12362b2b301ebcae8fa8d8084ce571ce6a2484a.webp",
            "http://i0.hdslb.com/bfs/creative/b51f501247a57996f5a7160a91f2c37af0f5a69e.webp",
            "http://i0.hdslb.com/bfs/creative/db9738f6937e97d4cd3e86fd6f65ad9056d0035a.webp",
            "http://i0.hdslb.com/bfs/creative/dd609f347285dcf9a87e0cc82a382bf662eecd00.webp",
            "http://i0.hdslb.com/bfs/creative/03918504a594715b82671a6a5db9d8f185f477dc.webp",
            "http://i0.hdslb.com/bfs/creative/d3ac47c68138d64b052de1acdad7e0783147a9ef.webp",
            "http://i0.hdslb.com/bfs/creative/6be381dde8c1b4ef5ac1b0ec6d32f6537b90a62e.webp",
            "http://i0.hdslb.com/bfs/creative/5f09db05c50118aa006354234977982477082bbd.webp",
            "http://i0.hdslb.com/bfs/creative/540d7260e226dc4ebd28a9afe2c055802ee447cf.webp",
            "http://boss.hdslb.com/material/00768126-7de1-4ead-bb2d-4f15868a8a60.gif",
            "http://i0.hdslb.com/bfs/creative/f7c8cd896161b57fe52851eb06a060703468cc15.webp",
            "http://i0.hdslb.com/bfs/creative/9d8550905d2eaa4f2a11137088e2f7d9e832eda9.webp",
            "http://i0.hdslb.com/bfs/creative/7799158a3f9e6ab3b76a1766288646060512c01a.webp",
            "http://i0.hdslb.com/bfs/creative/dfc2a1604265b0e1c84618abb85e3e1c84524d29.webp",
            "http://i0.hdslb.com/bfs/creative/2e68878fa9436048c4eacc5173141ff8c11e9b2f.webp",
            "http://i0.hdslb.com/bfs/creative/d30c1c1f10dff15aedbea5695866fca102059c67.webp",
            "http://i0.hdslb.com/bfs/creative/fb2779cc427bde110546db9a31c17633887b3a97.webp",
            "http://i0.hdslb.com/bfs/creative/98a9237fb1feb3795ad0d03cba2884c8a12f76c7.webp",
            "http://i0.hdslb.com/bfs/creative/37f54f96f6afef37d6a3abbdde4ec21eb9f8ee66.webp",
            "http://i0.hdslb.com/bfs/creative/5263743d2e4fc0d626437d024fb03df905ad9dbf.webp",
            "http://i0.hdslb.com/bfs/creative/0b17a4fe7257a3f790f9d2f32b23f2b7d6ef027f.webp",
            "http://i0.hdslb.com/bfs/creative/97eb19226d8375fca28a61e28040ed0dbea97967.webp",
            "http://i0.hdslb.com/bfs/creative/8aaa0a87752c48f4f6f9d8c65e9e528150ca4b4a.webp",
            "http://i0.hdslb.com/bfs/creative/3939999e9fd8c045abb8d6871c0e3ed84cb0a4c9.webp",
            "http://i0.hdslb.com/bfs/creative/c1adfe542d519835d74d0ca479eee7fe1e452aa6.webp",
            "http://i0.hdslb.com/bfs/creative/9266c9a4724bdb85b688670533611f1b44b386c7.webp",
            "http://i0.hdslb.com/bfs/creative/ddf7b1fce9fcbde4c6282189b1ec562e11edef76.webp",
            "http://i0.hdslb.com/bfs/creative/1c17d213708cb8b9f2bb8543ca3bee8d5b531e10.webp",
            "http://i0.hdslb.com/bfs/creative/7e17c0855059bd782f581b8c891c48cacc64c6a1.webp",
            "http://i0.hdslb.com/bfs/creative/01aefc1f748e6afb0f6764c91987ee5c06fe1dc6.webp",
            "http://i0.hdslb.com/bfs/creative/8f355ec4c3a59345e9975c94b649dd8450a5d0c8.webp",
        ),
        listOf(
            "http://i0.hdslb.com/bfs/creative/296d79807e4a854206bfc947877bf81312ca2674.webp",
            "http://i0.hdslb.com/bfs/creative/3ae039ad6d761c7f85e1654df0640aecfe8c92ce.webp",
            "http://i0.hdslb.com/bfs/creative/576373c73db5b83d46e0be388566f17e636fa081.gif",
            "http://i0.hdslb.com/bfs/creative/8a79922017fabc1610c34e03db326a4e4a748ff5.webp",
            "http://i0.hdslb.com/bfs/creative/563a6f04b6c3cb69a21bdb4b0b72a4ee97aec3df.webp",
            "http://i0.hdslb.com/bfs/creative/5d467d0a84613dbb89ba18a9bf80886caedb3dcb.webp",
            "http://i0.hdslb.com/bfs/creative/fd9fd2d152fad300b788bec2acba633e92ee2af2.webp",
            "http://i0.hdslb.com/bfs/creative/b68219fed27d83f7e873e4ba5ad730a0a8644513.png",
            "http://i0.hdslb.com/bfs/creative/f44d6d31400e1a03f5b134f6782ca12f76dda247.webp",
            "http://i0.hdslb.com/bfs/creative/6e143e8e5e1200277eb70425bde1959442961419.webp",
            "http://i0.hdslb.com/bfs/creative/e80d14224f7c29efede55ae6d8bcf31a61d8691a.webp",
            "http://i0.hdslb.com/bfs/creative/2f5df378282076ad4fdb2bde36248722ccc75d6b.webp",
            "http://i0.hdslb.com/bfs/creative/4392b467769ee6e95f3fe0096d06dc3b71abf2ae.webp",
            "http://i0.hdslb.com/bfs/creative/4ff1f5f48877a7b0c0e2146c86fcfdb01c94d36b.webp",
            "http://i0.hdslb.com/bfs/creative/1ab30d48cc4918c240068ed6060ae1c63ffeaf64.webp",
            "http://i0.hdslb.com/bfs/creative/9e530d6a6f7dddb5fd96cd2341707794e7d887c8.webp",
            "http://i0.hdslb.com/bfs/creative/c413c7371227a81cb61a7f85b0d04613b038b5ae.webp",
            "http://i0.hdslb.com/bfs/creative/85809f13f12df27f4945ea9182f7d0cabb98f827.webp",
            "http://i0.hdslb.com/bfs/creative/c603a3e162407c183975128450ca782fbf1bb155.webp",
            "http://i0.hdslb.com/bfs/creative/924dc71a09c8cc137f553354805e937942165dcc.webp",
            "http://i0.hdslb.com/bfs/creative/dd82d6ba3b87dec4557f18f455de31fbeacfeb47.webp",
            "http://i0.hdslb.com/bfs/creative/33722bc552c5ebb833fa2190c006974d7e093d2c.webp",
            "http://i0.hdslb.com/bfs/creative/8f62cfd4327e0d9b784b3a9b1a5d1d27b3052603.gif",
            "http://i0.hdslb.com/bfs/creative/0ac07aa86b2ffe98b65b0214dd472b25c35b9908.gif",
            "http://i0.hdslb.com/bfs/creative/08da2140e076741e09d93ce0528231fd4011190f.webp",
            "http://i0.hdslb.com/bfs/creative/4c45ceb463d01e81e66158d9026dc11d2d636cce.webp",
            "http://i0.hdslb.com/bfs/creative/210da24c22291065be33c7bef5541ee579919109.webp",
            "http://i0.hdslb.com/bfs/creative/ba663837cce6729aec11b65b2153492fe9a8978d.webp",
            "http://i0.hdslb.com/bfs/creative/272ba81f7a875023cf20eb848625d895e61924bc.jpg",
            "http://i0.hdslb.com/bfs/creative/c8469e35742d2d46e0ac8a1988eaf62a1e884ca8.webp",
            "http://i0.hdslb.com/bfs/creative/6d913c7caf3217c075bcc05e7f3d010090e41c6b.webp",
            "http://i0.hdslb.com/bfs/creative/5a869e82dc3f28b7cca61d5618ed7efd86ebf79b.webp",
            "http://i0.hdslb.com/bfs/creative/57e34cc63e64b09c37667423c529a22b105a9455.webp",
            "http://i0.hdslb.com/bfs/creative/cbc32f28e56324a1675f2a70e4190cca63e22cc1.webp",
            "http://i0.hdslb.com/bfs/creative/618beb49a972a6f22bf507e382403b98b5a0c063.webp",
            "http://i0.hdslb.com/bfs/creative/0257d02bda8635298204604beb06bec0c2fdd1d8.webp",
            "http://i0.hdslb.com/bfs/creative/3db498cac6d177b4267e8bc3a88dd9c321c8da38.webp",
            "http://i0.hdslb.com/bfs/creative/d31ac6560f4059939eec7bdd2561031a708350a4.webp",
            "http://i0.hdslb.com/bfs/creative/08b4dbc5c0fd652bb013aebacfb8f13603b3562d.webp",
            "http://i0.hdslb.com/bfs/creative/4eca8efebfda66c757bb8668fa2161ca045f57a9.webp",
            "http://i0.hdslb.com/bfs/creative/68ef3c24dab875fb24c98ed96ea9bd3aed8a85b6.webp",
            "http://i0.hdslb.com/bfs/creative/0b60b94ec0d712b40089d1c32c765be0c052c36c.webp",
            "http://i0.hdslb.com/bfs/creative/b5da1253b2ee90dbf48bbeefa7e287c42e5d1b67.webp",
            "http://i0.hdslb.com/bfs/creative/bd2f1ecd6c09992fed2f5cc5e60521412477e603.webp",
            "http://i0.hdslb.com/bfs/creative/3a2177c559f5b2845ebe79e570fefccbb48883c5.webp",
            "http://i0.hdslb.com/bfs/creative/aaa0ef060e299970e5b67775f913ce70c4ef77d3.webp",
            "http://i0.hdslb.com/bfs/creative/5c4a072c7da4d5169977d2eb54ffab74d069a710.webp",
            "http://i0.hdslb.com/bfs/creative/b256982f67c9b45e7f6a3aa4c0166bc952562f13.webp",
        )
    )


    private val gridAdapter by lazy {
        VarietyAdapter2().apply {
            addItemBuilder(FrescoGridProxy())
        }
    }

    private var rv: RecyclerView? = null

    private val contentView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent

            rv = RecyclerView {
                layout_id = "rv"
                layout_width = match_parent
                layout_height = 280
                top_toTopOf = parent_id
                layoutManager = GridLayoutManager(this@FrescoActivity, 4, GridLayoutManager.VERTICAL, false)
                adapter = gridAdapter
            }

        }
    }


    private lateinit var iv: SimpleDraweeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)
        initView()
    }

    private fun initView() {
        rv = find("rv")
        rv?.adapter = gridAdapter
        gridAdapter.dataList = gifUrls.first().map { GridBean(it) }
        /**
         * case: load static webp by Fresco
         */
        //        iv.setImageURI(gifUrls[0])
        /**
         * case: load animated webp by Fresco
         */
        //        iv.controller = Fresco.newDraweeControllerBuilder().setAutoPlayAnimations(true).setUri(gifUrls[0]).build()


    }


}

class FrescoGridProxy : VarietyAdapter2.ItemBuilder<GridBean, GridFrescoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = parent.context.run {
            ConstraintLayout {
                layout_width = match_parent
                layout_height = 100
                shape = shape {
                    solid_color = "#888888"
                    corner_radius = 20
                }
                SimpleDraweeView(this@run).apply {
                    layout_id = "iv"
                    layout_width = match_parent
                    layout_height = match_parent
                }.also { addView(it) }

            }
        }
        return GridFrescoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GridFrescoViewHolder, data: GridBean, index: Int, action: ((Any?) -> Unit)?) {
        holder.iv?.also {
            it.controller = Fresco.newDraweeControllerBuilder().setAutoPlayAnimations(true).setUri(data.str).build()
        }
    }
}

class GridFrescoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val iv = itemView.find<SimpleDraweeView>("iv")
}