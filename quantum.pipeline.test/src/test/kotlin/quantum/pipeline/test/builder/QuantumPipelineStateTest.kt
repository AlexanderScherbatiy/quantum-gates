package quantum.pipeline.test.builder

import org.junit.Test
import quantum.pipeline.QuantumPipelineBuilder
import quantum.pipeline.test.utils.registeredFactories
import quantum.state.*
import kotlin.test.assertEquals

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

    private fun testState(state: QuantumState) {
        registeredFactories()
                .forEach {

                    val pipeline = QuantumPipelineBuilder()
                            .factory(it)
                            .begin()
                            .state(state)
                            .end()

                    assertEquals(state, pipeline.state)
                    assertEquals(0, pipeline.gates.size)
                }
    }
}
