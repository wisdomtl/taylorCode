package test.taylor.com.taylorcode

import androidx.annotation.CallSuper
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class FeedsTest {

    @CallSuper
    @BeforeTest
    fun setup(){
       println("Feeds test starts")
    }

    @CallSuper
    @AfterTest
    fun tearDown(){
        println("Feeds test ends")
    }

    @Test
    fun test_log(){
       println("test log output")
        assertEquals(1,1)
    }


}