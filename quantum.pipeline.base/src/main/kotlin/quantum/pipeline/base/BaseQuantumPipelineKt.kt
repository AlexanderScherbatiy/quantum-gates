package quantum.pipeline.base

import quantum.bit.BitFunctionWithParameters
import quantum.bit.VariableBitFunction
import quantum.pipeline.AssembledQuantumPipeline
import quantum.pipeline.EvaluatedQuantumPipeline
import quantum.pipeline.QuantumPipeline
import quantum.pipeline.QuantumPipelineFactory
import quantum.state.*
import quantum.gate.*
import java.lang.RuntimeException

class BaseQuantumPipelineFactory : QuantumPipelineFactory {
    override val name = "base"
    override fun getPipeline(state: QuantumState, gates: List<QuantumGate>) = BaseQuantumPipeline(state, gates)
}


fun <T> blockValue(map: Map<String, T>, blockName: String, variableName: String): T =
        map.getOrElse(variableName) {
            throw RuntimeException("$blockName value '$variableName' is not provided.")
        }

class BaseQuantumPipeline(override val state: QuantumState,
                          override val gates: List<QuantumGate>) : QuantumPipeline {

    override fun assembly(statesMap: Map<String, QuantumState>,
                          gatesMap: Map<String, QuantumGate>,
                          bitFunctionsMap: Map<String, BitFunctionWithParameters>): AssembledQuantumPipeline {

        fun assembleState(s: QuantumState): QuantumState = when (s) {
            is VariableState -> blockValue(statesMap, "State", s.name)
            is TensorState -> TensorState(assembleState(s.state1), assembleState(s.state2))
            else -> s
        }

        val assembledState = assembleState(state)

        val assembledGates = gates.map { gate ->
            when (gate) {
                is VariableGate -> blockValue(gatesMap, "Gate", gate.name)
                is ControlledFunctionGate -> {
                    val f = gate.f
                    when (f) {
                        is VariableBitFunction -> ControlledFunctionGate(
                                gate.size,
                                blockValue(bitFunctionsMap, "BitFunction", f.name))
                        else -> gate
                    }
                }
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
