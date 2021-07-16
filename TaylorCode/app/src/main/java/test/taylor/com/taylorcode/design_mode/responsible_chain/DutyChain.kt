package test.taylor.com.taylorcode.design_mode.responsible_chain

class DutyChain<I, O>(private val firstData: I) {
    private val handlers = mutableListOf<Handler<I, O>>()

    fun addHandler(handler: Handler<I, O>) {
        handlers.add(handler)
    }

    fun execute(): O {
        return HandlerChain(handlers, 0).next(firstData)
    }
}