package quantum.pipeline

import quantum.state.QuantumGate
import quantum.state.QuantumState
import quantum.state.VariableGate
import quantum.state.VariableState
import java.lang.RuntimeException

interface QuantumPipeline {
    val state: QuantumState
    val gates: List<QuantumGate>
    fun assembly(stateValues: List<Pair<String, QuantumState>>,
                 gateValues: List<Pair<String, QuantumGate>>): AssembledQuantumPipeline
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
        val gatesMap = gateValues.map { it.first to it.second }.toMap()

        val assembledState = when (state) {
            is VariableState -> statesMap.getOrElse(state.name) {
                throw RuntimeException("State value '${state.name}' is not provided.")
            }
            else -> state
        }

        val assembledGates = gates.map { gate ->
            when (gate) {
                is VariableGate -> gatesMap.getOrElse(gate.name) {
                    throw RuntimeException("Gate value '${gate.name}' is not provided.")
                }
                else -> gate
            }
        }

        return AssembledBaseQuantumPipeline(assembledState, assembledGates)
    }
}

class AssembledBaseQuantumPipeline(override val state: QuantumState,
                                   override val gates: List<QuantumGate>) : AssembledQuantumPipeline {

    override fun run(): QuantumState {
        TODO("not implemented")
    }
}
