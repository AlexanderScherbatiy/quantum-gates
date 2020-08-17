package quantum.pipeline.test.evaluate

import org.junit.Test
import quantum.gate.IdentityGate
import quantum.pipeline.test.utils.assertQuantumStateEquals
import quantum.pipeline.test.utils.registeredFactories
import quantum.state.OneQubit
import quantum.state.ZeroQubit

class EvaluateQuantumPipelineTest {

    @Test
    fun testZeroQubit() {

        registeredFactories()
                .map {
                    it
                            .getPipeline()
                            .assembly(ZeroQubit)
                            .evaluate()
                }
                .forEach {
                    assertQuantumStateEquals(ZeroQubit, it.output)
                }
    }

    @Test
    fun testOneQubit() {
        registeredFactories()
                .map {
                    it
                            .getPipeline()
                            .assembly(OneQubit)
                            .evaluate()
                }
                .forEach {
                    assertQuantumStateEquals(OneQubit, it.output)
                }
    }

    @Test
    fun testQubitZeroIdentityGate() {

        registeredFactories()
                .map {
                    it
                            .getPipeline()
                            .assembly(ZeroQubit, IdentityGate(2))
                            .evaluate()
                }
                .forEach {
                    assertQuantumStateEquals(ZeroQubit, it.output)
                }
    }

    @Test
    fun testQubitOneIdentityGate() {
        registeredFactories()
                .map {
                    it
                            .getPipeline()
                            .assembly(OneQubit, IdentityGate(2))
                            .evaluate()
                }
                .forEach {
                    assertQuantumStateEquals(OneQubit, it.output)
                }
    }
}
