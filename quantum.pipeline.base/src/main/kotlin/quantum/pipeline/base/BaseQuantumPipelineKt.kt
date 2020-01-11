package quantum.pipeline.base

import quantum.bit.*
import quantum.complex.Complex
import quantum.complex.toComplex
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
            is ControlledFunctionGate -> {
                val f = gate.f
                when (f) {
                    is BitFunctionWithParameters -> state.controlledFunction(f)
                    else -> throw RuntimeException("BitFunction must be with parameters")

                }
            }
            else -> throw RuntimeException("Unknown gate: $gate")
        }

fun quantumState(values: List<Double>): ArrayQuantumState =
        ArrayQuantumState(values.map { it.toComplex() }.toTypedArray())

fun QuantumState.toArrayState() = when (this) {
    is ZeroQubit -> quantumState(listOf(1.0, 0.0))
    is OneQubit -> quantumState(listOf(0.0, 1.0))
    is ArrayQuantumState -> this
    is TensorState -> {
        when (state1) {
            is ZeroQubit -> when (state2) {
                is ZeroQubit -> quantumState(listOf(1.0, 0.0, 0.0, 0.0))
                is OneQubit -> quantumState(listOf(0.0, 1.0, 0.0, 0.0))
                else -> throw RuntimeException("Unknown state: $this")
            }
            is OneQubit -> when (state2) {
                is ZeroQubit -> quantumState(listOf(0.0, 0.0, 1.0, 0.0))
                is OneQubit -> quantumState(listOf(0.0, 0.0, 0.0, 1.0))
                else -> throw RuntimeException("Unknown state: $this")
            }
            is PlusQubit -> when (state2) {
                is MinusQubit -> quantumState(listOf(0.5, -0.5, 0.5, -0.5))
                else -> throw RuntimeException("Unknown state: $this")
            }
            else -> throw RuntimeException("Unknown state: $this")
        }
    }
    else -> throw RuntimeException("Unknown state: $this")
}

fun BitFunctionWithParameters.apply(bits: List<Bit>): Bit {

    assert(parameters.size == bits.size)

    val map = mutableMapOf<String, Bit>()

    for ((i, param) in parameters.withIndex()) {
        map[param] = bits[i]
    }

    return this.value.apply(map)
}

fun Bit.apply(map: Map<String, Bit>): Bit = when (this) {

    is ZeroBit, is OneBit -> this
    is VariableBit -> blockValue(map, "Bit value", name)
    is Not -> {
        val r = bit.apply(map)
        if (r == ZeroBit) OneBit else ZeroBit
    }
    is And -> {
        val r1 = bit1.apply(map)
        val r2 = bit2.apply(map)
        if (r1 == ZeroBit) r1 else r2
    }
    is Or -> {
        val r1 = bit1.apply(map)
        val r2 = bit2.apply(map)
        if (r1 == OneBit) r1 else r2
    }
    is Xor -> {
        val r1 = bit1.apply(map)
        val r2 = bit2.apply(map)
        if (r1 == r2) ZeroBit else OneBit
    }
    else -> throw RuntimeException("Unknown bit: $this")
}

fun QuantumState.controlledFunction(f: BitFunctionWithParameters): QuantumState {

    val input = toArrayState().values
    val stateSize = input.size
    val output = Array(stateSize) { Complex.Zero }

    val bitsSize = f.parameters.size + 1
    val bits = Array<Bit>(bitsSize) { ZeroBit }

    for (stateIndex in (0 until stateSize)) {

        val x = bits.take(bitsSize - 1)
        val y = bits[bitsSize - 1]

        val res = f.apply(x)

        // flip last index bit
        val i = when {
            res == ZeroBit -> stateIndex
            y == ZeroBit -> stateIndex + 1
            else -> stateIndex - 1
        }

        output[i] = input[stateIndex]

        // increment bits from right to left
        var bitsIndex = bitsSize - 1
        while (bitsIndex >= 0) {
            if (bits[bitsIndex] == ZeroBit) {
                bits[bitsIndex] = OneBit
                break
            } else {
                bits[bitsIndex] = ZeroBit
                bitsIndex--
            }
        }
    }

    return ArrayQuantumState(output)
}
