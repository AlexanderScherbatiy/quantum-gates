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
                .forEach {

                    val pipeline = QuantumPipelineBuilder()
                            .factory(it)
                            .begin()
                            .state(ZeroQubit tensor OneQubit)
                            .end()

                    // state
                    val state = pipeline.state
                    assertEquals(TensorState(ZeroQubit, OneQubit), state)

                    // gates
                    assertEquals(0, pipeline.gates.size)
                }
    }
}
