package quantum.pipeline.test.utils

import quantum.pipeline.utils.base.baseToArrayQuantumState
import quantum.state.QuantumState
import kotlin.test.assertEquals

fun assertQuantumStateEquals(expected: QuantumState, actual: QuantumState) {
    val values1 = expected.baseToArrayQuantumState().values
    val values2 = actual.baseToArrayQuantumState().values
    assertEquals(values1.size, values2.size)
    for (i in 0 until values1.size) {
        assertComplexEquals(values1[i], values2[i])
    }
}
