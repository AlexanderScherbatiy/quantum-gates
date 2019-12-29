package quantum.gate

interface QuantumGate

data class IdentityGate(val size: Int) : QuantumGate

/**
 * HadamardGate
 * ( 1  1)
 * ( 1 -1) / sqrt(2)
 *
 * Multiplication table:
 * HadamardGate * ZeroQubit  = PlusQubit
 * HadamardGate * OneQubit   = QubitMinuse
 * HadamardGate * PlusQubit  = ZeroQubit
 * HadamardGate * MinusQubit = OneQubit
 */
object HadamardGate : QuantumGate

data class VariableGate(val name: String, val size: Int) : QuantumGate
