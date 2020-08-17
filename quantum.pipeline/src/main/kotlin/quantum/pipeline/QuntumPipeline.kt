package quantum.pipeline

import quantum.gate.QuantumGate
import quantum.state.ArrayQuantumState
import quantum.state.QuantumState

interface QuantumPipeline {
    fun assembly(
            state: QuantumState,
            vararg gates: QuantumGate,
            variables: Map<String, Any> = mapOf()
    ): AssembledQuantumPipeline

    fun evaluate(state: QuantumState,
                 vararg gates: QuantumGate,
                 variables: Map<String, Any> = mapOf()
    ): EvaluatedQuantumPipeline = assembly(state, *gates, variables = variables).evaluate()
}

interface AssembledQuantumPipeline {
    val state: QuantumState
    val gates: List<QuantumGate>
    fun evaluate(variables: Map<String, Any> = mapOf()): EvaluatedQuantumPipeline
}

interface EvaluatedQuantumPipeline {
    val output: QuantumState
    val variables: Map<String, Any>
}

interface QuantumPipelineFactory {
    val name: String
    fun getPipeline(): QuantumPipeline
}
