package quantum.pipeline.builder

import org.junit.Test
import quantum.pipeline.QuantumPipelineBuilder
import quantum.state.OneQubit
import kotlin.test.assertEquals

class QuantumPipelineStateTest {

    @Test
    fun testState() {

        val pipeline = QuantumPipelineBuilder()
                .begin()
                .state(OneQubit)
                .end()

        // state
        val state = pipeline.state
        assertEquals(OneQubit, state)

        // gates
        assertEquals(0, pipeline.gates.size)
    }
}
