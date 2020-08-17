package quantum.pipeline.test.assembly

import org.junit.Test
import quantum.bit.BitFunctionWithParameters
import quantum.bit.OneBit
import quantum.bit.VariableBitFunction
import quantum.bit.ZeroBit
import quantum.bit.function
import quantum.gate.ControlledFunctionGate
import quantum.gate.HadamardGate
import quantum.gate.IdentityGate
import quantum.gate.VariableGate
import quantum.pipeline.test.utils.assertQuantumStateEquals
import quantum.pipeline.test.utils.registeredFactories
import quantum.state.OneQubit
import quantum.state.PlusQubit
import quantum.state.QuantumState
import quantum.state.ZeroQubit
import quantum.state.tensor

class AssemblyQuantumPipelineVariableGateTest {

    @Test
    fun testVariableIdentityGate() {

        registeredFactories()
                .map {
                    it
                            .getPipeline()
                            .assembly(
                                    ZeroQubit,
                                    VariableGate(2, "gate"),
                                    variables = mapOf("gate" to IdentityGate(2)))
                            .evaluate()
                }
                .forEach {
                    assertQuantumStateEquals(ZeroQubit, it.output)
                }
    }

    @Test
    fun testVariableHadamardGate() {

        registeredFactories()
                .map {
                    it
                            .getPipeline()
                            .assembly(
                                    ZeroQubit,
                                    VariableGate(2, "gate"),
                                    variables = mapOf("gate" to HadamardGate)
                            )
                            .evaluate()
                }
                .forEach {
                    assertQuantumStateEquals(PlusQubit, it.output)
                }
    }

    @Test
    fun controlledVariableFunctionGate() {
        checkControlledVariableFunctionGate(
                ZeroQubit tensor OneQubit,
                ZeroQubit tensor OneQubit,
                function(listOf("x")) { ZeroBit }
        )

        checkControlledVariableFunctionGate(
                ZeroQubit tensor OneQubit,
                ZeroQubit tensor ZeroQubit,
                function(listOf("x")) { OneBit }
        )
    }

    private fun checkControlledVariableFunctionGate(
            state: QuantumState,
            result: QuantumState,
            f: BitFunctionWithParameters) {

        registeredFactories()
                .map {
                    it
                            .getPipeline()
                            .assembly(
                                    state,
                                    ControlledFunctionGate(4, VariableBitFunction(1, "f")),
                                    variables = mapOf("f" to f)
                            )
                            .evaluate()
                }
                .forEach {
                    assertQuantumStateEquals(result, it.output)
                }
    }
}
