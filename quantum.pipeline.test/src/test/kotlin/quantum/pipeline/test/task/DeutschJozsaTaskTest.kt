package quantum.pipeline.test.task

import org.junit.Test
import quantum.bit.*
import quantum.gate.ControlledFunctionGate
import quantum.gate.HadamardGate
import quantum.gate.tensor
import quantum.pipeline.QuantumPipeline
import quantum.pipeline.QuantumPipelineBuilder
import quantum.pipeline.test.utils.registeredFactories
import quantum.pipeline.utils.memory.toQuantumState
import quantum.state.*
import kotlin.test.assertEquals

class DeutschJozsaTaskTest {

    @Test
    fun deutschJozsaTask1() {
        registeredFactories()
                .map {
                    QuantumPipelineBuilder()
                            .begin()
                            .state(PlusQubit tensor MinusQubit)
                            .gate(ControlledFunctionGate(4, VariableBitFunction(1, "f")))
                            .end()
                }
                .forEach { pipeline ->
                    checkJozsaTask(pipeline)
                }
    }

    @Test
    fun deutschJozsaTask2() {
        registeredFactories()
                .map {
                    QuantumPipelineBuilder()
                            .begin()
                            .state(ZeroQubit tensor OneQubit)
                            .gate(HadamardGate tensor HadamardGate)
                            .gate(ControlledFunctionGate(4, VariableBitFunction(1, "f")))
                            .end()
                }
                .forEach { pipeline ->
                    checkJozsaTask(pipeline)
                }
    }

    fun checkJozsaTask(pipeline: QuantumPipeline) {

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

        for ((f, expected) in funcsAndStates) {
            val assembledPipeline = pipeline.assembly(bitFunctionsMap = mapOf("f" to f))

            val evaluatedPipeline = assembledPipeline.evaluate()
            val output = evaluatedPipeline.output

            assertEquals(expected.toQuantumState(), output)
        }
    }
}
