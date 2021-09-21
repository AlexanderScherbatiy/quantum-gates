package quantum.pipeline.utils.base.gate

import org.junit.Assert.assertThrows
import quantum.complex.OneComplex
import quantum.complex.ZeroComplex
import quantum.pipeline.utils.base.baseDimension
import quantum.state.*
import kotlin.test.Test
import kotlin.test.assertEquals

class BaseDimensionTest {

    @Test
    fun baseQubitDimensionTest() {

        assertEquals(2, baseDimension(ZeroQubit, 0))
        assertEquals(2, baseDimension(OneQubit, 0))
        assertEquals(2, baseDimension(PlusQubit, 0))
        assertEquals(2, baseDimension(MinusQubit, 0))
    }

    @Test
    fun baseArrayQuantumStateDimensionTest() {

        assertEquals(1, baseDimension(ArrayQuantumState(OneComplex), 0));
        assertEquals(2, baseDimension(ArrayQuantumState(OneComplex, ZeroComplex), 0));
        assertEquals(3, baseDimension(ArrayQuantumState(ZeroComplex, OneComplex, ZeroComplex), 0));
        assertEquals(4, baseDimension(ArrayQuantumState(ZeroComplex, ZeroComplex, OneComplex, ZeroComplex), 0));
    }

    @Test
    fun baseQubitDimensionTest2() {
        assertThrows(IndexOutOfBoundsException::class.java) {
            baseDimension(ZeroQubit, 1)
        }
        assertThrows(IndexOutOfBoundsException::class.java) {
            baseDimension(OneQubit, 2)
        }
    }

    @Test
    fun baseArrayQuantumStateDimensionTest2() {
        assertThrows(IndexOutOfBoundsException::class.java) {
            baseDimension(ArrayQuantumState(OneComplex), 1)
        }
        assertThrows(IndexOutOfBoundsException::class.java) {
            baseDimension(ArrayQuantumState(ZeroComplex, ZeroComplex, OneComplex), 2)
        }
    }
}