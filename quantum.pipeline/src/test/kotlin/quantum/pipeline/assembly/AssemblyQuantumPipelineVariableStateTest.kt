package quantum.pipeline.assembly

import org.junit.Test
import quantum.pipeline.QuantumPipelineBuilder
import quantum.state.QubitZero
import quantum.state.VariableState
import kotlin.test.assertEquals

class AssemblyQuantumPipelineVariableStateTest {

    @Test
    fun testVariableState() {

        val pipeline = QuantumPipelineBuilder()
                .begin()
                .state(VariableState("input", 2))
                .end()


        // state
        val state = pipeline.state
        assertEquals(VariableState("input", 2), state)

        // gates
        assertEquals(0, pipeline.gates.size)

        // assembled pipeline
        val stateValues = listOf(Pair("input", QubitZero))
        val assembledPipeline = pipeline.assembly(stateValues, listOf())

        // assembled state
        val assembledState = assembledPipeline.state
        assertEquals(QubitZero, assembledState)

        // assembled gates
        assertEquals(0, assembledPipeline.gates.size)
    }
}
