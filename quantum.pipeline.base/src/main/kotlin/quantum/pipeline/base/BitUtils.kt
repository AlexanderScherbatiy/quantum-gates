package quantum.pipeline.utils.base

import quantum.bit.*
import quantum.pipeline.utils.variable.variableValue
import java.lang.RuntimeException

fun BitFunctionWithParameters.apply(bits: List<Bit>): Bit {

    assert(parameters.size == bits.size)

    val map = mutableMapOf<String, Bit>()

    for ((i, param) in parameters.withIndex()) {
        map[param] = bits[i]
    }

    return this.value.apply(map)
}

fun Bit.apply(variables: Map<String, Bit> = mapOf()): Bit = when (this) {

    ZeroBit, OneBit -> this
    is VariableBit -> variableValue("Bit value", name, variables)
    is Not -> {
        val r = bit.apply(variables)
        if (r == ZeroBit) OneBit else ZeroBit
    }
    is And -> {
        val r1 = bit1.apply(variables)
        val r2 = bit2.apply(variables)
        if (r1 == ZeroBit) r1 else r2
    }
    is Or -> {
        val r1 = bit1.apply(variables)
        val r2 = bit2.apply(variables)
        if (r1 == OneBit) r1 else r2
    }
    is Xor -> {
        val r1 = bit1.apply(variables)
        val r2 = bit2.apply(variables)
        if (r1 == r2) ZeroBit else OneBit
    }
    else -> throw RuntimeException("Unknown bit: $this")
}
