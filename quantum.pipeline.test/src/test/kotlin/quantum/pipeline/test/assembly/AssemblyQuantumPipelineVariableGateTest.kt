package quantum.pipeline.test.assembly

import org.junit.Test
import quantum.bit.*
import quantum.gate.ControlledFunctionGate
import quantum.pipeline.QuantumPipelineBuilder
import quantum.gate.IdentityGate
import quantum.gate.VariableGate
import quantum.pipeline.test.utils.registeredFactories
import quantum.state.OneQubit
import quantum.state.TensorState
import quantum.state.ZeroQubit
import quantum.state.tensor
import kotlin.test.assertEquals

class AssemblyQuantumPipelineVariableGateTest {

    @Test
    fun testVariableGate() {
        registeredFactories()
                .map {
                    QuantumPipelineBuilder()
                            .begin()
                            .state(ZeroQubit)
                            .gate(VariableGate(2, "gate"))
                            .end()
                }
                .forEach { pipeline ->

                    // state
                    val state = pipeline.state
                    assertEquals(ZeroQubit, state)

                    // gates
                    assertEquals(1, pipeline.gates.size)
                    val gate = pipeline.gates[0]
                    assertEquals(VariableGate(2, "gate"), gate)

                    // assembled pipeline
                    val gateValues = mapOf("gate" to IdentityGate(2))
                    val assembledPipeline = pipeline.assembly(gatesMap = gateValues)

                    // assembled state
                    val assembledState = assembledPipeline.state
                    assertEquals(ZeroQubit, assembledState)

                    // assembled gates
                    assertEquals(1, assembledPipeline.gates.size)
                    val assembledGate = assembledPipeline.gates[0]
                    assertEquals(IdentityGate(2), assembledGate)
                }
    }

    @Test
    fun controlledVariableFunctionGate() {
        registeredFactories()
                .map {

                    QuantumPipelineBuilder()
                            .begin()
                            .state(ZeroQubit tensor OneQubit)
                            .gate(ControlledFunctionGate(4, VariableBitFunction(1, "f")))
                            .end()
                }
                .forEach { pipeline ->

                    // state
                    val state = pipeline.state
                    assertEquals(TensorState(ZeroQubit, OneQubit), state)

                    // gates
                    assertEquals(1, pipeline.gates.size)

                    val gate = pipeline.gates[0]
                    assertEquals(
                            ControlledFunctionGate(4, VariableBitFunction(1, "f")),
                            gate)

                    // assembled pipeline
                    val bitValues = mapOf("f" to function(listOf("x")) { OneBit })
                    val assembledPipeline = pipeline.assembly(bitFunctionsMap = bitValues)

                    // assembled state
                    val assembledState = assembledPipeline.state
                    assertEquals(TensorState(ZeroQubit, OneQubit), assembledState)

                    // assembled gates
                    assertEquals(1, assembledPipeline.gates.size)
                    val assembledGate = assembledPipeline.gates[0]
                    assertEquals(
                            ControlledFunctionGate(4, BitFunctionWithParameters(listOf("x"), OneBit)),
                            assembledGate)
                }
    }

}
