package quantum.pipeline.utils

import quantum.bit.*
import java.lang.RuntimeException

fun BitFunctionWithParameters.apply(bits: List<Bit>): Bit {

    assert(parameters.size == bits.size)

    val map = mutableMapOf<String, Bit>()

    for ((i, param) in parameters.withIndex()) {
        map[param] = bits[i]
    }

    return this.value.apply(map)
}

fun Bit.apply(map: Map<String, Bit>): Bit = when (this) {

    ZeroBit, OneBit -> this
    is VariableBit -> blockValue( "Bit value", name, map)
    is Not -> {
        val r = bit.apply(map)
        if (r == ZeroBit) OneBit else ZeroBit
    }
    is And -> {
        val r1 = bit1.apply(map)
        val r2 = bit2.apply(map)
        if (r1 == ZeroBit) r1 else r2
    }
    is Or -> {
        val r1 = bit1.apply(map)
        val r2 = bit2.apply(map)
        if (r1 == OneBit) r1 else r2
    }
    is Xor -> {
        val r1 = bit1.apply(map)
        val r2 = bit2.apply(map)
        if (r1 == r2) ZeroBit else OneBit
    }
    else -> throw RuntimeException("Unknown bit: $this")
}
