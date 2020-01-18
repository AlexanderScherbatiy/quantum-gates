package quantum.pipeline.utils

import quantum.bit.BitFunctionWithParameters
import quantum.gate.*
import quantum.state.*
import java.lang.RuntimeException

fun multiply(gate: QuantumGate, state: QuantumState): QuantumState =
        when (gate) {
            is IdentityGate -> state
            is HadamardGate -> when (state) {
                is ZeroQubit -> PlusQubit
                is OneQubit -> MinusQubit
                is PlusQubit -> ZeroQubit
                is MinusQubit -> OneQubit
                else -> throw RuntimeException(
                        "Unknown multiplication Hadamard gate on state: $state")
            }
            is ControlledFunctionGate -> {
                val f = gate.f
                when (f) {
                    is BitFunctionWithParameters -> state.controlledFunction(f)
                    else -> throw RuntimeException("BitFunction must be with parameters")

                }
            }
            is TensorGate -> {
                when (state) {
                    is TensorState -> {
                        val out1 = multiply(gate.gate1, state.state1)
                        val out2 = multiply(gate.gate2, state.state2)
                        TensorState(out1, out2)
                    }
                    else -> throw RuntimeException("Unknown state: $state")
                }
            }
            else -> throw RuntimeException("Unknown gate: $gate")
        }

