package quantum.pipeline.base.multiply

import org.junit.Test
import quantum.pipeline.base.multiply
import quantum.state.*
import kotlin.test.assertEquals

class HadamardGateMultiplyTest {

    @Test
    fun multiplyTest() {

        assertEquals(PlusQubit, multiply(HadamardGate, ZeroQubit))
        assertEquals(MinusQubit, multiply(HadamardGate, OneQubit))
        assertEquals(ZeroQubit, multiply(HadamardGate, PlusQubit))
        assertEquals(OneQubit, multiply(HadamardGate, MinusQubit))
    }
}
