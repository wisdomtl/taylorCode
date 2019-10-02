package test.taylor.com.taylorcode.proxy.remote

import test.taylor.com.taylorcode.IRemoteSingleton

/**
 * a singleton belongs to the process started by [RemoteService]
 */
object RemoteServiceSingleton : IRemoteSingleton.Stub() {

    var text: String? = "start"

    var list2 = mutableListOf("a","b","c")

    var count2: Int = 2
    override fun setText2(text: String?) {
        this.text = text
    }

    override fun basicTypes(anInt: Int, aLong: Long, aBoolean: Boolean, aFloat: Float, aDouble: Double, aString: String?) {
    }

    override fun getText2(): String {
        return text ?: ""
    }

    override fun getCount(): Int {
        return count2
    }

    override fun getList(): MutableList<String> {
        return list2
    }

    override fun setCount(count: Int) {
        this.count2 = count
    }

    override fun add(element: String?) {
        list2.add(element ?: "")
    }

}