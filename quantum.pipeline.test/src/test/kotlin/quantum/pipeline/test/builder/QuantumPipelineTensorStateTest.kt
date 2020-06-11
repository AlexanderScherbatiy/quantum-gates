package quantum.pipeline.test.builder

import org.junit.Test
import quantum.pipeline.QuantumPipelineBuilder
import quantum.pipeline.test.utils.registeredFactories
import quantum.state.OneQubit
import quantum.state.TensorState
import quantum.state.ZeroQubit
import quantum.state.tensor
import kotlin.test.assertEquals

class QuantumPipelineTensorStateTest {

    @Test
    fun testTensorState() {
        registeredFactories()
                .map {
                    QuantumPipelineBuilder()
                            .factory(it)
                            .begin()
                            .state(ZeroQubit tensor OneQubit)
                            .end()
                }
                .forEach { pipeline ->
                    assertEquals(TensorState(ZeroQubit, OneQubit), pipeline.state)
                    assertEquals(0, pipeline.gates.size)
                }
    }
}
