package quantum.state

import quantum.complex.Complex

interface QuantumState

object QubitZero : QuantumState

object QubitOne : QuantumState

data class Qubit(val zero: Complex, val one: Complex) : QuantumState

data class VariableState(val name: String, val size: Int) : QuantumState

data class TensorState(val state1: QuantumState, val state2: QuantumState) : QuantumState

infix fun QuantumState.tensor(state: QuantumState) = TensorState(this, state)
