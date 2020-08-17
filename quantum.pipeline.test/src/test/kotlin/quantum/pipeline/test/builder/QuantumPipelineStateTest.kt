package quantum.pipeline.test.builder

import org.junit.Test
import quantum.pipeline.test.utils.assertQuantumStateEquals
import quantum.pipeline.test.utils.registeredFactories
import quantum.state.MinusQubit
import quantum.state.OneQubit
import quantum.state.PlusQubit
import quantum.state.QuantumState
import quantum.state.TensorState
import quantum.state.ZeroQubit

class QuantumPipelineStateTest {

    @Test
    fun testStateZero() {
        testState(ZeroQubit)
    }

    @Test
    fun testStateOne() {
        testState(OneQubit)
    }

    @Test
    fun testStatePlus() {
        testState(PlusQubit)
    }

    @Test
    fun testStateMinus() {
        testState(MinusQubit)
    }

    @Test
    fun testStateZeroTensorStateZero() {
        testState(TensorState(ZeroQubit, ZeroQubit))
    }

    @Test
    fun testStateZeroTensorStateOne() {
        testState(TensorState(ZeroQubit, OneQubit))
    }

    @Test
    fun testStateOneTensorStateZero() {
        testState(TensorState(OneQubit, ZeroQubit))
    }

    @Test
    fun testStateOneTensorStateOne() {
        testState(TensorState(OneQubit, OneQubit))
    }

    private fun testState(state: QuantumState) {
        registeredFactories()
                .map {
                    it
                            .getPipeline()
                            .assembly(state)
                            .evaluate()

                }
                .forEach {
                    assertQuantumStateEquals(state, it.output)
                }
    }
}
