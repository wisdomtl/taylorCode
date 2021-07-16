package test.taylor.com.taylorcode.design_mode.responsible_chain

import java.lang.AssertionError

class HandlerChain<I, O>(
    private val handlers: List<Handler<I, O>>,
    private var index: Int = 0
) : Handler.Chain<I, O> {

    override fun next(data: I): O {
        if (index >= handlers.size) throw AssertionError()
        val next = HandlerChain(handlers, index + 1)
        return handlers[index].handle(data, next)
    }
}