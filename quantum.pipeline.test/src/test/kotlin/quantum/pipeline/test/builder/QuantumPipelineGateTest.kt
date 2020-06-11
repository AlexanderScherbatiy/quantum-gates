package quantum.pipeline.test.builder

import org.junit.Test
import quantum.bit.BitFunctionWithParameters
import quantum.bit.ZeroBit
import quantum.bit.function
import quantum.gate.ControlledFunctionGate
import quantum.pipeline.QuantumPipelineBuilder
import quantum.gate.IdentityGate
import quantum.pipeline.test.utils.registeredFactories
import quantum.state.OneQubit
import quantum.state.TensorState
import quantum.state.ZeroQubit
import quantum.state.tensor
import kotlin.test.assertEquals

class QuantumPipelineGateTest {

    @Test
    fun testIdentityGate() {
        registeredFactories()
                .map {
                    QuantumPipelineBuilder()
                            .factory(it)
                            .begin()
                            .state(ZeroQubit)
                            .gate(IdentityGate(2))
                            .end()
                }
                .forEach { pipeline ->

                    assertEquals(ZeroQubit, pipeline.state)
                    assertEquals(1, pipeline.gates.size)

                    val gate = pipeline.gates[0]
                    assertEquals(IdentityGate(2), gate)
                }
    }

    @Test
    fun controlledFunctionGate() {
        registeredFactories()
                .map {
                    QuantumPipelineBuilder()
                            .factory(it)
                            .begin()
                            .state(ZeroQubit tensor OneQubit)
                            .gate(ControlledFunctionGate(4, function(listOf("x")) { ZeroBit }))
                            .end()
                }
                .forEach{ pipeline ->

                    assertEquals(TensorState(ZeroQubit, OneQubit), pipeline.state)
                    assertEquals(1, pipeline.gates.size)

                    val gate = pipeline.gates[0]
                    assertEquals(
                            ControlledFunctionGate(4, BitFunctionWithParameters(listOf("x"), ZeroBit)),
                            gate)
                }
    }
}
