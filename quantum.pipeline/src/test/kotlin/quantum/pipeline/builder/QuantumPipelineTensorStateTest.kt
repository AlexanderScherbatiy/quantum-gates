package quantum.pipeline.builder

import org.junit.Test
import quantum.pipeline.QuantumPipelineBuilder
import quantum.state.*
import kotlin.test.assertEquals

class QuantumPipelineTensorStateTest {

    @Test
    fun testTensorState() {

        val pipeline = QuantumPipelineBuilder()
                .begin()
                .state(ZeroQubit tensor OneQubit)
                .end()

        // state
        val state = pipeline.state
        assertEquals(TensorState(ZeroQubit, OneQubit), state)

        // gates
        assertEquals(0, pipeline.gates.size)
    }
}
