package quantum.pipeline.multiply

import org.junit.Test
import quantum.pipeline.multiply
import quantum.state.*
import kotlin.test.assertEquals

class HadamardGateMultiplyTest {

    @Test
    fun multiplyTest() {

        assertEquals(QubitPlus, multiply(HadamardGate, QubitZero))
        assertEquals(QubitMinus, multiply(HadamardGate, QubitOne))
        assertEquals(QubitZero, multiply(HadamardGate, QubitPlus))
        assertEquals(QubitOne, multiply(HadamardGate, QubitMinus))
    }
}
