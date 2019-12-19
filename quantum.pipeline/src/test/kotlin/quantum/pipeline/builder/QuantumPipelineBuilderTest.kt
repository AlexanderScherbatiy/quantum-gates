package quantum.pipeline.builder

import org.junit.Test
import quantum.pipeline.BlockType
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


        val blockItems = pipeline.blockItems()
        assertEquals(1, blockItems.size)

        val blockItem = blockItems[0]
        assertEquals(BlockType.GATE, blockItem.type)

        val block = blockItem.block as GateBlock
        assertTrue(block is GateBlock)

        val gate = block.gate
        assertTrue(gate is IdentityGate)
        assertEquals(2, gate.size)
    }
}
