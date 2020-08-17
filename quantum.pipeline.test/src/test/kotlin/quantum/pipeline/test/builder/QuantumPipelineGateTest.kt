package quantum.pipeline.test.builder

import org.junit.Test
import quantum.bit.ZeroBit
import quantum.bit.function
import quantum.gate.ControlledFunctionGate
import quantum.gate.IdentityGate
import quantum.pipeline.test.utils.assertQuantumStateEquals
import quantum.pipeline.test.utils.registeredFactories
import quantum.state.OneQubit
import quantum.state.TensorState
import quantum.state.ZeroQubit
import quantum.state.tensor

class QuantumPipelineGateTest {

    @Test
    fun testIdentityGate() {
        registeredFactories()
                .map {
                    it
                            .getPipeline()
                            .assembly(ZeroQubit, IdentityGate(2))
                            .evaluate()
                }
                .forEach {
                    assertQuantumStateEquals(ZeroQubit, it.output)
                }
    }

    @Test
    fun controlledFunctionGate() {
        registeredFactories()
                .map {
                    it
                            .getPipeline()
                            .assembly(
                                    ZeroQubit tensor OneQubit,
                                    ControlledFunctionGate(4, function(listOf("x")) { ZeroBit }))
                            .evaluate()
                }
                .forEach {
                    assertQuantumStateEquals(TensorState(ZeroQubit, OneQubit), it.output)
                }
    }
}
