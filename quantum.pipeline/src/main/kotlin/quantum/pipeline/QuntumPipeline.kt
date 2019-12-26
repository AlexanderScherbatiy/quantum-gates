package quantum.pipeline

import quantum.state.QuantumGate
import quantum.state.QuantumState
import quantum.state.VariableState
import java.lang.RuntimeException

interface QuantumPipeline {
    val state: QuantumState
    val gates: List<QuantumGate>
    fun assembly(states: List<Pair<String, QuantumState>>,
                 gates: List<Pair<String, QuantumGate>>): AssembledQuantumPipeline
}

interface AssembledQuantumPipeline {
    val state: QuantumState
    val gates: List<QuantumGate>
    fun run(): QuantumState
}

class BaseQuantumPipeline(override val state: QuantumState,
                          override val gates: List<QuantumGate>) : QuantumPipeline {

    override fun assembly(stateValues: List<Pair<String, QuantumState>>,
                          gateValues: List<Pair<String, QuantumGate>>): AssembledQuantumPipeline {

        val statesMap = stateValues.map { it.first to it.second }.toMap()

        val assembledState = when (state) {
            is VariableState -> statesMap.getOrElse(state.name) {
                throw RuntimeException("State value '${state.name}' is not provided.")
            }
            else -> state
        }

        return AssembledBaseQuantumPipeline(assembledState, gates)
    }
}

class AssembledBaseQuantumPipeline(override val state: QuantumState,
                                   override val gates: List<QuantumGate>) : AssembledQuantumPipeline {

    override fun run(): QuantumState {
        TODO("not implemented")
    }
}
