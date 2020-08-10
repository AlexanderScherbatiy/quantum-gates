package quantum.value

import org.junit.Test
import kotlin.test.assertEquals

class ValueTest {

    @Test
    fun testConstantValue() {
        assertEquals(ConstantValue(0), constant(0));
        assertEquals(ConstantValue(1), constant(1));
        assertEquals(ConstantValue(2), constant(2));

        assertEquals(ConstantValue(-1), constant(-1));
    }

    @Test
    fun testVariableValue() {
        assertEquals(VariableValue("a"), variable("a"))
        assertEquals(VariableValue("b"), variable("b"))
    }
}