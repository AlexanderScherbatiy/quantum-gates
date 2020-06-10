package quantum.pipeline.utils

import quantum.bit.BitFunctionWithParameters
import quantum.gate.*
import quantum.state.*
import java.lang.RuntimeException

/**
 * Multiplies standard quantum gates and states.
 * Rerturns UnknownQuantumState in case the result is not processed.
 */
fun multiply(gate: QuantumGate, state: QuantumState): QuantumState =
        when (gate) {
            is IdentityGate -> state
            is HadamardGate -> when (state) {
                is ZeroQubit -> PlusQubit
                is OneQubit -> MinusQubit
                is PlusQubit -> ZeroQubit
                is MinusQubit -> OneQubit
                else -> UnknownQuantumState
            }
            is ControlledNotGate -> controlledNot(state)
            is ControlledFunctionGate -> {
                val f = gate.f
                when (f) {
                    is BitFunctionWithParameters -> state.controlledFunction(f)
                    else -> UnknownQuantumState
                }
            }
            is TensorGate -> {
                when (state) {
                    is TensorState -> {
                        val out1 = multiply(gate.gate1, state.state1)
                        val out2 = multiply(gate.gate2, state.state2)
                        TensorState(out1, out2)
                    }
                    else -> UnknownQuantumState
                }
            }
            else -> UnknownQuantumState
        }

fun swap(state: QuantumState): QuantumState = when (state) {
    is ZeroQubit -> OneQubit
    is OneQubit -> ZeroQubit
    is Qubit -> Qubit(state.one, state.zero)
    else -> UnknownQuantumState
}


fun controlledNot(state: QuantumState): QuantumState = when (state) {
    is TensorState -> {
        val contorl = state.state1
        val target = state.state2
        when {
            contorl is ZeroQubit -> state
            contorl is OneQubit -> TensorState(contorl, swap(target))
            else -> UnknownQuantumState
        }
    }
    else -> UnknownQuantumState
}

fun memoryMultiply(gate: QuantumGate, state: QuantumState): QuantumState = when (gate) {
    is IdentityGate -> state
    is ControlledNotGate -> memoryControlledNot(state)
    else -> UnknownQuantumState
}


fun memoryControlledNot(state: QuantumState): QuantumState {
    val values = state.toArrayState().values
    val c0 = values[0]
    val c1 = values[1]
    val c2 = values[2]
    val c3 = values[3]
    return ArrayQuantumState(arrayOf(c0, c1, c3, c2))
}
