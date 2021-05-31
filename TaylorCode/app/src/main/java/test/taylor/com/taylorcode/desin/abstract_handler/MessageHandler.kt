package test.taylor.com.taylorcode.desin.abstract_handler

import android.util.Log

/**
 * abstract handler, polymorphism for the way of handle message
 */
interface MessageHandler<T : Message> {
    fun handleMessage(message: T)
}


class VoiceMessageHandler : MessageHandler<VoiceMessage> {
    override fun handleMessage(message: VoiceMessage) {
        Log.v("ttaylor", "voice message is handled id = ${message.id} data=${message.voiceData}")
    }
}

class VideoMessageHandler : MessageHandler<VideoMessage> {
    override fun handleMessage(message: VideoMessage) {
        Log.v("ttaylor", "video message is handled id = ${message.id} data=${message.videoData}")
    }
}
