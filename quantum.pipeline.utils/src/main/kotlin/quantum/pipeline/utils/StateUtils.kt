package quantum.pipeline.utils

import quantum.bit.Bit
import quantum.bit.BitFunctionWithParameters
import quantum.bit.OneBit
import quantum.bit.ZeroBit
import quantum.complex.Complex
import quantum.complex.toComplex
import quantum.state.*
import java.lang.RuntimeException
import kotlin.math.sqrt

val inv_sqrt2 = 1.0 / sqrt(2.0)

object UnknownQuantumState : QuantumState

fun List<Double>.toQuantumState(): ArrayQuantumState =
        ArrayQuantumState(this.map { it.toComplex() }.toTypedArray())

fun QuantumState.toArrayState() = when (this) {
    is ZeroQubit -> listOf(1.0, 0.0).toQuantumState()
    is OneQubit -> listOf(0.0, 1.0).toQuantumState()
    is ArrayQuantumState -> this
    is TensorState -> {
        when (state1) {
            is ZeroQubit -> when (state2) {
                is ZeroQubit -> listOf(1.0, 0.0, 0.0, 0.0).toQuantumState()
                is OneQubit -> listOf(0.0, 1.0, 0.0, 0.0).toQuantumState()
                else -> throw RuntimeException("Unknown state: $this")
            }
            is OneQubit -> when (state2) {
                is ZeroQubit -> listOf(0.0, 0.0, 1.0, 0.0).toQuantumState()
                is OneQubit -> listOf(0.0, 0.0, 0.0, 1.0).toQuantumState()
                else -> throw RuntimeException("Unknown state: $this")
            }
            is PlusQubit -> when (state2) {
                is ZeroQubit -> listOf(inv_sqrt2, 0.0, inv_sqrt2, 0.0).toQuantumState()
                is PlusQubit -> listOf(0.5, 0.5, 0.5, 0.5).toQuantumState()
                is MinusQubit -> listOf(0.5, -0.5, 0.5, -0.5).toQuantumState()
                else -> throw RuntimeException("Unknown state: $this")
            }
            else -> throw RuntimeException("Unknown state: $this")
        }
    }
    else -> throw RuntimeException("Unknown state: $this")
}

fun QuantumState.controlledFunction(f: BitFunctionWithParameters): QuantumState {

    val input = this.toArrayState().values
    val stateSize = input.size
    val output = Array(stateSize) { Complex.Zero }

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

    return ArrayQuantumState(output)
}
