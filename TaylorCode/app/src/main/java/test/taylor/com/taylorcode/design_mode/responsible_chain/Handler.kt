package test.taylor.com.taylorcode.design_mode.responsible_chain

interface Handler<I, O> {
    fun handle(data: I, chain: Chain<I, O>): O

    interface Chain<I, O> {
        fun next(data: I): O
    }
}
