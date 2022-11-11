package test.taylor.com.taylorcode.ui.fragment

/**
 * An interface should be implemented by the Fragment which wants to report it's exposure
 * The param is exactly the same as [Neurons.reportExposure]
 */
interface ExposureParam {
    val eventId: String
    fun getExtra(): Map<String, String?> = emptyMap()
    fun isForce():Boolean = false
}