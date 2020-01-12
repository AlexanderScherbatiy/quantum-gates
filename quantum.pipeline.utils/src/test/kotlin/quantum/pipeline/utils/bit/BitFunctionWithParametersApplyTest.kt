package quantum.pipeline.utils.bit

import org.junit.Test
import quantum.bit.*
import quantum.pipeline.utils.apply
import kotlin.test.assertEquals

class BitFunctionWithParametersApplyTest {

    @Test
    fun test() {

        val f = function(listOf("x", "y")) {
            VariableBit("x") and VariableBit("y")
        }

        assertEquals(ZeroBit, f.apply(listOf(ZeroBit, ZeroBit)))
        assertEquals(ZeroBit, f.apply(listOf(ZeroBit, OneBit)))
        assertEquals(ZeroBit, f.apply(listOf(OneBit, ZeroBit)))
        assertEquals(OneBit, f.apply(listOf(OneBit, OneBit)))
    }
}
