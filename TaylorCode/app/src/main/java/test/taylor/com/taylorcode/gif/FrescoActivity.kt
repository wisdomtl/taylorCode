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
                layout_height = match_parent
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
        rv = find<RecyclerView>("rv")
        rv?.adapter = gridAdapter
        gridAdapter.dataList = gifUrls.map { GridBean(it) }
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