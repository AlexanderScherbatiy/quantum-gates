package quantum.pipeline.utils.base

import quantum.bit.BitFunctionWithParameters
import quantum.gate.*
import quantum.pipeline.utils.memory.controlledFunction
import quantum.state.*

fun baseDimension(state: QuantumState, index: Int): Int = when (state) {
    is ZeroQubit, is OneQubit, is PlusQubit, is MinusQubit, is Qubit -> when (index) {
        0 -> 2
        else -> throw IndexOutOfBoundsException("Index $index is out of range for $state")
    }
    is ArrayQuantumState -> when (index) {
        0 -> state.values.size
        else -> throw IndexOutOfBoundsException("Index $index is out of range for $state")
    }
    else -> -1
}

/**
 * Multiplies base quantum gates and states.
 * Returns UnknownBaseQuantumState in case the result is not processed.
 */
fun baseMultiply(gate: QuantumGate, state: QuantumState): QuantumState =
    when (gate) {
        is IdentityGate -> state
        is HadamardGate -> when (state) {
            is ZeroQubit -> PlusQubit
            is OneQubit -> MinusQubit
            is PlusQubit -> ZeroQubit
            is MinusQubit -> OneQubit
            else -> UnknownBaseQuantumState
        }
        is ControlledNotGate -> baseControlledNot(state)
        is ControlledFunctionGate -> {
            val f = gate.f
            when (f) {
                is BitFunctionWithParameters -> state.controlledFunction(f)
                else -> UnknownBaseQuantumState
            }
        }
        is TensorGate -> {
            when (state) {
                is TensorState -> {
                    val out1 = baseMultiply(gate.gate1, state.state1)
                    val out2 = baseMultiply(gate.gate2, state.state2)
                    TensorState(out1, out2)
                }
                else -> UnknownBaseQuantumState
            }
        }

        is QuantumMeasureState -> baseMeasureState(state, gate.index)
        else -> UnknownBaseQuantumState
    }

fun baseSwap(state: QuantumState): QuantumState = when (state) {
    is ZeroQubit -> OneQubit
    is OneQubit -> ZeroQubit
    is PlusQubit -> PlusQubit
    is Qubit -> Qubit(state.one, state.zero)
    else -> UnknownBaseQuantumState
}


fun baseControlledNot(state: QuantumState): QuantumState = when (state) {
    is TensorState -> {
        val contorl = state.state1
        val target = state.state2
        when {
            contorl is ZeroQubit -> state
            contorl is OneQubit -> TensorState(contorl, baseSwap(target))
            else -> UnknownBaseQuantumState
        }
    }
    else -> UnknownBaseQuantumState
}

fun baseMeasureState(state: QuantumState, index: Int): QuantumState {

    val splits = baseSplitState(state, index)

    return UnknownBaseQuantumState
}

fun baseSplitState(state: QuantumState, index: Int): Map<Int, QuantumState> = when (state) {
    is ZeroQubit -> when (index) {
        0 -> mapOf(0 to ZeroQubit)
        else -> throw IndexOutOfBoundsException("Index: $index")
    }
    else -> throw Exception("Unable to split state: $state by index: $index")
}

fun baseDimensionsToRange(vararg dimensions: Int): List<IntArray> {

    val range = mutableListOf<IntArray>()

    if (dimensions.contains(0)) {
        return range
    }

    val values = IntArray(dimensions.size)

    var index = 0

    loop@
    while (true) {

        range.add(values.clone())

        while (true) {
            values[index]++;
            if (values[index] < dimensions[index]) {
                index = 0
                break
            }
            values[index] = 0
            index++

            if (index == dimensions.size) {
                break@loop
            }
        }
    }

    return range
}
