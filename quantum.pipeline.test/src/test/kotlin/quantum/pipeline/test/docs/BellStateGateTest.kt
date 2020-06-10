package quantum.pipeline.test.docs

import org.junit.Test
import quantum.gate.*
import quantum.pipeline.QuantumPipelineBuilder
import quantum.state.ZeroQubit
import quantum.state.tensor

class BellStateGateTest {

    @Test
    fun testBellStateGate1() {

        val bellState = QuantumPipelineBuilder()
                .begin()
                .state(ZeroQubit tensor ZeroQubit)
                .gate(HadamardGate tensor IdentityGate(1))
                .gate(ControlledNotGate)
                .end()
                .evaluate()
                .output


        println("bell state: $bellState")
    }
}