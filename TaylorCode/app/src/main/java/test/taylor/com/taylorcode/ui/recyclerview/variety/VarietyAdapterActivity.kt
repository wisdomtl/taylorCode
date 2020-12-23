package test.taylor.com.taylorcode.ui.recyclerview.variety

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import test.taylor.com.taylorcode.kotlin.*

class VarietyAdapterActivity : AppCompatActivity() {

    private var rv: RecyclerView? = null

    private var lastTextIndex: Int = -1

    private lateinit var datas: MutableList<Any>

    private val rootView by lazy {
        ConstraintLayout {
            layout_width = match_parent
            layout_height = match_parent

            rv = RecyclerView {
                layout_width = match_parent
                layout_height = wrap_content
                top_toTopOf = parent_id
                center_horizontal = true
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(rootView)

        val varietyAdapter = VarietyAdapter().apply {
            addProxy(TextAdapterProxy())
            addProxy(ImageAdapterProxy())
            action = { data ->
                if (lastTextIndex != -1) {
                    var lastData = datas[lastTextIndex]
                    if (lastData is Text){
                        lastData.text = lastData.text.plus("unselected")
                    }
                    notifyDataSetChanged()
                }
                lastTextIndex = data as Int
                var currentData = datas[data]
                if (currentData is Text){
                    currentData.text = currentData.text.plus("selected")
                }
                notifyDataSetChanged()
            }
        }

        datas = mutableListOf(
            Text("item 1"),
            Image("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1598432560218&di=86775e7582bd602267d8d77a4e04c734&imgtype=0&src=http%3A%2F%2Fatth.jzb.com%2Fforum%2F201507%2F30%2F133923yldlw9959ethludz.png"),
            Text("item 2"),
            Text("item 3"),
            Image("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1598432560218&di=86775e7582bd602267d8d77a4e04c734&imgtype=0&src=http%3A%2F%2Fatth.jzb.com%2Fforum%2F201507%2F30%2F133923yldlw9959ethludz.png"),
            Text("item 4"),
            Text("item 5"),
            Text("item 6"),
            Image("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1598432560218&di=86775e7582bd602267d8d77a4e04c734&imgtype=0&src=http%3A%2F%2Fatth.jzb.com%2Fforum%2F201507%2F30%2F133923yldlw9959ethludz.png"),
            Text("item 7"),
            Text("item 8"),
            Image("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1598432560218&di=86775e7582bd602267d8d77a4e04c734&imgtype=0&src=http%3A%2F%2Fatth.jzb.com%2Fforum%2F201507%2F30%2F133923yldlw9959ethludz.png"),
            Text("item 9")
        )
        varietyAdapter.dataList = datas

        rv?.adapter = varietyAdapter
        rv?.layoutManager = LinearLayoutManager(this)
        varietyAdapter.notifyDataSetChanged()
    }
}