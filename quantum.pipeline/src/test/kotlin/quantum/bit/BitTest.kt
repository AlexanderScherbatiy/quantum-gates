package quantum.bit

import org.junit.Test
import kotlin.test.assertEquals

class BitTest {

    @Test
    fun test() {
        assertEquals(Not(ZeroBit), ZeroBit.not())
        assertEquals(And(ZeroBit, OneBit), ZeroBit and OneBit)
        assertEquals(Or(ZeroBit, OneBit), ZeroBit or OneBit)
    }
}
