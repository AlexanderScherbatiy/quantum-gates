package quantum.pipeline.base.task

import org.junit.Test
import quantum.bit.*
import quantum.gate.ControlledFunctionGate
import quantum.pipeline.QuantumPipelineBuilder
import quantum.pipeline.base.quantumState
import quantum.state.MinusQubit
import quantum.state.PlusQubit
import quantum.state.tensor
import kotlin.test.assertEquals

class DeutschJozsaTaskTest {

    val funcsAndStates = listOf(
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
    fun deutschJozsaTask() {

        val pipeline = QuantumPipelineBuilder()
                .begin()
                .state(PlusQubit tensor MinusQubit)
                .gate(ControlledFunctionGate(4, VariableBitFunction(1, "f")))
                .end()


        for ((f, expected) in funcsAndStates) {
            val assembledPipeline = pipeline.assembly(bitFunctionsMap = mapOf("f" to f))

            val evaluatedPipeline = assembledPipeline.evaluate()
            val output = evaluatedPipeline.output

            assertEquals(quantumState(expected), output)
        }
    }
}
