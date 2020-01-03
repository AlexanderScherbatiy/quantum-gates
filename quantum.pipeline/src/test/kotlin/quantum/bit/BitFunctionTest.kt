package quantum.bit

import org.junit.Test
import kotlin.test.assertEquals

class BitFunctionTest {


    @Test
    fun testBitFunction() {
        assertEquals(
                BitFunctionWithParameters(listOf("x"), Not(VariableBit("x"))),
                function(listOf("x")) {
                    VariableBit("x").not()
                }
        )
    }
}
