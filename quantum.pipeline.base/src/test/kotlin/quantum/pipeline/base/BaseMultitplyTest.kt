package quantum.pipeline.base

import org.junit.Test
import quantum.gate.HadamardGate
import quantum.state.MinusQubit
import quantum.state.OneQubit
import quantum.state.PlusQubit
import quantum.state.ZeroQubit
import kotlin.test.assertEquals

class BaseMultitplyTest {

    @Test
    fun multiplyHadamardGate() {

        assertEquals(PlusQubit, baseMultiply(HadamardGate, ZeroQubit))
        assertEquals(MinusQubit, baseMultiply(HadamardGate, OneQubit))
        assertEquals(ZeroQubit, baseMultiply(HadamardGate, PlusQubit))
        assertEquals(OneQubit, baseMultiply(HadamardGate, MinusQubit))
    }
}
