package quantum.pipeline.test.assembly

import org.junit.Test
import quantum.pipeline.test.utils.assertQuantumStateEquals
import quantum.pipeline.test.utils.registeredFactories
import quantum.state.VariableState
import quantum.state.ZeroQubit

class AssemblyQuantumPipelineVariableStateTest {

    @Test
    fun testVariableState() {

        registeredFactories()
                .map {
                    it
                            .getPipeline()
                            .assembly(
                                    VariableState(2, "input"),
                                    variables = mapOf("input" to ZeroQubit)
                            )
                            .evaluate()
                }
                .forEach {
                    assertQuantumStateEquals(ZeroQubit, it.output)
                }
    }
}
