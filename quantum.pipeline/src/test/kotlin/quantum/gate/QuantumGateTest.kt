package quantum.gate

import org.junit.Test
import kotlin.test.assertEquals

class QuantumGateTest {

    @Test
    fun test() {
        assertEquals(
                TensorGate(IdentityGate(2), HadamardGate),
                IdentityGate(2) tensor HadamardGate)
    }
}
