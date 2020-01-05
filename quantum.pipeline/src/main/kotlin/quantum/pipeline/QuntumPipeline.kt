package quantum.pipeline

import quantum.bit.BitFunctionWithParameters
import quantum.state.QuantumState
import quantum.gate.QuantumGate

interface QuantumPipeline {
    val state: QuantumState
    val gates: List<QuantumGate>
    fun assembly(stateValues: List<Pair<String, QuantumState>> = listOf(),
                 gateValues: List<Pair<String, QuantumGate>> = listOf(),
                 bitFunctions: List<Pair<String, BitFunctionWithParameters>> = listOf()
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

