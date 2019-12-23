package quantum.pipeline

import quantum.state.QuantumGate

class QuantumPipelineBuilder {

    /*
    Finite state machine
    (state, gate) -> state
     */

    private var blocks = mutableListOf<QuantumBlock>()

    fun begin() = StateBuilder()

    inner class StateBuilder {
        fun gate(gate: QuantumGate): StateBuilder {
            blocks.add(GateBlock(gate))
            return StateBuilder()
        }

        fun end(): QuantumPipeline = BaseQuantumPipeline(blocks)
    }
}
