package quantum.pipeline.memory

import org.junit.Test
import quantum.gate.HadamardGate
import quantum.state.MinusQubit
import quantum.state.OneQubit
import quantum.state.PlusQubit
import quantum.state.ZeroQubit
import kotlin.test.assertEquals

class MemoryMultitplyTest {

    @Test
    fun multiplyHadamardGate() {

        assertEquals(PlusQubit, multiplyMemoryPipeline(HadamardGate, ZeroQubit))
        assertEquals(MinusQubit, multiplyMemoryPipeline(HadamardGate, OneQubit))
        assertEquals(ZeroQubit, multiplyMemoryPipeline(HadamardGate, PlusQubit))
        assertEquals(OneQubit, multiplyMemoryPipeline(HadamardGate, MinusQubit))
    }
}
