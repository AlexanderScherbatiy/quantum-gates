package quantum.pipeline.base.assembly

import org.junit.Test
import quantum.pipeline.QuantumPipelineBuilder
import quantum.state.ZeroQubit
import quantum.state.VariableState
import kotlin.test.assertEquals

class AssemblyQuantumPipelineVariableStateTest {

    @Test
    fun testVariableState() {

        val pipeline = QuantumPipelineBuilder()
                .begin()
                .state(VariableState(2, "input"))
                .end()


        // state
        val state = pipeline.state
        assertEquals(VariableState(2, "input"), state)

        // gates
        assertEquals(0, pipeline.gates.size)

        // assembled quantum.pipeline
        val stateValues = listOf(Pair("input", ZeroQubit))
        val assembledPipeline = pipeline.assembly(stateValues, listOf())

        // assembled state
        val assembledState = assembledPipeline.state
        assertEquals(ZeroQubit, assembledState)

        // assembled gates
        assertEquals(0, assembledPipeline.gates.size)
    }
}
