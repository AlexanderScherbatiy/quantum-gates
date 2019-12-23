package quantum.pipeline

import quantum.state.QuantumGate
import quantum.state.QuantumState

interface QuantumBlock

interface ValueQuantumBlock

data class StateBlock(val state: QuantumState) : QuantumBlock

data class GateBlock(val gate: QuantumGate) : QuantumBlock

interface QuantumPipeline {
    fun blocks(): List<QuantumBlock>
    fun compile(values: List<ValueQuantumBlock>)
}

class BaseQuantumPipeline(val blocks: List<QuantumBlock>) : QuantumPipeline {

    override fun blocks() = blocks

    override fun compile(values: List<ValueQuantumBlock>) {
        TODO("not implemented")
    }
}
