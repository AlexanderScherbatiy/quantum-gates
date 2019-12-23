package quantum.pipeline.assembly

import org.junit.Test
import quantum.pipeline.*
import quantum.state.IdentityGate
import quantum.state.QubitZero
import quantum.state.VariableState
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AssemblyQuantumPipelineTest {

    @Test
    fun testVariableState() {

        val pipeline = QuantumPipelineBuilder()
                .begin()
                .state(VariableState("input", 2))
                .gate(IdentityGate(2))
                .end()


        val blocks = pipeline.blocks()
        assertEquals(2, blocks.size)

        // state
        val stateBlock = blocks[0]
        assertTrue(stateBlock is StateBlock)

        val state = stateBlock.state
        assertTrue(state is VariableState)
        assertEquals("input", state.name)
        assertEquals(2, state.size)

        // gate
        val gateBlock = blocks[1]
        assertTrue(gateBlock is GateBlock)

        val gate = gateBlock.gate
        assertTrue(gate is IdentityGate)
        assertEquals(2, gate.size)

        // assembled pipeline
        val values = listOf(ValueStateBlock("input", QubitZero))
        val assembledPipeline = pipeline.assembly(values)

        val assembledBlocks = assembledPipeline.blocks()
        assertEquals(2, assembledBlocks.size)

        // state
        val assembledStateBlock = assembledBlocks[0]
        assertTrue(assembledStateBlock is StateBlock)

        val assembledState = assembledStateBlock.state
        assertEquals(QubitZero, assembledState)
    }
}
