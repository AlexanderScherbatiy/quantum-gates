package quantum.pipeline.utils.base

import quantum.complex.OneComplex
import quantum.complex.ZeroComplex
import quantum.gate.IndexedTensorQuantumGate
import quantum.gate.QuantumGate
import quantum.state.*
import java.lang.Exception

class IndexedQuantumOperation : QuantumOperation<IndexedTensorQuantumState, IndexedTensorQuantumGate> {

    override fun convert(state: QuantumState): IndexedTensorQuantumState = when (state) {
        is ZeroQubit -> IndexedTensorQuantumState(arrayOf(2), IndexedCoefficient(OneComplex, 0))
        is OneQubit -> IndexedTensorQuantumState(arrayOf(2), IndexedCoefficient(OneComplex, 1))
        is ArrayQuantumState -> {
            val coefficients = state.values
                .mapIndexed { index, complex -> Pair(complex, index) }
                .filter { it.first != ZeroComplex }
                .map { IndexedCoefficient(it.first, it.second) }
                .toTypedArray()
            IndexedTensorQuantumState(arrayOf(state.values.size), *coefficients)
        }
        else -> throw Exception("Unknown state: $state")
    }

    override fun convert(gate: QuantumGate): IndexedTensorQuantumGate {
        TODO("Not yet implemented")
    }

    override fun multiply(state: IndexedTensorQuantumState, gate: IndexedTensorQuantumGate): IndexedTensorQuantumState {
        TODO("Not yet implemented")
    }
}