package test.taylor.com.taylorcode.aysnc

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import test.taylor.com.taylorcode.kotlin.*

class HandlerThreadVsCoroutineActivity : AppCompatActivity() {

    private val TAG = "HandlerThreadVs1"

    private lateinit var tvName: TextView
    private lateinit var rvBill: RecyclerView
    private val mainScope = MainScope()

    private val rootView by lazy {
        ConstraintLayout {
            tvName = TextView {
                layout_width = wrap_content
                layout_height = wrap_content
                textSize = 20f
            }

            rvBill = RecyclerView {
                layout_width = match_parent
                layout_height = 400
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(rootView)

        /**
         * old fashion
         */
//        val mainHandler = Handler(Looper.getMainLooper())
//        val handlerThread = HandlerThread("user")
//        handlerThread.start()
//        val handler = Handler(handlerThread.looper)
//        runOnUiThread { }
//        handler.post(object : Runnable {
//            override fun run() {
//                val user = fetchUser()
//                mainHandler.post(object : Runnable {
//                    override fun run() {
//                        tvName.text = user.name
//                    }
//                })
//            }
//        })

        /**
         * coroutine style one
         */
//        GlobalScope.launch {
//            Log.v("test","1 tid=${Thread.currentThread().id}  ")
//            val user = async(Dispatchers.IO) { fetchUser() } // 在新线程1执行
//            Log.v("test","2 tid=${Thread.currentThread().id}  ") // 在新线程1执行
//            // 挂起点，协程代码的执行将被暂停以等待 fetchUser() 返回
//            val name = user.await().name
//            Log.v("test","3 tid=${Thread.currentThread().id}  ")// 在新线程2执行
//            // 恢复点，当用户信息从服务器返回时，协程代码继续执行
//            tvName.text = name
//        }

        /**
         * case: sequence network request
         */
        GlobalScope.launch {
            val user = fetchUser()
            Log.d(TAG, " after fetchUser")
            val bills = fetchBill(user)
            Log.d(TAG, " after fetchBill")
            withContext(Dispatchers.Main) {
                rvBill.adapter = BillAdapter(bills)
                Log.i(TAG, "after ui display tid=${Thread.currentThread().id}")
            }
            Log.v(TAG, " after launche")
        }

//        /**
//         * case: parallel network request
//         */
//        mainScope.launch {
//            val user = async(Dispatchers.IO) { fetchUser() }
//            val gifts = async(Dispatchers.IO) { fetchGift() }
//            user.await()
//            gifts.await()
//            Log.v("ttaylor","tag=asdf, parallel network request all finished  ")
//        }

    }



    suspend fun fetchGift(){
        Log.v("ttaylor","tag=asdf, fetch gift start tid=${Thread.currentThread().id}  ")
        delay(4000)
        Log.v("ttaylor","tag=asdf, fetch gift finished  tid=${Thread.currentThread().id} ")
    }

    suspend fun fetchUser(): User {
        Log.v("test", " tag=asdf fetchUser start tid=${Thread.currentThread().id}  ") // 在新线程3执行
        delay(1000)
        Log.v("test", "tag= asdf fetchUser finished  tid=${Thread.currentThread().id}  ") // 在新线程3执行
        return User("taylor", 20, 0)
    }

    suspend fun fetchBill(user: User): List<Bill> {
        delay(2000)
        return mutableListOf(Bill("a", 10), Bill("b", 20))
    }

//    private fun fetchUser(): User {
//        Thread.sleep(1000)
//        return User("taylor", 20, 0)
//    }
}

class BillAdapter(var bills: List<Bill>) : RecyclerView.Adapter<BillViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillViewHolder {
        val itemView = parent.run {
            ConstraintLayout {
            }
        }
        return BillViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return bills.size
    }

    override fun onBindViewHolder(holder: BillViewHolder, position: Int) {

    }
}

class BillViewHolder(var itemView: View) : RecyclerView.ViewHolder(itemView) {

}

data class Bill(var title: String, var money: Int)
data class User(var name: String, var age: Int, var gender: Int)