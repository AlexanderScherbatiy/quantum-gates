package quantum.pipeline

import quantum.bit.BitFunctionWithParameters
import quantum.state.QuantumState
import quantum.gate.QuantumGate

interface QuantumPipeline {
    val state: QuantumState
    val gates: List<QuantumGate>
    fun assembly(statesMap: Map<String, QuantumState> = mapOf(),
                 gatesMap: Map<String, QuantumGate> = mapOf(),
                 bitFunctionsMap: Map<String, BitFunctionWithParameters> = mapOf()
    ): AssembledQuantumPipeline
}

interface AssembledQuantumPipeline {
    val state: QuantumState
    val gates: List<QuantumGate>
    fun evaluate(): EvaluatedQuantumPipeline
}

interface EvaluatedQuantumPipeline {
    val output: QuantumState
}

interface QuantumPipelineFactory {
    val name: String
    fun getPipeline(state: QuantumState,
                    gates: List<QuantumGate>): QuantumPipeline
}

