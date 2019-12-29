package quantum.state

import quantum.complex.Complex

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

data class VariableState(val name: String, val size: Int) : QuantumState

data class TensorState(val state1: QuantumState, val state2: QuantumState) : QuantumState

infix fun QuantumState.tensor(state: QuantumState) = TensorState(this, state)