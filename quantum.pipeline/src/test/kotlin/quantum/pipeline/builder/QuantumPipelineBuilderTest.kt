package quantum.pipeline.builder

import org.junit.Test
import quantum.pipeline.GateBlock
import quantum.pipeline.QuantumPipelineBuilder
import quantum.state.IdentityGate
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class QuantumPipelineBuilderTest {

    @Test
    fun testBuilder() {

        val pipeline = QuantumPipelineBuilder()
                .begin()
                .gate(IdentityGate(2))
                .end()


        val blocks = pipeline.blocks()
        assertEquals(1, blocks.size)

        val gateBlock = blocks[0]
        assertTrue(gateBlock is GateBlock)

        val gate = gateBlock.gate
        assertTrue(gate is IdentityGate)
        assertEquals(2, gate.size)
    }
}
