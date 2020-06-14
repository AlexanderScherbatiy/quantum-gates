package quantum.complex

import org.junit.Test
import kotlin.test.assertEquals

class ComplexTest {

    @Test
    fun testPolar() {

        assertEquals(PolarComplex(0.0, 0.0), polar(0.0))
        assertEquals(PolarComplex(1.0, 0.0), polar(1.0))
        assertEquals(PolarComplex(2.0, 0.0), polar(2.0))

        assertEquals(PolarComplex(1.0, 0.0), polar(1.0, 0.0))
        assertEquals(PolarComplex(1.0, 2.0), polar(1.0, 2.0))
        assertEquals(PolarComplex(2.0, 2.0), polar(2.0, 2.0))
    }

    @Test
    fun testComplex() {

        assertEquals(CartesianComplex(0.0, 0.0), complex(0.0))
        assertEquals(CartesianComplex(1.0, 0.0), complex(1.0))
        assertEquals(CartesianComplex(2.0, 0.0), complex(2.0))

        assertEquals(CartesianComplex(1.0, 0.0), complex(1.0, 0.0))
        assertEquals(CartesianComplex(1.0, 2.0), complex(1.0, 2.0))
        assertEquals(CartesianComplex(2.0, 2.0), complex(2.0, 2.0))
    }
}
