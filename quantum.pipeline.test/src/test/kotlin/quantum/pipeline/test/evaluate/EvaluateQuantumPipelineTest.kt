package quantum.pipeline.test.evaluate

import org.junit.Test
import quantum.pipeline.QuantumPipelineBuilder
import quantum.gate.IdentityGate
import quantum.pipeline.test.utils.registeredFactories
import quantum.state.OneQubit
import quantum.state.ZeroQubit
import kotlin.test.assertEquals

class EvaluateQuantumPipelineTest {

    @Test
    fun testZeroQubit() {
        registeredFactories()
                .map {
                    QuantumPipelineBuilder()
                            .begin()
                            .state(ZeroQubit)
                            .end()
                }
                .forEach { pipeline ->

                    val assembledPipeline = pipeline.assembly()
                    val evaluatedPipeline = assembledPipeline.evaluate()
                    val output = evaluatedPipeline.output
                    assertEquals(ZeroQubit, output)
                }
    }

    @Test
    fun testOneQubit() {
        registeredFactories()
                .map {
                    QuantumPipelineBuilder()
                            .begin()
                            .state(OneQubit)
                            .end()
                }
                .forEach { pipeline ->
                    val assembledPipeline = pipeline.assembly()
                    val evaluatedPipeline = assembledPipeline.evaluate()
                    val output = evaluatedPipeline.output
                    assertEquals(OneQubit, output)
                }
    }

    @Test
    fun testQubitZeroIdentityGate() {
        registeredFactories()
                .map {
                    QuantumPipelineBuilder()
                            .begin()
                            .state(ZeroQubit)
                            .gate(IdentityGate(2))
                            .end()
                }
                .forEach { pipeline ->

                    val assembledPipeline = pipeline.assembly()
                    val evaluatedPipeline = assembledPipeline.evaluate()
                    val output = evaluatedPipeline.output
                    assertEquals(ZeroQubit, output)
                }
    }

    @Test
    fun testQubitOneIdentityGate() {
        registeredFactories()
                .map {
                    QuantumPipelineBuilder()
                            .begin()
                            .state(OneQubit)
                            .gate(IdentityGate(2))
                            .end()
                }
                .forEach { pipeline ->

                    val assembledPipeline = pipeline.assembly()
                    val evaluatedPipeline = assembledPipeline.evaluate()
                    val output = evaluatedPipeline.output
                    assertEquals(OneQubit, output)
                }
    }
}
