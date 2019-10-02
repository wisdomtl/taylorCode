package test.taylor.com.taylorcode.aidl

import test.taylor.com.taylorcode.ILocalServiceBoundByRemote

/**
 * case: a special singleton in main process and will be bound by a remote activity
 */
object LocalSingleton : ILocalServiceBoundByRemote.Stub() {
    private var string2 = "zero"
    override fun setString(string: String?) {
        string2 = string ?: ""
    }

    override fun basicTypes(anInt: Int, aLong: Long, aBoolean: Boolean, aFloat: Float, aDouble: Double, aString: String?) {
    }

    override fun getString(): String {
        return string2
    }

}

