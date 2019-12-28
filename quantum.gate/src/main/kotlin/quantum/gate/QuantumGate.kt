package quantum.state

interface QuantumGate

data class IdentityGate(val size: Int) : QuantumGate

/**
 * HadamardGate
 * ( 1  1)
 * ( 1 -1) / sqrt(2)
 *
 * Multiplication table:
 * HadamardGate * QubitZero  = QubitPlus
 * HadamardGate * QubitOne   = QubitMinuse
 * HadamardGate * QubitPlus  = QubitZero
 * HadamardGate * QubitMinus = QubitOne
 */
object HadamardGate : QuantumGate

data class VariableGate(val name: String, val size: Int) : QuantumGate
