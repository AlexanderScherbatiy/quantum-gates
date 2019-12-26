package quantum.pipeline.builder

import org.junit.Test
import quantum.pipeline.QuantumPipelineBuilder
import quantum.state.IdentityGate
import quantum.state.QubitZero
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class QuantumPipelineBuilderTest {

    @Test
    fun testBuilder() {

        val pipeline = QuantumPipelineBuilder()
                .begin()
                .state(QubitZero)
                .gate(IdentityGate(2))
                .end()

        // state
        val state = pipeline.state
        assertEquals(QubitZero, state)

        // gates
        assertEquals(1, pipeline.gates.size)

        val gate = pipeline.gates[0]
        assertTrue(gate is IdentityGate)
        assertEquals(2, gate.size)
    }
}
