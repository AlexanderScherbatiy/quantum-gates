package quantum.pipeline.utils.base

import quantum.gate.QuantumGate
import quantum.state.QuantumState

interface QuantumOperation<State : QuantumState, Gate : QuantumGate> {
    fun convert(state: QuantumState): State
    fun convert(gate: QuantumGate): Gate
    fun multiply(state: State, gate: Gate): State
}