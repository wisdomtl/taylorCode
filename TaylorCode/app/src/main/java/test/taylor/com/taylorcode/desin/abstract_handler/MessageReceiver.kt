package test.taylor.com.taylorcode.desin.abstract_handler

import kotlin.reflect.KClass

class MessageReceiver {
    /**
     * holding an abstract list of [MessageHandler]
     */
    val messageHandlerMap = mutableMapOf<KClass<*>, MessageHandler<*>>()

    /**
     * add [MessageHandler] to abstract list
     */
    inline fun <reified T : Message> addMessageHandler(handler: MessageHandler<T>) {
        messageHandlerMap[T::class] = handler
    }

    /**
     * invoke handleMessage()
     */
    fun onReceiveMessage(message: Message) {
        (messageHandlerMap[message::class] as? MessageHandler<Message>)?.handleMessage(message)
    }
}