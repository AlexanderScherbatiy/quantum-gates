package quantum.gate

import quantum.bit.BitFunction

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

data class ControlledFunctionGate(val size: Int, val f: BitFunction) : QuantumGate

data class VariableGate(val name: String, val size: Int) : QuantumGate

data class TensorGate(val gate1: QuantumGate, val gate2: QuantumGate) : QuantumGate

infix fun QuantumGate.tensor(gate: QuantumGate): QuantumGate = TensorGate(this, gate)
