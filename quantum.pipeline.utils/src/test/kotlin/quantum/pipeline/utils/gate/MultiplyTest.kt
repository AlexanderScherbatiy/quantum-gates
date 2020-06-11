package quantum.pipeline.utils.gate

import org.junit.Test
import quantum.gate.HadamardGate
import quantum.pipeline.utils.multiply
import quantum.state.MinusQubit
import quantum.state.OneQubit
import quantum.state.PlusQubit
import quantum.state.ZeroQubit
import kotlin.test.assertEquals

class MultiplyTest {

    @Test
    fun multiplyHadamardGate() {
        assertEquals(PlusQubit, multiply(HadamardGate, ZeroQubit))
        assertEquals(MinusQubit, multiply(HadamardGate, OneQubit))
        assertEquals(ZeroQubit, multiply(HadamardGate, PlusQubit))
        assertEquals(OneQubit, multiply(HadamardGate, MinusQubit))
    }
}
