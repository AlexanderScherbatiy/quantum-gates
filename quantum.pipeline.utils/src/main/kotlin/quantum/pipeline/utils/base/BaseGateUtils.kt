package quantum.pipeline.utils.base

import quantum.bit.BitFunctionWithParameters
import quantum.gate.*
import quantum.pipeline.utils.memory.controlledFunction
import quantum.state.*

/**
 * Multiplies base quantum gates and states.
 * Returns UnknownBaseQuantumState in case the result is not processed.
 */
fun baseMultiply(gate: QuantumGate, state: QuantumState): QuantumState =
        when (gate) {
            is IdentityGate -> state
            is HadamardGate -> when (state) {
                is ZeroQubit -> PlusQubit
                is OneQubit -> MinusQubit
                is PlusQubit -> ZeroQubit
                is MinusQubit -> OneQubit
                else -> UnknownBaseQuantumState
            }
            is ControlledNotGate -> baseControlledNot(state)
            is ControlledFunctionGate -> {
                val f = gate.f
                when (f) {
                    is BitFunctionWithParameters -> state.controlledFunction(f)
                    else -> UnknownBaseQuantumState
                }
            }
            is TensorGate -> {
                when (state) {
                    is TensorState -> {
                        val out1 = baseMultiply(gate.gate1, state.state1)
                        val out2 = baseMultiply(gate.gate2, state.state2)
                        TensorState(out1, out2)
                    }
                    else -> UnknownBaseQuantumState
                }
            }
            else -> UnknownBaseQuantumState
        }

fun baseSwap(state: QuantumState): QuantumState = when (state) {
    is ZeroQubit -> OneQubit
    is OneQubit -> ZeroQubit
    is PlusQubit -> PlusQubit
    is Qubit -> Qubit(state.one, state.zero)
    else -> UnknownBaseQuantumState
}


fun baseControlledNot(state: QuantumState): QuantumState = when (state) {
    is TensorState -> {
        val contorl = state.state1
        val target = state.state2
        when {
            contorl is ZeroQubit -> state
            contorl is OneQubit -> TensorState(contorl, baseSwap(target))
            else -> UnknownBaseQuantumState
        }
    }
    else -> UnknownBaseQuantumState
}
