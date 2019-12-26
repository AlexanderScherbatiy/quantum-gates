package quantum.pipeline.builder

import org.junit.Test
import quantum.pipeline.QuantumPipelineBuilder
import quantum.state.IdentityGate
import quantum.state.QubitZero
import kotlin.test.assertEquals

class QuantumPipelineGateTest {

    @Test
    fun testGate() {

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
        assertEquals(IdentityGate(2), gate)
    }
}
