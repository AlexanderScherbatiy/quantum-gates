package quantum.pipeline

import quantum.state.QuantumGate
import quantum.state.QuantumState

class QuantumPipelineBuilder {

    /*
    Finite state machine
    (state, gate) -> state
     */

    private var blocks = mutableListOf<QuantumBlock>()

    fun begin() = StateBuilder()

    inner class StateBuilder {
        fun state(state: QuantumState): GateBuilder {
            blocks.add(StateBlock(state))
            return GateBuilder()
        }
    }

    inner class GateBuilder {
        fun gate(gate: QuantumGate): GateBuilder {
            blocks.add(GateBlock(gate))
            return GateBuilder()
        }

        fun end(): QuantumPipeline = BaseQuantumPipeline(blocks)
    }
}
