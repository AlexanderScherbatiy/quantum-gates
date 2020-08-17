package quantum.pipeline.test.docs

import org.junit.Test
import quantum.gate.*
import quantum.pipeline.test.utils.assertQuantumStateEquals
import quantum.pipeline.test.utils.registeredFactories
import quantum.pipeline.utils.memory.inv_sqrt2
import quantum.pipeline.utils.memory.toQuantumState
import quantum.state.ZeroQubit
import quantum.state.tensor

class BellStateGateTest {

    @Test
    fun testBellStateGate1() {

        registeredFactories()
                .map {
                    it
                            .getPipeline()
                            .assembly(
                                    ZeroQubit tensor ZeroQubit,
                                    HadamardGate tensor IdentityGate(1),
                                    ControlledNotGate
                            )
                            .evaluate()
                }
                .forEach {
                    assertQuantumStateEquals(listOf(inv_sqrt2, 0.0, 0.0, inv_sqrt2).toQuantumState(), it.output)
                }
    }
}