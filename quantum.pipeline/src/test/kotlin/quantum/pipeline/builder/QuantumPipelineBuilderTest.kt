package quantum.pipeline.builder

import org.junit.Test
import quantum.pipeline.GateBlock
import quantum.pipeline.QuantumPipelineBuilder
import quantum.pipeline.StateBlock
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


        val blocks = pipeline.blocks()
        assertEquals(2, blocks.size)

        // state
        val stateBlock = blocks[0]
        assertTrue(stateBlock is StateBlock)

        val state = stateBlock.state
        assertTrue(state is QubitZero)

        // gate
        val gateBlock = blocks[1]
        assertTrue(gateBlock is GateBlock)

        val gate = gateBlock.gate
        assertTrue(gate is IdentityGate)
        assertEquals(2, gate.size)
    }
}
