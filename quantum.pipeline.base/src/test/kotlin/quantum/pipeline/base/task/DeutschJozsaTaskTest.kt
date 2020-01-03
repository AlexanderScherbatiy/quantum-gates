package quantum.pipeline.base.task

import org.junit.Test
import quantum.bit.VariableBitFunction
import quantum.gate.ControlledFunctionGate
import quantum.pipeline.QuantumPipelineBuilder
import quantum.state.MinusQubit
import quantum.state.PlusQubit
import quantum.state.tensor

class DeutschJozsaTaskTest {

    @Test
    fun deutschJozsaTask() {

        val pipeline = QuantumPipelineBuilder()
                .begin()
                .state(PlusQubit tensor MinusQubit)
                .gate(ControlledFunctionGate(4, VariableBitFunction(2, "f")))
                .end()

    }
}
