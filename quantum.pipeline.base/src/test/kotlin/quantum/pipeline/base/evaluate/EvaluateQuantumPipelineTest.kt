package quantum.pipeline.base.evaluate

import org.junit.Test
import quantum.pipeline.QuantumPipelineBuilder
import quantum.state.IdentityGate
import quantum.state.OneQubit
import quantum.state.ZeroQubit
import kotlin.test.assertEquals

class EvaluateQuantumPipelineTest {

    @Test
    fun testQubitZero() {

        val pipeline = QuantumPipelineBuilder()
                .begin()
                .state(ZeroQubit)
                .end()

        val assembledPipeline = pipeline.assembly(listOf(), listOf())
        val evaluatedPipeline = assembledPipeline.evaluate()
        val output = evaluatedPipeline.output
        assertEquals(ZeroQubit, output)
    }

    @Test
    fun testQubitOne() {

        val pipeline = QuantumPipelineBuilder()
                .begin()
                .state(OneQubit)
                .end()

        val assembledPipeline = pipeline.assembly(listOf(), listOf())
        val evaluatedPipeline = assembledPipeline.evaluate()
        val output = evaluatedPipeline.output
        assertEquals(OneQubit, output)
    }

    @Test
    fun testQubitZeroIdentityGate() {

        val pipeline = QuantumPipelineBuilder()
                .begin()
                .state(ZeroQubit)
                .gate(IdentityGate(2))
                .end()

        val assembledPipeline = pipeline.assembly(listOf(), listOf())
        val evaluatedPipeline = assembledPipeline.evaluate()
        val output = evaluatedPipeline.output
        assertEquals(ZeroQubit, output)
    }

    @Test
    fun testQubitOneIdentityGate() {

        val pipeline = QuantumPipelineBuilder()
                .begin()
                .state(OneQubit)
                .gate(IdentityGate(2))
                .end()

        val assembledPipeline = pipeline.assembly(listOf(), listOf())
        val evaluatedPipeline = assembledPipeline.evaluate()
        val output = evaluatedPipeline.output
        assertEquals(OneQubit, output)
    }
}
