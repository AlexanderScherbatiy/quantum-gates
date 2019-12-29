package quantum.pipeline.base.multiply

import org.junit.Test
import quantum.gate.HadamardGate
import quantum.pipeline.base.multiply
import quantum.state.MinusQubit
import quantum.state.OneQubit
import quantum.state.PlusQubit
import quantum.state.ZeroQubit
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
