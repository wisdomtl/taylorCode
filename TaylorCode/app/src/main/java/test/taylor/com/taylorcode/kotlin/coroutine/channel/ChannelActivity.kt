package test.taylor.com.taylorcode.kotlin.coroutine.channel

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch

class ChannelActivity : AppCompatActivity() {

    private val mainScope = MainScope()

    private val goods = listOf("taylor", "peter", "avidance")

    private var orderId = 0

    /**
     * case: create a producer coroutine
     */
    private val receiver = mainScope.produce {
        goods.forEach {
            kotlinx.coroutines.delay(1000)
            trySend(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * case: consume the upper producer
         */
        mainScope.launch(Dispatchers.IO) {
            for (element in receiver) {
                Log.v("ttaylor[channel]", "receive value from producer channel $element")
            }
        }



        /**
         * case: use channel as pipeline(flow)
         */
        val orders = listOf(
            PizzaOrder(id = orderId++, Math.random().toInt(), 0),
            PizzaOrder(id = orderId++, Math.random().toInt(), 0),
            PizzaOrder(id = orderId++, Math.random().toInt(), 0),
            PizzaOrder(id = orderId++, Math.random().toInt(), 0),
            PizzaOrder(id = orderId++, Math.random().toInt(), 0),
        )

        val realOrders = mainScope.produce {
            orders.forEach {
                kotlinx.coroutines.delay(1000)
                send(it)
            }
        }

        mainScope.launch {
            val readyOrders = topping(baking(realOrders))
            for (order in readyOrders) {
                Log.v("ttaylor","[channel pipeline]ready orders=$order")
            }
        }


    }

    private fun CoroutineScope.baking(orders: ReceiveChannel<PizzaOrder>) = produce {
        for (order in orders) {
            kotlinx.coroutines.delay(1000)
            Log.v("ttaylor", "[channel pipeline]baking(id=${order.id})")
            send(order.copy(state = 1))
        }
    }

    private fun CoroutineScope.topping(orders: ReceiveChannel<PizzaOrder>) = produce {
        for (order in orders) {
            kotlinx.coroutines.delay(1000)
            Log.v("ttaylor", "[channel pipeline]topping(id=${order.id})")
            send(order.copy(state = 2))
        }
    }
}

data class PizzaOrder(val id: Int, val price: Int, val state: Int = 0)