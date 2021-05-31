package test.taylor.com.taylorcode.desin.abstract_handler

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class AbstractHandlerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val receiver = MessageReceiver().apply {
            addMessageHandler(VoiceMessageHandler())
            addMessageHandler(VideoMessageHandler())
        }
        receiver.onReceiveMessage(VoiceMessage(id = "1", voiceData = "voiceData"))
        receiver.onReceiveMessage(VideoMessage(id = "2", videoData = "videoData"))
    }
}