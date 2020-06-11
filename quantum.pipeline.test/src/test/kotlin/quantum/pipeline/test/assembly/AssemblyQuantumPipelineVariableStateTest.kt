package quantum.pipeline.test.assembly

import org.junit.Test
import quantum.pipeline.QuantumPipelineBuilder
import quantum.pipeline.test.utils.registeredFactories
import quantum.state.ZeroQubit
import quantum.state.VariableState
import kotlin.test.assertEquals

class AssemblyQuantumPipelineVariableStateTest {

    @Test
    fun testVariableState() {
        registeredFactories()
                .map {
                    QuantumPipelineBuilder()
                            .begin()
                            .state(VariableState(2, "input"))
                            .end()
                }
                .forEach { pipeline ->

                    // state
                    val state = pipeline.state
                    assertEquals(VariableState(2, "input"), state)

                    // gates
                    assertEquals(0, pipeline.gates.size)

                    // assembled quantum.pipeline
                    val stateValues = mapOf("input" to ZeroQubit)
                    val assembledPipeline = pipeline.assembly(statesMap = stateValues)

                    // assembled state
                    val assembledState = assembledPipeline.state
                    assertEquals(ZeroQubit, assembledState)

                    // assembled gates
                    assertEquals(0, assembledPipeline.gates.size)
                }

    }
}
