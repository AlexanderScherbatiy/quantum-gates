package quantum.pipeline

import quantum.state.*
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

        fun valueNameError(description: String, name: String): Nothing =
                throw RuntimeException("$description value '$name' is not provided.")

        fun assembleState(s: QuantumState): QuantumState = when (s) {
            is VariableState -> statesMap.getOrElse(s.name) { valueNameError("State", s.name) }
            is TensorState -> TensorState(assembleState(s.state1), assembleState(s.state2))
            else -> s
        }

        val assembledState = assembleState(state)

        val assembledGates = gates.map { gate ->
            when (gate) {
                is VariableGate -> gatesMap.getOrElse(gate.name) { valueNameError("Gate", gate.name) }
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
