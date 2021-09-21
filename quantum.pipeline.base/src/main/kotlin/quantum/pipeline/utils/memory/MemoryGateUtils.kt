package quantum.pipeline.utils.memory

import quantum.gate.ControlledNotGate
import quantum.gate.IdentityGate
import quantum.gate.QuantumGate
import quantum.pipeline.utils.base.UnknownBaseQuantumState
import quantum.pipeline.utils.base.baseToArrayQuantumState
import quantum.state.ArrayQuantumState
import quantum.state.QuantumState

fun memoryMultiply(gate: QuantumGate, state: QuantumState): QuantumState = when (gate) {
    is ControlledNotGate -> memoryControlledNot(state)
    else -> UnknownBaseQuantumState
}


fun memoryControlledNot(state: QuantumState): QuantumState {
    val values = state.baseToArrayQuantumState().values
    val c0 = values[0]
    val c1 = values[1]
    val c2 = values[2]
    val c3 = values[3]
    return ArrayQuantumState(c0, c1, c3, c2)
}
