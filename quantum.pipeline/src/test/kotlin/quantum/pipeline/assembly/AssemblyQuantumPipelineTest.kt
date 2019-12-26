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


        // state
        val state = pipeline.state
        assertTrue(state is VariableState)
        assertEquals("input", state.name)
        assertEquals(2, state.size)

        // gates
        assertEquals(1, pipeline.gates.size)
        val gate = pipeline.gates[0]
        assertTrue(gate is IdentityGate)
        assertEquals(2, gate.size)

        // assembled pipeline
        val stateValues = listOf(Pair("input", QubitZero))
        val assembledPipeline = pipeline.assembly(stateValues, listOf())

        // assembled state
        val assembledState = assembledPipeline.state
        assertEquals(QubitZero, assembledState)

        // assembled gates
        assertEquals(1, assembledPipeline.gates.size)
        val assembledGate = assembledPipeline.gates[0]
        assertTrue(assembledGate is IdentityGate)
        assertEquals(2, assembledGate.size)
    }
}
