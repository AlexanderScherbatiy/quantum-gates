package quantum.pipeline.assembly

import org.junit.Test
import quantum.pipeline.QuantumPipelineBuilder
import quantum.state.QubitOne
import quantum.state.QubitZero
import quantum.state.VariableState
import quantum.state.tensor
import kotlin.test.assertEquals

class AssemblyQuantumPipelineVariableTensorStateTest {

    @Test
    fun testVariableTensorState() {

        val pipeline = QuantumPipelineBuilder()
                .begin()
                .state(QubitZero tensor VariableState("state2", 2))
                .end()


        // state
        val state = pipeline.state
        assertEquals(QubitZero tensor VariableState("state2", 2), state)

        // gates
        assertEquals(0, pipeline.gates.size)

        // assembled pipeline
        val stateValues = listOf(Pair("state2", QubitOne))
        val assembledPipeline = pipeline.assembly(stateValues, listOf())

        // assembled state
        val assembledState = assembledPipeline.state
        assertEquals(QubitZero tensor QubitOne, assembledState)

        // assembled gates
        assertEquals(0, assembledPipeline.gates.size)
    }
}
