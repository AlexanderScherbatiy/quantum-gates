package quantum.pipeline

import quantum.state.QuantumGate
import quantum.state.QuantumState

enum class BlockType {
    GATE
}

interface QuantumBlock

data class GateBlock(val gate: QuantumGate) : QuantumBlock

data class BlockItem(val type: BlockType, val block: QuantumBlock)

interface QuantumPipeline {
    fun blockItems(): List<BlockItem>
    fun compute(state: QuantumState)
}

class BaseQuantumPipeline(val blockItems: List<BlockItem>) : QuantumPipeline {

    override fun blockItems() = blockItems

    override fun compute(state: QuantumState) {
        TODO("not implemented")
    }
}
