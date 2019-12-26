package quantum.pipeline

import quantum.state.QuantumGate
import quantum.state.QuantumState

class QuantumPipelineBuilder {

    /*
    Finite state machine
    (state, gate) -> state
     */

    private lateinit var state: QuantumState
    private val gates = mutableListOf<QuantumGate>()

    fun begin() = StateBuilder()

    inner class StateBuilder {
        fun state(state: QuantumState): GateBuilder {
            this@QuantumPipelineBuilder.state = state
            return GateBuilder()
        }
    }

    inner class GateBuilder {
        fun gate(gate: QuantumGate): GateBuilder {
            this@QuantumPipelineBuilder.gates.add(gate)
            return GateBuilder()
        }

        fun end(): QuantumPipeline = BaseQuantumPipeline(state, gates)
    }
}
