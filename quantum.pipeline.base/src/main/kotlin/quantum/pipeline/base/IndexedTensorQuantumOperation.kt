package quantum.pipeline.base

import quantum.complex.OneComplex
import quantum.complex.ZeroComplex
import quantum.gate.IdentityGate
import quantum.gate.IndexedTensorQuantumGate
import quantum.gate.QuantumGate
import quantum.gate.TensorIdentityGate
import quantum.state.*
import java.lang.Exception

class IndexedTensorQuantumOperation : QuantumOperation<IndexedTensorQuantumState, IndexedTensorQuantumGate> {

    override fun convert(state: QuantumState): IndexedTensorQuantumState = when (state) {
        is ZeroQubit -> IndexedTensorQuantumState(arrayOf(2), IndexedStateValue(OneComplex, 0))
        is OneQubit -> IndexedTensorQuantumState(arrayOf(2), IndexedStateValue(OneComplex, 1))
        is ArrayQuantumState -> {
            val coefficients = state.values
                .mapIndexed { index, complex -> Pair(complex, index) }
                .filter { it.first != ZeroComplex }
                .map { IndexedStateValue(it.first, it.second) }
                .toTypedArray()
            IndexedTensorQuantumState(arrayOf(state.values.size), *coefficients)
        }
        else -> throw Exception("Unknown state: $state")
    }

    override fun convert(gate: QuantumGate): IndexedTensorQuantumGate = when (gate) {
        else -> throw Exception("Unknown gate: $gate")
    }

    override fun multiply(state: IndexedTensorQuantumState, gate: IndexedTensorQuantumGate): IndexedTensorQuantumState {
        TODO("Not yet implemented")
    }
}