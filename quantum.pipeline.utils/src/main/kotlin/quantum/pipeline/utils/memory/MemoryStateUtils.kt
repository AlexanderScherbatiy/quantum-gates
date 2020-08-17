package quantum.pipeline.utils.memory

import quantum.bit.Bit
import quantum.bit.BitFunctionWithParameters
import quantum.bit.OneBit
import quantum.bit.ZeroBit
import quantum.complex.Complex
import quantum.complex.ZeroComplex
import quantum.pipeline.utils.base.toComplex
import quantum.pipeline.utils.base.apply
import quantum.pipeline.utils.base.baseToArrayQuantumState
import quantum.state.*
import java.lang.RuntimeException
import kotlin.math.sqrt

val inv_sqrt2 = 1.0 / sqrt(2.0)

fun List<Double>.toQuantumState(): ArrayQuantumState =
        ArrayQuantumState(*(this.map { it.toComplex() }.toTypedArray()))

fun QuantumState.controlledFunction(f: BitFunctionWithParameters): QuantumState {

    val input = this.baseToArrayQuantumState().values
    val stateSize = input.size
    val output = Array<Complex>(stateSize) { ZeroComplex }

    val bitsSize = f.parameters.size + 1
    val bits = Array<Bit>(bitsSize) { ZeroBit }

    for (stateIndex in (0 until stateSize)) {

        val x = bits.take(bitsSize - 1)
        val y = bits[bitsSize - 1]

        val res = f.apply(x)

        // flip last index bit
        val i = when {
            res == ZeroBit -> stateIndex
            y == ZeroBit -> stateIndex + 1
            else -> stateIndex - 1
        }

        output[i] = input[stateIndex]

        // increment bits from right to left
        var bitsIndex = bitsSize - 1
        while (bitsIndex >= 0) {
            if (bits[bitsIndex] == ZeroBit) {
                bits[bitsIndex] = OneBit
                break
            } else {
                bits[bitsIndex] = ZeroBit
                bitsIndex--
            }
        }
    }

    return ArrayQuantumState(*output)
}
