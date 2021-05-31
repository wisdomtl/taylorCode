package test.taylor.com.taylorcode.desin.abstract_handler


open class Message(val id: String = "", val content: String = "")


class VoiceMessage(id: String = "", content: String = "", val voiceData: String = "") : Message(id, content)
class VideoMessage(id: String = "", content: String = "", val videoData: String = "") : Message(id, content)