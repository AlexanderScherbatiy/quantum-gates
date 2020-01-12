package quantum.bit

interface Bit

object ZeroBit : Bit

object OneBit : Bit

data class VariableBit(val name: String) : Bit

data class Not(val bit: Bit) : Bit

data class And(val bit1: Bit, val bit2: Bit) : Bit

data class Or(val bit1: Bit, val bit2: Bit) : Bit

data class Xor(val bit1: Bit, val bit2: Bit) : Bit

interface BitFunction : Bit

data class BitFunctionWithParameters(val parameters: List<String>, val value: Bit) : BitFunction

data class VariableBitFunction(val size: Int, val name: String) : BitFunction

fun Bit.not(): Bit = Not(this)

infix fun Bit.and(bit: Bit): Bit = And(this, bit)

infix fun Bit.or(bit: Bit): Bit = Or(this, bit)

infix fun Bit.xor(bit: Bit): Bit = Xor(this, bit)

fun function(parameters: List<String>, f: (List<String>) -> Bit): BitFunctionWithParameters =
        BitFunctionWithParameters(parameters, f(parameters))
