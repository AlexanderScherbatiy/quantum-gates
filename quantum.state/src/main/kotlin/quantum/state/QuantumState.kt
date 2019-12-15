package quantum.state

import quantum.complex.Complex

sealed class QuantumState

class QubitZero : QuantumState()

class QubitOne : QuantumState()

data class Qubit(val zero: Complex, val one: Complex) : QuantumState()



