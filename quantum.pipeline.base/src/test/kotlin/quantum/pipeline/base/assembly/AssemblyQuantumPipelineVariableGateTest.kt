package quantum.pipeline.base.assembly

import org.junit.Test
import quantum.pipeline.QuantumPipelineBuilder
import quantum.gate.IdentityGate
import quantum.gate.VariableGate
import quantum.state.ZeroQubit
import kotlin.test.assertEquals

class AssemblyQuantumPipelineVariableGateTest {

    @Test
    fun testVariableState() {

        val pipeline = QuantumPipelineBuilder()
                .begin()
                .state(ZeroQubit)
                .gate(VariableGate(2, "gate"))
                .end()

        // state
        val state = pipeline.state
        assertEquals(ZeroQubit, state)

        // gates
        assertEquals(1, pipeline.gates.size)
        val gate = pipeline.gates[0]
        assertEquals(VariableGate(2, "gate" ), gate)

        // assembled quantum.pipeline
        val gateValues = listOf(Pair("gate", IdentityGate(2)))
        val assembledPipeline = pipeline.assembly(listOf(), gateValues)

        // assembled state
        val assembledState = assembledPipeline.state
        assertEquals(ZeroQubit, assembledState)

        // assembled gates
        assertEquals(1, assembledPipeline.gates.size)
        val assembledGate = assembledPipeline.gates[0]
        assertEquals(IdentityGate(2), assembledGate)
    }
}
