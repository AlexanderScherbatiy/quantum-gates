package quantum.pipeline.test.assembly

import org.junit.Test
import quantum.pipeline.test.utils.assertQuantumStateEquals
import quantum.pipeline.test.utils.registeredFactories
import quantum.state.OneQubit
import quantum.state.VariableState
import quantum.state.ZeroQubit
import quantum.state.tensor

class AssemblyQuantumPipelineVariableTensorStateTest {

    @Test
    fun testVariableTensorState() {

        registeredFactories()
                .map {
                    it
                            .getPipeline()
                            .assembly(
                                    ZeroQubit tensor VariableState(2, "state"),
                                    variables = mapOf("state" to OneQubit)
                            )
                            .evaluate()
                }
                .forEach {
                    assertQuantumStateEquals(ZeroQubit tensor OneQubit, it.output)
                }
    }
}
