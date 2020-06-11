package quantum.pipeline.test.assembly

import org.junit.Test
import quantum.pipeline.QuantumPipelineBuilder
import quantum.pipeline.test.utils.registeredFactories
import quantum.state.OneQubit
import quantum.state.ZeroQubit
import quantum.state.VariableState
import quantum.state.tensor
import kotlin.test.assertEquals

class AssemblyQuantumPipelineVariableTensorStateTest {

    @Test
    fun testVariableTensorState() {
        registeredFactories()
                .map {
                    QuantumPipelineBuilder()
                            .begin()
                            .state(ZeroQubit tensor VariableState(2, "state"))
                            .end()
                }
                .forEach { pipeline ->

                    // state
                    val state = pipeline.state
                    assertEquals(ZeroQubit tensor VariableState(2, "state"), state)

                    // gates
                    assertEquals(0, pipeline.gates.size)

                    // assembled quantum.pipeline
                    val stateValues = mapOf("state" to OneQubit)
                    val assembledPipeline = pipeline.assembly(statesMap = stateValues)

                    // assembled state
                    val assembledState = assembledPipeline.state
                    assertEquals(ZeroQubit tensor OneQubit, assembledState)

                    // assembled gates
                    assertEquals(0, assembledPipeline.gates.size)
                }
    }
}