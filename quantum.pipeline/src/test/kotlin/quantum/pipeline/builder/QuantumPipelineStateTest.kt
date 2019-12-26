package quantum.pipeline.builder

import org.junit.Test
import quantum.pipeline.QuantumPipelineBuilder
import quantum.state.IdentityGate
import quantum.state.QubitOne
import quantum.state.QubitZero
import kotlin.test.assertEquals

class QuantumPipelineStateTest {

    @Test
    fun testState() {

        val pipeline = QuantumPipelineBuilder()
                .begin()
                .state(QubitOne)
                .end()

        // state
        val state = pipeline.state
        assertEquals(QubitOne, state)

        // gates
        assertEquals(0, pipeline.gates.size)
    }
}
