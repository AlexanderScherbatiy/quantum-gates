package quantum.pipeline.utils.base

import quantum.complex.OneComplex
import quantum.complex.ZeroComplex
import quantum.pipeline.utils.memory.inv_sqrt2
import quantum.pipeline.utils.memory.toQuantumState
import quantum.state.*
import java.lang.RuntimeException
import kotlin.math.sqrt

object UnknownBaseQuantumState : QuantumState

fun QuantumState.baseToArrayQuantumState() = when (this) {
    is ZeroQubit -> listOf(1.0, 0.0).toQuantumState()
    is OneQubit -> listOf(0.0, 1.0).toQuantumState()
    is PlusQubit -> listOf(inv_sqrt2, inv_sqrt2).toQuantumState()
    is MinusQubit -> listOf(inv_sqrt2, -inv_sqrt2).toQuantumState()
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