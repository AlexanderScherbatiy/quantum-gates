package quantum.bit

interface Bit

object ZeroBit : Bit

object OneBit : Bit

data class VariableBit(val name: String) : Bit

data class Not(val bit: Bit) : Bit

data class And(val bit1: Bit, val bit2: Bit) : Bit

data class Or(val bit1: Bit, val bit2: Bit) : Bit

data class BitFunction(val parameters: List<String>, val value: Bit) : Bit

fun Bit.not(): Bit = Not(this)

infix fun Bit.and(bit: Bit): Bit = And(this, bit)

infix fun Bit.or(bit: Bit): Bit = Or(this, bit)

fun function(parameters: List<String>, f: (List<String>) -> Bit): BitFunction =
        BitFunction(parameters, f(parameters))
