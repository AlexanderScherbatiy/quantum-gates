package quantum.pipeline

import quantum.state.QuantumGate
import quantum.state.QuantumState
import quantum.state.VariableState
import java.lang.RuntimeException

interface QuantumBlock

interface ValueQuantumBlock {
    val name: String
}

data class StateBlock(val state: QuantumState) : QuantumBlock

data class GateBlock(val gate: QuantumGate) : QuantumBlock

data class ValueStateBlock(override val name: String, val state: QuantumState) : ValueQuantumBlock

interface QuantumPipeline {
    fun blocks(): List<QuantumBlock>
    fun assembly(values: List<ValueQuantumBlock>): AssembledQuantumPipeline
}

interface AssembledQuantumPipeline {
    fun blocks(): List<QuantumBlock>
    fun run(): QuantumState
}

class BaseQuantumPipeline(val blocks: List<QuantumBlock>) : QuantumPipeline {

    override fun blocks() = blocks

    override fun assembly(values: List<ValueQuantumBlock>): AssembledQuantumPipeline {

        val valuesMap = values.map { it.name to it }.toMap()

        val assembledBlocks = blocks.map { block ->
            when {
                block is StateBlock && block.state is VariableState -> {
                    val name = block.state.name
                    val value = valuesMap.getOrElse(name) {
                        throw RuntimeException("State value '$name' is not provided.")
                    }
                    when (value) {
                        is ValueStateBlock -> StateBlock(value.state)
                        else -> throw RuntimeException(
                                "Block provided for name: '$name' is not a ValueStateBlock.")
                    }
                }
                else -> block
            }
        }

        return AssembledBaseQuantumPipeline(assembledBlocks)
    }
}

class AssembledBaseQuantumPipeline(val blocks: List<QuantumBlock>) : AssembledQuantumPipeline {

    override fun blocks() = blocks

    override fun run(): QuantumState {
        TODO("not implemented")
    }
}
