package quantum.pipeline.base

import quantum.pipeline.AssembledQuantumPipeline
import quantum.pipeline.EvaluatedQuantumPipeline
import quantum.pipeline.QuantumPipeline
import quantum.pipeline.QuantumPipelineFactory
import quantum.state.*
import java.lang.RuntimeException

class BaseQuantumPipelineFactory : QuantumPipelineFactory {
    override val name = "base"
    override fun getPipeline(state: QuantumState, gates: List<QuantumGate>) = BaseQuantumPipeline(state, gates)
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

        return BaseAssembledQuantumPipeline(assembledState, assembledGates)
    }
}

class BaseAssembledQuantumPipeline(override val state: QuantumState,
                                   override val gates: List<QuantumGate>) : AssembledQuantumPipeline {

    override fun evaluate(): EvaluatedQuantumPipeline {
        var output = state
        for (gate in gates) {
            output = multiply(gate, output)
        }
        return BaseEvaluatedQuantumPipeline(output)
    }
}

data class BaseEvaluatedQuantumPipeline(override val output: QuantumState) : EvaluatedQuantumPipeline

fun multiply(gate: QuantumGate, state: QuantumState): QuantumState =
        when (gate) {
            is IdentityGate -> state
            is HadamardGate -> when (state) {
                is ZeroQubit -> PlusQubit
                is OneQubit -> MinusQubit
                is PlusQubit -> ZeroQubit
                is MinusQubit -> OneQubit
                else -> throw RuntimeException(
                        "Unknown multiplication Hadamard gate on state: $state")
            }
            else -> throw RuntimeException("Unknown gate: $gate")
        }
