package quantum.pipeline.evaluate

import org.junit.Test
import quantum.pipeline.QuantumPipelineBuilder
import quantum.state.IdentityGate
import quantum.state.QubitOne
import quantum.state.QubitZero
import kotlin.test.assertEquals

class EvaluateQuantumPipelineTest {

    @Test
    fun testQubitZero() {

        val pipeline = QuantumPipelineBuilder()
                .begin()
                .state(QubitZero)
                .end()

        val assembledPipeline = pipeline.assembly(listOf(), listOf())
        val evaluatedPipeline = assembledPipeline.evaluate()
        val output = evaluatedPipeline.output
        assertEquals(QubitZero, output)
    }

    @Test
    fun testQubitOne() {

        val pipeline = QuantumPipelineBuilder()
                .begin()
                .state(QubitOne)
                .end()

        val assembledPipeline = pipeline.assembly(listOf(), listOf())
        val evaluatedPipeline = assembledPipeline.evaluate()
        val output = evaluatedPipeline.output
        assertEquals(QubitOne, output)
    }

    @Test
    fun testQubitZeroIdentityGate() {

        val pipeline = QuantumPipelineBuilder()
                .begin()
                .state(QubitZero)
                .gate(IdentityGate(2))
                .end()

        val assembledPipeline = pipeline.assembly(listOf(), listOf())
        val evaluatedPipeline = assembledPipeline.evaluate()
        val output = evaluatedPipeline.output
        assertEquals(QubitZero, output)
    }

    @Test
    fun testQubitOneIdentityGate() {

        val pipeline = QuantumPipelineBuilder()
                .begin()
                .state(QubitOne)
                .gate(IdentityGate(2))
                .end()

        val assembledPipeline = pipeline.assembly(listOf(), listOf())
        val evaluatedPipeline = assembledPipeline.evaluate()
        val output = evaluatedPipeline.output
        assertEquals(QubitOne, output)
    }
}
