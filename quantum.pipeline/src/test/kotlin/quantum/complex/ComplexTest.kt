package quantum.complex

import org.junit.Test
import kotlin.test.assertEquals
import quantum.complex.CartesianComplex

class ComplexTest {

    @Test
    fun testCartesianComplex() {

        assertEquals(2.0, CartesianComplex(2.0, 3.0).real)
        assertEquals(3.0, CartesianComplex(2.0, 3.0).imaginary)

        assertEquals(CartesianComplex(4.0, 0.0), CartesianComplex(4.0))
        assertEquals(CartesianComplex(4.0, 5.0), CartesianComplex(4.0, 5.0))
        assertEquals(CartesianComplex(2.0, 0.0), CartesianComplex(2.0))
    }
}
