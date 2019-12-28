package quantum.pipeline

import quantum.state.*

interface QuantumPipeline {
    val state: QuantumState
    val gates: List<QuantumGate>
    fun assembly(stateValues: List<Pair<String, QuantumState>>,
                 gateValues: List<Pair<String, QuantumGate>>): AssembledQuantumPipeline
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

