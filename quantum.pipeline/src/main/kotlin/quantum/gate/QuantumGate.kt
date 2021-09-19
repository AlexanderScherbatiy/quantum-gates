package quantum.gate

import quantum.bit.Bit
import quantum.bit.BitFunction
import quantum.state.IndexedCoefficient
import quantum.state.QuantumState

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

object ControlledNotGate : QuantumGate

data class ControlledFunctionGate(val size: Int, val f: BitFunction) : QuantumGate

data class VariableGate(val size: Int, val name: String) : QuantumGate

data class TensorGate(val gate1: QuantumGate, val gate2: QuantumGate) : QuantumGate

class IndexedTensorQuantumGate(
    val dimensions: Array<Int>,
    vararg val coefficients: IndexedCoefficient
) : QuantumGate

interface QuantumMeasure : QuantumGate

data class QuantumMeasureState(val index: Int, val resultBits: String) : QuantumMeasure

infix fun QuantumGate.tensor(gate: QuantumGate): QuantumGate = TensorGate(this, gate)
