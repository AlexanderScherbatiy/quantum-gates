package quantum.pipeline.utils.base.bit

import org.junit.Test
import quantum.bit.*
import quantum.pipeline.utils.base.apply
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class BitApplyTest {

    @Test
    fun test() {
        assertEquals(ZeroBit, ZeroBit.apply())
        assertEquals(OneBit, OneBit.apply())
        assertNotEquals(OneBit, ZeroBit.apply())

        assertEquals(ZeroBit, ZeroBit.apply(mapOf()))
        assertEquals(OneBit, OneBit.apply(mapOf()))
        assertNotEquals(OneBit, ZeroBit.apply(mapOf()))
    }

    @Test
    fun testNot() {
        assertEquals(OneBit, ZeroBit.not().apply(mapOf()))
        assertEquals(OneBit, ZeroBit.not().apply(mapOf()))
        assertNotEquals(OneBit, OneBit.not().apply(mapOf()))
    }

    @Test
    fun testAnd() {
        assertEquals(ZeroBit, (ZeroBit and ZeroBit).apply(mapOf()))
        assertEquals(ZeroBit, (ZeroBit and OneBit).apply(mapOf()))
        assertEquals(ZeroBit, (OneBit and ZeroBit).apply(mapOf()))
        assertEquals(OneBit, (OneBit and OneBit).apply(mapOf()))
    }

    @Test
    fun testOr() {
        assertEquals(ZeroBit, (ZeroBit or ZeroBit).apply(mapOf()))
        assertEquals(OneBit, (ZeroBit or OneBit).apply(mapOf()))
        assertEquals(OneBit, (OneBit or ZeroBit).apply(mapOf()))
        assertEquals(OneBit, (OneBit or OneBit).apply(mapOf()))
    }

    @Test
    fun testXor() {
        assertEquals(ZeroBit, (ZeroBit xor ZeroBit).apply(mapOf()))
        assertEquals(OneBit, (ZeroBit xor OneBit).apply(mapOf()))
        assertEquals(OneBit, (OneBit xor ZeroBit).apply(mapOf()))
        assertEquals(ZeroBit, (OneBit xor OneBit).apply(mapOf()))
    }

    @Test
    fun testVariable() {
        assertEquals(ZeroBit, VariableBit("x").apply(mapOf("x" to ZeroBit)))
        assertEquals(OneBit, VariableBit("y").apply(mapOf("y" to OneBit)))

        val map = mapOf("zero" to ZeroBit, "one" to OneBit)

        assertEquals(OneBit, VariableBit("zero").not().apply(map))
        assertEquals(ZeroBit, VariableBit("one").not().apply(map))

        assertEquals(OneBit, (OneBit and VariableBit("one")).apply(map))
        assertEquals(OneBit, (VariableBit("one") and OneBit).apply(map))

        assertEquals(OneBit, (ZeroBit or VariableBit("one")).apply(map))
        assertEquals(OneBit, (VariableBit("one") or ZeroBit).apply(map))

        assertEquals(OneBit, (ZeroBit xor VariableBit("one")).apply(map))
        assertEquals(OneBit, (VariableBit("one") xor ZeroBit).apply(map))
    }
}
