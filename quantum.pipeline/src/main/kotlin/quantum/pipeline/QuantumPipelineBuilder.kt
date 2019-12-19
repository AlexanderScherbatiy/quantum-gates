package quantum.pipeline

import quantum.state.QuantumGate

class QuantumPipelineBuilder {

    /*
    Finite state machine
    (state, gate) -> state
     */

    private var blockItems = mutableListOf<BlockItem>()

    fun begin() = StateBuilder()

    inner class StateBuilder {
        fun gate(gate: QuantumGate): StateBuilder {
            blockItems.add(BlockItem(BlockType.GATE, GateBlock(gate)))
            return StateBuilder()
        }

        fun end(): QuantumPipeline = BaseQuantumPipeline(blockItems)
    }
}
