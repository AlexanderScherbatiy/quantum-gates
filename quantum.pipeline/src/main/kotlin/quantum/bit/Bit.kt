package quantum.bit

interface Bit

object ZeroBit : Bit

object OneBit : Bit

data class Not(val bit: Bit) : Bit

data class And(val bit1: Bit, val bit2: Bit) : Bit

data class Or(val bit1: Bit, val bit2: Bit) : Bit

fun Bit.not(): Bit = Not(this)

infix fun Bit.and(bit: Bit): Bit = And(this, bit)

infix fun Bit.or(bit: Bit): Bit = Or(this, bit)
