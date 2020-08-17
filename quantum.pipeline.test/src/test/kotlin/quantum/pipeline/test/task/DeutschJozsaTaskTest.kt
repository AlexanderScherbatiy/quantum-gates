package quantum.pipeline.test.task

import org.junit.Test
import quantum.bit.OneBit
import quantum.bit.VariableBit
import quantum.bit.VariableBitFunction
import quantum.bit.ZeroBit
import quantum.bit.function
import quantum.bit.not
import quantum.gate.ControlledFunctionGate
import quantum.gate.HadamardGate
import quantum.gate.tensor
import quantum.pipeline.test.utils.assertQuantumStateEquals
import quantum.pipeline.test.utils.registeredFactories
import quantum.pipeline.utils.memory.toQuantumState
import quantum.state.MinusQubit
import quantum.state.OneQubit
import quantum.state.PlusQubit
import quantum.state.ZeroQubit
import quantum.state.tensor

class DeutschJozsaTaskTest {

    private val funcsAndStates = listOf(
            // f(x) = 0
            Pair(function(listOf("x")) { ZeroBit }, listOf(0.5, -0.5, 0.5, -0.5)),
            // f(x) = x
            Pair(function(listOf("x")) { VariableBit("x") }, listOf(0.5, -0.5, -0.5, 0.5)),
            // f(x) = not x
            Pair(function(listOf("x")) { VariableBit("x").not() }, listOf(-0.5, 0.5, 0.5, -0.5)),
            // f(x) = 1
            Pair(function(listOf("x")) { OneBit }, listOf(-0.5, 0.5, -0.5, 0.5))
    )

    @Test
    fun deutschJozsaTask1() {

        funcsAndStates.map { (f, expected) ->
            registeredFactories()
                    .map {
                        it
                                .getPipeline()
                                .assembly(
                                        PlusQubit tensor MinusQubit,
                                        ControlledFunctionGate(4, VariableBitFunction(1, "f")),
                                        variables = mapOf("f" to f)
                                )
                                .evaluate()
                    }
                    .forEach {
                        assertQuantumStateEquals(expected.toQuantumState(), it.output)
                    }
        }
    }

    @Test
    fun deutschJozsaTask2() {

        funcsAndStates.map { (f, expected) ->
            registeredFactories()
                    .map {
                        it
                                .getPipeline()
                                .assembly(
                                        ZeroQubit tensor OneQubit,
                                        HadamardGate tensor HadamardGate,
                                        ControlledFunctionGate(4, VariableBitFunction(1, "f")),
                                        variables = mapOf("f" to f)
                                )
                                .evaluate()
                    }
                    .forEach {
                        assertQuantumStateEquals(expected.toQuantumState(), it.output)
                    }
        }
    }
}
