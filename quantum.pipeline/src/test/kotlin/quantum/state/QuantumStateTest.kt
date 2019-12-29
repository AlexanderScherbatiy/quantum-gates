package quantum.state

import org.junit.Test
import kotlin.test.assertEquals

class QuantumStateTest {

    @Test
    fun test() {
        assertEquals(TensorState(ZeroQubit, OneQubit), ZeroQubit tensor OneQubit)
    }
}
