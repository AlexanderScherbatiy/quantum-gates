package quantum.pipeline.memory

import quantum.bit.BitFunctionWithParameters
import quantum.bit.VariableBitFunction
import quantum.gate.ControlledFunctionGate
import quantum.gate.QuantumGate
import quantum.gate.VariableGate
import quantum.pipeline.AssembledQuantumPipeline
import quantum.pipeline.EvaluatedQuantumPipeline
import quantum.pipeline.QuantumPipeline
import quantum.pipeline.QuantumPipelineFactory
import quantum.pipeline.utils.base.baseMultiply
import quantum.pipeline.utils.base.UnknownBaseQuantumState
import quantum.pipeline.utils.variable.variableValue
import quantum.pipeline.utils.memory.memoryMultiply
import quantum.state.QuantumState
import quantum.state.TensorState
import quantum.state.VariableState

class MemoryQuantumPipelineFactory : QuantumPipelineFactory {
    override val name = "memory"
    override fun getPipeline(state: QuantumState, gates: List<QuantumGate>) = MemoryQuantumPipeline(state, gates)
}


class MemoryQuantumPipeline(override val state: QuantumState,
                            override val gates: List<QuantumGate>) : QuantumPipeline {

    override fun assembly(statesMap: Map<String, QuantumState>,
                          gatesMap: Map<String, QuantumGate>,
                          bitFunctionsMap: Map<String, BitFunctionWithParameters>): AssembledQuantumPipeline {

        fun assembleState(s: QuantumState): QuantumState = when (s) {
            is VariableState -> variableValue("State", s.name, statesMap)
            is TensorState -> TensorState(assembleState(s.state1), assembleState(s.state2))
            else -> s
        }

        val assembledState = assembleState(state)

        val assembledGates = gates.map { gate ->
            when (gate) {
                is VariableGate -> variableValue("Gate", gate.name, gatesMap)
                is ControlledFunctionGate -> {
                    val f = gate.f
                    when (f) {
                        is VariableBitFunction -> ControlledFunctionGate(
                                gate.size,
                                variableValue("BitFunction", f.name, bitFunctionsMap))
                        else -> gate
                    }
                }
                else -> gate
            }
        }

        return MemoryAssembledQuantumPipeline(assembledState, assembledGates)
    }
}

class MemoryAssembledQuantumPipeline(override val state: QuantumState,
                                     override val gates: List<QuantumGate>) : AssembledQuantumPipeline {

    override fun evaluate(): EvaluatedQuantumPipeline {
        var output = state
        for (gate in gates) {
            output = multiplyMemoryPipeline(gate, output)
        }
        return MemoryEvaluatedQuantumPipeline(output)
    }
}

data class MemoryEvaluatedQuantumPipeline(override val output: QuantumState) : EvaluatedQuantumPipeline

fun multiplyMemoryPipeline(gate: QuantumGate, state: QuantumState): QuantumState {
    val res = baseMultiply(gate, state)
    return if (res == UnknownBaseQuantumState) memoryMultiply(gate, state) else res
}
