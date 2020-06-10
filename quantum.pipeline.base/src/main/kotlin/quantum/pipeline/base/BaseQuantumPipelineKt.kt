package quantum.pipeline.base

import quantum.bit.BitFunctionWithParameters
import quantum.bit.VariableBitFunction
import quantum.gate.ControlledFunctionGate
import quantum.gate.QuantumGate
import quantum.gate.VariableGate
import quantum.pipeline.AssembledQuantumPipeline
import quantum.pipeline.EvaluatedQuantumPipeline
import quantum.pipeline.QuantumPipeline
import quantum.pipeline.QuantumPipelineFactory
import quantum.pipeline.utils.UnknownQuantumState
import quantum.pipeline.utils.blockValue
import quantum.pipeline.utils.memoryMultiply
import quantum.pipeline.utils.multiply
import quantum.state.QuantumState
import quantum.state.TensorState
import quantum.state.VariableState

class BaseQuantumPipelineFactory : QuantumPipelineFactory {
    override val name = "base"
    override fun getPipeline(state: QuantumState, gates: List<QuantumGate>) = BaseQuantumPipeline(state, gates)
}


class BaseQuantumPipeline(override val state: QuantumState,
                          override val gates: List<QuantumGate>) : QuantumPipeline {

    override fun assembly(statesMap: Map<String, QuantumState>,
                          gatesMap: Map<String, QuantumGate>,
                          bitFunctionsMap: Map<String, BitFunctionWithParameters>): AssembledQuantumPipeline {

        fun assembleState(s: QuantumState): QuantumState = when (s) {
            is VariableState -> blockValue("State", s.name, statesMap)
            is TensorState -> TensorState(assembleState(s.state1), assembleState(s.state2))
            else -> s
        }

        val assembledState = assembleState(state)

        val assembledGates = gates.map { gate ->
            when (gate) {
                is VariableGate -> blockValue("Gate", gate.name, gatesMap)
                is ControlledFunctionGate -> {
                    val f = gate.f
                    when (f) {
                        is VariableBitFunction -> ControlledFunctionGate(
                                gate.size,
                                blockValue("BitFunction", f.name, bitFunctionsMap))
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
            output = baseMultiply(gate, output)
        }
        return BaseEvaluatedQuantumPipeline(output)
    }
}

data class BaseEvaluatedQuantumPipeline(override val output: QuantumState) : EvaluatedQuantumPipeline

private fun baseMultiply(gate: QuantumGate, state: QuantumState): QuantumState {
    val res = multiply(gate, state)
    return if (res == UnknownQuantumState) memoryMultiply(gate, state) else res
}

