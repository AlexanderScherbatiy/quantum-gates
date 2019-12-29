package quantum.pipeline.base.builder

import org.junit.Test
import quantum.pipeline.QuantumPipelineBuilder
import quantum.gate.IdentityGate
import quantum.state.ZeroQubit
import kotlin.test.assertEquals

class QuantumPipelineGateTest {

    @Test
    fun testGate() {

        val pipeline = QuantumPipelineBuilder()
                .begin()
                .state(ZeroQubit)
                .gate(IdentityGate(2))
                .end()

        // state
        val state = pipeline.state
        assertEquals(ZeroQubit, state)

        // gates
        assertEquals(1, pipeline.gates.size)

        val gate = pipeline.gates[0]
        assertEquals(IdentityGate(2), gate)
    }
}
