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
                .forEach {

                    val pipeline = QuantumPipelineBuilder()
                            .factory(it)
                            .begin()
                            .state(ZeroQubit)
                            .gate(IdentityGate(2))
                            .end()

                    // state
                    val state = pipeline.state
                    assertEquals(ZeroQubit, state)

                    // gates
                    assertEquals(1, pipeline.gates.size)

                    val gate = pipeline.gates[0]
                    assertEquals(IdentityGate(2), gate)

                }
    }

    @Test
    fun controlledFunctionGate() {
        registeredFactories()
                .forEach {

                    val pipeline = QuantumPipelineBuilder()
                            .factory(it)
                            .begin()
                            .state(ZeroQubit tensor OneQubit)
                            .gate(ControlledFunctionGate(4, function(listOf("x")) { ZeroBit }))
                            .end()

                    // state
                    val state = pipeline.state
                    assertEquals(TensorState(ZeroQubit, OneQubit), state)

                    // gates
                    assertEquals(1, pipeline.gates.size)

                    val gate = pipeline.gates[0]
                    assertEquals(
                            ControlledFunctionGate(4, BitFunctionWithParameters(listOf("x"), ZeroBit)),
                            gate)
                }
    }
}
