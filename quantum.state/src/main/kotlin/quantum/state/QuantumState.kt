package quantum.state

import quantum.complex.Complex

interface QuantumState

object QubitZero : QuantumState

object QubitOne : QuantumState

data class Qubit(val zero: Complex, val one: Complex) : QuantumState

data class VariableState(val name: String, val size: Int) : QuantumState
