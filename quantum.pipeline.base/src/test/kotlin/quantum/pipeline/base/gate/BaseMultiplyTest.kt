package quantum.pipeline.utils.base.gate

import org.junit.Test
import quantum.gate.HadamardGate
import quantum.pipeline.utils.base.baseMultiply
import quantum.state.MinusQubit
import quantum.state.OneQubit
import quantum.state.PlusQubit
import quantum.state.ZeroQubit
import kotlin.test.assertEquals

class BaseMultiplyTest {

    @Test
    fun multiplyHadamardGate() {
        assertEquals(PlusQubit, baseMultiply(HadamardGate, ZeroQubit))
        assertEquals(MinusQubit, baseMultiply(HadamardGate, OneQubit))
        assertEquals(ZeroQubit, baseMultiply(HadamardGate, PlusQubit))
        assertEquals(OneQubit, baseMultiply(HadamardGate, MinusQubit))
    }
}
