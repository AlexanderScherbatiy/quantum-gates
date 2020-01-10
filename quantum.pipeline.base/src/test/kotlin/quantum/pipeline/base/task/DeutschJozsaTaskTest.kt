package quantum.pipeline.base.task

import org.junit.Test
import quantum.bit.OneBit
import quantum.bit.VariableBitFunction
import quantum.bit.function
import quantum.gate.ControlledFunctionGate
import quantum.pipeline.QuantumPipelineBuilder
import quantum.pipeline.base.quantumState
import quantum.state.MinusQubit
import quantum.state.PlusQubit
import quantum.state.tensor
import kotlin.test.assertEquals

class DeutschJozsaTaskTest {

    @Test
    fun deutschJozsaTask() {

        val pipeline = QuantumPipelineBuilder()
                .begin()
                .state(PlusQubit tensor MinusQubit)
                .gate(ControlledFunctionGate(4, VariableBitFunction(1, "f")))
                .end()

        // assembled pipeline
        val bitValues = mapOf("f" to function(listOf("x")) { OneBit })
        val assembledPipeline = pipeline.assembly(bitFunctionsMap = bitValues)

        val evaluatedPipeline = assembledPipeline.evaluate()
        val output = evaluatedPipeline.output

        assertEquals(quantumState(listOf(-0.5, 0.5, -0.5, 0.5)), output)
    }
}
