package test.taylor.com.taylorcode.photo.okhttp.model_loader.track


interface NetworkTrackCallback {
    /**
     * An callback which will be invoke when a [okhttp3.Call] ends
     * biz layer should implement it and append extra data to the [map], then upload the tracking data to the cloud
     * @param map all the network tracking data which ukinet could collect
     */
    fun onCallEnd(map: Map<String, Any>)
}