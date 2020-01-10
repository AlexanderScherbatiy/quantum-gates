package quantum.state

import quantum.complex.Complex
import java.util.*

interface QuantumState

/**
 * Zero qubit:
 * |0>
 */
object ZeroQubit : QuantumState

/**
 * One qubit:
 * |1>
 */
object OneQubit : QuantumState

/**
 * Plus qubit:
 * (|0> + |1>)/sqrt(2)
 */
object PlusQubit : QuantumState

/**
 * Minus qubit:
 * (|0> - |1>)/sqrt(2)
 */
object MinusQubit : QuantumState

/**
 * Qubit:
 * zero * |0> + one * |1>
 */
data class Qubit(val zero: Complex, val one: Complex) : QuantumState

data class ArrayQuantumState(val values: Array<Complex>) : QuantumState {

    override fun equals(other: Any?) = when (other) {
        is ArrayQuantumState -> Arrays.equals(this.values, other.values)
        else -> false
    }

    override fun hashCode(): Int {
        return Arrays.hashCode(values)
    }

    override fun toString(): String {
        return "ArrayQuantumState ${values.contentDeepToString()}"
    }
}

data class VariableState(val size: Int, val name: String) : QuantumState

data class TensorState(val state1: QuantumState, val state2: QuantumState) : QuantumState

infix fun QuantumState.tensor(state: QuantumState) = TensorState(this, state)
