package quantum.pipeline.utils.base.operation

import org.junit.Test
import quantum.complex.OneComplex
import quantum.complex.ZeroComplex
import quantum.pipeline.utils.base.IndexedQuantumOperation
import quantum.state.ArrayQuantumState
import quantum.state.OneQubit
import quantum.state.ZeroQubit
import kotlin.test.assertEquals

class IndexedQuantumOperationTest {

    @Test
    fun convertQubitToState() {

        val operation = IndexedQuantumOperation()

        val zeroQubit = operation.convert(ZeroQubit)
        assertEquals(1, zeroQubit.dimensions.size)
        assertEquals(2, zeroQubit.dimensions[0])
        assertEquals(1, zeroQubit.coefficients.size)
        assertEquals(1, zeroQubit.coefficients[0].indicies.size)
        assertEquals(0, zeroQubit.coefficients[0].indicies[0])
        assertEquals(OneComplex, zeroQubit.coefficients[0].value)

        val oneQubit = operation.convert(OneQubit)
        assertEquals(1, oneQubit.dimensions.size)
        assertEquals(2, oneQubit.dimensions[0])
        assertEquals(1, oneQubit.coefficients.size)
        assertEquals(1, oneQubit.coefficients[0].indicies.size)
        assertEquals(1, oneQubit.coefficients[0].indicies[0])
        assertEquals(OneComplex, oneQubit.coefficients[0].value)
    }

    @Test
    fun convertArrayStateToState() {

        val operation = IndexedQuantumOperation()

        val state1 = operation.convert(ArrayQuantumState(OneComplex))
        assertEquals(1, state1.dimensions.size)
        assertEquals(1, state1.dimensions[0])
        assertEquals(1, state1.coefficients.size)
        assertEquals(1, state1.coefficients[0].indicies.size)
        assertEquals(0, state1.coefficients[0].indicies[0])
        assertEquals(OneComplex, state1.coefficients[0].value)

        val state10 = operation.convert(ArrayQuantumState(OneComplex, ZeroComplex))
        assertEquals(1, state10.dimensions.size)
        assertEquals(2, state10.dimensions[0])
        assertEquals(1, state10.coefficients.size)
        assertEquals(1, state10.coefficients[0].indicies.size)
        assertEquals(0, state10.coefficients[0].indicies[0])
        assertEquals(OneComplex, state10.coefficients[0].value)

        val state01 = operation.convert(ArrayQuantumState(ZeroComplex, OneComplex))
        assertEquals(1, state01.dimensions.size)
        assertEquals(2, state01.dimensions[0])
        assertEquals(1, state01.coefficients.size)
        assertEquals(1, state01.coefficients[0].indicies.size)
        assertEquals(1, state01.coefficients[0].indicies[0])
        assertEquals(OneComplex, state01.coefficients[0].value)
    }
}